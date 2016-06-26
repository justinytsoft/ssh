package com.goldCityWeb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.goldCityWeb.domain.HorseMessage;
import com.goldCityWeb.service.HorseMessageService;
import com.goldCityWeb.util.PageSupport;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {
	@Autowired
	private HorseMessageService horseMessageService;
	
	@RequestMapping(value="/list", method={RequestMethod.POST, RequestMethod.GET})
	public String noticeList(HttpServletRequest request, Model model, @RequestParam(required=false) Integer type) {
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		if(type!= null && type.intValue() >= 0) {
			param.put("type", type);
			model.addAttribute("type", type);
		}
		List<HorseMessage> hmList = horseMessageService.queryHorseMessage(ps, param);
		model.addAttribute("hmList", hmList);
		return "pages/notice/list";
	}
	
	@RequestMapping(value="/add")
	public String noticeAdd(HttpServletRequest request, Model model) {
		return "pages/notice/add";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String noticeSave(HttpServletRequest request, Model model, @RequestParam String message) {
		HorseMessage hm = new HorseMessage();
		hm.setMessage(message);
		hm.setType(1);
		horseMessageService.saveHorseMessage(hm);
		return "redirect:/notice/list";
	}
}
