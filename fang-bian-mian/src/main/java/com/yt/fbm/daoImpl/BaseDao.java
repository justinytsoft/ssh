package com.yt.fbm.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yt.fbm.bean.Province;
import com.yt.fbm.dao.IBaseDao;
import com.yt.fbm.utils.HibernateUtils;

@Repository
public class BaseDao implements IBaseDao{

	public List<Province> getAllProvince() {
		
		String sql = "FROM Province";
		
		Session session = HibernateUtils.getSession();
		Query query = session.createQuery(sql);
		
		return query.list();
	}

}
