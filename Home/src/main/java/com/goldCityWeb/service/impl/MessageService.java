package com.goldCityWeb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldCityWeb.dao.IMessageDao;
import com.goldCityWeb.domain.Message;
import com.goldCityWeb.service.IMessageService;

@Service
public class MessageService implements IMessageService {

	@Autowired
	private IMessageDao messageDao;

	@Override
	public List<Message> queryMessages(Map<String, Object> param) {
		return messageDao.queryMessages(param);
	}

	@Override
	public int queryMessagesTotal(Map<String, Object> param) {
		return messageDao.queryMessagesTotal(param);
	}

	@Override
	public void updateMessagesToLooked(Integer id) {
		messageDao.updateMessagesToLooked(id);
	}

	@Override
	public void delMessageById(Integer uid, Integer id) {
		messageDao.delMessageById(uid, id);
	}

	@Override
	public void insertMessage(Message message) {
		messageDao.insertMessage(message);
	}
}
