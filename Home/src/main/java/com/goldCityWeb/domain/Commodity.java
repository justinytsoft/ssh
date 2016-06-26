package com.goldCityWeb.domain;

public class Commodity {
	private Integer id;
	private String name;
	private String image;
	private Integer type;
	private String describe;
	private Float price;
	private String type_name;
	private Integer receive_type;
	private String receive_address;
	private Integer limit_level;
	private String use_way;
	private String special_work;
	private String work_description;
	private String use_description;

	public String getUse_way() {
		return use_way;
	}

	public void setUse_way(String use_way) {
		this.use_way = use_way;
	}

	public String getSpecial_work() {
		return special_work;
	}

	public void setSpecial_work(String special_work) {
		this.special_work = special_work;
	}

	public String getWork_description() {
		return work_description;
	}

	public void setWork_description(String work_description) {
		this.work_description = work_description;
	}

	public String getUse_description() {
		return use_description;
	}

	public void setUse_description(String use_description) {
		this.use_description = use_description;
	}

	public Integer getReceive_type() {
		return receive_type;
	}

	public void setReceive_type(Integer receive_type) {
		this.receive_type = receive_type;
	}

	public String getReceive_address() {
		return receive_address;
	}

	public void setReceive_address(String receive_address) {
		this.receive_address = receive_address;
	}

	public Integer getLimit_level() {
		return limit_level;
	}

	public void setLimit_level(Integer limit_level) {
		this.limit_level = limit_level;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
