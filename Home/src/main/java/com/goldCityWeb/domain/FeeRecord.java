package com.goldCityWeb.domain;

import java.util.Date;

public class FeeRecord {

	private Integer id;
	private Integer uid; //用户ID
	private Integer mid; //商家ID
	private Integer fee_type;
	private String fee_number; //消费单号
	private String pay_number; //消费单号
	private String merchant_name; //商家名称
	private Float fee_price; //消费金额
	private Float use_gold; //使用黄金币
	private Float real_fee; //实际消费金额
	private Integer status; //是否结算 0 未结算 1 申请结算 2 已结算
	private Integer pay_status; // 1 未付款 2 已付款
	private Date create_time; //创建时间
	private Integer t_type; //提现类型 1 黄金币 2 人民币
	
	private String fee_user; //消费用户
	private String str_create_time;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public Float getFee_price() {
		return fee_price;
	}
	public void setFee_price(Float fee_price) {
		this.fee_price = fee_price;
	}
	public Float getUse_gold() {
		return use_gold;
	}
	public void setUse_gold(Float use_gold) {
		this.use_gold = use_gold;
	}
	public Float getReal_fee() {
		return real_fee;
	}
	public void setReal_fee(Float real_fee) {
		this.real_fee = real_fee;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getFee_number() {
		return fee_number;
	}
	public void setFee_number(String fee_number) {
		this.fee_number = fee_number;
	}
	public String getFee_user() {
		return fee_user;
	}
	public void setFee_user(String fee_user) {
		this.fee_user = fee_user;
	}
	public String getStr_create_time() {
		return str_create_time;
	}
	public void setStr_create_time(String str_create_time) {
		this.str_create_time = str_create_time;
	}
	public Integer getPay_status() {
		return pay_status;
	}
	public void setPay_status(Integer pay_status) {
		this.pay_status = pay_status;
	}
	public Integer getFee_type() {
		return fee_type;
	}
	public void setFee_type(Integer fee_type) {
		this.fee_type = fee_type;
	}
	public String getPay_number() {
		return pay_number;
	}
	public void setPay_number(String pay_number) {
		this.pay_number = pay_number;
	}
	public Integer getT_type() {
		return t_type;
	}
	public void setT_type(Integer t_type) {
		this.t_type = t_type;
	}
}
