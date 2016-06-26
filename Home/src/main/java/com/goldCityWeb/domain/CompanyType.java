package com.goldCityWeb.domain;

public class CompanyType {
	private Integer id;
	private Integer type; //商家类型， 1 商家 2 微商
	private String type_name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
