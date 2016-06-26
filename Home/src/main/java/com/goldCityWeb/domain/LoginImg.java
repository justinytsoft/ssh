package com.goldCityWeb.domain;

import java.util.Date;

public class LoginImg {
	private Integer id;
	private String path;//图片路径
	private Integer sort_num;//排序优先级
	private Date create_time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getSort_num() {
		return sort_num;
	}
	public void setSort_num(Integer sort_num) {
		this.sort_num = sort_num;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

}
