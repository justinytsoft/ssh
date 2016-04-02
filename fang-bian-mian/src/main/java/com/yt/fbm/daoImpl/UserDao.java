package com.yt.fbm.daoImpl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yt.fbm.bean.User;
import com.yt.fbm.dao.IUserDao;
import com.yt.fbm.utils.HibernateUtils;

@Repository
public class UserDao implements IUserDao {
	
	public User queryUserByUP(String username, String password) {
		
		String sql = "FROM User WHERE username = :username AND password = :password";

		Session session = HibernateUtils.getSession();
		Query query = session.createQuery(sql);
		query.setString("username", username);
		query.setString("password", password);
		
		return (User) query.uniqueResult();
	}
}
