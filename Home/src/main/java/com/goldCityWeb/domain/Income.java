package com.goldCityWeb.domain;

import java.util.Date;

public class Income {

	private Integer id;
	private Integer uid; //用户ID
	private Integer mid; //商家ID
	private Integer adv_id; //商家ID
	private Integer use_card; //use_card
	private String merchant_name; //商家名称
	private String desc; //收益内容
	private String logo;
	private Float gold_count;
	private Date create_time; //创建时间
	
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Integer getAdv_id() {
		return adv_id;
	}
	public void setAdv_id(Integer adv_id) {
		this.adv_id = adv_id;
	}
	public Float getGold_count() {
		return gold_count;
	}
	public void setGold_count(Float gold_count) {
		this.gold_count = gold_count;
	}
	public Integer getUse_card() {
		return use_card;
	}
	public void setUse_card(Integer use_card) {
		this.use_card = use_card;
	}
}
