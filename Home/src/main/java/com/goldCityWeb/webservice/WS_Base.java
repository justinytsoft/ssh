package com.goldCityWeb.webservice;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.ChargeType;
import com.goldCityWeb.domain.City;
import com.goldCityWeb.domain.HeadList;
import com.goldCityWeb.domain.Location;
import com.goldCityWeb.domain.Major;
import com.goldCityWeb.domain.OldLevel;
import com.goldCityWeb.domain.Position;
import com.goldCityWeb.domain.Province;
import com.goldCityWeb.domain.Section;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.util.Constants;
import com.goldCityWeb.util.PageSupport;
import com.goldCityWeb.util.SettingUtils;

@Controller
@RequestMapping(value = "/services")
public class WS_Base {
	
	@Autowired
	private BaseService baseService;
	
	

	@RequestMapping(value = "/getMessage", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper getMessage(HttpServletRequest request, @RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,Model model
			) {
		JsonResWrapper response = new JsonResWrapper();
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
		
		Map<String,Object> cosp = new HashMap<String,Object>();
		cosp.put("flag", 1);
		cosp.put("msg", "恭喜用户获得大红包!");
		response.setData(cosp);
		return response;
	}
	
	
	@RequestMapping(value = "/getSection", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper getCollege(HttpServletRequest request, @RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,Model model
			) {
		JsonResWrapper response = new JsonResWrapper();
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
		List<Section> sc = baseService.querySection();
		if(sc==null){
			sc = new ArrayList<Section>();
		}
		Map<String,Object> cosp = new HashMap<String,Object>();
		cosp.put("Section", sc);
		response.setData(cosp);
		return response;
	}
	
	@RequestMapping(value = "/getProvince", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper getLocation(HttpServletRequest request, Model model
			) {
		JsonResWrapper response = new JsonResWrapper();
		
		Map<String, Object> param = new HashMap<String, Object>();
//		PageSupport ps = PageSupport.initPageSupport(request);
		
		List<Province> sc = baseService.queryAllProvince(param,null);
		if(sc==null){
			sc = new ArrayList<Province>();
		}
		Map<String,Object> cosp = new HashMap<String,Object>();
		cosp.put("Province", sc);
		response.setData(cosp);
		return response;
	}
	
	@RequestMapping(value = "/getCity", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper getCity(HttpServletRequest request, Model model ,@RequestParam Integer provinceId
			) {
		JsonResWrapper response = new JsonResWrapper();
		
		Map<String, Object> param = new HashMap<String, Object>();
//		PageSupport ps = PageSupport.initPageSupport(request);
		param.put("provinceId", provinceId);
		List<City> city = baseService.queryCityByProvince(param,null);
		if(city==null){
			city = new ArrayList<City>();
		}
		Map<String,Object> cosp = new HashMap<String,Object>();
		cosp.put("City", city);
		response.setData(cosp);
		return response;
	}
	
	@RequestMapping(value = "/getPosition", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper getPosition(HttpServletRequest request, Model model ,@RequestParam Integer c_id
			) {
		JsonResWrapper response = new JsonResWrapper();
		/*UserDetail users = (UserDetail) request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
		if(users != null) {
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(users.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		}*/
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("c_id", c_id);
		List<Position> position = baseService.queryPositionByCity(param,null);
		if(position==null){
			position = new ArrayList<Position>();
		}
		Map<String,Object> cosp = new HashMap<String,Object>();
		cosp.put("Position", position);
		response.setData(cosp);
		return response;
	}
	
	
	@RequestMapping(value = "/getHeadList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper getHeadList(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude, Model model
			) {
		JsonResWrapper response = new JsonResWrapper();
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
		List<HeadList> sc = baseService.queryHeadList();
		if(sc==null){
			sc = new ArrayList<HeadList>();
		} else {
			for(HeadList s:sc){
				s.setPath(SettingUtils.getCommonSetting("base.url")+s.getPath());
			}
		}
		Map<String,Object> cosp = new HashMap<String,Object>();
		cosp.put("HeadList", sc);
		response.setData(cosp);
		return response;
	}
	
	@RequestMapping(value = "/getMajor", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper getMajor(HttpServletRequest request, Model model,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude, @RequestParam(required = false) Integer college_id, @RequestParam(required = false) String search_name
			) {
		
		JsonResWrapper response = new JsonResWrapper();
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
		if(college_id==null || college_id.intValue() < 0){
			response.setStatus("201");
			response.setMessage("学校id为空或不正确!");
			return response;
		}
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String,Object> param = new HashMap<String,Object>();
		if (!StringUtils.isBlank(search_name)) {
			try {
				param.put("search_name", new String(search_name.getBytes("ISO-8859-1"),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		param.put("college_id", college_id);
		
		List<Major> mjs = baseService.queryMajor(param, ps);
		if(mjs==null){
			mjs = new ArrayList<Major>();
		}
		Map<String,Object> cosp = new HashMap<String,Object>();
		cosp.put("Majors", mjs);
		response.setData(cosp);
		return response;
	}
	
	
	
	@RequestMapping(value = "/getOldLevel", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper getOldLevel(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude, Model model
			) {
		
		JsonResWrapper response = new JsonResWrapper();
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
		
		List<OldLevel> ol = baseService.queryAllOldLevel();
		if(ol==null){
			ol = new ArrayList<OldLevel>();
		}
		Map<String,Object> cosp = new HashMap<String,Object>();
		cosp.put("OldLevels", ol);
		response.setData(cosp);
		return response;
	}
	
	@RequestMapping(value = "/getPayType", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper getPayType(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude, Model model
			) {
		
		JsonResWrapper response = new JsonResWrapper();
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
		
		List<ChargeType> ol = baseService.queryChargeType();
		if(ol==null){
			ol = new ArrayList<ChargeType>();
		}
		Map<String,Object> cosp = new HashMap<String,Object>();
		cosp.put("ChargeType", ol);
		response.setData(cosp);
		return response;
	}
}
