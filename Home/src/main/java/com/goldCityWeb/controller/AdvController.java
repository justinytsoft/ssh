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
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.Adv;
import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.MainList;
import com.goldCityWeb.domain.Message;
import com.goldCityWeb.domain.MessageType;
import com.goldCityWeb.domain.Mould;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.service.AdvService;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.service.IMessageService;
import com.goldCityWeb.service.IRedbagService;
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.PageSupport;

@Controller
@RequestMapping(value = "/adv")
public class AdvController {
	@Autowired
	private AdvService advService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IRedbagService redbagService;
	@Autowired
	private UserService userService;

	/**
	 * 广告列表
	 * @param request
	 * @param model
	 * @param verify_status
	 * @return
	 */
	@RequestMapping(value = "/verify_list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String verifyAdv(HttpServletRequest request, Model model,
			@RequestParam(required = false) Integer verify_status) {
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		//默认显示未审核的
		if(verify_status==null){
			param.put("verify_status", 0);
		}
		if (verify_status != null && verify_status.intValue() != 2) {
			param.put("verify_status", verify_status);
			model.addAttribute("verify_status", verify_status);
		}else{
			model.addAttribute("verify_status", verify_status);
		}
		List<MainList> advList = advService.queryAdvByVerify(param, ps);
		model.addAttribute("advList", advList);
		return "pages/adv/verify_list";
	}

	/**
	 * 广告审核
	 * @param request
	 * @param model
	 * @param id
	 * @param verify_status
	 * @return
	 */
	@RequestMapping(value = "/save_verify")
	@ResponseBody
	public Map<String, Object> saveVerify(HttpServletRequest request, Model model,
										  @RequestParam(required=false) Integer id, 
										  @RequestParam(required=false) Integer verify_status,
										  @RequestParam(required=false) String reason) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(id==null || verify_status==null){
			map.put("flag", false);
			map.put("msg", "参数为空");
			return map;
		}
		advService.updateAdvVerifyStatus(id, verify_status);
		Adv a = advService.queryAdvById(id);
		Company c = companyService.queryCompanyById(a.getCompany_id());
		//发送信息
		Message message = new Message();
		message.setUid(c.getUser_id());
		if(verify_status == 1){
			message.setContent("广告审核通过");
			
			//更新公司 已发金币数量
			c.setGrant_count(c.getGrant_count() + a.getAmount());
			companyService.updateCompanyGrant_count(c);
		} else {
			message.setContent("广告审核未通过,理由："+reason);
			
			UserDetail ud = userService.queryUserDetailById(c.getUser_id());
			ud.setGold_count(ud.getGold_count()+a.getAmount());
			userService.updateMerGoldCount(ud);
		}
		message.setType(MessageType.CREATE_REDBAG);
		messageService.insertMessage(message);
		
		map.put("flag", true);
		map.put("msg", "操作成功");
		return map;
	}

	/**
	 * 广告详情
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/detail")
	public String advDetail(HttpServletRequest request, Model model,
			@RequestParam Integer id) {
		List<Mould> ms = advService.queryAllMoould();
		model.addAttribute("ms", ms);
		if (id != null) {
			MainList m = redbagService.queryAdvById(id);
			model.addAttribute("m", m);
		}
		return "pages/adv/detail";
	}
}
