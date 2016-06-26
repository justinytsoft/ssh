package com.goldCityWeb.service;

import java.util.List;
import java.util.Map;

import com.goldCityWeb.domain.Commission;
import com.goldCityWeb.domain.SysAdv;
import com.goldCityWeb.util.PageSupport;

public interface SysAdvService {
	public void saveSysAdv(SysAdv sa);
	
	public SysAdv queryAysAdvById(Integer id);
	
	public List<SysAdv> querySysAdvList(Map<String, Object> param, PageSupport ps);
	
	public void deleteSysAdvById(Integer id);
	
	public Commission queryCommission();
	
	public void updateCommission(Commission commission);
}
