package com.goldCityWeb.domain;

import java.util.Date;

public class Remind {

	private Integer id;
	private Integer adv_id;
	private Integer user_id;
	private String remind_num;
	private Date create_time;
	private Integer send;
	
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
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getRemind_num() {
		return remind_num;
	}
	public void setRemind_num(String remind_num) {
		this.remind_num = remind_num;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Integer getSend() {
		return send;
	}
	public void setSend(Integer send) {
		this.send = send;
	}
	
}
