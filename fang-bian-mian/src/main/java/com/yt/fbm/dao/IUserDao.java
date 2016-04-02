package com.yt.fbm.dao;

import com.yt.fbm.bean.User;

public interface IUserDao {

	User queryUserByUP(String username, String password);

}
