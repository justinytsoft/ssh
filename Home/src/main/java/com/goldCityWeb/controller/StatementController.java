package com.goldCityWeb.controller;

import java.util.HashMap;
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

import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.FeeRecord;
import com.goldCityWeb.domain.Message;
import com.goldCityWeb.domain.MessageType;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.push.PushServer;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.service.IMessageService;
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.PageSupport;

@Controller
@RequestMapping(value="/statement")
public class StatementController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private IMessageService messageService;
	
	/**
	 * 结算列表
	 * @param request
	 * @param model
	 * @param phone
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/statement_list", method = {RequestMethod.GET, RequestMethod.POST})
	public String found_list(HttpServletRequest request, Model model,
							 @RequestParam(required=false) String phone,
							 @RequestParam(required=false) Integer status) {
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String,Object> param = new HashMap<String,Object>();
		
		if(!StringUtils.isBlank(phone)){
			param.put("phone", phone);
			model.addAttribute("phone", phone);
		}
		if(status!=null){
			param.put("status", status);
			model.addAttribute("status", status);
		}else{
			param.put("status", 1);
			model.addAttribute("status", 1);
		}
		
		List<Company> companys = companyService.queryStatementCompanyList(ps, param);
		
		model.addAttribute("cs", companys);
		return "pages/statement/statement_list";
	}
	
	/**
	 * 结算详情
	 * @param request
	 * @param id 公司ID
	 * @return
	 */
	@RequestMapping(value = "/statement_detail", method = {RequestMethod.GET, RequestMethod.POST})
	public String statement_detail(HttpServletRequest request, Model model,
							 @RequestParam(required=false) Integer id,
							 @RequestParam(required=false) Integer status,
							 @RequestParam(required=false) Integer flag) {
		if(id==null){
			return "redirect: statement_list";
		}
		
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("mid", id);
		param.put("status", status);
		param.put("page", ps.getPageOffset());
		param.put("row", ps.getPageSize());
		model.addAttribute("status", status);
		Company c = companyService.queryCompanyById(id);
		List<FeeRecord> frs = userService.queryMerFeeRecords(param);
		
		if(flag!=null){
			model.addAttribute("msg", "结算成功");
		}
				
		model.addAttribute("c", c);
		model.addAttribute("frs", frs);
		return "pages/statement/statement_detail";
	}
	
	/**
	 * 确认结算
	 * @param request
	 * @param id 公司ID
	 * @return
	 */
	@RequestMapping(value = "/settlement", method = {RequestMethod.GET, RequestMethod.POST})
	public String settlement(HttpServletRequest request, Model model,
							 @RequestParam(required=false) Integer id) {
		if(id==null){
			return "redirect: statement_list";
		}
		userService.updateFeeRecordStatusToSettlement(id);
		
		Company c = companyService.queryCompanyById(id);
		Message m = new Message();
		m.setUid(c.getUser_id());
		m.setType(MessageType.CLOSE_APPLY);
		m.setContent("您的提现申请已被处理");
		messageService.insertMessage(m);
		
		UserDetail ud = userService.queryUserDetailById(c.getUser_id());
		//推送
		if(!StringUtils.isBlank(ud.getToken()) && ud.getToken_type()!=null){
			String msg = "您提交的结算申请已经通过审核并已完成结算，请至您的银行账户查收。";
			PushServer.pushActivityClientByToken("【黄金都市】", msg, ud.getToken(), "", ud.getToken_type(), ud.getType());
		}
		return "redirect: statement_detail?flag=1&id="+id;
	}
}
