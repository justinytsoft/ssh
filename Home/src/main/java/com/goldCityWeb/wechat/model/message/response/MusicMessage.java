/**
 * 
 */
package com.goldCityWeb.wechat.model.message.response;

import com.goldCityWeb.wechat.model.message.response.BaseMessage;

/**
 * @author Administrator
 *
 */
public class MusicMessage extends BaseMessage {
	// 音乐  
    private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
    
    
}
