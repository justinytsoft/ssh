package com.goldCityWeb.domain;

import java.util.List;

public class College {

	private Integer id;
	private String code;
	private String name;
	private String desc;
	private Integer provinceId;

	private List<Major> detailList;

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

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public List<Major> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<Major> detailList) {
		this.detailList = detailList;
	}
}
