package com.goldCityWeb.domain;

import java.util.Date;

public class ChargeRecord {
	private Integer id;
	private Integer company_id;
	private Date charge_time;
	private Float charge_count;
	private Integer charge_type;
	private Integer receipt;
	private Integer status;
	private String fee_num;
	private String pay_num;
	
	private String charge_type_name;
	private String str_charge_time;
	private String name;
	private String phone;
	private String address;

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

	public Float getCharge_count() {
		return charge_count;
	}

	public void setCharge_count(Float charge_count) {
		this.charge_count = charge_count;
	}

	public Integer getCharge_type() {
		return charge_type;
	}

	public void setCharge_type(Integer charge_type) {
		this.charge_type = charge_type;
	}

	public String getCharge_type_name() {
		return charge_type_name;
	}

	public void setCharge_type_name(String charge_type_name) {
		this.charge_type_name = charge_type_name;
	}

	public Integer getReceipt() {
		return receipt;
	}

	public void setReceipt(Integer receipt) {
		this.receipt = receipt;
	}

	public String getFee_num() {
		return fee_num;
	}

	public void setFee_num(String fee_num) {
		this.fee_num = fee_num;
	}

	public String getPay_num() {
		return pay_num;
	}

	public void setPay_num(String pay_num) {
		this.pay_num = pay_num;
	}

	public String getStr_charge_time() {
		return str_charge_time;
	}

	public void setStr_charge_time(String str_charge_time) {
		this.str_charge_time = str_charge_time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
