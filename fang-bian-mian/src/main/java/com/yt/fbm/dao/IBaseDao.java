package com.yt.fbm.dao;

import java.util.List;

import com.yt.fbm.bean.Province;


public interface IBaseDao {

	/**
	 * 查询所有省份
	 * @return
	 */
	List<Province> getAllProvince();

}
