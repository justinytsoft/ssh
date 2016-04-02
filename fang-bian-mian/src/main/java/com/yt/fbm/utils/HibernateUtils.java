package com.yt.fbm.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

	private static SessionFactory sf;
	
	static {
		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		StandardServiceRegistry sr = srb.build();
		sf = cfg.buildSessionFactory(sr);
	}
	
	private HibernateUtils(){}
	
	public static Session getSession(){
		return sf.openSession();
	}

}
