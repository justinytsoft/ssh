package com.goldCityWeb.push;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.goldCityWeb.domain.SysAdv;
import com.goldCityWeb.util.SettingUtils;
import com.tencent.xinge.ClickAction;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;

public class PushServer {
	public static Integer PLAYER = 1;
	public static Integer COMPANY = 2;
	public static Integer PUSH_NOTICE = 1;
	public static Integer PUSH_URL = 2;
	
	
	/**
	 * 对所有指定客户端推送活动
	 */
	public static JSONObject pushActivityClientByToken(String title,String content,String token,String send_time,Integer token_type,Integer user_type) {
		Map<String, Object> customMap = new HashMap<String, Object>();
		
		
		
		Message playermsg = new Message();
		playermsg.setCustom(customMap);
		playermsg.setTitle(title);
		playermsg.setContent(content);
		playermsg.setType(Message.TYPE_NOTIFICATION);
		if(!StringUtils.isBlank(send_time)){
			playermsg.setSendTime(send_time);
		}
		//playermsg.setAction(playCa);
		
		
		
		MessageIOS msgIos = new MessageIOS();
		msgIos.setCustom(customMap);
		msgIos.setAlert(content);
		msgIos.setBadge(1);
		msgIos.setSound("beep.wav");
		if(!StringUtils.isBlank(send_time)){
			msgIos.setSendTime(send_time);
		}
		
		if(user_type == 2) {
			//PushUtils.pushAllAndroid(PLAYER, playermsg);
			//PushUtils.pushAllIOS(PLAYER, msgIos);
			if(token_type==1){
				return PushUtils.pushAndroidByToken(PLAYER,playermsg , token);
			}else{
				return PushUtils.pushIOSByToken(PLAYER, msgIos, token);
			}
		} else {
			/*PushUtils.pushAllAndroid(COMPANY, companymsg);
			PushUtils.pushAllIOS(COMPANY, msgIos);*/
			if(token_type==1){
				return PushUtils.pushAndroidByToken(COMPANY,playermsg , token);
			} else{
				return PushUtils.pushIOSByToken(COMPANY, msgIos, token);
			}
		}
	}
	
	
	/**
	 * 对所有客户端推送活动
	 */
	public static void pushActivityAllClient(SysAdv sa) {
		Map<String, Object> customMap = new HashMap<String, Object>();
		String baseHtmlUrl = SettingUtils.getCommonSetting("base.html.url");
		customMap.put("activity_url", baseHtmlUrl + sa.getContent());//活动内容
		customMap.put("push_type", PUSH_URL);
		
		ClickAction playCa = new ClickAction();
		playCa.setActionType(ClickAction.TYPE_ACTIVITY);
		playCa.setActivity("com.jssj.goldenCity.view.WebWindowActivity");
		
		Message playermsg = new Message();
		playermsg.setCustom(customMap);
		playermsg.setTitle(sa.getPush_title());
		playermsg.setContent(sa.getPush_content());
		playermsg.setType(Message.TYPE_NOTIFICATION);
		playermsg.setAction(playCa);
		
		ClickAction companyCa = new ClickAction();
		companyCa.setActionType(ClickAction.TYPE_ACTIVITY);
		companyCa.setActivity("com.jssj.Business.goldenCity.view.WebWindowActivity");
		
		Message companymsg = new Message();
		companymsg.setCustom(customMap);
		companymsg.setTitle(sa.getPush_title());
		companymsg.setContent(sa.getPush_content());
		companymsg.setType(Message.TYPE_NOTIFICATION);
		companymsg.setAction(companyCa);
		
		MessageIOS msgIos = new MessageIOS();
		msgIos.setCustom(customMap);
		msgIos.setAlert(sa.getPush_content());
		msgIos.setBadge(1);
		msgIos.setSound("beep.wav");
		
		if(sa.getPush_type() == 0) {
			PushUtils.pushAllAndroid(PLAYER, playermsg);
			PushUtils.pushAllIOS(PLAYER, msgIos);
		} else {
			PushUtils.pushAllAndroid(COMPANY, companymsg);
			PushUtils.pushAllIOS(COMPANY, msgIos);
		}
	}

	public static void pushAllClient(String title, String content) {
		PushUtils.pushAllAndroid(PLAYER, title, content);
		PushUtils.pushAllAndroid(COMPANY, title, content);
		PushUtils.pushAllIOS(PLAYER, content);
		PushUtils.pushAllIOS(COMPANY, content);
	}
	
	public static void main(String[] args) {
		
		JSONObject test = pushActivityClientByToken("test", "test", "b0fbb1eefd86533f46c8b7255d34d6e452b80f04", "", 1, 2);
		System.out.println(test.toString());
		
		//JSONObject  ret = PushUtils.cancelTimingPush(user_type, pushId);
		//Message playermsg = new Message();
		//playermsg.setCustom(customMap);
		//playermsg.setTitle("test");
		//playermsg.setContent("test");
		//playermsg.setType(Message.TYPE_NOTIFICATION);
		//PushUtils.pushAllAndroid(PLAYER, playermsg);
		//PushUtils.pushAllIOS(PLAYER, msgIos);
	}
	
	
}
