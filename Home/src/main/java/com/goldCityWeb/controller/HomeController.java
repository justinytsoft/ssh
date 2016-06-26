package com.goldCityWeb.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.CompanyCharge;
import com.goldCityWeb.domain.CompanyType;
import com.goldCityWeb.domain.HeadList;
import com.goldCityWeb.domain.Report;
import com.goldCityWeb.domain.RobotGold;
import com.goldCityWeb.domain.Section;
import com.goldCityWeb.domain.SignRecord;
import com.goldCityWeb.domain.SysUsers;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.json.ResponseStatus;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.Constants;
import com.goldCityWeb.util.DataUtil;
import com.goldCityWeb.util.PageSupport;




@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model, @RequestParam(required = false) String error) {
		model.addAttribute("error", error);
		return "pages/frame/login";
	}

	@RequestMapping(value="/pages/user/report",method=RequestMethod.GET)
	public String reportpage(HttpServletRequest request,Model model){
		return "/pages/user/report";
	}
	
	@RequestMapping(value="/uploadhead",method=RequestMethod.GET)
	public String uploadheadpage(HttpServletRequest request){
		return "/pages/user/uploadhead";
	}
	
	@RequestMapping(value="/addsection",method=RequestMethod.GET)
	public String addsectionpage(HttpServletRequest request){
		return "/pages/user/addsection";
	}
	
	@RequestMapping(value="/addloginimg",method=RequestMethod.GET)
	public String addloginimgpage(HttpServletRequest request){
		return "/pages/user/addloginimg";
	}
	
	@RequestMapping(value="/addcomptype",method=RequestMethod.GET)
	public String addcomptypepage(HttpServletRequest request){
		return "/pages/user/addcomptype";
	}
	
	
	/**
	 * 头像管理页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pages/user/headmanage",method = { RequestMethod.GET, RequestMethod.POST })
	public String headmanage(HttpServletRequest request,Model model){
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		List<HeadList> hls=userService.queryHeadList(param,ps);
		model.addAttribute("hls",hls);
		return "/pages/user/headmanage";
		
	}
	
	/**
	 * 商家类型管理
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pages/user/comptypemanage",method = { RequestMethod.GET, RequestMethod.POST })
	public String compmanage(HttpServletRequest request,Model model){
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		List<CompanyType> cts=userService.queryCompanyTypeList(param,ps);
		model.addAttribute("cts",cts);
		return "/pages/user/comptypemanage";
		
	}
	
	/**
	 * 商家金币充值信息
	 * @param request
	 * @param model
	 * @param date
	 * @return
	 */
	@RequestMapping(value="/pages/gold/cmpchargedetail",method = { RequestMethod.GET, RequestMethod.POST })
	public String compchargedate(HttpServletRequest request,Model model,@RequestParam(required=false) String date1,@RequestParam(required=false) String date2){
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		if(date1!=null){
			param.put("date1", date1);
			model.addAttribute("date1",date1);
		}
		if(date2!=null){
			param.put("date2", date2);
			model.addAttribute("date2",date2);
		}
		List<CompanyCharge> ccs=companyService.queryCompanyChargeList(param,ps);
		model.addAttribute("ccs",ccs);
		return "/pages/gold/cmpchargedetail";
	}
	
	/**
	 * 机器人抢金币流水
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pages/gold/robotgolddetail",method = { RequestMethod.GET, RequestMethod.POST })
	public String robotgolddeteil(HttpServletRequest request,Model model,@RequestParam(required=false) String date1,@RequestParam(required=false) String date2){
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		if(date1!=null){
			param.put("date1", date1);
			model.addAttribute("date1",date1);
		}
		if(date2!=null){
			param.put("date2", date2);
			model.addAttribute("date2",date2);
		}
		List<RobotGold> rgs=userService.queryRobotGoldList(param,ps);
		model.addAttribute("rgs",rgs);
		return "/pages/gold/robotgolddetail";
	}
	
	/**
	 * 签到记录
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/pages/user/signrecorddetail",method = { RequestMethod.GET, RequestMethod.POST })
	public String signrecord(HttpServletRequest request,Model model,@RequestParam(required=false) String date1,@RequestParam(required=false) String date2) {
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		if(date1!=null){
			param.put("date1", date1);
			model.addAttribute("date1",date1);
		}
		if(date2!=null){
			param.put("date2", date2);
			model.addAttribute("date2",date2);
		}
		List<SignRecord> srs=userService.querySignRecordList(param,ps);
		model.addAttribute("srs",srs);
		return "/pages/user/signrecorddetail";
	}
	
	/**
	 * 地区管理页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pages/user/sectionmanage",method = { RequestMethod.GET, RequestMethod.POST })
	public String sectionmanage(HttpServletRequest request,Model model){
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		List<Section> sections=userService.querySectionList(param,ps);
		model.addAttribute("sections",sections);
		return "/pages/user/sectionmanage";
		
	}
	
	/**
	 * 删除地区
	 */
	@RequestMapping(value="/page/user/deletesection",method = { RequestMethod.GET, RequestMethod.POST })
	public String deletesection(HttpServletRequest request,@RequestParam Integer id){
		
		if(id!=null){
			userService.deleteSection(id);
		}
		return "redirect:/pages/user/sectionmanage";
	}
	
	/**
	 * 删除公司类型
	 */
	@RequestMapping(value="/page/user/deletecomptype",method = { RequestMethod.GET, RequestMethod.POST })
	public String deletecomptype(HttpServletRequest request,@RequestParam Integer id){
		if(id!=null){
			userService.deletecomptype(id);
		}
		return "redirect:/pages/user/comptypemanage";
	}
	
	/**
	 * 添加公司类型
	 * @param request
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/pages/user/addcomptype",method =  RequestMethod.POST)
	public String addcomptype(HttpServletRequest request,@RequestParam String name){
		CompanyType cType=new CompanyType();
		if(name==null){
			return "/pages/user/comptypemanage";
		}
		cType.setType_name(name);
		userService.saveCompType(cType);
		return "redirect:/pages/user/comptypemanage";
	}
	
	
	/**
	 * 添加地区信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/pages/user/addsection",method =  RequestMethod.POST)
	public String addsection(HttpServletRequest request,@RequestParam String name,@RequestParam String message){
		Section section=new Section();
		if(name==null){
			return "/pages/user/sectionmanage";
		}
		section.setName(name);
		section.setMessage(message);
		/*try {
			List<String> paths = DataUtil.uploadImg(request, "file");
			if(!CollectionUtils.isEmpty(paths)){
				String path=paths.get(0);
				section.setPath(path);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		userService.saveSection(section);
		return "redirect:/pages/user/sectionmanage";
	}
	
	
	/**
	 * 删除头像
	 */
	@RequestMapping(value="/page/user/deletehead",method = { RequestMethod.GET, RequestMethod.POST })
	public String deletehead(HttpServletRequest request,@RequestParam Integer id){
		
		if(id!=null){
			userService.deleteHead(id);
		}
		System.out.println("id="+id);
		return "redirect:/pages/user/headmanage";
	}
	/**
	 * 上传头像图片
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/pages/user/headupload",method =  RequestMethod.POST)
	public String headupload(HttpServletRequest request){
		try {
			List<String> paths = DataUtil.uploadImg(request, "file");
			if(!CollectionUtils.isEmpty(paths)){
				paths.get(0);
				userService.saveHeadPath(paths.get(0));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/pages/user/headmanage";
	}
	
	
	/**
	 * 举报管理
	 * @param request
	 * @param model
	 * @param name
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/pages/user/reportmanage",method = { RequestMethod.GET, RequestMethod.POST })
	public String reportnamagepage(HttpServletRequest request,Model model,@RequestParam(required=false) String name,@RequestParam(required=false) String type){
		UserDetail ud = getSessionUser(request);
		Map<String, Object> param = new HashMap<String, Object>();
//		if(ud==null){
//			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
//			jrw.setMessage("session失效,请重新登录");
//			return jrw;
//		}
	
		if(!StringUtils.isBlank(name)) {
			param.put("name", name);
			model.addAttribute("name", name);
		}
		if(!StringUtils.isBlank(type)){
			param.put("type",type);
			model.addAttribute("type",type);
		}
		PageSupport ps = PageSupport.initPageSupport(request);
		List<Report> reports = userService.queryReportlist(param,ps);
		model.addAttribute("reports",reports);
		return "/pages/user/reportmanage";
	}
	
	/**
	 * 举报管理
	 * @param request
	 * @param model
	 * @param name
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="reportmanage",method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResWrapper reportmanage(HttpServletRequest request,Model model,@RequestParam(required=false) String name,@RequestParam(required=false) String type){
		UserDetail ud = getSessionUser(request);
		JsonResWrapper jrw = new JsonResWrapper();
		Map<String, Object> param = new HashMap<String, Object>();
		if(ud==null){
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			jrw.setMessage("session失效,请重新登录");
			return jrw;
		}
		if(!StringUtils.isBlank(name)) {
			param.put("name", name);
			model.addAttribute("name", name);
		}
		if(!StringUtils.isBlank(type)){
			param.put("type",type);
			model.addAttribute("type",type);
		}
		PageSupport ps = PageSupport.initPageSupport(request);
		List<Report> reports = userService.queryReportlist(param,ps);
		model.addAttribute("reports",reports);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("reports", isEmpty(reports));
		jrw.setData(data);
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	
	
	@RequestMapping(value = { "/", "/index" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String home(Model model,HttpServletRequest request) {
		request.getSession().setMaxInactiveInterval(120*60);
		return "pages/frame/main";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String header(Model model, @RequestParam(required = false) String error) {
		return "pages/frame/top";
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(HttpServletRequest request, Model model, @RequestParam(required = false) String error, @RequestParam(required=false) Integer id) {
		SysUsers ud = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		/*if(ud.getId() != null && userService.queryRoleByUser(ud.getId()) != 1) {
			//当前时间
			Date nowTime=new Date();
			//创建时间
			Date create_time = ud.getCreate_time();
			//有效期（月）
			Integer validity = ud.getValidity();
			//使用期（月）
			Integer usenum = DataUtil.getUseNum(nowTime, create_time);
			System.out.println(ud.isOverdue());
			if(usenum > validity) {
				userService.updateOverdue(ud.getId(), true);
			} else if (usenum <= validity && ud.isOverdue()) {
				userService.updateOverdue(ud.getId(), false);
			}
		}*/
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置显示格式
		//String nowTime="";
		//nowTime=;
		return "pages/frame/index";
	}

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String menuList(Model model, @RequestParam(required = false) String error) {
		return "pages/frame/left";
	}
	
	@RequestMapping(value = "/admin/user/mod_pwd", method = RequestMethod.GET)
	public String mod_pwd(Model model) {
		return "pages/user/mod_pwd";
	}

	@RequestMapping(value = "/admin/modPassword", method = { RequestMethod.POST })
	public String savePassword(HttpServletRequest request, Model model, @RequestParam String historyPwd, @RequestParam String newPwd) {
		SysUsers ud = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		SysUsers u = userService.querySysUserByUPD(ud.getUsername(), null);
		BCryptPasswordEncoder spe = new BCryptPasswordEncoder();
		if (spe.matches(historyPwd, u.getPassword())) {
			userService.updatePassword(u.getId(), spe.encode(newPwd));
			model.addAttribute("state", "修改成功！");
		} else {
			model.addAttribute("state", "修改失败！原始密码不正确！");
		}
		return "pages/user/mod_pwd";
	}
	
	private UserDetail getSessionUser(HttpServletRequest request){
		return (UserDetail)request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
	}
	
	private <T> List<T> isEmpty(List<T> param){
		return CollectionUtils.isEmpty(param) ? new ArrayList<T>() : param;
	}
	
}