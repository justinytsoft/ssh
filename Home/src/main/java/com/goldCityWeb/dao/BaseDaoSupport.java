/**
 * Project: chip
 * 
 * File Created at 2011-12-27
 * $Id$
 * 
 * Copyright 2011 Sybercom
 * All rights reserved.
 *
 */
package com.goldCityWeb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.CollectionUtils;

import com.goldCityWeb.util.PageSupport;

public class BaseDaoSupport extends SqlSessionDaoSupport {

	public int save(String key, Object object) {
		return this.getSqlSession().insert(key, object);
	}

	public void update(String key, Object object) {
		this.getSqlSession().update(key, object);
	}

	public void delete(String key, Object object) {
		this.getSqlSession().delete(key, object);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key, Object object) {
		return (T) this.getSqlSession().selectOne(key, object);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) this.getSqlSession().selectOne(key);
	}

	public <T> List<T> getList(String key) {
		return this.getSqlSession().selectList(key);
	}

	public <T> List<T> getList(String key, Object object) {
		return this.getSqlSession().selectList(key, object);
	}
	
	/**
	 * 采用内存分页，当数据量少时可用此方式
	 * 在Controller中需要PageSupport.initPageSupport(HttpServletRequest request)方法支持
	 * 
	 * @param key
	 * @param object
	 * @param pageSupport
	 * @return
	 */
	public <T> List<T> getListPageSupport(String key, Object object, PageSupport pageSupport) {
		SqlSession sqlSession = this.getSqlSession();

		int size = sqlSession.selectList(key, object).size();
		pageSupport.setTotalRecord(size);

		RowBounds rowBounds = new RowBounds(pageSupport.getPageOffset(), pageSupport.getPageSize());
		
		return sqlSession.selectList(key, object, rowBounds);
	}
	
	/**
	 * 采用数据库分页，性能高于getListPageSupport(String key, Object object, PageSupport pageSupport)。<br />
	 * 在Controller中需要PageSupport.initPageSupport(HttpServletRequest request, int totalRecord)方法支持, <br />
	 * 参数totalRecord需要手动查询
	 * 
	 * SQL语句中增加参数limit和offset, 如:<br />
	 * SELECT *<br />
	 * FROM  table<br />
	 * LIMIT #{limit} OFFSET #{offset}
	 * 
	 * @param key
	 * @param param
	 * @param pageSupport
	 * @return
	 */
	public <T> List<T> getListPageSupportByManualOperation(String key, Map<String, Object> param, PageSupport pageSupport) throws IllegalArgumentException{
		if (pageSupport.getTotalRecord() <= 0)
			throw new IllegalArgumentException("totalRecord is less than or equal to 0, please to get total record number firstly and invoke pageSupport.setTotalRecord() method!");
		SqlSession sqlSession = this.getSqlSession();
		if (param == null)
			param = new HashMap<String, Object>();
		param.put("limit", pageSupport.getPageSize());
		param.put("offset", pageSupport.getPageOffset());
		return sqlSession.selectList(key, param);
	}
	
	/**
	 * 采用数据库分页，性能高于getListPageSupport(String key, Object object, PageSupport pageSupport)。<br />
	 * 在Controller中需要PageSupport.initPageSupport(HttpServletRequest request, int totalRecord)方法支持, <br />
	 * 参数totalRecord需要手动查询
	 * 
	 * SQL语句中增加参数limit和offset, 如:<br />
	 * SELECT *<br />
	 * FROM  table<br />
	 * LIMIT #{limit} OFFSET #{offset}
	 * 
	 * @param key
	 * @param countKey 统计数量的key
	 * @param param
	 * @param pageSupport
	 * @return
	 * @throws IllegalArgumentException
	 */
	public <T> List<T> getListPageSupportByManualOperation(String key, String countKey, Map<String, Object> param, PageSupport pageSupport) throws IllegalArgumentException{
		Integer totalRecord = 0;
		if (!CollectionUtils.isEmpty(param))
			totalRecord = this.get(countKey, param);
		else
			totalRecord = this.get(countKey);
		if (totalRecord == null || totalRecord.intValue() <= 0)
			return null;
		pageSupport.setTotalRecord(totalRecord);
		SqlSession sqlSession = this.getSqlSession();
		if (param == null)
			param = new HashMap<String, Object>();
		param.put("limit", pageSupport.getPageSize());
		param.put("offset", pageSupport.getPageOffset());
		return sqlSession.selectList(key, param);
	}
}
