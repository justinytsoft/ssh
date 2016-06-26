package com.goldCityWeb.domain;

import java.util.Date;

public class MainList {
	private Integer id;
	private Integer sub_section_id;
	private Integer remind;
	private Integer see;
	private String sub_section_name;
	private String company_name;
	private String phone_num;
	private String old_adv_img; //原图
	private String adv_img; //文字图片合并后的图片
	private Integer adv_status;
	private Long adv_sub_time;
	private Long adv_play_time;
	private Date adv_time;
	private Date adv_pre_time;
	private Integer company_id;
	private Date create_time;
	private Integer section_id;// 地区id
	private String title;
	private String sub_title;
	private Integer type;
	private Float amount;
	private Integer number;
	private Integer status;
	private Integer verify_status;
	private Integer click_count;
	private Integer gold_type;
	private Date over_time;
	private String lucky_name;
	private String logo;
	
	private String section_name; //地区名称

	
	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public Integer getVerify_status() {
		return verify_status;
	}

	public void setVerify_status(Integer verify_status) {
		this.verify_status = verify_status;
	}

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

	public Long getAdv_sub_time() {
		return adv_sub_time;
	}

	public void setAdv_sub_time(Long adv_sub_time) {
		this.adv_sub_time = adv_sub_time;
	}

	public Date getAdv_time() {
		return adv_time;
	}

	public void setAdv_time(Date adv_time) {
		this.adv_time = adv_time;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
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

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSub_section_id() {
		return sub_section_id;
	}

	public void setSub_section_id(Integer sub_section_id) {
		this.sub_section_id = sub_section_id;
	}

	public String getSub_section_name() {
		return sub_section_name;
	}

	public void setSub_section_name(String sub_section_name) {
		this.sub_section_name = sub_section_name;
	}

	public Integer getClick_count() {
		return click_count;
	}

	public void setClick_count(Integer click_count) {
		this.click_count = click_count;
	}

	public Date getOver_time() {
		return over_time;
	}

	public void setOver_time(Date over_time) {
		this.over_time = over_time;
	}

	public String getLucky_name() {
		return lucky_name;
	}

	public void setLucky_name(String lucky_name) {
		this.lucky_name = lucky_name;
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

	public String getSection_name() {
		return section_name;
	}

	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getOld_adv_img() {
		return old_adv_img;
	}

	public void setOld_adv_img(String old_adv_img) {
		this.old_adv_img = old_adv_img;
	}

	public Integer getGold_type() {
		return gold_type;
	}

	public void setGold_type(Integer gold_type) {
		this.gold_type = gold_type;
	}

	public Integer getRemind() {
		return remind;
	}

	public void setRemind(Integer remind) {
		this.remind = remind;
	}

	public Integer getSee() {
		return see;
	}

	public void setSee(Integer see) {
		this.see = see;
	}

}
