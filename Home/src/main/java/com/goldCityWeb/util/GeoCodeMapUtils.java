package com.goldCityWeb.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GeoCodeMapUtils {
	
	private static String KEY = "d9abb0fb49a52057ebf1f9a77438b2d7";  
	private static Pattern pattern = Pattern.compile("\"location\":\"(\\d+\\.\\d+),(\\d+\\.\\d+)\"");  
   
    public static float[] addressToGPS(String address) {  
        try {  
            
        	address = URLEncoder.encode(address, "utf-8");
            String url = String .format("http://restapi.amap.com/v3/geocode/geo?&s=rsv3&address=%s&key=%s", address, KEY); 
            URL myURL = null; 
            URLConnection httpsConn = null; 
            try { 
            	myURL = new URL(url); 
            } catch (MalformedURLException e) { 
            	e.printStackTrace(); 
            } 
            InputStreamReader insr = null;
			BufferedReader br = null;
			httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
			if (httpsConn != null) { 
				insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
				br = new BufferedReader(insr); 
				String data = "";
				String line = null; 
				while((line= br.readLine())!=null){
					data+=line;
				} 
	            Matcher matcher = pattern.matcher(data);  
	            if (matcher.find() && matcher.groupCount() == 2) {  
	                float[] gps = new float[2];  
	                gps[0] = Float.valueOf(matcher.group(1));  
	                gps[1] = Float.valueOf(matcher.group(2));  
	                return gps;  
	            }
			}
        }catch (Exception e) {
        	e.printStackTrace(); 
        	return null;
        }
        return null;
    }
    
    public static void main(String[] args) {  
    	float [] data = GeoCodeMapUtils.addressToGPS("成都市武侯区天府软件园c区7栋");
    	if(data!=null){
    		System.out.println("经度:"+data[0]);
    		System.out.println("纬度:"+data[1]);
    	} else {
    		System.out.println("不存在");
    	}
    } 

}
		