package com.goldCityWeb.service;

import java.util.List;
import java.util.Map;

import com.goldCityWeb.domain.Commodity;
import com.goldCityWeb.domain.CommodityType;
import com.goldCityWeb.util.PageSupport;

public interface CommodityService {

	public void saveCommodity(Commodity commodity);
	
	public Commodity queryCommodityById(Integer id);
	
	public List<CommodityType> queryCommodityType();
	
	public List<Commodity> queryCommodityList(Map<String, Object> param,PageSupport ps);
}
