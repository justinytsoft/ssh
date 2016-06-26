package com.goldCityWeb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.goldCityWeb.domain.Commission;
import com.goldCityWeb.domain.Message;
import com.goldCityWeb.domain.MessageType;
import com.goldCityWeb.domain.SysAdv;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.push.PushServer;
import com.goldCityWeb.service.IMessageService;
import com.goldCityWeb.service.SysAdvService;
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.DataUtil;
import com.goldCityWeb.util.FileUtils;
import com.goldCityWeb.util.PageSupport;

@Controller
@RequestMapping(value = "/sysadv")
public class SysAdvController {
	@Autowired
	private SysAdvService sysAdvService;
	
	@Autowired
	private IMessageService messageService;
	
	@Autowired
	private UserService userService;

	/**
	 * 添加系统公告
	 * 
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String addSysAdv(Model model) {
		return "pages/sysadv/index";
	}
	
	@RequestMapping(value="/detail")
	public String sysAdvDetail(@RequestParam Integer id, Model model) {
		SysAdv sa = sysAdvService.queryAysAdvById(id);
		model.addAttribute("sa", sa);
		return "pages/sysadv/detail";
	}

	/**
	 * 手续费
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/commission",method = { RequestMethod.GET, RequestMethod.POST })
	public String commissionmanage(HttpServletRequest request,Model model){
		Commission commission=sysAdvService.queryCommission();
		model.addAttribute("commission",commission);
		return "pages/gold/commission";
	}
	
	@RequestMapping(value="/changecommission",method = { RequestMethod.GET, RequestMethod.POST })
	public String commissionmanage1(HttpServletRequest request,@RequestParam(required=false)Integer flag,@RequestParam(required=false)Float num){
		Commission commission=new Commission();
		if(flag==null)
			return "pages/gold/commission";
		commission.setFlag(flag);
		commission.setNum(num);
		sysAdvService.updateCommission(commission);
		return "redirect:/sysadv/commission";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveSysAdv(Model model, HttpServletRequest request,
			@RequestParam(required = false) Integer id,
			@RequestParam(required = false) Integer type,
			@RequestParam(required = false) String text_content,
			@RequestParam String content, @RequestParam String push_title, @RequestParam String push_content,
			@RequestParam(required=false) Integer push_type) {
		String path = null;
		SysAdv sa = new SysAdv();
		
		if(id!=null && id.intValue() > 0) {
			sa.setId(id);
		}
		
		sa.setText_content(text_content);
		sa.setType(type);
		sa.setPush_title(push_title);
		sa.setPush_content(push_content);
		sa.setPush_type(push_type);
		
		sa.setContent(content);
		sa.setImage(path);

		sysAdvService.saveSysAdv(sa);
		PushServer.pushActivityAllClient(sa);
		
		// 发送信息
		Message message = new Message();
		
		message.setContent("有新的活动发布了!");
		message.setType(MessageType.NOTIFY);
		Map<String,Object> param = new HashMap<String ,Object>();
		if(push_type==0){
			param.put("type", 2);
		} else{
			param.put("type", 3);
		}
		List<UserDetail> uds = userService.queryUserdetailList(param, null);
		if(!CollectionUtils.isEmpty(uds)){
			for(UserDetail u:uds){
				message.setUid(u.getId());
				messageService.insertMessage(message);
			}
		}
		
		return "redirect:/sysadv/list";
	}

	@RequestMapping(value = "/list")
	public String sysAdvList(Model model, HttpServletRequest request,
			@RequestParam(required = false) Integer type) {
		System.out.println("===============================================");
		//JSONObject json = new JSONObject();
		/*System.out.println("===============================================");
		
		Map<String, Object> param = new HashMap<String, Object>();
		if (type != null && type.intValue() >= 0) {
			param.put("type", type);
			model.addAttribute("type", type);
		}
		PageSupport ps = PageSupport.initPageSupport(request);*/
		List<SysAdv> saList = new ArrayList<SysAdv>();
		SysAdv sa = sysAdvService.queryAysAdvById(1);
		//商户端活动
		saList.add(sa);
		SysAdv companysa = sysAdvService.queryAysAdvById(2);
		saList.add(companysa);
		model.addAttribute("saList", saList);
		System.out.println("1111111111111111111111111111111111111111");

		return "pages/sysadv/list";
	}

	@RequestMapping(value = "/delete")
	public String sysAdvDelete(Model model, HttpServletRequest request,
			@RequestParam Integer id,
			@RequestParam(required = false) Integer type) {
		sysAdvService.deleteSysAdvById(id);

		Map<String, Object> param = new HashMap<String, Object>();
		if (type != null) {
			param.put("type", type);
			model.addAttribute("type", type);
		}
		PageSupport ps = PageSupport.initPageSupport(request);
		List<SysAdv> saList = sysAdvService.querySysAdvList(param, ps);
		model.addAttribute("saList", saList);

		return "pages/sysadv/list";
	}
}
