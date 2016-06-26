package com.goldCityWeb.dao;

import java.util.List;
import java.util.Map;

import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.UserDetail;

public interface IRankDao {

	List<UserDetail> queryGoldMaster(Map<String, Object> param);
	
	List<Company> queryCompanyMaster(Map<String, Object> param);
	
	UserDetail queryUserRankById(Map<String, Object> param);
	
	Company queryCompanyRankById(Map<String, Object> param);
	
	Integer querySysCount(Map<String, Object> param);

}
