/**
 * 
 */
package com.goldCityWeb.wechat;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * @author Administrator
 * 
 */
public class AccessTokenTool {
	
	//订阅号
	//public static final String AppId = "wx0f60a8adb6eeb889";
	public static final String AppId = "wx790b61d535ba2c2f ";
	public static final String AppSecret = "2c176f6c2dd9bdf529cc21e89f77754c";

	//订阅号
	//public static final String AppSecret = "db8108f564b7158c05877e8307068968";
	public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
			+ AppId + "&secret=" + AppSecret;
	public static String access_token = "";

	/**
	 * 当access_token提示失效时，需要重新获取token，此时reloadAccessToken传为true
	 * 
	 * @param reloadAccessToken
	 * @return
	 */
	public static synchronized String getAccessToken(Boolean reloadAccessToken) {
		if (StringUtils.isBlank(access_token) || reloadAccessToken) {
			access_token = getAccessToken();
			if(!StringUtils.isBlank(access_token)){
				Sign.getJsapi_ticket();
			}
		}
		
		return access_token;
	}
	
	private static String getAccessToken() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			System.out.println("-=-=-=-9808"+GET_ACCESS_TOKEN_URL);
			HttpGet httpget = new HttpGet(GET_ACCESS_TOKEN_URL);
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				
				String responseContent = EntityUtils.toString(entity, "utf-8");
				JSONObject object = new JSONObject();
				try {
					object = new JSONObject(responseContent);
					return (String)object.get("access_token");
				} catch (JSONException e) {
					System.out.println("获取token失败 errcode:" + object.get("errcode") + " errmsg:" + object.getString("errmsg"));
					e.printStackTrace();
					
				}
				
		        return null;
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	//dtPe5dEVOYpWW1ZpxJdwYBIcK5eFymWM67dOi4RUX8n9HwGjJh5vMYdjp7TdggQbElKFIv0r7EVxSs54VWFE4Zfzbsbnr-iJJr13krpjT1UOTD74Lrn8LVzN-idwMQjP83tJMnaOXUiOeryhg8fKCQ
	public static void main(String [] args) {
		System.out.println(getAccessToken(false));
	}
}
