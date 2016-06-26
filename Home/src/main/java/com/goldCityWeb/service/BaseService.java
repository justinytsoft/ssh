package com.goldCityWeb.service;

import java.util.List;
import java.util.Map;

import com.goldCityWeb.domain.Career;
import com.goldCityWeb.domain.ChargeRecord;
import com.goldCityWeb.domain.ChargeType;
import com.goldCityWeb.domain.City;
import com.goldCityWeb.domain.College;
import com.goldCityWeb.domain.FeeRecord;
import com.goldCityWeb.domain.HeadList;
import com.goldCityWeb.domain.Industry;
import com.goldCityWeb.domain.Level;
import com.goldCityWeb.domain.Location;
import com.goldCityWeb.domain.Major;
import com.goldCityWeb.domain.OldLevel;
import com.goldCityWeb.domain.Position;
import com.goldCityWeb.domain.Province;
import com.goldCityWeb.domain.Section;
import com.goldCityWeb.util.PageSupport;

public interface BaseService {

	public List<College> queryCollege(Map<String,Object> param,PageSupport ps);
	
	public List<Major> queryMajor(Map<String,Object> param,PageSupport ps);
	
	public List<Major> queryAllMajor(Map<String,Object> param,PageSupport ps);
	
	public List<Career> queryAllCareer(Map<String,Object> param,PageSupport ps);
	
	public List<Position> queryPositionByCity(Map<String,Object> param,PageSupport ps);
	
	public List<Industry> queryAllIndustry(Map<String,Object> param,PageSupport ps);
	
	public List<Career> queryCareerByIndustryId(Map<String,Object> param,PageSupport ps);
	
	public List<Province> queryAllProvince(Map<String,Object> param,PageSupport ps);
	
	public List<City> queryCityByProvince(Map<String,Object> param,PageSupport ps);
	
	public List<OldLevel> queryAllOldLevel();
	
	public Level queryLevelByLevel(Integer l);
	
	public List<Section> querySection();
	
	public List<HeadList> queryHeadList();
	
	public List<ChargeType> queryChargeType();
	
	public void saveLocation(Location l);
	
	public void saveChargeRecord(ChargeRecord cr);
	
	public void saveFeeRecord(FeeRecord f);
	
	public void updateFeeRecordPayStatus(FeeRecord f);
	
	public void updateChargeRecordStatus(ChargeRecord cr);
	
	public ChargeRecord queryChargeRecordByFeeNum(String feeNum);
	
	public void updateGoldCount(Integer user_id,Float gold);
	
	public College queryCollegeById(Integer id);
	
	public Major queryMajorById(Integer id);

	public City queryCityById(Integer city_id);

	public Position queryPositionById(Integer position_id);
	
}
