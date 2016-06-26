package com.goldCityWeb.domain;

import java.util.Date;

public class AdvApp {

	private Integer id;
	private String company_name;
	private String adv_img;
	private Integer adv_status;
	private Integer use_card;
	private Long adv_sub_time;
	private Long adv_play_time;
	private Date adv_pre_time;
	private Date adv_time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getAdv_img() {
		return adv_img;
	}
	public void setAdv_img(String adv_img) {
		this.adv_img = adv_img;
	}
	public Integer getAdv_status() {
		return adv_status;
	}
	public void setAdv_status(Integer adv_status) {
		this.adv_status = adv_status;
	}
	public Date getAdv_time() {
		return adv_time;
	}
	public void setAdv_time(Date adv_time) {
		this.adv_time = adv_time;
	}
	public Long getAdv_sub_time() {
		return adv_sub_time;
	}
	public void setAdv_sub_time(Long adv_sub_time) {
		this.adv_sub_time = adv_sub_time;
	}
	public Date getAdv_pre_time() {
		return adv_pre_time;
	}
	public void setAdv_pre_time(Date adv_pre_time) {
		this.adv_pre_time = adv_pre_time;
	}
	public Long getAdv_play_time() {
		return adv_play_time;
	}
	public void setAdv_play_time(Long adv_play_time) {
		this.adv_play_time = adv_play_time;
	}
	public Integer getUse_card() {
		return use_card;
	}
	public void setUse_card(Integer use_card) {
		this.use_card = use_card;
	}
	
}
