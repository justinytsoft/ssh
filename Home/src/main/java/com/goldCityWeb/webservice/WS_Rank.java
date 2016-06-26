package com.goldCityWeb.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.Location;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.json.ResponseStatus;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.service.IRankService;
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.Constants;
import com.goldCityWeb.util.SettingUtils;

@Controller
@RequestMapping("/services/rank")
public class WS_Rank {

	@Autowired
	private IRankService rankService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BaseService baseService;
	
	/**
	 * 金币达人排行榜
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/goldMaster", method = {RequestMethod.GET,RequestMethod.POST})
	public JsonResWrapper goldMaster(HttpServletRequest request,@RequestParam(required=false) Integer type,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude){
		JsonResWrapper jrw = new JsonResWrapper();
		UserDetail ud = getSessionUser(request);
		if(ud==null){
			jrw.setMessage("session失效请重新登录!");
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			return jrw;
		}
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(ud.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		Map<String, Object> param = new HashMap<String, Object>();
		if(type!=null){
			param.put("type", type);
		}
		param.put("limit", 20);
		param.put("offset", 0);
		List<UserDetail> uds = rankService.queryGoldMaster(param);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("userDetail", isEmpty(uds));
		
		Map<String, Object> pa = new HashMap<String, Object>();
		pa.put("id", ud.getId());
		pa.put("type", 2);
		UserDetail udi = rankService.queryUserRankById(pa);
		if(udi==null){
			udi = new UserDetail();
			udi = userService.queryUserDetailById(ud.getId());
			udi.setGold_count(0f);
			
			udi.setRank((rankService.querySysCount(pa))+1);
		}
		data.put("myRank", udi);
		
		jrw.setData(data);
		return jrw;
	}
	

	/**
	 * 金主排行榜
	 * @param request
	 * @param type(0今日,1上周,2上月)
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/companyMaster")
	public JsonResWrapper  companyMaster(HttpServletRequest request,@RequestParam(required=false) Integer type){
		JsonResWrapper jrw = new JsonResWrapper();
		UserDetail ud = getSessionUser(request);
		if(ud==null){
			jrw.setMessage("session失效请重新登录!");
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			return jrw;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		if(type!=null){
			param.put("type", type);
		}
		param.put("limit", 20);
		param.put("offset", 0);
		List<Company> cmps = rankService.queryCompanyMaster(param);
		System.out.println("List cmps:"+cmps);
		Map<String, Object> data = new HashMap<String, Object>();
		if(!CollectionUtils.isEmpty(cmps)){
			for(Company cm:cmps){
				cm.setLogo(SettingUtils.getCommonSetting("base.image.url") + cm.getLogo());
			}
		}
		data.put("companyDetail", isEmpty(cmps));
		Map<String, Object> pa = new HashMap<String, Object>();
		pa.put("id", ud.getId());
		pa.put("type", 3);
		Company c = rankService.queryCompanyRankById(pa);
		if(c==null){
			c = new Company();
			c.setGold_count(0f);
			UserDetail u = userService.queryUserDetailById(ud.getId());
			c.setNick_name(u.getNick_name());
			c.setLevel(u.getLevel());
			c.setRank((rankService.querySysCount(pa))+1);
		} else {
			c.setLogo(SettingUtils.getCommonSetting("base.image.url") + c.getLogo());
		}
		data.put("myRank", c);
		jrw.setData(data);
		return jrw;
	}
	
	/**
	 * 判断集合是否为 null
	 * @param param
	 * @return 如果不为 null 直接返回，否则创建一个新的 ArrayList 返回
	 */
	private <T> List<T> isEmpty(List<T> param){
		return CollectionUtils.isEmpty(param) ? new ArrayList<T>() : param;
	}
	
	/**
	 * 获取session里的用户
	 * @param request
	 * @return
	 */
	private UserDetail getSessionUser(HttpServletRequest request){
		return (UserDetail)request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
	}
}
