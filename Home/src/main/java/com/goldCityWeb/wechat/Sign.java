package com.goldCityWeb.wechat;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.goldCityWeb.util.JsonUtil;
import com.goldCityWeb.wechat.model.message.response.ResponseStatus;



public class Sign {
	public static String jsapi_ticket = "";
	
    public static synchronized Map<String, String> getSign(String url) {
    	
    	//test_Access();
    	if(StringUtils.isBlank(jsapi_ticket)){
    		getJsapi_ticket();
    	}
    	

        // 注意 URL 一定要动态获取，不能 hardcode
        //String url = "http://wx2.scdzjx.cn/newexcavator/mobile/test_share";
        Map<String, String> ret = sign(jsapi_ticket, url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        return ret;
    };

    public static void test_Access(){
    	CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/groups/get?access_token="+AccessTokenTool.getAccessToken(false));
			
			//httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
			
			CloseableHttpResponse response = httpclient.execute(httpGet);
			try {
				HttpEntity entity = response.getEntity();
				
				String responseContent = EntityUtils.toString(entity, "utf-8");
				//JSONObject object = new JSONObject();
				System.out.println("===================>>" + responseContent);
				//object = new JSONObject(responseContent);
				//String code = (String)object.get("errcode");
				ResponseStatus status = null;
				try {
        			status = JsonUtil.fromJson(responseContent, ResponseStatus.class);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
            	if (status != null && status.getErrcode() != null) {
            		Integer errcode = status.getErrcode();
            		System.out.println("errcode===========>" + errcode);
            		if (errcode.intValue() == 42001) {
            			AccessTokenTool.getAccessToken(true);
            		}
            	}
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
    
    public static void getJsapi_ticket(){
    	CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+AccessTokenTool.getAccessToken(false)+"&type=jsapi");
			
			httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
			
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				
				String responseContent = EntityUtils.toString(entity, "utf-8");
				JSONObject object = new JSONObject();
				System.out.println("===================>>" + responseContent);
				object = new JSONObject(responseContent);
				jsapi_ticket = (String)object.get("ticket");
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
    
    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
