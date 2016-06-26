package com.goldCityWeb.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.SysAdv;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.json.ResponseStatus;
import com.goldCityWeb.service.SysAdvService;
import com.goldCityWeb.util.PageSupport;

@Controller
@RequestMapping(value="/services/sysadv")
public class WS_SysAdv {
	@Autowired
	private SysAdvService sysAdvService;
	
	/**
	 * 查看公告详情内容
	 * @return
	 */
	@RequestMapping(value="/detail")
	public String sysAdvContent(Model model, @RequestParam Integer id) {
		SysAdv sa = sysAdvService.queryAysAdvById(id);
		model.addAttribute("sa", sa);
		return "pages/sysadv/content_detail";
	}
	
	@RequestMapping(value="/list")
	public String sysAdvList(Model model, HttpServletRequest request) {
		Map<String, Object> param1 = new HashMap<String, Object>();
		param1.put("type", 0);
		PageSupport ps1 = new PageSupport();
		ps1.setPageSize(2);
		List<SysAdv> imageAdvList = sysAdvService.querySysAdvList(param1, ps1);
		model.addAttribute("imageList", imageAdvList);
		Map<String, Object> param2 = new HashMap<String, Object>();
		param2.put("type", 1);
		PageSupport ps2 = PageSupport.initPageSupport(request);
		List<SysAdv> textAdvList = sysAdvService.querySysAdvList(param2, ps2);
		model.addAttribute("textList", textAdvList);
		
		return "pages/sysadv/mobile_list";
	}
	
	@RequestMapping(value="/getlist")
	public @ResponseBody JsonResWrapper sysAdvGetList(HttpServletRequest request, @RequestParam(required=false) Integer type) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(type != null) {
			param.put("type", type);
		} else {
			param.put("type", 1);
		}
		PageSupport ps = PageSupport.initPageSupport(request);
		List<SysAdv> saList = sysAdvService.querySysAdvList(param, ps);
		JsonResWrapper response = new JsonResWrapper();
		response.setData(saList);
		response.setStatus(ResponseStatus.OK);
		return response;
	}
}
