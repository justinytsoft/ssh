package com.goldCityWeb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.goldCityWeb.domain.Commission;
import com.goldCityWeb.domain.SysAdv;

public interface SysAdvDao {

	public void saveSysAdv(SysAdv sa);
	
	public void updateSyaAdv(SysAdv sa);
	
	public SysAdv queryAysAdvById(@Param(value="id") Integer id);
	
	public void deleteSysAdvById(@Param(value="id") Integer id);
	
	public Commission queryCommission();
	
	public void updateCommission(Commission commission);
}
