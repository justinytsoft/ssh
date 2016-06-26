package com.goldCityWeb.service;

import java.util.List;
import java.util.Map;

import com.goldCityWeb.domain.Message;

public interface IMessageService {

	
	/**
	 * 查询消息
	 * @param 
	 * @return
	 */
	List<Message> queryMessages(Map<String, Object> param);

	/**
	 * 查询消息总数
	 * @param 
	 * @return
	 */
	int queryMessagesTotal(Map<String, Object> param);

	/**
	 * 当用户查看消息列表时，把所有消息更新为已查看
	 * @param id
	 */
	void updateMessagesToLooked(Integer id);

	/**
	 * 删除消息
	 * @param uid 用户ID
	 * @param id 消息ID
	 */
	void delMessageById(Integer uid, Integer id);

	/**
	 * 保存消息
	 * @param message
	 */
	void insertMessage(Message message);

}
