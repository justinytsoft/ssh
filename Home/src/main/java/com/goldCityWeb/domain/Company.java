package com.goldCityWeb.domain;

import java.util.Date;

public class Company {
	private Integer id;
	private Integer user_id;
	private Integer rank;
	private String nick_name;
	private String company_name;
	private String company_phone;
	private Integer province_id;
	private Integer city_id;
	private Integer position_id;
	private String address;
	private String link_man;
	private String phone_num;
	private String web_link;
	private Integer company_type;
	private String licence;
	private String trade_license;
	private String type_name;
	private Integer verify_status;
	private String verify_desc;
	private Date verify_date;
	private Float grant_count;
	private Float gold_count;
	private Integer level;
	private String head;
	private String logo;
	private String description;
	private String bank_card_num;
	private String bank_name;
	/**
	 * 开户行信息， 不是电话
	 */
	private String bank_phone;
	private String real_auth;
	private Float longitude;
	private Float latitude;
	
	private Float statement_money; //结算金额
	private Integer statement_num; //结算数量
	
	private String provinceName;
	private String cityName;
	private String positionName;
	
	public Date getVerify_date() {
		return verify_date;
	}


	public void setVerify_date(Date verify_date) {
		this.verify_date = verify_date;
	}


	public String getVerify_desc() {
		return verify_desc;
	}


	public void setVerify_desc(String verify_desc) {
		this.verify_desc = verify_desc;
	}

	
	public Integer getVerify_status() {
		return verify_status;
	}


	public void setVerify_status(Integer verify_status) {
		this.verify_status = verify_status;
	}


	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public Float getGrant_count() {
		return grant_count;
	}

	public void setGrant_count(Float grant_count) {
		this.grant_count = grant_count;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_phone() {
		return company_phone;
	}

	public void setCompany_phone(String company_phone) {
		this.company_phone = company_phone;
	}

	public Integer getProvince_id() {
		return province_id;
	}

	public void setProvince_id(Integer province_id) {
		this.province_id = province_id;
	}

	public Integer getCity_id() {
		return city_id;
	}

	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}

	public Integer getPosition_id() {
		return position_id;
	}

	public void setPosition_id(Integer position_id) {
		this.position_id = position_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getWeb_link() {
		return web_link;
	}

	public void setWeb_link(String web_link) {
		this.web_link = web_link;
	}

	public Integer getCompany_type() {
		return company_type;
	}

	public void setCompany_type(Integer company_type) {
		this.company_type = company_type;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getTrade_license() {
		return trade_license;
	}

	public void setTrade_license(String trade_license) {
		this.trade_license = trade_license;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public Float getGold_count() {
		return gold_count;
	}
	public void setGold_count(Float gold_count) {
		this.gold_count = gold_count;
	}


	public String getNick_name() {
		return nick_name;
	}


	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}


	public Integer getRank() {
		return rank;
	}


	public void setRank(Integer rank) {
		this.rank = rank;
	}


	public String getLogo() {
		return logo;
	}


	public void setLogo(String logo) {
		this.logo = logo;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getReal_auth() {
		return real_auth;
	}


	public void setReal_auth(String real_auth) {
		this.real_auth = real_auth;
	}


	public String getBank_card_num() {
		return bank_card_num;
	}


	public void setBank_card_num(String bank_card_num) {
		this.bank_card_num = bank_card_num;
	}


	public String getBank_name() {
		return bank_name;
	}


	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}


	public String getBank_phone() {
		return bank_phone;
	}


	public void setBank_phone(String bank_phone) {
		this.bank_phone = bank_phone;
	}


	public Float getStatement_money() {
		return statement_money;
	}


	public void setStatement_money(Float statement_money) {
		this.statement_money = statement_money;
	}


	public Integer getStatement_num() {
		return statement_num;
	}


	public void setStatement_num(Integer statement_num) {
		this.statement_num = statement_num;
	}


	public String getLink_man() {
		return link_man;
	}


	public void setLink_man(String link_man) {
		this.link_man = link_man;
	}


	public Float getLatitude() {
		return latitude;
	}


	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}


	public Float getLongitude() {
		return longitude;
	}


	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}


	public String getProvinceName() {
		return provinceName;
	}


	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}


	public String getCityName() {
		return cityName;
	}


	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	public String getPositionName() {
		return positionName;
	}


	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
}
