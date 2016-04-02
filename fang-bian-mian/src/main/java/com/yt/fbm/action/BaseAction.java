package com.yt.fbm.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.yt.fbm.bean.Province;
import com.yt.fbm.bean.User;
import com.yt.fbm.dao.IBaseDao;
import com.yt.fbm.service.IUserSerivce;
import com.yt.fbm.utils.JsonUtils;

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = 9188002502480718950L;

	@Autowired
	private IUserSerivce userService;
	@Autowired
	private IBaseDao baseDao;

	private String username;
	private String password;
	

	/**
	 * 登陆页面
	 * @return
	 */
	public String login() {
		
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return ERROR;
		}
		
		User user = userService.queryUserByUP(username,password);
		
		ServletContext session = ServletActionContext.getServletContext();
		session.setAttribute("user", user);
		return SUCCESS;
	}
	
	/**
	 * 获取省
	 * @return
	 */
	public String getProvince(){
		Map<String, Object> datas = new HashMap<String, Object>();
		try {
			List<Province> provinces = baseDao.getAllProvince();
			datas.put("provinces", provinces);
			JsonUtils.toJson(ServletActionContext.getResponse(), datas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/* getter and setter */
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
