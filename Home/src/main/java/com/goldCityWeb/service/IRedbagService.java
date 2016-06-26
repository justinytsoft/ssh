package com.goldCityWeb.service;

import java.util.List;
import java.util.Map;

import com.goldCityWeb.domain.MainList;

public interface IRedbagService {

	List<MainList> queryAdv(Map<String, Object> param);

	int queryAdvTotal(Map<String, Object> param);

	MainList queryAdvById(Integer id);
}
