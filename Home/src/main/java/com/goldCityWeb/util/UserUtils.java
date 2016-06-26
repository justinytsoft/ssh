package com.goldCityWeb.util;

import javax.servlet.http.HttpServletRequest;

import com.goldCityWeb.domain.UserDetail;

public class UserUtils {
	
	 /**
	 * 获取session里的用户
	 * @param request
	 * @return
	 */
	public static UserDetail getSessionUser(HttpServletRequest request){
		return (UserDetail)request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
	}
}
