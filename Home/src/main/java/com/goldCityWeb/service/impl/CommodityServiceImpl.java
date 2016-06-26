package com.goldCityWeb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldCityWeb.dao.CommodityDao;
import com.goldCityWeb.domain.Commodity;
import com.goldCityWeb.domain.CommodityType;
import com.goldCityWeb.service.CommodityService;
import com.goldCityWeb.util.AbstractModuleSuport;
import com.goldCityWeb.util.PageSupport;

@Service
public class CommodityServiceImpl extends AbstractModuleSuport implements CommodityService {
	@Autowired
	private CommodityDao commodityDao;
	
	
	@Override
	public void saveCommodity(Commodity commodity) {
		// TODO Auto-generated method stub
		if(commodity.getId() != null && commodity.getId().intValue() > 0) {
			commodityDao.updateCommodity(commodity);
		} else {
			commodityDao.saveCommodity(commodity);
		}
	}
	
	@Override
	public Commodity queryCommodityById(Integer id) {
		return commodityDao.queryCommodityById(id);
	}
	
	@Override
	public List<CommodityType> queryCommodityType() {
		return commodityDao.queryCommodityType();
	}
	
	@Override
	public List<Commodity> queryCommodityList(Map<String, Object> param,PageSupport ps) {
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.CommodityDao.queryCommodityList", "com.goldCityWeb.dao.CommodityDao.queryCommodityList_count", param, ps);
	}
}
