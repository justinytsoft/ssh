package com.goldCityWeb.webservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/services/system")
public class WS_System {

	/**
	 * 规则
	 * @return
	 */
	@RequestMapping(value="/rules")
	public String goldRules() {
		return "pages/mobile/system/rules";
	}
	
	/**
	 * 服务条款
	 * @return
	 */
	@RequestMapping(value="/provision")
	public String provision() {
		return "pages/mobile/system/provision";
	}
	
	/**
	 * 用户协议
	 * @return
	 */
	@RequestMapping(value="/agreement")
	public String agreement() {
		return "pages/mobile/system/agreement";
	}
	
	/**
	 * 隐私策略
	 * @return
	 */
	@RequestMapping(value="/privacy")
	public String privacy() {
		return "pages/mobile/system/privacy";
	}
}
