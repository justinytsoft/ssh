package com.goldCityWeb.domain;

import java.util.Date;
import java.util.List;

public class Product {

	private Integer id;
	private Integer mid; // 商家ID
	private Integer category; // 分类 0 道具类 1 物品类
	private String name; // 名称
	private Float price; // 单价
	private String desc; // 功能描述
	private Integer stock; // 库存(未用上)
	private String address; // 道具类使用地方，物品类领取地方(未用上)
	private String phone; // 领取电话(未用上)
	private String prompt; // 领取物品类商品的提示(未用上)
	private Boolean deleted; // 是否删除(未用上)
	private Boolean status; // 是否上架(未用上)
	private Date create_time; // 创建时间(未用上)
	private Integer receive_type;// 领取类型1：快递，2：自提
	private String receive_address;//自提地址
	private Integer limit_level;//领取等级
	private Integer number;//数量
	private String use_way;//使用方法
	private String special_work;
	private String work_description;
	private String use_description;

	private List<ProductCover> covers; // 商品封面

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public List<ProductCover> getCovers() {
		return covers;
	}

	public void setCovers(List<ProductCover> covers) {
		this.covers = covers;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
