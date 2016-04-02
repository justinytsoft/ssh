package com.yt.fbm.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yt.fbm.bean.User;
import com.yt.fbm.dao.IUserDao;
import com.yt.fbm.service.IUserSerivce;

@Service
public class UserServiceImpl implements IUserSerivce{
	
	@Autowired
	private IUserDao userDao;

	public User queryUserByUP(String username, String password) {
		return userDao.queryUserByUP(username, password);
	}

}
