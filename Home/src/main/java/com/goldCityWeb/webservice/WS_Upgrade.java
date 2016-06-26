package com.goldCityWeb.webservice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.Location;
import com.goldCityWeb.domain.Upgrade;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.json.ResponseStatus;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.service.UpgradeService;
import com.goldCityWeb.util.Constants;




@Controller
@RequestMapping(value = "/services")
public class WS_Upgrade {

	@Autowired
	private UpgradeService upgradeService;
	
	@Autowired
	private BaseService baseService;
	/**
	 * 获取更新信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/upgrade/info", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper upgradeInfo(HttpServletRequest request,Model model,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude) {
		JsonResWrapper res = new JsonResWrapper();
		UserDetail users = (UserDetail) request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
		if(users != null) {
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(users.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		}
		res.setStatus(ResponseStatus.OK);
		List<Upgrade> us = upgradeService.selectAllUpgrade();
		res.setData(us);
		return res;
	}
	
	
	/**
	 * 获取更新信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/share/link", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper shareLink(HttpServletRequest request,Model model,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude) {
		JsonResWrapper res = new JsonResWrapper();
		
		res.setStatus(ResponseStatus.OK);
		res.setData("http://baidu.com");
		return res;
	}
}
