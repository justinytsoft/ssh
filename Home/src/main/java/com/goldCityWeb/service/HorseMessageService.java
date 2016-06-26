package com.goldCityWeb.service;

import java.util.List;
import java.util.Map;

import com.goldCityWeb.domain.HorseMessage;
import com.goldCityWeb.util.PageSupport;

public interface HorseMessageService {
	public void saveHorseMessage(HorseMessage message);
	
	public List<HorseMessage> queryHorseMessage(PageSupport ps, Map<String, Object> param);
}
