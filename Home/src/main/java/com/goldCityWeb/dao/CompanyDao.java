package com.goldCityWeb.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.goldCityWeb.domain.ChargeRecord;
import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.CompanyType;

public interface CompanyDao {
	public List<CompanyType> queryAllCompanyType();
	
	public void addCompany(Company company);
	
	public Company queryCompanyByName(@Param(value="company_name") String company_name);
	
	public Company queryCompanyByUserId(@Param(value="user_id") Integer user_id);
	
	public Company queryCompanyById(@Param(value="id") Integer id);
	
	public void updateCompanyStatus(Company company) ;

	public void updateCompany(Company company);

	public List<ChargeRecord> queryMerChargeRecord(@Param("param") Map<String, Object> param);

	public int queryMerChargeRecordTotal(@Param("param") Map<String, Object> param);

	public ChargeRecord queryNewestMerChargeRecord(@Param("id") Integer id);

	public void updateCompanyGrant_count(Company c);
	
}
