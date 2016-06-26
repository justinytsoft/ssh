package com.goldCityWeb.domain;

import java.util.Date;

public class Adv {
	private Integer id;
	private Integer company_id;
	private String company_name;
	private String adv_img;
	private Date adv_time;
	private Date create_time;
	private Integer section_id;// 地区id
	private String title;
	private String sub_title;
	private Integer type;
	private Long adv_play_time;
	private Date adv_pre_time;
	private Integer amount;
	private Integer number;
	private Integer grab_count;
	private Float grab_gold;
	private Integer status;
	private Integer adv_status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Date getAdv_time() {
		return adv_time;
	}

	public void setAdv_time(Date adv_time) {
		this.adv_time = adv_time;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getSection_id() {
		return section_id;
	}

	public void setSection_id(Integer section_id) {
		this.section_id = section_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSub_title() {
		return sub_title;
	}

	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getAdv_play_time() {
		return adv_play_time;
	}

	public void setAdv_play_time(Long adv_play_time) {
		this.adv_play_time = adv_play_time;
	}

	public Date getAdv_pre_time() {
		return adv_pre_time;
	}

	public void setAdv_pre_time(Date adv_pre_time) {
		this.adv_pre_time = adv_pre_time;
	}

	public Integer getAdv_status() {
		return adv_status;
	}

	public void setAdv_status(Integer adv_status) {
		this.adv_status = adv_status;
	}

	public Integer getGrab_count() {
		return grab_count;
	}

	public void setGrab_count(Integer grab_count) {
		this.grab_count = grab_count;
	}

	public Float getGrab_gold() {
		return grab_gold;
	}

	public void setGrab_gold(Float grab_gold) {
		this.grab_gold = grab_gold;
	}
}
