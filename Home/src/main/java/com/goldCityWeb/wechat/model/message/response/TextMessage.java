package com.goldCityWeb.wechat.model.message.response;

import com.goldCityWeb.wechat.model.message.response.BaseMessage;

public class TextMessage extends BaseMessage {
	// 回复的消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
