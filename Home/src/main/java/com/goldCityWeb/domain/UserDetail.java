package com.goldCityWeb.domain;

import java.util.Date;

public class UserDetail {

	private Integer id;
	private Integer type; //用户类型
	private Integer rank;
	private String nick_name; //昵称
	private Integer level; //等级
	private Integer experience; //经验
	private Float gold_count; //金币数量
	private String head; //头像
	private Integer age; //年龄
	private Integer sex; //性别
	private String address; //地址
	private String work; //工作
	private Float income; //收入
	private Integer sign_count; //签到次数
	private Integer sign_status; //
	private Integer enabled;
	private Integer verify_status;
	private Integer grab;
	private Integer lucky;
	private Date share_time;
	private String token;
	private Integer token_type;
	private String session_id;
	
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getExperience() {
		return experience;
	}
	public void setExperience(Integer experience) {
		this.experience = experience;
	}
	public Float getGold_count() {
		return gold_count;
	}
	public void setGold_count(Float gold_count) {
		this.gold_count = gold_count;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public Float getIncome() {
		return income;
	}
	public void setIncome(Float income) {
		this.income = income;
	}
	public Integer getSign_count() {
		return sign_count;
	}
	public void setSign_count(Integer sign_count) {
		this.sign_count = sign_count;
	}
	public Integer getSign_status() {
		return sign_status;
	}
	public void setSign_status(Integer sign_status) {
		this.sign_status = sign_status;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getVerify_status() {
		return verify_status;
	}
	public void setVerify_status(Integer verify_status) {
		this.verify_status = verify_status;
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
	public Date getShare_time() {
		return share_time;
	}
	public void setShare_time(Date share_time) {
		this.share_time = share_time;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getToken_type() {
		return token_type;
	}
	public void setToken_type(Integer token_type) {
		this.token_type = token_type;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

}
