package com.goldCityWeb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.MainList;
import com.goldCityWeb.service.IRedbagService;
import com.goldCityWeb.util.EasyuiPaging;

/**
 * 发放记录
 * @author Administrator
 *
 */
@RequestMapping("/merchant/record")
@Controller
public class ME_RecordController {

	@Autowired
	private IRedbagService redbagService;
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(){
		return "merchant/money_gived";
	}
	
	/**
	 * 已完成的发放记录
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/complatedList")
	@ResponseBody
	public EasyuiPaging<MainList> complatedList(@RequestParam(required=false) Integer page,
										   @RequestParam(required=false) Integer rows){
		EasyuiPaging<MainList> ep = new EasyuiPaging<MainList>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("page", ((page-1)*rows));
		param.put("rows", rows);
		param.put("adv_status", 2);
		param.put("verify_status", 1);
		
		List<MainList> advs = redbagService.queryAdv(param);
		int total = redbagService.queryAdvTotal(param);
		
		ep.setRows(advs);
		ep.setTotal(total);
		return ep;
	}
	
	/**
	 * 未完成的发放记录
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/uncomplatedList")
	@ResponseBody
	public EasyuiPaging<MainList> uncomplatedList(@RequestParam(required=false) Integer page,
										   @RequestParam(required=false) Integer rows){
		EasyuiPaging<MainList> ep = new EasyuiPaging<MainList>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("page", ((page-1)*rows));
		param.put("rows", rows);
		param.put("adv_status", 3); //查询未完成的广告
		param.put("verify_status", 1);
		
		List<MainList> advs = redbagService.queryAdv(param);
		int total = redbagService.queryAdvTotal(param);
		
		ep.setRows(advs);
		ep.setTotal(total);
		return ep;
	}
}
