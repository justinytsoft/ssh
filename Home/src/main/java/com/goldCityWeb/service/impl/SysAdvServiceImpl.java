package com.goldCityWeb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldCityWeb.dao.SysAdvDao;
import com.goldCityWeb.domain.Commission;
import com.goldCityWeb.domain.SysAdv;
import com.goldCityWeb.service.SysAdvService;
import com.goldCityWeb.util.AbstractModuleSuport;
import com.goldCityWeb.util.PageSupport;

@Service(value="sysAdvService")
public class SysAdvServiceImpl extends AbstractModuleSuport implements SysAdvService {
	@Autowired
	private SysAdvDao sysAdvDao;
	
	@Override
	public void saveSysAdv(SysAdv sa) {
		// TODO Auto-generated method stub
		if(sa.getId()!=null && sa.getId().intValue() > 0) {
			sysAdvDao.updateSyaAdv(sa);
		} else {
			sysAdvDao.saveSysAdv(sa);
		}
	}

	public Commission queryCommission(){
		return sysAdvDao.queryCommission();
	}
	
	public void updateCommission(Commission commission){
		sysAdvDao.updateCommission(commission);
	}
	@Override
	public SysAdv queryAysAdvById(Integer id) {
		// TODO Auto-generated method stub
		return sysAdvDao.queryAysAdvById(id);
	}

	@Override
	public List<SysAdv> querySysAdvList(Map<String, Object> param, PageSupport ps) {
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysAdvDao.querySysAdvList", "com.goldCityWeb.dao.SysAdvDao.querySysAdvList_count", param, ps);
	}
	
	@Override
	public void deleteSysAdvById(Integer id) {
		sysAdvDao.deleteSysAdvById(id);
	}
}
