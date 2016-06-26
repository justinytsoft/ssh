package com.goldCityWeb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goldCityWeb.domain.Feedback;
import com.goldCityWeb.service.FeedbackService;
import com.goldCityWeb.util.PageSupport;

@RequestMapping("/admin/feedback")
@Controller
public class FeedbackController {
	
	@Autowired
	private FeedbackService feedbackService;

	/**
	 * 反馈首页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model){
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		
		List<Feedback> fs = feedbackService.queryFeedbacks(ps, param);
		
		model.addAttribute("fs", fs);
		return "pages/feedback/index";
	}
}
