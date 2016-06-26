package com.goldCityWeb.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldCityWeb.dao.CompanyDao;
import com.goldCityWeb.dao.SysUsersDao;
import com.goldCityWeb.domain.ChargeRecord;
import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.CompanyCharge;
import com.goldCityWeb.domain.CompanyType;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.util.AbstractModuleSuport;
import com.goldCityWeb.util.PageSupport;

@Service
public class CompanyServiceImpl extends AbstractModuleSuport implements CompanyService{
	@Autowired
	private CompanyDao shopDao;
	
	@Autowired
	private SysUsersDao sysUsersDao;
	
	@Override
	public List<CompanyType> queryAllCompanyType() {
		// TODO Auto-generated method stub
		return shopDao.queryAllCompanyType();
	}

	
	@Override
	public void addCompany(Company company) {
		// TODO Auto-generated method stub
		if(company.getId() == null || company.getId().intValue() <= 0) 
			shopDao.addCompany(company);
		else 
			shopDao.updateCompany(company);
		
		Company c = shopDao.queryCompanyById(company.getId());
		UserDetail u = new UserDetail();
		u.setId(c.getUser_id());
		u.setNick_name(c.getCompany_name());
		sysUsersDao.updateUserDetail(u);
	}

	@Override
	public Company queryCompanyByName(String company_name) {
		// TODO Auto-generated method stub
		return shopDao.queryCompanyByName(company_name);
	}
	
	@Override
	public Company queryCompanyByUserId(Integer user_id) {
		return shopDao.queryCompanyByUserId(user_id);
	}
	
	@Override
	public Company queryCompanyById(Integer id) {
		return shopDao.queryCompanyById(id);
	}
	
	@Override
	public List<Company> queryCompanyList(PageSupport ps, Map<String, Object> param) {
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.CompanyDao.queryCompanyList", "com.goldCityWeb.dao.CompanyDao.queryCompanyList_count", param, ps);
	}
	public List<CompanyCharge> queryCompanyChargeList( Map<String, Object> param,PageSupport ps){
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.CompanyDao.queryCompanyChargeList", "com.goldCityWeb.dao.CompanyDao.queryCompanyChargeList_count", param, ps);
	}

	public List<Company> queryHotCompanyList(PageSupport ps, Map<String, Object> param) {
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.CompanyDao.queryHotCompanyList", "com.goldCityWeb.dao.CompanyDao.queryCompanyList_count", param, ps);
	}
	public void updateCompanyStatus(Company company){
		 shopDao.updateCompanyStatus(company);
	}
	
	@Override
	public List<ChargeRecord> queryChargeRecordByUser(Map<String, Object> param, PageSupport ps) {
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.CompanyDao.queryChargeRecordByUser", "com.goldCityWeb.dao.CompanyDao.queryChargeRecordByUser_count", param, ps);
	}

	@Override
	public List<ChargeRecord> queryMerChargeRecord(Map<String, Object> param) {
		return shopDao.queryMerChargeRecord(param);
	}

	@Override
	public int queryMerChargeRecordTotal(Map<String, Object> param) {
		return shopDao.queryMerChargeRecordTotal(param);
	}
	
	@Override
	public List<Company> queryStatementCompanyList(PageSupport ps, Map<String, Object> param) {
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.CompanyDao.queryStatementCompanyList", "com.goldCityWeb.dao.CompanyDao.queryStatementCompanyList_count", param, ps);
	}


	@Override
	public List<ChargeRecord> queryChargeRecord(Map<String, Object> param,
			PageSupport ps) {
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.CompanyDao.queryChargeRecord", "com.goldCityWeb.dao.CompanyDao.queryChargeRecord_count", param, ps);
	}


	@Override
	public ChargeRecord queryNewestMerChargeRecord(Integer id) {
		return shopDao.queryNewestMerChargeRecord(id);
	}

	@Override
	public void updateCompanyGrant_count(Company c) {
		shopDao.updateCompanyGrant_count(c);
	}
}
