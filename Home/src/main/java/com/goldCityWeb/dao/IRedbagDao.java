package com.goldCityWeb.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.goldCityWeb.domain.MainList;

public interface IRedbagDao {

	List<MainList> queryAdv(@Param("param") Map<String, Object> param);

	int queryAdvTotal(@Param("param") Map<String, Object> param);

	MainList queryAdvById(@Param("id") Integer id);

}
