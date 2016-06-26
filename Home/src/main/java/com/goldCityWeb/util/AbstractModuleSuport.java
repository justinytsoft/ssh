/**
 * 
 */
package com.goldCityWeb.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.goldCityWeb.dao.BaseDaoSupport;

/**
 * 提供Web层和服务层常用的各种功能
 * 
 */
public class AbstractModuleSuport extends BaseDaoSupport {
	/**
	 * 存放消息文件的根目录
	 */
	public static final String MESSAGE_ROOT = "/messages/";

	/**
	 * 页面上提示信息对应的键值
	 */
	public static final String KEY_PAGE_INFORMATION = "KEY_PAGE_INFORMATION";
	/**
	 * 页面上错误信息对应的键值
	 */
	public static final String KEY_PAGE_ERROR = "KEY_PAGE_ERROR";

	protected Logger log = Logger.getLogger(getClass());

	private static Map<String, Properties> messageResources = new ConcurrentHashMap<String, Properties>();



	protected String getText(String key) {
		MessageResource msgRes = getClass().getAnnotation(MessageResource.class);
		if (msgRes == null) {
			// 如果没有注解，则直接返回key
			return key;
		}

		if ("".equals(msgRes.value())) {
			// 如果未设置消息文件内容，则直接返回key
			return key;
		}

		String uri = MESSAGE_ROOT + msgRes.value();
		loadMessage(uri);
		Properties prop = messageResources.get(uri);
		String content = prop.getProperty(key);
		if (content == null) {
			// 默认未找到则返回Key
			content = key;
		}
		return content;
	}

	protected String getText(String key, Object... objects) {
		MessageResource msgRes = getClass().getAnnotation(MessageResource.class);
		if (msgRes == null) {
			// 如果没有注解，则直接返回key
			return key;
		}

		if ("".equals(msgRes.value())) {
			// 如果未设置消息文件内容，则直接返回key
			return key;
		}

		String uri = MESSAGE_ROOT + msgRes.value();
		loadMessage(uri);
		Properties prop = messageResources.get(uri);
		String content = prop.getProperty(key);
		if (content == null) {
			// 默认未找到则返回Key
			content = key;
		} else {
			for (int i = 0; i < objects.length; i++) {
				content = content.replaceAll("\\{" + i + "\\}", objects[i] + "");
			}
		}

		return content;
	}

	/**
	 * 加载消息文件，如果已经加载则忽略
	 * 
	 * @param uri
	 */
	private void loadMessage(String uri) {
		if (messageResources.get(uri) == null) {
			// 没有加载则加载
			InputStream is = getClass().getResourceAsStream(uri);
			if (is == null) {
				throw new IllegalArgumentException("Resource [" + uri + "] not found");
			}
			Properties prop = new Properties();
			try {
				prop.load(is);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
			messageResources.put(uri, prop);
		}
	}

	/**
	 * 向页面输出提示信息，可以多个
	 * 
	 * @param message
	 */
	@SuppressWarnings("unchecked")
	public void addInformation(HttpServletRequest request,String message) {
		HttpServletRequest req = request;
		List<String> messages = (List<String>) req.getAttribute(KEY_PAGE_INFORMATION);
		if (messages == null) {
			messages = new ArrayList<String>();
			req.setAttribute(KEY_PAGE_INFORMATION, messages);
		}
		messages.add(message);
	}

	/**
	 * 向页面输出错误信息，可以多个
	 * 
	 * @param message
	 */
	@SuppressWarnings("unchecked")
	public void addError(HttpServletRequest request,String message) {
		HttpServletRequest req = request;
		List<String> messages = (List<String>) req.getAttribute(KEY_PAGE_ERROR);
		if (messages == null) {
			messages = new ArrayList<String>();
			req.setAttribute(KEY_PAGE_ERROR, messages);
		}
		messages.add(message);
	}

}
