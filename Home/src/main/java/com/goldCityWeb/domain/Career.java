package com.goldCityWeb.domain;

import java.util.List;

public class Career {

	private Integer id;
	private Integer industry_id;
	private String name;
	private String desc;

	private List<Position> detailList;

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

	public Integer getIndustry_id() {
		return industry_id;
	}

	public void setIndustry_id(Integer industry_id) {
		this.industry_id = industry_id;
	}

	public List<Position> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<Position> detailList) {
		this.detailList = detailList;
	}

}
