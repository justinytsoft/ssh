package com.goldCityWeb.dao;

import java.util.List;

import com.goldCityWeb.domain.Commodity;
import com.goldCityWeb.domain.CommodityType;

public interface CommodityDao {
	public void saveCommodity(Commodity commodity);
	
	public void updateCommodity(Commodity commodity);
	
	public Commodity queryCommodityById(Integer id);
	
	public List<CommodityType> queryCommodityType();
	
}
