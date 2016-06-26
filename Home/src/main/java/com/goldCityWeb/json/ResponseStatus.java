package com.goldCityWeb.json;

public class ResponseStatus {
	/**
	 * 请求成功
	 */
	public static final String OK = "200";
	
	/**
	 * 请求失败
	 */
	public static final String FAILED = "201";
	/**
	 * 参数为空
	 */
	public static final String FAILED_PARAM_LOST = "202";
	
	/**
	 * 账号已存在
	 */
	public static final String FAILED_REG_EXISTS = "203";
	
	/**
	 * session失效
	 */
	public static final String FAILED_SESSION_INVALID = "209";
}
