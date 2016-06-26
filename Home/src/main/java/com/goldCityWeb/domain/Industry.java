package com.goldCityWeb.domain;

import java.util.List;

public class Industry {

	private Integer id;
	private String name;
	private String desc;

	private List<Career> detailList;

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Career> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<Career> detailList) {
		this.detailList = detailList;
	}

}
