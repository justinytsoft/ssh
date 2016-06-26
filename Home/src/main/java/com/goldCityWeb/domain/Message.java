package com.goldCityWeb.domain;

import java.util.Date;

public class Message {

	private Integer id;
	private Integer uid;//用户ID
	private Integer mid;//商家ID，如果是商家的消息，则mid为null
	private String content;//消息内容
	private Boolean looked; //是否已查看 0 否 1 是
	private Date create_time;//创建时间
	private Integer type;//消息类型 0 用户消费 1 提现申请 2 发红包 3 发红包审核
	
	private String str_create_time; //格式化后的日期 yyyy-MM-dd HH:dd
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Boolean getLooked() {
		return looked;
	}
	public void setLooked(Boolean looked) {
		this.looked = looked;
	}
	public String getStr_create_time() {
		return str_create_time;
	}
	public void setStr_create_time(String str_create_time) {
		this.str_create_time = str_create_time;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
