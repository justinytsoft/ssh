package com.goldCityWeb.domain;

public class Commission {
	private Integer id;
	private Integer flag;//类型,1为百分比,2为固定价格
	private Float num;//价格数字
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Float getNum() {
		return num;
	}
	public void setNum(Float num) {
		this.num = num;
	}

}
