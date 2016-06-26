package com.goldCityWeb.domain;

import java.util.Date;

public class RobotGold {

	private Integer id;
	private Integer adv_id;
	private Date grab_time;
	private float gold_count;
	private Integer company_id;
	private String company_name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAdv_id() {
		return adv_id;
	}
	public void setAdv_id(Integer adv_id) {
		this.adv_id = adv_id;
	}
	public Date getGrab_time() {
		return grab_time;
	}
	public void setGrab_time(Date grab_time) {
		this.grab_time = grab_time;
	}
	public float getGold_count() {
		return gold_count;
	}
	public void setGold_count(float gold_count) {
		this.gold_count = gold_count;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
}
