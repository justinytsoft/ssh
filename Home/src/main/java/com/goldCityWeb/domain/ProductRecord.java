package com.goldCityWeb.domain;

import java.util.Date;

public class ProductRecord {

	private Integer id;
	private Integer p_id;
	private Integer u_id;
	private Integer type;
	private Float gold;
	private Integer count;
	private String order_num;
	private Integer receive_type;
	private String p_name;
	private String p_path;
	private String address;
	private String name;
	private String phone;
	private Date create_time;
	
	private String express_name;
	private String express_num;
	private String remark;
	private Boolean handle;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getP_id() {
		return p_id;
	}
	public void setP_id(Integer p_id) {
		this.p_id = p_id;
	}
	public Integer getU_id() {
		return u_id;
	}
	public void setU_id(Integer u_id) {
		this.u_id = u_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	public Integer getReceive_type() {
		return receive_type;
	}
	public void setReceive_type(Integer receive_type) {
		this.receive_type = receive_type;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getP_path() {
		return p_path;
	}
	public void setP_path(String p_path) {
		this.p_path = p_path;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Float getGold() {
		return gold;
	}
	public void setGold(Float gold) {
		this.gold = gold;
	}
	public Boolean getHandle() {
		return handle;
	}
	public void setHandle(Boolean handle) {
		this.handle = handle;
	}
	public String getExpress_name() {
		return express_name;
	}
	public void setExpress_name(String express_name) {
		this.express_name = express_name;
	}
	public String getExpress_num() {
		return express_num;
	}
	public void setExpress_num(String express_num) {
		this.express_num = express_num;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	} 
	
}
