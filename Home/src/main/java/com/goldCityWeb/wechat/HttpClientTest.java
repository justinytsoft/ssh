package com.goldCityWeb.wechat;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {
	public static void main(String [] args) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost("http://localhost:8080/excavator/wechat/auth");
			String xml = "<xml>";
			xml += "<ToUserName><![CDATA[toUser]]></ToUserName>";
			xml += "<FromUserName><![CDATA[fromUser]]></FromUserName> ";
			xml += "<CreateTime>1348831860</CreateTime>";
			xml += "<MsgType><![CDATA[text]]></MsgType>";
			xml += "<Content><![CDATA[this is a test]]></Content>";
			xml += "<MsgId>1234567890123456</MsgId>";
			xml += "</xml>";
			
			StringEntity entity = new StringEntity(xml);
			httpPost.setEntity(entity);
			httpPost.setHeader("Content-Type", "text/xml;charset=utf-8");
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity resEntity = response.getEntity();
				
				String responseContent = EntityUtils.toString(resEntity, "utf-8");
				System.out.println(responseContent);
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
	}
}
