/**
 * 
 */
package com.goldCityWeb.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.goldCityWeb.util.SettingUtils;

/**
 * @author randy
 *
 */
public class StartupListener extends ContextLoaderListener implements ServletContextListener {
	private static final Log log = LogFactory.getLog(StartupListener.class);
	
	public void contextInitialized(ServletContextEvent event){
		ServletContext context = event.getServletContext();
		
		if(log.isDebugEnabled()){
			log.debug("start initializing context...");
		}
		
		//call Spring's context ContextLoaderListener to initialize all the context files specified in web.xml
		super.contextInitialized(event);
		
		if(log.isDebugEnabled()){
			log.debug("end initializing context...");
		}
		String baseImageUrl = SettingUtils.getCommonSetting("base.image.url");
		context.setAttribute("baseImageUrl", baseImageUrl);
		String baseFileUrl = SettingUtils.getCommonSetting("base.file.url");
		context.setAttribute("baseFileUrl", baseFileUrl);
		String baseHtmlUrl = SettingUtils.getCommonSetting("base.html.url");
		context.setAttribute("baseHtmlUrl", baseHtmlUrl);
		
	}
}
