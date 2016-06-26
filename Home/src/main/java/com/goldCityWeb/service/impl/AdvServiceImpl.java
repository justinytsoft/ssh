package com.goldCityWeb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldCityWeb.dao.AdvDao;
import com.goldCityWeb.dao.BaseDao;
import com.goldCityWeb.dao.SysUsersDao;
import com.goldCityWeb.domain.Adv;
import com.goldCityWeb.domain.AdvGrabRecord;
import com.goldCityWeb.domain.Income;
import com.goldCityWeb.domain.MainList;
import com.goldCityWeb.domain.Mould;
import com.goldCityWeb.domain.RedBag;
import com.goldCityWeb.domain.Remind;
import com.goldCityWeb.domain.SeeRecord;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.service.AdvService;
import com.goldCityWeb.util.AbstractModuleSuport;
import com.goldCityWeb.util.HongBaoUtils;
import com.goldCityWeb.util.PageSupport;
@Service
public class AdvServiceImpl extends AbstractModuleSuport implements AdvService {
	@Autowired
	private AdvDao advDao;
	
	@Autowired
	private SysUsersDao sysUsersDao;
	
	@Autowired
	private BaseDao baseDao;

	
	@Override
	public List<MainList> queryMainList(Map<String, Object> param,
			PageSupport ps) {
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.AdvDao.queryMainList", "com.goldCityWeb.dao.AdvDao.queryMainList_count", param, ps);
	}
	
	@Override
	public List<MainList> queryAdvList(Map<String,Object> param,PageSupport ps) {
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.AdvDao.queryAdvList", "com.goldCityWeb.dao.AdvDao.queryAdvList_count", param, ps);
	}

	@Override
	public void saveAdv(MainList adv){
		advDao.insertAdv(adv);
		float[] f = HongBaoUtils.generateHongbao(adv.getAmount().intValue());
		java.text.DecimalFormat  df   =new   java.text.DecimalFormat("#.00");
		List<RedBag> rb = new ArrayList<RedBag>();
		for(int i = 0; i < f.length; i++){
			RedBag r = new RedBag();
			if(i==0){
				r.setAdv_id(adv.getId());
				r.setBig(1);
				r.setPrice(Float.valueOf(df.format(f[i])));
				rb.add(r);
				
			} else {
				r.setAdv_id(adv.getId());
				r.setBig(0);
				r.setPrice(Float.valueOf(df.format(f[i])));
				rb.add(r);
			}
		}
		advDao.insertHongBao(rb);
	}

	@Override
	public Adv queryAdvById(Integer id) {
		return advDao.queryAdvById(id);
	}
	
	@Override
	public List<MainList> queryAdvByVerify(Map<String,Object> param,PageSupport ps) {
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.AdvDao.queryAdvByVerify", "com.goldCityWeb.dao.AdvDao.queryAdvByVerify_count", param, ps);
	}
	
	@Override
	public void updateAdvVerifyStatus(Integer id, Integer verify_status) {
		advDao.updateAdvVerifyStatus(id, verify_status);
	}

	@Override
	public Adv queryLatestAdvByCId(Integer id) {
		return advDao.queryLatestAdvByCId(id);
	}

	@Override
	public List<Mould> queryAllMoould() {
		return advDao.queryAllMoould();
	}

	@Override
	public Income queryInComeByUserIdAndAdvId(Integer user_id, Integer id) {
		return advDao.queryInComeByUserIdAndAdvId(user_id,id);
	}

	@Override
	public void updateGrab(Adv adv, Integer user_id,String grab,String lucky) {
		Random random = new Random();
		java.text.DecimalFormat  df   =new   java.text.DecimalFormat("#.00"); 
		Float gold = Float.valueOf(df.format(random.nextFloat()  * (adv.getAmount()-adv.getGrab_gold()) * 0.08 * 0.50));
		Income i = new Income();
		i.setUid(user_id);
		i.setMid(adv.getCompany_id());
		i.setMerchant_name(adv.getCompany_name());
		i.setDesc("获得黄金币"+gold);
		i.setGold_count(gold);
		i.setAdv_id(adv.getId());
		if((!StringUtils.isBlank(grab) &&  !grab.equals("0")) || (!StringUtils.isBlank(lucky) && !lucky.equals("0"))){
			i.setUse_card(1);
		} else {
			i.setUse_card(0);
		}
		advDao.insertInCome(i);
		adv.setGrab_gold(gold);
		UserDetail su = new UserDetail();
		su.setId(user_id);
		su.setGrab(StringUtils.isBlank(grab)? 0 : Integer.valueOf(grab));
		su.setLucky(StringUtils.isBlank(lucky) ? 0:Integer.valueOf(lucky));
		sysUsersDao.updateUserItem(su);
		baseDao.updateGoldCount(su.getId(),gold);
		//更新抢购数量
		advDao.updateAdvGrab(adv);
		//更新状态
		advDao.updateAdvStatus(adv);
	}

	@Override
	public Mould queryMouldById(Integer id) {
		return advDao.queryMouldById(id);
	}

	@Override
	public void updateAllAdvStatus() {
		advDao.updateAllAdvStatus();
	}

	@Override
	public void updateAdvClickCount(Adv a) {
		advDao.updateAdvClickCount(a);
	}

	@Override
	public Remind queryAdvRemindByUserIdANDAdvId(Integer user_id, Integer adv_id) {
		return advDao.queryAdvRemindByUserIdANDAdvId(user_id,adv_id);
	}

	@Override
	public void deleteAdvRemindById(Integer id) {
		advDao.deleteAdvRemindById(id);
	}

	@Override
	public void insertAdvRemind(Remind r) {
		advDao.insertAdvRemind(r);
	}

	@Override
	public SeeRecord queryAdvSeeRecordByUserIdANDAdvId(Integer user_id,
			Integer adv_id) {
		return advDao.queryAdvSeeRecordByUserIdANDAdvId(user_id,adv_id);
	}

	@Override
	public void insertAdvSeeRecord(SeeRecord r) {
		advDao.insertAdvSeeRecord(r);
		
	}

	@Override
	public AdvGrabRecord queryAdvGrabRecordByUserIdANDAdvId(Integer user_id,
			Integer adv_id) {
		return advDao.queryAdvGrabRecordByUserIdANDAdvId(user_id,adv_id);
	}

	@Override
	public void insertAdvGrabRecord(AdvGrabRecord r) {
		advDao.insertAdvGrabRecord(r);
	}

	@Override
	public List<MainList> querySoonSendAdv() {
		return advDao.querySoonSendAdv();
	}


}
