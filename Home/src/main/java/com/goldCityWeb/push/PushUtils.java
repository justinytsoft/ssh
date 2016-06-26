package com.goldCityWeb.push;

import org.json.JSONObject;

import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;

public class PushUtils {
	//商铺, ANDROID
	private static long COMPANY_ACCESS_ID = 2100203251L;
	private static String COMPANY_SECRET_KEY = "0643427aa000e9b8f99a1809e45efa86";
	//普通玩家 ANDROID
	private static long PLAYER_ACCESS_ID = 2100203249L;
	private static String PLAYER__SECRET_KEY = "58d992e5ece25b117f8c816ddf53e4a7";
	//IOS 商户
	private static long IOS_COMPANY_ACCESS_ID = 2200205017L;
	private static String IOS_COMPANY_SECRET_KEY = "102a4cc359a6c145842e11fcb79ae1fa";
	//IOS 玩家
	private static long IOS_PLAYER_ACCESS_ID = 2200205005L;
	private static String IOS_PLAYER_SECRET_KEY = "8a212b6aa2915332da7340bcdc22a55e";
	
	/*public static void main(String[] args) {

		SysAdv sa = new SysAdv();
		sa.setContent("1466405408928.html");
		sa.setPush_content("hhhhhhhhhhhh");
		sa.setPush_title("12312313");
		
		Map<String, Object> customMap = new HashMap<String, Object>();
		String baseHtmlUrl = SettingUtils.getCommonSetting("base.html.url");
		customMap.put("activity_url", baseHtmlUrl + sa.getContent());//活动内容
		customMap.put("push_type", 2);
		
		Message msg = new Message();
		msg.setCustom(customMap);
		msg.setTitle(sa.getPush_title());
		msg.setContent(sa.getPush_content());
		
		pushAllAndroid(2, msg);
	}
	*/
	public static boolean getResultCode(JSONObject result) {
		System.out.println(result.toString());
		//JSONObject result = re.getJSONObject("result");
		int ret_code = result.getInt("ret_code");
		if(ret_code == 0) {
			System.out.println("推送成功");
			return true;
		} else {
			printlnExperience(ret_code);
			return false;
		}
	}
	
	public static void printlnExperience(int experience_code) {
		System.err.println("Push Err:ret_code is " + experience_code);
		switch (experience_code) {
		case -1:
			System.err.println("Push Err:参数错误，请对照文档检查请求参数");
			break;
		case -2:
			System.err.println("Push Err:请求时间戳不在有效期内，检查秦秋的时间戳设置是否正确，机器时间是否正确");
			break;
		case -3:
			System.err.println("Push Err:sign 校验无效，检查 access id 和 secret key（注意不是 access key）");
			break;
		case 2:
			System.err.println("Push Err:参数错误，请对照文档检查请求参数");
			break;
		case 7:
			System.err.println("Push Err:别名/账号绑定的终端数满了（10 个），请解绑部分终端");
			break;
		case 14:
			System.err.println("Push Err:收到非法 token，例如 ios 终端没能拿到正确的 token，请检查 token 是否正确");
			break;
		case 15:
			System.err.println("Push Err:信鸽逻辑服务器繁忙，请稍后再试");
			break;
		case 20:
			System.err.println("Push Err:鉴权错误，请检查 access id 和 access key 是否正确");
			break;
		case 40:
			System.err.println("Push Err:推送的 token 没有在信鸽中注册，或者推送的帐号没有绑定 token，请检查注册逻辑");
			break;
		case 48:
			System.err.println("Push Err:推送的账号没有在信鸽中注册，请检查注册逻辑数");
			break;
		case 63:
			System.err.println("Push Err:标签系统忙，请稍后再试");
			break;
		case 71:
			System.err.println("Push Err:APNS 服务器繁忙，请稍后再试");
			break;
		case 73:
			System.err.println("Push Err:消息字符数超限，请缩小消息内容");
			break;
		case 76:
			System.err.println("Push Err:请求过于频繁，请稍后再试");
			break;
		case 78:
			System.err.println("Push Err:循环任务参数错误，请对照文档检查请求参数");
			break;
		case 100:
			System.err.println("Push Err:APNS 证书错误。请重新提交正确的证书");
			break;
		default:
			System.err.println("Push Err:内部错误");
			break;
		}
	}
	
	/**
	 * 给全部安卓客户端推送消息
	 * @param client_type  客户端类型：1：玩家端，2:商户端
	 * @param title   标题
	 * @param content	内容
	 * @return
	 */
	public static boolean pushAllAndroid(Integer client_type, String title, String content) {
		JSONObject result = null;
		switch (client_type) {
		case 1:
			result = XingeApp.pushAllAndroid(PLAYER_ACCESS_ID, PLAYER__SECRET_KEY, title, content);
			break;
		case 2:
			result = XingeApp.pushAllAndroid(COMPANY_ACCESS_ID, COMPANY_SECRET_KEY, title, content);
			break;
		}
		//正确返回格式
		//{"result":{"push_id":"1432994677"},"ret_code":0}
		//

		return getResultCode(result);
	}
	
