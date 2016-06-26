package com.goldCityWeb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.dao.BaseDao;
import com.goldCityWeb.domain.ChargeType;
import com.goldCityWeb.domain.City;
import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.Position;
import com.goldCityWeb.domain.Province;
import com.goldCityWeb.domain.Section;
import com.goldCityWeb.domain.SysUsers;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.service.IMessageService;
import com.goldCityWeb.service.UserService;

@RequestMapping("/merchant")
@Controller
public class ME_HomeController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private BaseService baseService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model,
						@RequestParam(required = false) String error) {
		model.addAttribute("error", error);
		return "merchant/login";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model,@RequestParam(required = false) String error) {
		return "merchant/frame/iframe";
	}
	
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String top(HttpServletRequest request, Model model,@RequestParam(required = false) String error) {
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user!=null){
			UserDetail ud = userService.queryUserDetailById(user.getId());
			Company company = companyService.queryCompanyByUserId(user.getId());
			model.addAttribute("user", ud==null?new UserDetail():ud);
			model.addAttribute("company", company==null?new Company():company);
		}
		return "merchant/frame/top";
	}
	
	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String left(HttpServletRequest request, Model model,@RequestParam(required = false) String error) {
		return "merchant/frame/left";
	}
	
	/**
	 * 获取地区
	 * @return
	 */
	@RequestMapping("getSections")
	@ResponseBody
	public List<Section> getSections(){
		List<Section> sections = baseDao.querySection();
		return CollectionUtils.isEmpty(sections) ? new ArrayList<Section>() : sections;
	}
	
	/**
	 * 获取支付方式
	 * @return
	 */
	@RequestMapping("getChargeType")
	@ResponseBody
	public List<ChargeType> getChargeType(@RequestParam(required=false) Boolean needAll){
		List<ChargeType> chargeType = baseDao.queryChargeType();
		if(needAll!=null && needAll){
			ChargeType all = new ChargeType();
			all.setId(-1);
			all.setName("不限");
			chargeType.add(0, all);
		}
		return CollectionUtils.isEmpty(chargeType) ? new ArrayList<ChargeType>() : chargeType;
	}
	
	/**
	 * 获取省份
	 * @return
	 */
	@RequestMapping(value = "/getProvince", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<Province> getLocation() {
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<Province> sc = baseService.queryAllProvince(param,null);
		if(sc==null){
			sc = new ArrayList<Province>();
		}
		
		return sc;
	}
	
	/**
	 * 获取城市
	 * @return
	 */
	@RequestMapping(value = "/getCity", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<City> getCity(@RequestParam Integer provinceId) {
	
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("provinceId", provinceId);
		List<City> city = baseService.queryCityByProvince(param,null);
		if(city==null){
			city = new ArrayList<City>();
		}
		
		return city;
	}
	
	/**
	 * 获取区县
	 * @return
	 */
	@RequestMapping(value = "/getPosition", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<Position> getPosition(@RequestParam Integer c_id) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("c_id", c_id);
		List<Position> position = baseService.queryPositionByCity(param,null);
		if(position==null){
			position = new ArrayList<Position>();
		}
		
		return position;
	}
}
