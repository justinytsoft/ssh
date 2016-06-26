package com.goldCityWeb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.goldCityWeb.domain.ChargeRecord;
import com.goldCityWeb.domain.ChargeType;
import com.goldCityWeb.domain.City;
import com.goldCityWeb.domain.College;
import com.goldCityWeb.domain.FeeRecord;
import com.goldCityWeb.domain.HeadList;
import com.goldCityWeb.domain.Level;
import com.goldCityWeb.domain.Location;
import com.goldCityWeb.domain.Major;
import com.goldCityWeb.domain.OldLevel;
import com.goldCityWeb.domain.Position;
import com.goldCityWeb.domain.Section;

public interface BaseDao {

	public List<OldLevel> queryAllOldLevel();
	
	public College queryCollegeById(Integer id);
	
	public Major queryMajorById(Integer id);
	
	public List<Section> querySection();
	
	public List<HeadList> queryHeadList();
	
	public void saveLocation(Location l);
	
	public void saveChargeRecord(ChargeRecord cr);
	
	public void updateChargeRecordStatus(ChargeRecord cr);
	
	public void saveFeeRecord(FeeRecord f);
	
	public void updateFeeRecordPayStatus(FeeRecord f);
	
	public Level queryLevelByLevel(Integer l);
	
	public void updateGoldCount(@Param(value="user_id")Integer user_id,@Param(value="gold")Float gold);
	
	public void updateUserGold(@Param(value="user_id")Integer user_id,@Param(value="gold")Float gold);
	
	public void updateUserFreezeGold(@Param(value="user_id")Integer user_id,@Param(value="gold")Float gold);
	
	public FeeRecord queryFeeRecordByFeeNumber(String fee_number);

	public List<ChargeType> queryChargeType();
	
	public ChargeRecord queryChargeRecordByFeeNum(String fee_num);

	public City queryCityById(@Param("id") Integer city_id);

	public Position queryPositionById(@Param("id") Integer position_id);
}
