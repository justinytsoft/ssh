package com.goldCityWeb.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.Message;
import com.goldCityWeb.domain.SysUsers;
import com.goldCityWeb.service.IMessageService;
import com.goldCityWeb.util.EasyuiPaging;

@Controller
@RequestMapping("/merchant/message")
public class ME_MessageController {

	@Autowired
	private IMessageService messageService;
	
	/**
	 * 删除消息
	 * @param id
	 * @return
	 */
	@RequestMapping("/delMessage")
	@ResponseBody
	public Map<String, Object> delMessage(@RequestParam(required=false) Integer id){
		
		Map<String, Object> map = new HashMap<String, Object>();
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(user==null){
			map.put("flag", false);
			map.put("msg", "session失效，请重新登录");
			return map;
		}
		
		if(id==null){
			map.put("flag", false);
			map.put("msg", "参数为空");
			return map;
		}
		
		messageService.delMessageById(user.getId(), id);
		
		map.put("flag", true);
		map.put("msg", "删除成功");
		return map;
	}
	
	/**
	 * 消息首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(){
		return "merchant/message";
	}
	
	/**
	 * 获取所有消息
	 * @param page 从第几条开始查
	 * @param rows 查多少条
	 * @param looked 是否已查看 0 否 1 是
	 * @return
	 */
	@RequestMapping("/getMessages")
	@ResponseBody
	public EasyuiPaging<Message> getMessages(@RequestParam(required=false) Integer page,
											 @RequestParam(required=false) Integer rows,
											 @RequestParam(required=false) Integer type,
											 @RequestParam(required=false) String sdate,
											 @RequestParam(required=false) String edate,
											 @RequestParam(required=false) Boolean looked){
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		EasyuiPaging<Message> ep = new EasyuiPaging<Message>();
		
		if(user==null){
			return ep;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uid", user.getId());
		if(page!=null && rows!=null){
			param.put("rows", rows);
			param.put("page", ((page-1)*rows));
		}
		if(looked!=null){
			param.put("looked", looked);
		}
		if(type!=null && type!=-1){
			param.put("type", type);
		}
		try{
			if(!StringUtils.isBlank(sdate)){
				sdate += " 00:00:00";
				param.put("sdate", sdf.parse(sdate));
			}
			if(!StringUtils.isBlank(edate)){
				edate += " 23:59:59";
				param.put("edate", sdf.parse(edate));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		List<Message> ms = messageService.queryMessages(param);
		int total = messageService.queryMessagesTotal(param);

		ep.setRows(isEmpty(ms));
		ep.setTotal(total);
		
		//当用户查看消息列表时，把所有消息更新为已查看
		if(page!=null && rows!=null){
			messageService.updateMessagesToLooked(user.getId());
		}
		
		return ep;
	}
	
	/**
	 * 判断集合是否为null , 是 创建一个ArrayList并返回, 否 直接返回传入的集合
	 * @param list
	 * @return
	 */
	private <T> List<T> isEmpty(List<T> list){
		return CollectionUtils.isEmpty(list) ? new ArrayList<T>() : list;
	}
}
