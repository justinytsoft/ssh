package com.goldCityWeb.service;

import java.util.List;
import java.util.Map;

import com.goldCityWeb.domain.ChargeRecord;
import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.CompanyCharge;
import com.goldCityWeb.domain.CompanyType;
import com.goldCityWeb.util.PageSupport;

public interface CompanyService {
	public List<CompanyType> queryAllCompanyType();

	public void addCompany(Company company);

	public Company queryCompanyByName(String company_name);

	public Company queryCompanyByUserId(Integer user_id);
	
	public Company queryCompanyById(Integer id);
	
	public List<Company> queryCompanyList(PageSupport ps, Map<String, Object> param);
	
	public List<Company> queryHotCompanyList(PageSupport ps, Map<String, Object> param);
	
	public void updateCompanyStatus(Company company);
	
	public List<CompanyCharge> queryCompanyChargeList( Map<String, Object> param,PageSupport ps);

	public List<ChargeRecord> queryChargeRecordByUser(Map<String, Object> param, PageSupport ps);
	
	public List<ChargeRecord> queryChargeRecord(Map<String, Object> param, PageSupport ps);

	public List<ChargeRecord> queryMerChargeRecord(Map<String, Object> param);

	public int queryMerChargeRecordTotal(Map<String, Object> param);

	/**
	 * 申请结算的商家
	 * @param ps
	 * @param param
	 * @return
	 */
	public List<Company> queryStatementCompanyList(PageSupport ps,Map<String, Object> param);

	/**
	 * 查询最新的未付款的充值记录
	 * @param id
	 * @return
	 */
	public ChargeRecord queryNewestMerChargeRecord(Integer id);

	/**
	 * 更新公司已发放金额
	 * @param f
	 */
	public void updateCompanyGrant_count(Company c);
}
