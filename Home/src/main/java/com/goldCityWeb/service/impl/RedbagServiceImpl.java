package com.goldCityWeb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldCityWeb.dao.IRedbagDao;
import com.goldCityWeb.domain.MainList;
import com.goldCityWeb.service.IRedbagService;

@Service
public class RedbagServiceImpl implements IRedbagService {

	@Autowired
	private IRedbagDao redbagDao;

	@Override
	public List<MainList> queryAdv(Map<String, Object> param) {
		return redbagDao.queryAdv(param);
	}

	@Override
	public int queryAdvTotal(Map<String, Object> param) {
		return redbagDao.queryAdvTotal(param);
	}

	@Override
	public MainList queryAdvById(Integer id) {
		return redbagDao.queryAdvById(id);
	}
	
	
}
