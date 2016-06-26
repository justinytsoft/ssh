package com.goldCityWeb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.goldCityWeb.domain.College;
import com.goldCityWeb.domain.FeeRecord;
import com.goldCityWeb.domain.Income;
import com.goldCityWeb.domain.LoginImg;
import com.goldCityWeb.domain.SysUsers;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.Constants;
import com.goldCityWeb.util.DataUtil;
import com.goldCityWeb.util.PageSupport;
import com.google.zxing.qrcode.decoder.Mode;

@Controller
@RequestMapping(value = "/admin/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BaseService baseService;
	
	
	
	@RequestMapping(value="/add",method = RequestMethod.GET)
	public String add(Model model,@RequestParam(required=false) Integer id, @RequestParam(required=false) Integer status){
		//PageSupport.initPageSupport(request);
		//List<SysRoles> sr = userService.quertSysRoles();
		if(status != null && status == 1) {
			model.addAttribute("msg", "保存成功");
		} else if(status != null && status == -1) {
			model.addAttribute("msg", "保存失败,用户名已存在");
		}
		Map<String,Object> param = new HashMap<String,Object>();
		List<College> cs = baseService.queryCollege(param, null);
		model.addAttribute("colleges", cs);
		return "pages/user/add";
	}
	
	@RequestMapping(value="/edit",method = RequestMethod.GET)
	public String edit(Model model,@RequestParam(required=false) Integer id, @RequestParam(required=false) Integer status){
		//PageSupport.initPageSupport(request);
		//List<SysRoles> sr = userService.quertSysRoles();
		if(status != null && status == 1) {
			model.addAttribute("msg", "保存成功");
		} else if(status != null && status == -1) {
			model.addAttribute("msg", "保存失败");
		}
		if(id!=null&&id.intValue()>0){
			SysUsers user = userService.querySysUserById(id);
			model.addAttribute("su", user);
		}
		Map<String,Object> param = new HashMap<String,Object>();
		List<College> cs = baseService.queryCollege(param, null);
		model.addAttribute("colleges", cs);
		return "pages/user/edit";
	}
	
	@RequestMapping(value="/save_user",method = RequestMethod.POST)
	public String save_user(Model model,@RequestParam(required = false) Integer id,@RequestParam(required = false) String username,@RequestParam(required = false) String password,@RequestParam(required = false) Integer college_id){
		SysUsers su = new SysUsers();
		su.setId(id);
		//su.setCollege_id(college_id);
		if(id==null){
			SysUsers s = userService.querySysUserByUPD(username, null);
			if(s!=null){
				return "redirect:add?status=-1";
			}
		}
		su.setUsername(username);
		su.setName("后勤管理员");
		if(!StringUtils.isBlank(password)){
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String pwd = passwordEncoder.encode(password);
			su.setPassword(pwd);
		}
		userService.saveHouQin(su);
		
		return "redirect:edit?status=1&id="+su.getId();
	}
	
	@RequestMapping(value="/list",method = {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request, Model model,@RequestParam(required = false) Integer delete
			,@RequestParam(required = false)String username,@RequestParam(required = false)String college){
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		
		if(!StringUtils.isBlank(username)){
			param.put("username", username);
			model.addAttribute("username", username);
		}
		if(!StringUtils.isBlank(college)){
			param.put("college", college);
			model.addAttribute("college", college);
		}
		if(delete!=null && delete.intValue() == 1){
			model.addAttribute("msg", "删除成功!");
		}
		List<SysUsers> users = userService.queryHouQinList(param, ps);
		model.addAttribute("users", users);
		return "pages/user/h_list";
	}
	
	@RequestMapping(value="/h_delete",method = {RequestMethod.GET,RequestMethod.POST})
	public String h_delete(HttpServletRequest request, Model model,@RequestParam Integer id
			){
		userService.deleteSysUsers(id);
		return "redirect:list?delete=1";
	}
	/**
	 * 玩家注册信息列表
	 */
	@RequestMapping(value="/userdetaillist",method = { RequestMethod.GET, RequestMethod.POST })
	public String usermessage(HttpServletRequest request,Model model,@RequestParam(required = false)String name){
		UserDetail ud=getSessionUser(request);
		if(ud==null){
			model.addAttribute("msg", "请先登录");
			//return "index";
		}
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		if(!StringUtils.isBlank(name)){
			param.put("name", name);
			model.addAttribute("name", name);
		}
		param.put("type", 2);
		List<UserDetail> users=userService.queryUserdetailList(param, ps);
		model.addAttribute("users",users);
		return "pages/user/userdetaillist";
	}
	
	/**
	 * login_img list
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/loginimglist",method = { RequestMethod.GET, RequestMethod.POST })
	public String loginimgpage(HttpServletRequest request,Model model){
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		List<LoginImg> logimgs=userService.queryLoginImgList(param, ps);
		model.addAttribute("logimgs",logimgs);
		return "pages/user/loginimglist";
	}
	
	/**
	 * 添加login_img
	 * @param request
	 * @param sort_num
	 * @return
	 */
	@RequestMapping(value="/addloginimg",method =  RequestMethod.POST)
	public String addloginimg(HttpServletRequest request,@RequestParam Integer sort_num){
		LoginImg loginImg=new LoginImg();
		if(sort_num==null){
			return "pages/user/loginimglist";
		}
		loginImg.setSort_num(sort_num);
		try {
			List<String> paths = DataUtil.uploadImg(request, "file");
			if(!CollectionUtils.isEmpty(paths)){
				String path=paths.get(0);
				loginImg.setPath(path);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userService.saveLoginImg(loginImg);
		return "redirect:/admin/user/loginimglist";
	}
	
	
	@RequestMapping(value="/deleteloginimg",method = { RequestMethod.GET, RequestMethod.POST })
	public String deletesection(HttpServletRequest request,@RequestParam Integer id){
		
		if(id!=null){
			userService.deleteloginimg(id);
		}
		return "redirect:/admin/user/loginimglist";
	}
	
	/**
	 * 收益纪录
	 * @param request
	 * @param model
	 * @param uid
	 * @return
	 */
	@RequestMapping(value="/listincom",method = { RequestMethod.GET, RequestMethod.POST })
	public String listincom(HttpServletRequest request,Model model,@RequestParam(required = false)Integer uid){
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uid", uid);
		List<Income> incomes=userService.queryIncomes(ps, param);
		model.addAttribute("incomes",incomes);
		return "pages/user/incomlist";
	}
	
	/**
	 * 消费记录
	 * @param request
	 * @param model
	 * @param uid
	 * @return
	 */
	@RequestMapping(value="/listcost",method = { RequestMethod.GET, RequestMethod.POST })
	public String listcost(HttpServletRequest request,Model model,@RequestParam(required = false)Integer uid){
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uid", uid);
		List<FeeRecord> feeRecords=userService.queryCostlist(param,ps);
		model.addAttribute("feeRecords",feeRecords);
		return "pages/user/feeRecordslist";
	}
	
	
	@RequestMapping(value="/student/list",method = {RequestMethod.GET,RequestMethod.POST})
	public String student_list(HttpServletRequest request, Model model,@RequestParam(required = false)String phone
			,@RequestParam(required = false)String name,@RequestParam(required = false)Integer update){
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", 5);
		if(!StringUtils.isBlank(name)){
			param.put("name", name);
			model.addAttribute("name", name);
		}
		if(!StringUtils.isBlank(phone)){
			param.put("phone", phone);
			model.addAttribute("phone", phone);
		}
		if(update!=null && update.intValue() == 1){
			model.addAttribute("msg", "改变状态成功!");
		}
		List<SysUsers> users = userService.queryUserList(param, ps);
		model.addAttribute("users", users);
		return "pages/user/list";
	}
	
	@RequestMapping(value="/company/list",method = {RequestMethod.GET,RequestMethod.POST})
	public String company_list(HttpServletRequest request, Model model,@RequestParam(required = false)String phone
			,@RequestParam(required = false)String name,@RequestParam(required = false)Integer update){
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", 4);
		if(!StringUtils.isBlank(name)){
			param.put("name", name);
			model.addAttribute("name", name);
		}
		if(!StringUtils.isBlank(phone)){
			param.put("phone", phone);
			model.addAttribute("phone", phone);
		}
		if(update!=null && update.intValue() == 1){
			model.addAttribute("msg", "改变状态成功!");
		}
		List<SysUsers> users = userService.queryUserList(param, ps);
		model.addAttribute("users", users);
		return "pages/user/c_list";
	}
	
	
	@RequestMapping(value="/student/delete",method = {RequestMethod.GET,RequestMethod.POST})
	public String student_delete(HttpServletRequest request, Model model,@RequestParam Integer id
			){
		userService.deleteSysUsers(id);
		return "redirect:list";
	}
	
	@RequestMapping(value="/company/delete",method = {RequestMethod.GET,RequestMethod.POST})
	public String company_delete(HttpServletRequest request, Model model,@RequestParam Integer id
			){
		userService.deleteSysUsers(id);
		return "redirect:list";
	}
	/**
	 * 冻结与解冻
	 * @param request
	 * @param model
	 * @param id
	 * @param enabled
	 * @return
	 */
	@RequestMapping(value="/company/update",method = {RequestMethod.GET,RequestMethod.POST})
	public String company_update(HttpServletRequest request, Model model,@RequestParam Integer id
			,@RequestParam Boolean enabled){
		//userService.deleteSysUsers(id);
		if(enabled){
			userService.updateuserEnabled(id,0);
		}else{
			userService.updateuserEnabled(id,1);
		}
		return "redirect:/admin/user/userdetaillist";
	}
	
	@RequestMapping(value="/student/update",method = {RequestMethod.GET,RequestMethod.POST})
	public String student_update(HttpServletRequest request, Model model,@RequestParam Integer id
			,@RequestParam Boolean enabled){
		//userService.deleteSysUsers(id);
		if(enabled){
			userService.updateuserEnabled(id,0);
		}else{
			userService.updateuserEnabled(id,1);
		}
		return "redirect:list?update=1";
	}
	
	private UserDetail getSessionUser(HttpServletRequest request){
		return (UserDetail)request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
	}
	
}
