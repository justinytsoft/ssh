package com.goldCityWeb.domain;

/**
 * 消息类型
 * 修改后还要改前台页面上的 下拉框
 * @author Administrator
 *
 */
public class MessageType {

	/**
	 * 用户消费
	 */
	public static final Integer USER_FEE = 0;
	
	/**
	 * 提现
	 */
	public static final Integer CLOSE_APPLY = 1;
	
	/**
	 * 红包
	 */
	public static final Integer CREATE_REDBAG = 2;
	
	/**
	 * 通知
	 */
	public static final Integer NOTIFY = 3;
	
	/**
	 * 商户审核
	 */
	public static final Integer AUDIT = 4;
	
	/**
	 * 兑换类
	 */
	public static final Integer EXCHANGE = 5;
}
