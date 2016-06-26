package com.goldCityWeb.domain;

import java.util.Date;

public class SignRecord {
	private Integer id;
	private Integer user_id;
	private String name;
	private Date sign_time;
	private Integer grab;//抢位卡
	private Integer lucky;//幸运卡
	private Integer exp;//经验
	private float gold_count;//金币
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Date getSign_time() {
		return sign_time;
	}
	public void setSign_time(Date sign_time) {
		this.sign_time = sign_time;
	}
	public Integer getGrab() {
		return grab;
	}
	public void setGrab(Integer grab) {
		this.grab = grab;
	}
	public Integer getLucky() {
		return lucky;
	}
	public void setLucky(Integer lucky) {
		this.lucky = lucky;
	}
	public Integer getExp() {
		return exp;
	}
	public void setExp(Integer exp) {
		this.exp = exp;
	}
	public float getGold_count() {
		return gold_count;
	}
	public void setGold_count(float gold_count) {
		this.gold_count = gold_count;
	}

}
