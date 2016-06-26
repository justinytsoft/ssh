package com.goldCityWeb.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.goldCityWeb.domain.Message;

public interface IMessageDao {

	List<Message> queryMessages(@Param("param") Map<String, Object> param);

	int queryMessagesTotal(@Param("param") Map<String, Object> param);

	void updateMessagesToLooked(@Param("id") Integer id);

	void delMessageById(@Param("uid") Integer uid, @Param("id") Integer id);

	void insertMessage(Message message);

}
