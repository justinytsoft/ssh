package com.goldCityWeb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.goldCityWeb.domain.Adv;
import com.goldCityWeb.domain.AdvGrabRecord;
import com.goldCityWeb.domain.Income;
import com.goldCityWeb.domain.MainList;
import com.goldCityWeb.domain.Mould;
import com.goldCityWeb.domain.RedBag;
import com.goldCityWeb.domain.Remind;
import com.goldCityWeb.domain.SeeRecord;

public interface AdvDao {
	public void insertAdv(MainList adv);
	
	public void updateAdvStatus(Adv adv);
	
	public void insertHongBao(List<RedBag> adv);
	
	public void insertInCome(Income i);
	
	public void updateAdvGrab(Adv adv);
	
	public void insertAdvSeeRecord(SeeRecord sr);
	
	public void updateAllAdvStatus();
	
	public void updateAdvClickCount(Adv a);
	
	public void updateAdvVerifyStatus(@Param(value="id") Integer id, @Param(value="verify_status") Integer verify_status);
	
	public Adv queryAdvById(Integer id);
	
	public Adv queryLatestAdvByCId(Integer id);
	
	public List<Mould> queryAllMoould();
	
	public Income queryInComeByUserIdAndAdvId(@Param(value="user_id")Integer user_id,@Param(value="adv_id")Integer id);
	
	
	public SeeRecord queryAdvSeeRecordByUserIdANDAdvId(@Param(value="user_id")Integer user_id,@Param(value="adv_id")Integer adv_id);
	
	public AdvGrabRecord queryAdvGrabRecordByUserIdANDAdvId(@Param(value="user_id")Integer user_id,@Param(value="adv_id")Integer adv_id);
	
	public void insertAdvGrabRecord(AdvGrabRecord r);
	
	public Remind queryAdvRemindByUserIdANDAdvId(@Param(value="user_id")Integer user_id,@Param(value="adv_id")Integer adv_id);
	
	public void deleteAdvRemindById(Integer id);
	
	public void insertAdvRemind(Remind r);

	public Mould queryMouldById(@Param(value="id") Integer id);

	public List<MainList> querySoonSendAdv();
	
	
}
