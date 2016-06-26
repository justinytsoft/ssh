package com.goldCityWeb.service;

import java.util.List;
import java.util.Map;

import com.goldCityWeb.domain.Adv;
import com.goldCityWeb.domain.AdvGrabRecord;
import com.goldCityWeb.domain.Income;
import com.goldCityWeb.domain.MainList;
import com.goldCityWeb.domain.Mould;
import com.goldCityWeb.domain.Remind;
import com.goldCityWeb.domain.SeeRecord;
import com.goldCityWeb.util.PageSupport;

public interface AdvService {

	List<MainList> queryMainList(Map<String,Object> param,PageSupport ps);

	public List<MainList> queryAdvList(Map<String,Object> param,PageSupport ps);
	
	
	public void updateAllAdvStatus();
	
	public void deleteAdvRemindById(Integer id);
	
	public void insertAdvRemind(Remind r);
	
	public void insertAdvSeeRecord(SeeRecord r);
	
	public void insertAdvGrabRecord(AdvGrabRecord r);
	
	public void updateAdvClickCount(Adv a);
	
	
	public List<Mould> queryAllMoould();
	
	public void saveAdv(MainList adv);
	
	public void updateGrab(Adv adv,Integer user_id,String grab,String lucky);
	
	public Income queryInComeByUserIdAndAdvId(Integer user_id,Integer id);
	
	public Remind queryAdvRemindByUserIdANDAdvId(Integer user_id,Integer adv_id);
	
	public SeeRecord queryAdvSeeRecordByUserIdANDAdvId(Integer user_id,Integer adv_id);
	
	public AdvGrabRecord queryAdvGrabRecordByUserIdANDAdvId(Integer user_id,Integer adv_id);
	
	public Adv queryAdvById(Integer id);
	
	public Adv queryLatestAdvByCId(Integer id);
	
	public List<MainList> queryAdvByVerify(Map<String,Object> param,PageSupport ps);
	
	public void updateAdvVerifyStatus(Integer id, Integer verify_status);

	Mould queryMouldById(Integer id);

	List<MainList> querySoonSendAdv();
}
