package com.goldCityWeb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldCityWeb.dao.HorseMessageDao;
import com.goldCityWeb.domain.HorseMessage;
import com.goldCityWeb.service.HorseMessageService;
import com.goldCityWeb.util.AbstractModuleSuport;
import com.goldCityWeb.util.PageSupport;

@Service
public class HorseMessageServiceImpl extends AbstractModuleSuport implements HorseMessageService{
	@Autowired
	private HorseMessageDao horseMessageDao;
	
	@Override
	public void saveHorseMessage(HorseMessage message) {
		// TODO Auto-generated method stub
		horseMessageDao.saveHorseMessage(message);
	}

	@Override
	public List<HorseMessage> queryHorseMessage(PageSupport ps,
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.HorseMessageDao.queryHorseMessage", "com.goldCityWeb.dao.HorseMessageDao.queryHorseMessage_count", param, ps);
	}

}
