package com.goldCityWeb.wechat;

import java.util.Calendar;
import java.util.Date;

import com.goldCityWeb.domain.PayReqData;
import com.goldCityWeb.util.DateFormatter;


public class WxPayTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PayReqData p = new PayReqData("JSAPI", "土豪金32G","土豪金32G大甩卖", "testattach", "t003", 1, "WEB", "8.8.8.8", "", "20150801130810", "www.baidu.com");
		/*try {
		WxPayUtil wx = new WxPayUtil();
			String reqXml = wx.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", p);
			
			Map<String, String> requestMap = MessageUtils.parseXml(reqXml);
			
			
			System.out.println("+++++"+requestMap.get("return_code"));
			System.out.println("+++++"+requestMap.get("return_msg"));*/
			
			Calendar ca = Calendar.getInstance();
			ca.setTime(new Date());
			ca.add(Calendar.MINUTE, 30);
			Date d = new Date();
			System.out.println(DateFormatter.stringToDate("20150813111910", "yyyyMMddHHmmss"));
			//System.out.println("++++++"+DateFormatter.dateToString(new Date(), "yyyyMMddHHmmss"));
			//System.out.println("++++++"+DateFormatter.dateToString(ca.getTime(), "yyyyMMddHHmmss"));
			
		/*} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}



