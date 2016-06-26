package com.goldCityWeb.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * 工具类: 提供密码加密解密算法等工具方法。
 * 
 */
public class PasswordUtil {

	private static MessageDigest messageDigest;

	/**
	 * 将密码进行加密处理。处理后的结果不可逆。其中算法要进行两次加密和交叉换位算法。增加破解难度
	 * @param password
	 * @return
	 */
	public static String encode(String password) {
		if (messageDigest == null) {
			try {
				messageDigest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}
		messageDigest.reset();
		byte[] buf = messageDigest.digest(password.getBytes());
		//将加密后的结果进行交叉换位
		buf=exchangeByte(buf);
		//再次进行加密和交叉换位
		buf=messageDigest.digest(buf);
		buf=exchangeByte(buf);

		return toString(buf);
	}
	
	/**
	 * 算法与encode(String)相同。增加可以截断出指定长度的字符串。
	 * @param password
	 * @param length
	 * @return
	 */
	public static String encode(String password,Integer length){
		return encode(password).substring(0,length);
	}
	
	/**
	 * 将加密后的结果进行交叉换位，增加破解难度.<br/>
	 * 换位方式: 拆分成为2个登长数组，然后交叉顺序放入新数组中。
	 * @param buf
	 * @return
	 */
	private static byte[] exchangeByte(byte[] buf){
		byte[] rlt=new byte[buf.length];
		int size=buf.length/2;
		int position=0;
		for(int i=0;i<size;i++){
			rlt[position++]=buf[i];
			rlt[position++]=buf[i+size];
		}
		return rlt;
	}

	/**
	 * 将生成的加密串转换成为字符串输出
	 * @param buf
	 * @return
	 */
	public static String toString(byte[] buf) {
		StringBuilder sb = new StringBuilder();
		for (byte b : buf) {
			sb.append(Integer.toHexString(0xFF & b));
		}
		return sb.toString();
	}
	

	public static void main(String [] args) {
		PasswordEncoder pe = new StandardPasswordEncoder();
		System.out.println(pe.matches("1111", "c9d242d02310d6081553d9e009080c143ce91cd050a3099b467e50a97b748093b290aa5a2ef4f8d0"));
//		System.out.println(pe.encode("1111"));
//		System.out.println(PasswordUtil.encode("1111"));
	}
}
