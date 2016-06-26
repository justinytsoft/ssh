//package com.push;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.net.ConnectException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//public class HttpRequestUtil {
//	private static String session_value;
//	final static String ENCORDING = "UTF-8";
//	private static Integer postNum = 1;
//
//	public static String PostHttp(String url, String params) {
//		PrintWriter out = null;
//		BufferedReader in = null;
//		String result = "";
//		try {
//			URL realUrl = new URL(url);
//			// 打开连接
//			URLConnection conn = realUrl.openConnection();
//			conn.setConnectTimeout(5*1000);  
//			conn.setReadTimeout(5*1000);
//			conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Keep-Alive");
//			conn.setRequestProperty("user-agent",
//					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
//			conn.setRequestProperty("Charset", "UTF-8");
//			// conn.setRequestProperty("Content-Type", "text/xml");
//			// 保存session信息
//			
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//
//			out = new PrintWriter(conn.getOutputStream());
//			out.print(params);
//			// 关闭流
//			out.flush();
//			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//			String line;
//			while ((line = in.readLine()) != null) {
//				result += "\n" + line;
//			}
//			
//			if (.isBlank(result)) {
//				result = "发送post请求错误";
//			}
//		} catch (ConnectException ce) {
//			result = "请检查网络";
//			ce.printStackTrace();
//		} catch (IOException e) {
//			result = "请求超时，请稍后再试";
//			e.printStackTrace();
//		}
//
//		finally {
//			try {
//				if (out != null) {
//					out.close();
//				}
//				if (in != null) {
//					in.close();
//				}
//			} catch (IOException ex) {
//
//			}
//		}
//		if(result.equals("{\"status\":\"205\",\"message\":\"请先登录\"}")) {
//			Boolean isLogin = getSession(app);
//			if(isLogin && postNum < 5) {
//				postNum++;
//				result = PostHttp(url, params,oilapp);
//			} else {
//				result = "服务不可用";
//				postNum = 1;
//			}
//		}
//		return result;
//	}
//	
//	/**
//	 * 发送post请求
//	 */
//	public static String PostHttp(String url, String params) {
//		PrintWriter out = null;
//		BufferedReader in = null;
//		String result = "";
//		try {
//			URL realUrl = new URL(url);
//			// 打开连接
//			URLConnection conn = realUrl.openConnection();
//			conn.setConnectTimeout(5*1000);  
//			conn.setReadTimeout(5*1000);
//			conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Keep-Alive");
//			conn.setRequestProperty("user-agent",
//					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
//			conn.setRequestProperty("Charset", "UTF-8");
//			// conn.setRequestProperty("Content-Type", "text/xml");
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//
//			out = new PrintWriter(conn.getOutputStream());
//			out.print(params);
//			// 关闭流
//			out.flush();
//			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//			String line;
//			while ((line = in.readLine()) != null) {
//				result += "\n" + line;
//			}
//			if (StringUtil.isBlank(result)) {
//				result = "发送post请求错误";
//			}
//		} catch (ConnectException ce) {
//			result = "请检查网络";
//			ce.printStackTrace();
//		} catch (Exception e) {
//			result = "请求超时，请稍后再试";
//			e.printStackTrace();
//		}
//
//		finally {
//			try {
//				if (out != null) {
//					out.close();
//				}
//				if (in != null) {
//					in.close();
//				}
//			} catch (IOException ex) {
//				
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 向服务器发送get请求
//	 * 
//	 */
//	public static String sendGet(String url, String params, OilApplication oil) {
//		app = oil;
//		String result = "";
//		BufferedReader in = null;
//		try {
//			String urlName = url + "?" + params + "&by_user=1";
//			URL realUrl = new URL(urlName);
//			URLConnection conn = realUrl.openConnection();
//			conn.setConnectTimeout(5*1000);  
//			conn.setReadTimeout(5*1000);
//			conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Keep-Alive");
//			conn.setRequestProperty("user-agent",
//					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
//			conn.setRequestProperty("Charset", "UTF-8");
//			if (app.getJSESSIONID() != null) {
//				conn.setRequestProperty("Cookie", app.getJSESSIONID());
//			} else {
//				Boolean isLogin = getSession(app);
//				if (isLogin) {
//					conn.setRequestProperty("Cookie", app.getJSESSIONID());
//				}
//			}
//			conn.connect();
//
//			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String line;
//			while ((line = in.readLine()) != null) {
//				result += "\n" + line;
//			}
//			JSONObject json = new JSONObject(result);
//			if (StringUtil.isBlank(result)) {
//				return "error";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "error";
//		} finally {
//			try {
//				if (in != null) {
//					in.close();
//				}
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
//		return result;
//	}
//
//	public static String sendGet(String url, String params) {
//		String result = "";
//		BufferedReader in = null;
//		try {
//			String urlName = url + "?" + params;
//			URL realUrl = new URL(urlName);
//			URLConnection conn = realUrl.openConnection();
//			conn.setConnectTimeout(5*1000);  
//			conn.setReadTimeout(5*1000);
//			conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Keep-Alive");
//			conn.setRequestProperty("user-agent",
//					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
//			conn.setRequestProperty("Charset", "UTF-8");
//			conn.connect();
//
//			in = new BufferedReader(
//					new InputStreamReader(conn.getInputStream()));
//			String line;
//			while ((line = in.readLine()) != null) {
//				result += "\n" + line;
//			}
//			JSONObject json = new JSONObject(result);
//			if (StringUtil.isBlank(result)) {
//				return "error";
//			}
//			if (json.getInt("status") == 200) {
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "error";
//		} finally {
//			try {
//				if (in != null) {
//					in.close();
//				}
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
//		return result;
//	}
//
//	public static String sendGetByLogin(String url, String params,
//			OilApplication oilapp) {
//		app = oilapp;
//		String result = "";
//		BufferedReader in = null;
//		try {
//			String urlName = url + "?" + params;
//			URL realUrl = new URL(urlName);
//			URLConnection conn = realUrl.openConnection();
//			conn.setConnectTimeout(5*1000);  
//			conn.setReadTimeout(5*1000);
//			conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Keep-Alive");
//			conn.setRequestProperty("user-agent",
//					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
//			conn.setRequestProperty("Charset", "UTF-8");
//			if (app.getJSESSIONID() != null) {
//				conn.setRequestProperty("Cookie", app.getJSESSIONID());
//			} else {
//				boolean isLogin = getSession(app);
//				if (isLogin) {
//					conn.setRequestProperty("Cookie", app.getJSESSIONID());
//				}
//			}
//			conn.connect();
//			Map<String, List<String>> map = conn.getHeaderFields();
//			for (String key : map.keySet()) {
//				//
//			}
//			in = new BufferedReader(
//					new InputStreamReader(conn.getInputStream()));
//			String line;
//			while ((line = in.readLine()) != null) {
//				result += "\n" + line;
//			}
//			if (StringUtil.isBlank(result)) {
//				return "error";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "error";
//		} finally {
//			try {
//				if (in != null) {
//					in.close();
//				}
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
//		return result;
//	}
//
//	// 从服务器获取数据字符串
//	public static String getDataFromServer(String path) {
//		StringBuffer sb = new StringBuffer();
//		String line = null;
//		try {
//			URL url = new URL(path);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setConnectTimeout(5*1000);  
//			conn.setReadTimeout(5*1000);
//			if (conn.getResponseCode() == 200) {
//				BufferedReader br = new BufferedReader(new InputStreamReader(
//						conn.getInputStream()));
//				while ((line = br.readLine()) != null) {
//					sb.append(line);
//				}
//			} else {
//				sb.append(conn.getResponseCode());
//			}
//		} catch (ConnectException ce) {
//			return "请检查网络";
//		}catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return sb.toString();
//	}
//
//	// 从服务器获取数据字符串
//	public static String getDataFromServer(String path, OilApplication oil) {
//		StringBuffer sb = new StringBuffer();
//		String line = null;
//		try {
//			URL url = new URL(path);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setConnectTimeout(5*1000);  
//			conn.setReadTimeout(5*1000);
//			if (app.getJSESSIONID() != null) {
//				conn.setRequestProperty("Cookie", app.getJSESSIONID());
//			} else {
//				Boolean isLogin = getSession(app);
//				if (isLogin) {
//					conn.setRequestProperty("Cookie", app.getJSESSIONID());
//				}
//			}
//			if (conn.getResponseCode() == 200) {
//				BufferedReader br = new BufferedReader(new InputStreamReader(
//						conn.getInputStream()));
//				while ((line = br.readLine()) != null) {
//					sb.append(line);
//				}
//			}
//		} catch (ConnectException ce) {
//			return "请检查网络";
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return sb.toString();
//	}
//
//	/**
//	 * 单个文件上传请求
//	 * 
//	 * @param file
//	 * @return
//	 * @throws Exception
//	 */
//	public static String uploadFile(File file) throws Exception {
//		String boundary = "---------------------------7db1c523809b2";// +java.util.UUID.randomUUID().toString();//
//		// 分割线
//
//		String filenames = file.getName().substring(0,
//				file.getName().indexOf("."));
//		String name = new String(filenames.getBytes(), "ISO-8859-1");
//		String fileName = new String(file.getName().getBytes(), "ISO-8859-1");
//		// 用来解析主机名和端口
//		URL url = new URL(Urls.URL + "services/uploadFile?fileName=" + name);
//		// 用来开启连接
//		StringBuilder sb = new StringBuilder();
//		// 用来拼装请求
//
//		// 文件部分
//		sb.append("--" + boundary + "\r\n");
//		sb.append("Content-Disposition: form-data; name=\"" + name
//				+ "\"; filename=\"" + fileName + "\"" + "\r\n");
//		sb.append("Content-Type: application/octet-stream" + "\r\n");
//		sb.append("\r\n");
//
//		// 将开头和结尾部分转为字节数组，因为设置Content-Type时长度是字节长度
//		byte[] before = sb.toString().getBytes(ENCORDING);
//		byte[] after = ("\r\n--" + boundary + "--\r\n").getBytes(ENCORDING);
//
//		// 打开连接, 设置请求头
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		// conn.setConnectTimeout(10000);
//		conn.setConnectTimeout(5*1000);  
//		conn.setReadTimeout(5*1000);
//		conn.setRequestMethod("POST");
//		conn.setRequestProperty("Content-Type",
//				"multipart/form-data; boundary=" + boundary);
//		conn.setRequestProperty("Content-Length", before.length + file.length()
//				+ after.length + "");
//
//		conn.setDoOutput(true);
//		conn.setDoInput(true);
//
//		// 获取输入输出流
//		OutputStream out = conn.getOutputStream();
//		// 将开头部分写出
//		out.write(before);
//
//		FileInputStream fis = new FileInputStream(file);
//		// 写出文件数据
//		byte[] buf = new byte[4096];
//		int len;
//		while ((len = fis.read(buf)) != -1)
//			out.write(buf, 0, len);
//
//		// 将结尾部分写出
//		out.write(after);
//
//		InputStream in = conn.getInputStream();
//		InputStreamReader isReader = new InputStreamReader(in);
//		BufferedReader bufReader = new BufferedReader(isReader);
//		String line = null;
//		String data = "";
//		while ((line = bufReader.readLine()) != null)
//			data += line;
//		Log.e("fromServer", "result=" + data);
//		boolean sucess = conn.getResponseCode() == 200;
//		in.close();
//		fis.close();
//		out.close();
//		conn.disconnect();
//
//		return data;
//	}
//
//	/**
//	 * 单张图片上传
//	 * 
//	 * @param file
//	 * @return
//	 * @throws Exception
//	 */
//	public static String uploadImage(File file) throws Exception {
//		String boundary = "---------------------------7db1c523809b2";// +java.util.UUID.randomUUID().toString();//
//		// 分割线
//		String filenames = file.getName().substring(0,
//				file.getName().indexOf("."));
//		String name = new String(filenames.getBytes(), "ISO-8859-1");
//		String fileName = new String(file.getName().getBytes(), "ISO-8859-1");
//		// 用来解析主机名和端口
//		URL url = new URL(Urls.URL + "services/uploadImage?fileName=" + name);
//		// 用来开启连接
//		StringBuilder sb = new StringBuilder();
//		// 用来拼装请求
//
//		// 文件部分
//		sb.append("--" + boundary + "\r\n");
//		sb.append("Content-Disposition: form-data; name=\"" + name
//				+ "\"; filename=\"" + fileName + "\"" + "\r\n");
//		sb.append("Content-Type: application/octet-stream" + "\r\n");
//		sb.append("\r\n");
//
//		// 将开头和结尾部分转为字节数组，因为设置Content-Type时长度是字节长度
//		byte[] before = sb.toString().getBytes(ENCORDING);
//		byte[] after = ("\r\n--" + boundary + "--\r\n").getBytes(ENCORDING);
//
//		// 打开连接, 设置请求头
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//		conn.setConnectTimeout(5*1000);  
//		conn.setReadTimeout(5*1000);
//		conn.setRequestMethod("POST");
//		conn.setRequestProperty("Content-Type",
//				"multipart/form-data; boundary=" + boundary);
//		conn.setRequestProperty("Content-Length", before.length + file.length()
//				+ after.length + "");
//
//		conn.setDoOutput(true);
//		conn.setDoInput(true);
//
//		// 获取输入输出流
//		OutputStream out = conn.getOutputStream();
//		FileInputStream fis = new FileInputStream(file);
//		// 将开头部分写出
//		out.write(before);
//
//		// 写出文件数据
//		byte[] buf = new byte[1024 * 5];
//		int len;
//		while ((len = fis.read(buf)) != -1)
//			out.write(buf, 0, len);
//
//		// 将结尾部分写出
//		out.write(after);
//
//		InputStream in = conn.getInputStream();
//		InputStreamReader isReader = new InputStreamReader(in);
//		BufferedReader bufReader = new BufferedReader(isReader);
//		String line = null;
//		String data = "";
//		while ((line = bufReader.readLine()) != null)
//			data += line;
//		Log.e("fromServer", "result=" + data);
//		boolean sucess = conn.getResponseCode() == 200;
//		in.close();
//		fis.close();
//		out.close();
//		conn.disconnect();
//
//		return data;
//	}
//
//	public static String uploadFiles(List<File> files) throws IOException {
//
//		String BOUNDARY = java.util.UUID.randomUUID().toString();
//		String PREFIX = "--", LINEND = "\r\n";
//		String MULTIPART_FROM_DATA = "multipart/form-data";
//		String CHARSET = "UTF-8";
//		URL uri = new URL(Urls.URL + "services/uploadFile?fileName=file");
//		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
//
//		conn.setConnectTimeout(5*1000);  
//		conn.setReadTimeout(5*1000);
//		conn.setDoInput(true);// 允许输入
//		conn.setDoOutput(true);// 允许输出
//		conn.setUseCaches(false);
//		conn.setRequestMethod("POST"); // Post方式
//		conn.setRequestProperty("connection", "keep-alive");
//		conn.setRequestProperty("Charsert", "UTF-8");
//		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
//				+ ";boundary=" + BOUNDARY);
//		// 首先组拼文本类型的参数
//		StringBuilder sb = new StringBuilder();
//
//		DataOutputStream outStream = new DataOutputStream(
//				conn.getOutputStream());
//		outStream.write(sb.toString().getBytes());
//		// 发送文件数据
//		if (files != null)
//			for (File f : files) {
//				Log.e("filepath", f.getPath() + "=====================");
//				StringBuilder sb1 = new StringBuilder();
//				sb1.append(PREFIX);
//				sb1.append(BOUNDARY);
//				sb1.append(LINEND);
//				sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
//						+ f.getName() + "\"" + LINEND);
//				sb1.append("Content-Type: multipart/form-data; charset="
//						+ CHARSET + LINEND);
//				sb1.append(LINEND);
//				outStream.write(sb1.toString().getBytes());
//
//				InputStream is = new FileInputStream(f);
//				byte[] buffer = new byte[1024];
//				int len = 0;
//				while ((len = is.read(buffer)) != -1) {
//					outStream.write(buffer, 0, len);
//				}
//				is.close();
//				outStream.write(LINEND.getBytes());
//			}
//		// 请求结束标志
//		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
//		outStream.write(end_data);
//		outStream.flush();
//		// 得到响应码
//		boolean success = conn.getResponseCode() == 200;
//		InputStream in = conn.getInputStream();
//		InputStreamReader isReader = new InputStreamReader(in);
//		BufferedReader bufReader = new BufferedReader(isReader);
//		String line = null;
//		String data = "";
//		while ((line = bufReader.readLine()) != null)
//			data += line;
//
//		outStream.close();
//		conn.disconnect();
//		return data;
//	}
//
//	public static String uploadImages(List<File> files) throws Exception {
//		String BOUNDARY = java.util.UUID.randomUUID().toString();
//		String PREFIX = "--", LINEND = "\r\n";
//		String MULTIPART_FROM_DATA = "multipart/form-data";
//		String CHARSET = "UTF-8";
//		URL uri = new URL(Urls.URL + "services/uploadImage?fileName=file");
//		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
//		// conn.setReadTimeout(5 * 1000);
//		conn.setConnectTimeout(5*1000);  
//		conn.setReadTimeout(5*1000);
//		conn.setDoInput(true);// 允许输入
//		conn.setDoOutput(true);// 允许输出
//		conn.setUseCaches(false);
//		conn.setRequestMethod("POST"); // Post方式
//		conn.setRequestProperty("connection", "keep-alive");
//		conn.setRequestProperty("Charsert", "UTF-8");
//		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
//				+ ";boundary=" + BOUNDARY);
//		// 首先组拼文本类型的参数
//		StringBuilder sb = new StringBuilder();
//
//		DataOutputStream outStream = new DataOutputStream(
//				conn.getOutputStream());
//		outStream.write(sb.toString().getBytes());
//		// 发送文件数据
//		if (files != null) {
//			for (File f : files) {
//				Log.e("filepath", f + "=====================");
//				StringBuilder sb1 = new StringBuilder();
//				sb1.append(PREFIX);
//				sb1.append(BOUNDARY);
//				sb1.append(LINEND);
//				sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
//						+ (f == null ? "" : f.getName()) + "\"" + LINEND);
//				sb1.append("Content-Type: multipart/form-data; charset="
//						+ CHARSET + LINEND);
//				sb1.append(LINEND);
//				outStream.write(sb1.toString().getBytes());
//				if (f != null) {
//					InputStream is = new FileInputStream(f);
//					byte[] buffer = new byte[4096];
//					int len = 0;
//					while ((len = is.read(buffer)) != -1) {
//						outStream.write(buffer, 0, len);
//					}
//					is.close();
//				}
//				outStream.write(LINEND.getBytes());
//			}
//		}
//		// 请求结束标志
//		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
//		outStream.write(end_data);
//		outStream.flush();
//		// 得到响应码
//		InputStream in = conn.getInputStream();
//		InputStreamReader isReader = new InputStreamReader(in);
//		BufferedReader bufReader = new BufferedReader(isReader);
//		String line = null;
//		String data = "";
//		while ((line = bufReader.readLine()) != null) {
//			data += line;
//		}
//		outStream.close();
//		conn.disconnect();
//		return data;
//	}
//
//	public static void HttpURLConnection_Post(String URL_Post,
//			OilApplication app) {
//		String resultData = "";
//		try {
//			// 通过openConnection 连接
//			URL url = new java.net.URL(URL_Post);
//			HttpURLConnection urlConn = (HttpURLConnection) url
//					.openConnection();
//			urlConn.setConnectTimeout(5*1000);  
//			urlConn.setReadTimeout(5*1000);
//			// 设置输入和输出流
//			urlConn.setDoOutput(true);
//			urlConn.setDoInput(true);
//
//			urlConn.setRequestMethod("POST");
//			urlConn.setUseCaches(false);
//			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
//			urlConn.setRequestProperty("Content-Type",
//					"application/x-www-form-urlencoded");
//			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
//			// 要注意的是connection.getOutputStream会隐含的进行connect。
//			urlConn.connect();
//			// DataOutputStream流
//			DataOutputStream out = new DataOutputStream(
//					urlConn.getOutputStream());
//			// 要上传的参数
//			String content = "j_username=" + app.getUsername() + "&j_password="
//					+ app.getPassword();
//			// 将要上传的内容写入流中
//			out.writeBytes(content);
//			Map<String, List<String>> handles = urlConn.getHeaderFields();
//			Set<String> key = handles.keySet();
//			for (Iterator it = key.iterator(); it.hasNext();) {
//				String s = (String) it.next();
//				for (String ss : handles.get(s)) {
//				}
//			}
//			String session = urlConn.getHeaderField("");
//			// 刷新、关闭
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					urlConn.getInputStream()));
//			String line;
//			while ((line = in.readLine()) != null) {
//				if (line.indexOf("jsessionid") > 0) {
//					int endIndex = line.lastIndexOf("\"");
//					// app.setWEBJSSIONID(line.substring(line.indexOf("jsessionid"),endIndex));
//
//				}
//			}
//			out.flush();
//			out.close();
//		} catch (Exception e) {
//			resultData = "连接超时";
//			e.printStackTrace();
//		}
//	}
//
//	public static void getFile(String url, Context context) {
//		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//		context.startActivity(intent);
//	}
//
//	public static String postLogin(String username, String password,
//			OilApplication oilapp) throws IOException {
//		app = oilapp;
//		PrintWriter out = null;
//		BufferedReader in = null;
//		String params = "username=" + username + "&password=" + password;
//		String result = "";
//		try {
//			URL realUrl = new URL(Urls.LOGIN);
//			// 打开连接
//			System.out.println("___________________开始请求————————————————————————");
//			URLConnection conn = realUrl.openConnection();
//			conn.setConnectTimeout(5*1000);  
//			conn.setReadTimeout(5*1000);
//			conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Keep-Alive");
//			conn.setRequestProperty("user-agent",
//					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
//			conn.setRequestProperty("Charset", "UTF-8");
//			// conn.setRequestProperty("Content-Type", "text/xml");
//			// 保存session信息
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//
//			out = new PrintWriter(conn.getOutputStream());
//			out.print(params);
//			// 关闭流
//			out.flush();
//			in = new BufferedReader(
//					new InputStreamReader(conn.getInputStream()));
//			if (app.getJSESSIONID() == null) {
//				try {
//					session_value = conn.getHeaderField("Set-Cookie");
//					String[] sessionId = session_value.split(";");
//					app.setJSESSIONID(sessionId[0]);
//				} catch (NullPointerException e) {
//					e.printStackTrace();
//				}
//			}
//			String line;
//			while ((line = in.readLine()) != null) {
//				result += "\n" + line;
//			}
//			if (StringUtil.isBlank(result)) {
//				result = "发送post请求错误";
//			}
//		} finally {
//			try {
//				if (out != null) {
//					out.close();
//				}
//				if (in != null) {
//					in.close();
//				}
//			} catch (IOException ex) {
//
//			}
//		}
//		return result;
//	}
//
//	public static boolean getSession(OilApplication oilapp) {
//		Boolean result = false;
//		BufferedReader in = null;
//		SharedPreferences pre = oilapp.getSharedPreferences("longinvalue", 1);
//		
//		try {
//			// 通过openConnection 连接
//			URL url = new URL(Urls.LOGIN);
//			HttpURLConnection urlConn = (HttpURLConnection) url
//					.openConnection();
//			urlConn.setConnectTimeout(5*1000);  
//			urlConn.setReadTimeout(5*1000);
//			// 设置输入和输出流
//			urlConn.setDoOutput(true);
//			urlConn.setDoInput(true);
//
//			urlConn.setRequestMethod("POST");
//			urlConn.setUseCaches(false);
//			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
//			urlConn.setRequestProperty("Content-Type",
//					"application/x-www-form-urlencoded");
//			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
//			// 要注意的是connection.getOutputStream会隐含的进行connect。
//			urlConn.connect();
//			// DataOutputStream流
//			DataOutputStream out = new DataOutputStream(
//					urlConn.getOutputStream());
//			// 要上传的参数
//			String content = "username="
//					+ pre.getString("name", "") + "&password="
//					+ pre.getString("pass", "");
//			System.out.println(content);
//			// 将要上传的内容写入流中
//			out.writeBytes(content);
//			if (urlConn.getResponseCode() == 200) {
//				String session = urlConn.getHeaderField("Set-Cookie");
//				oilapp.setJSESSIONID(session);
//				result = true;
//			}
//			// 刷新、关闭
//			in = new BufferedReader(new InputStreamReader(
//					urlConn.getInputStream()));
//			out.flush();
//			out.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			result = false;
//		} finally {
//			try {
//				if (in != null) {
//					in.close();
//				}
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
//		return result;
//	}
//}
