package com.goldCityWeb.wechat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import com.tencent.common.Configure;
import com.tencent.common.MD5;
import com.tencent.common.RandomStringGenerator;
import com.tencent.common.Util;

public class PaySign {
	public static String jsapi_ticket = "";
	
    public static synchronized Map<String, String> getSign(String prepay_id) {
    	
    	
    	

        // 注意 URL 一定要动态获取，不能 hardcode
        //String url = "http://wx2.scdzjx.cn/newexcavator/mobile/test_share";
        Map<String, String> ret = sign(prepay_id);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        return ret;
    };


    public static String getSign(Map<String,String> map){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,String> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        
        result += "key=" + Configure.getKey();
        System.out.println("-=-=-=+"+result+"-=-=-=-=-=");
        //Util.log("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        Util.log("Sign Result:" + result);
        return result;
    }
    
    


    
    public static Map<String, String> sign(String prepay_id) {
        Map<String, String> ret = new HashMap<String, String>();
        
        
        ret.put("appid", Configure.getAppid());
        ret.put("partnerid", Configure.getMchid());
        ret.put("prepayid", prepay_id);
        ret.put("timestamp", create_timestamp());
        ret.put("noncestr", create_nonce_str());
        ret.put("package", "Sign=WXPay");
        
        String signature = getSign(ret);

        //注意这里参数名必须全部小写，且必须有序
        

        ret.put("sign", signature);
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
        return RandomStringGenerator.getRandomStringByLength(32);
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
