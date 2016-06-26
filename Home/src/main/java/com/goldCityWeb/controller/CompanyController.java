package com.goldCityWeb.controller;

import java.util.Date;
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

import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.Message;
import com.goldCityWeb.domain.MessageType;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.json.ResponseStatus;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.service.IMessageService;
import com.goldCityWeb.util.PageSupport;

@Controller
@RequestMapping(value="/company")
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	@Autowired
	private IMessageService messageService;
	
	/**
	 * 商家列表
	 * @param model
	 * @param request
	 * @param verify_status
	 * @return
	 */
	@RequestMapping(value="/verify")
	public String companyVerify(Model model, HttpServletRequest request, @RequestParam(required=false) Integer verify_status) {
		PageSupport ps = PageSupport.initPageSupport(request);
		System.out.println("ps参数:" + ps);
		Map<String, Object> param = new HashMap<String, Object>();
		if(verify_status != null && verify_status.intValue() != -2) {
			param.put("verify_status", verify_status);
			model.addAttribute("verify_status", verify_status);
		}
		List<Company> comList =  companyService.queryCompanyList(ps, param);
		model.addAttribute("comList", comList);
		//List<Company>
		//System.out.println("comList size is " + comList.size());
		return "pages/company/verify";
	}
	
	/**
	 * 公司详情
	 * @param model
	 * @param request
	 * @param company_id
	 * @return
	 */
	@RequestMapping(value="/verify/detail")
	public String companyVerifyDetail(Model model, HttpServletRequest request, 
									  @RequestParam(required=false) Integer company_id,
									  @RequestParam(required=false) Integer status) {
		if(status!=null){
			if(status==0){
				model.addAttribute("msg", "参数为空");
			}else if(status==1){
				model.addAttribute("msg", "商家不存在");
			}else if(status==2){
				model.addAttribute("msg", "操作成功");
			}
		}
		
		Company company = companyService.queryCompanyById(company_id);
		model.addAttribute("company", company);
		return "pages/company/verify_detail";
	}
	
	/**
	 * 审核
	 * @param request
	 * @param company_id
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/verify/audit", method=RequestMethod.POST)
	public String audit(HttpServletRequest request, Model model,
						@RequestParam(required=false) Integer cid, 
						@RequestParam(required=false) Integer status,
						@RequestParam(required=false) String reason) {
		
		if(cid==null || status==null || reason==null){
			return "redirect:/company/verify/detail?status=0&company_id=" + cid;
		}
		Company company = companyService.queryCompanyById(cid);
		if(company==null){
			return "redirect:/company/verify/detail?status=1&company_id=" + cid;
		}
		company.setVerify_status(status); 
		company.setVerify_desc(reason);
		companyService.updateCompanyStatus(company);
		
		Message m = new Message();
		m.setUid(company.getUser_id());
		m.setType(MessageType.AUDIT);
		m.setContent("公司信息审核" + (status==1?"成功":"失败，请重新提交审核信息进行审核；理由：" + reason));
		messageService.insertMessage(m);
		
		model.addAttribute("company",company);
		return "redirect:/company/verify/detail?status=2&company_id=" + cid;
	}
	
	
	/**
	 * 暂停使用 ，不知 其他地方是否引用
	 * @param model
	 * @param request
	 * @param company_id
	 * @return
	 */
	@RequestMapping(value="/verify/savestatus", method=RequestMethod.POST)
	public @ResponseBody JsonResWrapper saveCompanyVerifyStatus(HttpServletRequest request, @RequestParam Integer company_id, @RequestParam Integer status) {
		JsonResWrapper response = new JsonResWrapper();
		Company company = companyService.queryCompanyById(company_id);
		if(company == null) {
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("无此商家");
			return response;
		}
		company.setVerify_status(status); 
		//Date now = new Date();
		//company.setVerify_date(now);
		companyService.updateCompanyStatus(company);
		response.setMessage("保存成功");
		response.setStatus(ResponseStatus.OK);
		return response;
	}
	
	/**
	 * 暂停使用
	 * @param model
	 * @param request
	 * @param company_id
	 * @return
	 */
	@RequestMapping(value="/verify/save", method=RequestMethod.POST)
	public String saveCompanyVerify(Model model, HttpServletRequest request, @RequestParam Integer company_id) {
		Company company = companyService.queryCompanyById(company_id);
		company.setVerify_status(1); 
		Date now = new Date();
		company.setVerify_date(now);
		companyService.updateCompanyStatus(company);
		model.addAttribute("company",company);
		return "redirect:/company/verify/detail?company_id=" + company_id;
	}
	
	/**
	 * 暂停使用
	 * @param model
	 * @param request
	 * @param company_id
	 * @return
	 */
	@RequestMapping(value="/verify/deny",method=RequestMethod.POST)
	public String denyCompanyVerify(Model model,HttpServletRequest request,@RequestParam Integer company_id,@RequestParam String verify_desc){
		Company company = companyService.queryCompanyById(company_id);
		company.setVerify_status(-1); 
		company.setVerify_desc(verify_desc);
		Date now = new Date();
		company.setVerify_date(now);
		companyService.updateCompanyStatus(company);
		model.addAttribute("company",company);
		
		return "redirect:/company/verify/detail?company_id=" + company_id;
	}
	
}
