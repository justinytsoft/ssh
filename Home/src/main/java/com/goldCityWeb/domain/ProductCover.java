package com.goldCityWeb.domain;

public class ProductCover {

	private Integer id;
	private Integer pid; //商品ID
	private String cover; //封面地址
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
}
