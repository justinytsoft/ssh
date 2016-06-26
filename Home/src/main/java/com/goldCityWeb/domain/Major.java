package com.goldCityWeb.domain;

import java.util.List;

public class Major {

	private Integer id;
	private String code;
	private String name;
	private String desc;

	private List<Position> detailList;// 与专业绑定的职位列表

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Position> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<Position> detailList) {
		this.detailList = detailList;
	}
}
