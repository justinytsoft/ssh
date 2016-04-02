package com.yt.fbm.service;

import com.yt.fbm.bean.User;

public interface IUserSerivce {

	/**
	 * 通过用户名和密码查询用户
	 * @param username
	 * @param password
	 * @return
	 */
	User queryUserByUP(String username, String password);

}
