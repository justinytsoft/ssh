package com.goldCityWeb.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goldCityWeb.domain.Message;
import com.goldCityWeb.domain.MessageType;
import com.goldCityWeb.domain.ProductRecord;
import com.goldCityWeb.service.IMessageService;
import com.goldCityWeb.service.IProductService;
import com.goldCityWeb.util.PageSupport;

@Controller
@RequestMapping("/exchange")
public class ExchangeController {

	@Autowired
	private IProductService productService;
	@Autowired
	private IMessageService messageService;
	
	/**
	 * 兑换处理
	 * @param request
	 * @param id 订单id
	 * @param type 订单类型 0 虚拟 1 实体
	 * @param express_name 快递名称
	 * @param express_num 快递单号
	 * @param remark 备注
	 * @return
	 */
	@RequestMapping("/handle")
	public String detail(HttpServletRequest request,
						 @RequestParam(required=false) Integer id,
						 @RequestParam(required=false) Integer type,
						 @RequestParam(required=false) String express_name,
						 @RequestParam(required=false) String express_num,
						 @RequestParam(required=false) String remark){
		if(id==null){
			return "redirect:detail?flag=-1&id="+id;
		}
		
		if(type==1 && StringUtils.isBlank(express_name) && StringUtils.isBlank(express_num)){
			return "redirect:detail?flag=-1&id="+id;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("express_name", express_name);
		param.put("express_num", express_num);
		param.put("remark", remark);
		param.put("id", id);
		productService.updateProductExchangeHandle(param);
		
		ProductRecord pr = productService.queryProductRecordById(id);
		
		String kd = String.format("您的【%s】已寄出，快递单号为【%s】", pr.getP_name(), express_num);
		String xn = String.format("您兑换的【%s】已被成功领取", pr.getP_name());
		
		Message m = new Message();
		m.setType(MessageType.EXCHANGE);
		m.setUid(pr.getU_id());
		m.setContent(type==1?kd:xn);
		messageService.insertMessage(m);
		
		return "redirect:detail?flag=1&id="+id;
	}
	
	/**
	 * 兑换详情
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request, Model model,
						 @RequestParam(required=false) Integer id,
						 @RequestParam(required=false) Integer flag){
		if(flag!=null){
			if(flag==1){
				model.addAttribute("msg", "订单处理成功");
			}else if(flag==-1){
				model.addAttribute("msg", "订单处理失败");
			}
		}
		
		if(id==null){
			return "redirect:index";
		}
		
		ProductRecord pr = productService.queryProductRecordById(id);
		
		model.addAttribute("pr", pr==null?new ProductRecord():pr);
		return "pages/exchange/detail";
	}
	
	/**
	 * 兑换管理首页
	 * @param request
	 * @param model
	 * @param phone 用户手机号
	 * @param status 是否已处理
	 * @param sdate 开始时间
	 * @param edate 结算时间
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model,
						@RequestParam(required=false) String phone,
						@RequestParam(required=false) Integer status,
						@RequestParam(required=false) String sdate,
						@RequestParam(required=false) String edate){
		
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(!StringUtils.isBlank(phone)){
			param.put("phone", phone);
			model.addAttribute("phone", phone);
		}
		if(status!=null && status!=-1){
			param.put("status", status);
			model.addAttribute("status", status);
		}
		try {
			if(!StringUtils.isBlank(sdate)){
				model.addAttribute("sdate", sdate);
				param.put("sdate", sdf.parse((sdate+" 00:00:00")));
			}
			
			if(!StringUtils.isBlank(edate)){
				model.addAttribute("edate", edate);
				param.put("edate", sdf.parse((edate+" 23:59:59")));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<ProductRecord> prs = productService.queryProductRecord(ps,param);
		
		model.addAttribute("prs", prs);
		return "pages/exchange/index";
	}
}
