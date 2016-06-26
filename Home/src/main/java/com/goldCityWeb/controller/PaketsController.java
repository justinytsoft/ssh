package com.goldCityWeb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/pakets")
public class PaketsController {
	
	
	
	@RequestMapping(value = "/pakets_list", method = RequestMethod.GET)
	public String found_list(HttpServletRequest request, Model model, @RequestParam(required = false) String error) {
		model.addAttribute("error", error);
		return "pages/pakets/pakets_list";
	}
}
