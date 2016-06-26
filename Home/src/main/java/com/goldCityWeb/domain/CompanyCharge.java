package com.goldCityWeb.domain;

import java.util.Date;

public class CompanyCharge {
	private Integer id;
	private Integer company_id;
	private String city_name;
	private Date charge_time;
	private String company_name;
	private Integer charge_count;
	private Integer charge_type;//充值类型 1,支付宝,2微信
	private String name;//充值名称
	private int receipt;//是否需要发票 0,1
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public Date getCharge_time() {
		return charge_time;
	}
	public void setCharge_time(Date charge_time) {
		this.charge_time = charge_time;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public Integer getCharge_count() {
		return charge_count;
	}
	public void setCharge_count(Integer charge_count) {
		this.charge_count = charge_count;
	}
	public Integer getCharge_type() {
		return charge_type;
	}
	public void setCharge_type(Integer charge_type) {
		this.charge_type = charge_type;
	}
	public int getReceipt() {
		return receipt;
	}
	public void setReceipt(int receipt) {
		this.receipt = receipt;
	}

}
