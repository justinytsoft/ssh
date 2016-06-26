package com.goldCityWeb.domain;

import java.util.List;

public class City {

	private Integer id;
	private Integer provinceId;
	private String name;

//	private List<College> detailList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public List<College> getDetailList() {
//		return detailList;
//	}
//
//	public void setDetailList(List<College> detailList) {
//		this.detailList = detailList;
//	}

}