	/**
	 * 
	 * @param client_type
	 * @param message
	 * @return
	 */
	public static boolean pushAllAndroid(Integer client_type, Message message) {
		JSONObject result = null;
		switch (client_type) {
		case 1:
			result = XingeApp.pushAllAndroid(PLAYER_ACCESS_ID, PLAYER__SECRET_KEY, message);
			break;
		case 2:
			result = XingeApp.pushAllAndroid(COMPANY_ACCESS_ID, COMPANY_SECRET_KEY, message);
			break;
		}
		return getResultCode(result);
	}
	
	/**
	 * 对单个安卓进行推送
	 * @param client_type
	 * @param title
	 * @param content
	 * @param token
	 * @return
	 */
	public static boolean pushAndroidByToken(Integer client_type, String title, String content, String token) {
		JSONObject result = null;
		switch (client_type) {
		case 1:
			result = XingeApp.pushTokenAndroid(PLAYER_ACCESS_ID, PLAYER__SECRET_KEY, title, content, token);
			break;
		case 2:
			result = XingeApp.pushTokenAndroid(COMPANY_ACCESS_ID, COMPANY_SECRET_KEY, title, content, token);
			break;
		}
		return getResultCode(result);
	}
	
	public static JSONObject pushAndroidByToken(Integer client_type, Message message, String token) {
		JSONObject result = null;
		switch (client_type) {
		case 1:
			result = XingeApp.pushTokenAndroid(PLAYER_ACCESS_ID, PLAYER__SECRET_KEY, message, token);
			break;
		case 2:
			result = XingeApp.pushTokenAndroid(COMPANY_ACCESS_ID, COMPANY_SECRET_KEY, message, token);
			break;
		}
		return result;
	}
	
	/**
	 * 给全部安卓客户端推送消息
	 * @param client_type  客户端类型：1：玩家端，2:商户端
	 * @param title   标题
	 * @param content	内容
	 * @return
	 */
	public static boolean pushAllIOS(Integer client_type, String content) {
		JSONObject result = null;
		switch (client_type) {
		case 1:
			result = XingeApp.pushAllIos(IOS_PLAYER_ACCESS_ID, IOS_PLAYER_SECRET_KEY, content, XingeApp.IOSENV_DEV);
			break;
		case 2:
			result = XingeApp.pushAllIos(IOS_COMPANY_ACCESS_ID, IOS_COMPANY_SECRET_KEY, content, XingeApp.IOSENV_DEV);
			break;
		}
		
		return getResultCode(result);
	}
	
	/**
	 * 
	 * @param client_type
	 * @param message
	 * @return
	 */
	public static boolean pushAllIOS(Integer client_type, MessageIOS message) {
		JSONObject result = null;
		switch (client_type) {
		case 1:
			result = XingeApp.pushAllIos(IOS_PLAYER_ACCESS_ID, IOS_PLAYER_SECRET_KEY, message, XingeApp.IOSENV_DEV);
			break;
		case 2:
			result = XingeApp.pushAllIos(IOS_COMPANY_ACCESS_ID, IOS_COMPANY_SECRET_KEY, message, XingeApp.IOSENV_DEV);
			break;
		}
		return getResultCode(result);
	}
	
	/**
	 * 对单个安卓进行推送
	 * @param client_type
	 * @param title
	 * @param content
	 * @param token
	 * @return
	 */
	public static boolean pushIOSByToken(Integer client_type, String content, String token) {
		JSONObject result = null;
		switch (client_type) {
		case 1:
			result = XingeApp.pushTokenIos(IOS_PLAYER_ACCESS_ID, IOS_PLAYER_SECRET_KEY, content, token, XingeApp.IOSENV_DEV);
			break;
		case 2:
			result = XingeApp.pushTokenIos(IOS_COMPANY_ACCESS_ID, IOS_COMPANY_SECRET_KEY, content, token, XingeApp.IOSENV_DEV);
			break;
		}
		return getResultCode(result);
	}
	
	public static JSONObject pushIOSByToken(Integer client_type, MessageIOS message, String token) {
		JSONObject result = null;
		switch (client_type) {
		case 1:
			result = XingeApp.pushTokenIos(IOS_PLAYER_ACCESS_ID, IOS_PLAYER_SECRET_KEY, message, token, XingeApp.IOSENV_DEV);
			break;
		case 2:
			result = XingeApp.pushTokenIos(IOS_COMPANY_ACCESS_ID, IOS_COMPANY_SECRET_KEY, message, token, XingeApp.IOSENV_DEV);
			break;
		}
		return result;
	}
	
	
	public static JSONObject cancelTimingPush(Integer token_type,Integer user_type, String pushId) {
		JSONObject result = null;
		XingeApp xingeApp = null;
		switch (user_type) {
		case 2:
			if(token_type==1){
				xingeApp = new XingeApp(PLAYER_ACCESS_ID, PLAYER__SECRET_KEY);
			} else {
				xingeApp = new XingeApp(IOS_PLAYER_ACCESS_ID, IOS_PLAYER_SECRET_KEY);
			}
			break;
		case 3:
			if(token_type==1){
				xingeApp = new XingeApp(IOS_COMPANY_ACCESS_ID, IOS_COMPANY_SECRET_KEY);
			} else {
				xingeApp = new XingeApp(COMPANY_ACCESS_ID, COMPANY_SECRET_KEY);
			}
			break;
		}
		result = xingeApp.cancelTimingPush(pushId);
		return result;
	}
	
}
