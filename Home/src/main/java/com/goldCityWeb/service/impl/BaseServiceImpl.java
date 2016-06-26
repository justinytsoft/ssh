package com.goldCityWeb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldCityWeb.dao.BaseDao;
import com.goldCityWeb.dao.SysUsersDao;
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
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.util.AbstractModuleSuport;
import com.goldCityWeb.util.PageSupport;

@Service
public class BaseServiceImpl extends AbstractModuleSuport implements BaseService {

	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private SysUsersDao sysUsersDao;
	
	@Override
	public List<College> queryCollege(Map<String, Object> param, PageSupport ps) {
		if(ps == null){
			return this.getList("com.goldCityWeb.dao.BaseDao.queryCollege", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.BaseDao.queryCollege", "com.goldCityWeb.dao.BaseDao.queryCollege_count", param, ps);
		}
	}

	@Override
	public List<Major> queryMajor(Map<String, Object> param, PageSupport ps) {
		if(ps == null){
			return this.getList("com.goldCityWeb.dao.BaseDao.queryMajor", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.BaseDao.queryMajor", "com.goldCityWeb.dao.BaseDao.queryMajor_count", param, ps);
		}
	}

	@Override
	public List<Major> queryAllMajor(Map<String, Object> param, PageSupport ps) {
		if(ps == null){
			return this.getList("com.goldCityWeb.dao.BaseDao.queryAllMajor", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.BaseDao.queryAllMajor", "com.goldCityWeb.dao.BaseDao.queryAllMajor_count", param, ps);
		}
	}

	@Override
	public List<Career> queryAllCareer(Map<String, Object> param, PageSupport ps) {
		if(ps == null){
			return this.getList("com.goldCityWeb.dao.BaseDao.queryAllCareer", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.BaseDao.queryAllCareer", "com.goldCityWeb.dao.BaseDao.queryAllCareer_count", param, ps);
		}
	}
	
	@Override
	public List<Position> queryPositionByCity(Map<String,Object> param,PageSupport ps) {
		if(ps == null){
			return this.getList("com.goldCityWeb.dao.BaseDao.queryPositionByCity", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.BaseDao.queryPositionByCity", "com.goldCityWeb.dao.BaseDao.queryPositionByCity_count", param, ps);
		}
	}

	@Override
	public List<Industry> queryAllIndustry(Map<String, Object> param,
			PageSupport ps) {
		if(ps == null){
			return this.getList("com.goldCityWeb.dao.BaseDao.queryAllIndustry", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.BaseDao.queryAllIndustry", "com.goldCityWeb.dao.BaseDao.queryAllIndustry_count", param, ps);
		}
	}

	@Override
	public List<Career> queryCareerByIndustryId(Map<String, Object> param,
			PageSupport ps) {
		if(ps == null){
			return this.getList("com.goldCityWeb.dao.BaseDao.queryCareerByIndustryId", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.BaseDao.queryCareerByIndustryId", "com.goldCityWeb.dao.BaseDao.queryCareerByIndustryId_count", param, ps);
		}
	}

	@Override
	public List<Province> queryAllProvince(Map<String, Object> param,
			PageSupport ps) {
		if(ps == null){
			return this.getList("com.goldCityWeb.dao.BaseDao.queryAllProvince", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.BaseDao.queryAllProvince", "com.goldCityWeb.dao.BaseDao.queryAllProvince_count", param, ps);
		}
	}

	@Override
	public List<City> queryCityByProvince(Map<String, Object> param,
			PageSupport ps) {
		if(ps == null){
			return this.getList("com.goldCityWeb.dao.BaseDao.queryCityByProvince", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.BaseDao.queryCityByProvince", "com.goldCityWeb.dao.BaseDao.queryCityByProvince_count", param, ps);
		}
	}

	@Override
	public List<OldLevel> queryAllOldLevel() {
		return baseDao.queryAllOldLevel();
	}

	@Override
	public College queryCollegeById(Integer id) {
		return baseDao.queryCollegeById(id);
	}

	@Override
	public Major queryMajorById(Integer id) {
		return baseDao.queryMajorById(id);
	}

	@Override
	public List<Section> querySection() {
		return baseDao.querySection();
	}
	
	@Override
	public List<HeadList> queryHeadList() {
		return baseDao.queryHeadList();
	}

	@Override
	public void saveLocation(Location l) {
		baseDao.saveLocation(l);
	}

	@Override
	public void saveChargeRecord(ChargeRecord cr) {
		baseDao.saveChargeRecord(cr);
		
	}

	@Override
	public void updateChargeRecordStatus(ChargeRecord cr) {
		baseDao.updateChargeRecordStatus(cr);
		
	}

	@Override
	public void saveFeeRecord(FeeRecord f) {
		baseDao.saveFeeRecord(f);
		if(f.getPay_status()==1){
			baseDao.updateUserFreezeGold(f.getUid(), f.getUse_gold());
		} else {
			baseDao.updateUserFreezeGold(f.getUid(), f.getUse_gold());
			baseDao.updateUserGold(f.getUid(), f.getUse_gold());
			sysUsersDao.updateUserExperience(f.getUid(), f.getFee_price().intValue());
		}
	}
	
	@Override
	public void updateFeeRecordPayStatus(FeeRecord f) {
		FeeRecord fr = baseDao.queryFeeRecordByFeeNumber(f.getFee_number());
		baseDao.updateUserGold(fr.getUid(),fr.getUse_gold());
		baseDao.updateFeeRecordPayStatus(f);
		sysUsersDao.updateUserExperience(fr.getUid(), fr.getFee_price().intValue());
	}

	@Override
	public List<ChargeType> queryChargeType() {
		return baseDao.queryChargeType();
	}

	@Override
	public ChargeRecord queryChargeRecordByFeeNum(String fee_num) {
		return baseDao.queryChargeRecordByFeeNum(fee_num);
	}

	@Override
	public void updateGoldCount(Integer user_id, Float gold) {
		baseDao.updateGoldCount(user_id, gold);
	}

	@Override
	public Level queryLevelByLevel(Integer l) {
		return baseDao.queryLevelByLevel(l);
	}

	@Override
	public City queryCityById(Integer city_id) {
		return baseDao.queryCityById(city_id);
	}

	@Override
	public Position queryPositionById(Integer position_id) {
		return baseDao.queryPositionById(position_id);
	}

}
