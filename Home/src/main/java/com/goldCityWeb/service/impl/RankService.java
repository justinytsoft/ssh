package com.goldCityWeb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldCityWeb.dao.IRankDao;
import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.service.IRankService;

@Service
public class RankService implements IRankService {

	@Autowired
	private IRankDao rankDao;
	
	@Override
	public List<UserDetail> queryGoldMaster(Map<String, Object> param) {
		return rankDao.queryGoldMaster(param);
	}
	
	public List<Company> queryCompanyMaster(Map<String, Object> param){
		return rankDao.queryCompanyMaster(param);
	}

	@Override
	public UserDetail queryUserRankById(Map<String, Object> param) {
		return rankDao.queryUserRankById(param);
	}

	@Override
	public Company queryCompanyRankById(Map<String, Object> param) {
		return rankDao.queryCompanyRankById(param);
	}

	@Override
	public Integer querySysCount(Map<String, Object> param) {
		return rankDao.querySysCount(param);
	}

}
