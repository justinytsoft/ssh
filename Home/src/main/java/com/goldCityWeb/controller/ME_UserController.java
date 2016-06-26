package com.goldCityWeb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.MainList;
import com.goldCityWeb.domain.SysUsers;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.service.IRedbagService;
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.DataValid;
import com.goldCityWeb.util.DateUtil;

@Controller
@RequestMapping("/merchant/user")
public class ME_UserController {

	@Autowired
	private UserService UserService;
	@Autowired
	private IRedbagService redbagService;
	@Autowired
	private CompanyService companyService;
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model){
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(user==null){
			return "redirect:../../logout?lp=/merchant/login";
		}
		
		Company company = companyService.queryCompanyByUserId(user.getId());
		int advCount = 0;
		int advCountMoney = 0;
		
		if(company!=null){

			Map<String, Object> param = new HashMap<String,Object>();
			param.put("id", company.getId());
			
			List<MainList> lists = redbagService.queryAdv(param);
			for(MainList ml : lists){
				advCount++;
				advCountMoney += ml.getAmount();
			}

		}
		
		model.addAttribute("advCount", advCount);
		model.addAttribute("advCountMoney", advCountMoney);
		model.addAttribute("company", company);
		return "merchant/index";
	}
	
	/**
	 * 用户注册页面
	 * @return
	 */
	@RequestMapping("/reg")
	public String reg(Model model){
		return "merchant/regist";
	}
	
	/**
	 * 忘记密码页面
	 * @return
	 */
	@RequestMapping("/forget")
	public String forget(Model model){
		return "merchant/password_forget";
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value="/password_mod",method=RequestMethod.POST)
	public String password_mod(Model model, HttpServletRequest request,
						   @RequestParam(required=false) String username,
					  	   @RequestParam(required=false) String password,
					  	   @RequestParam(required=false) String verifyCode){
		Map<String, Object> map = new HashMap<String, Object>();
		SysUsers user = UserService.querySysUserByUPD(username, null);
		
		if(!DataValid.isMobile(username) || StringUtils.isBlank(password) || StringUtils.isBlank(verifyCode)){
			map.put("flag", false);
			map.put("msg", "参数为空");
		}else if(user==null){
			map.put("flag", false);
			map.put("msg", "手机号未注册");
		}else{
			String code = (String) request.getSession().getAttribute("VC_"+username);
			Date date = (Date) request.getSession().getAttribute("VC_"+username+"_time");
			if(StringUtils.isBlank(code) || date==null){
				map.put("flag", false);
				map.put("msg", "请先获取验证码");
			}else{
				PasswordEncoder spe = new BCryptPasswordEncoder();
				long later = DateUtil.getMinute(date, 3l);
				long now = new Date().getTime();
				
				if(!verifyCode.equalsIgnoreCase(code)){
					map.put("flag", false);
					map.put("msg", "验证码错误");
				}else if(now > later){
					map.put("flag", false);
					map.put("msg", "验证码已过期");
				}else if(spe.matches(password,user.getPassword())){
					map.put("flag", false);
					map.put("msg", "新密码不能和旧密码相同");
				}else{
					map.put("flag", true);
					UserService.updatePassword(user.getId(), spe.encode(password));
				}
			}
		}
		
		map.put("u", username);
		map.put("p", password);
		model.addAttribute("data", map);
		return "merchant/password_forget";
	}
	
	/**
	 * 保存用户注册页面
	 * @return
	 */
	@RequestMapping(value="/save_reg",method=RequestMethod.POST)
	public String save_reg(Model model, HttpServletRequest request,
						   @RequestParam(required=false) String username,
					  	   @RequestParam(required=false) String password,
					  	   @RequestParam(required=false) String verifyCode){
		Map<String, Object> map = new HashMap<String, Object>();
		SysUsers user = UserService.querySysUserByUPD(username, null);
		
		if(!DataValid.isMobile(username) || StringUtils.isBlank(password) || StringUtils.isBlank(verifyCode)){
			map.put("flag", false);
			map.put("msg", "参数为空");
		}else if(user!=null){
			map.put("flag", false);
			map.put("msg", "手机号已经注册");
		}else{
			String code = (String) request.getSession().getAttribute("VC_"+username);
			Date date = (Date) request.getSession().getAttribute("VC_"+username+"_time");
			if(StringUtils.isBlank(code) || date==null){
				map.put("flag", false);
				map.put("msg", "请先获取验证码");
			}else{
				long later = DateUtil.getMinute(date, 3l);
				long now = new Date().getTime();
				
				if(!verifyCode.equalsIgnoreCase(code)){
					map.put("flag", false);
					map.put("msg", "验证码错误");
				}else if(now > later){
					map.put("flag", false);
					map.put("msg", "验证码已过期");
				}else{
					map.put("flag", true);
					PasswordEncoder spe = new BCryptPasswordEncoder();
					SysUsers su = new SysUsers();
					su.setUsername(username);
					su.setPassword(spe.encode(password));
					su.setType(3);
					UserService.saveUser(su);
				}
			}
		}
		
		map.put("u",username);
		map.put("p", password);
		model.addAttribute("data", map);
		return "merchant/regist";
	}
	
	/**
	 * 查询用户信息
	 * @return
	 */
	@RequestMapping("/getUserInfo")
	@ResponseBody
	public Map<String, Object> getUserInfo(){
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(user==null){
			map.put("flag", false);
		}else{
			UserDetail ud = UserService.queryUserDetailById(user.getId());
			if(ud!=null){
				map.put("flag", true);
				map.put("userDetail", ud);
			}else{
				map.put("flag", false);
			}
		}
		
		return map;
	}
}
