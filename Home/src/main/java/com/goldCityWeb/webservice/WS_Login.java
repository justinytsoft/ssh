/**
 * 
 */
package com.goldCityWeb.webservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.Adv;
import com.goldCityWeb.domain.AdvApp;
import com.goldCityWeb.domain.AdvGrabRecord;
import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.Income;
import com.goldCityWeb.domain.Item;
import com.goldCityWeb.domain.Level;
import com.goldCityWeb.domain.Location;
import com.goldCityWeb.domain.MainList;
import com.goldCityWeb.domain.SignRecord;
import com.goldCityWeb.domain.SignTable;
import com.goldCityWeb.domain.SysUsers;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.json.ResponseStatus;
import com.goldCityWeb.service.AdvService;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.Constants;
import com.goldCityWeb.util.DataUtil;
import com.goldCityWeb.util.PageSupport;
import com.goldCityWeb.util.SendTemplateSMS;
import com.goldCityWeb.util.SettingUtils;



/**
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/services")
public class WS_Login {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BaseService baseService;
	
	@Autowired
	private AdvService advService;
	
	@Autowired
	private CompanyService companyService;

	

	/**
	 * App登录接口
	 * 
	 * @param request
	 * @param model
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	JsonResWrapper login(HttpServletRequest request, Model model,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude, @RequestParam(required = false) String username, @RequestParam(required = false) String password, @RequestParam(required = false) Integer type) {
		JsonResWrapper response = new JsonResWrapper();
		if (StringUtils.isBlank(username)) {
			response.setMessage("用户名[username]为空！");
			response.setStatus(ResponseStatus.FAILED_PARAM_LOST);
		} else if (StringUtils.isBlank(password)) {
			response.setMessage("密码[password]为空！");
			response.setStatus(ResponseStatus.FAILED_PARAM_LOST);
		}else if (type==null) {
				response.setMessage("登录类型[type]为空！");
				response.setStatus(ResponseStatus.FAILED_PARAM_LOST);
		} else {
			PasswordEncoder spe = new BCryptPasswordEncoder();
			SysUsers user = userService.querySysUserByUPD(username,null);
			if (user == null || !spe.matches(password, user.getPassword())) {
				response.setMessage("用户名或密码错误！");
				response.setStatus(ResponseStatus.FAILED);
				return response;
			} else if (user.getType().intValue() == 1) {
				response.setMessage("禁止后台账号登录！");
				response.setStatus(ResponseStatus.FAILED);
				return response;
			} else {
				if(user.getType()!=type){
					response.setMessage("用户名或密码错误!");
					response.setStatus(ResponseStatus.FAILED);
					return response;
				}
				if(user.isEnabled()==false){
					response.setMessage("该帐号已被冻结");
					response.setStatus(ResponseStatus.FAILED);
					return response;
				}
				UserDetail ud = userService.queryUserDetailById(user.getId());
				if(user.getType()==3){
					Company c = companyService.queryCompanyByUserId(user.getId());
					if(c!=null){
						ud.setVerify_status(c.getVerify_status());
					} else {
						ud.setVerify_status(0);
					}
				}
				Map<String,Object> param = new HashMap<String,Object>();
				SignRecord sr = userService.querySignStatusById(ud.getId());
				if(sr!=null){
					ud.setSign_status(1);
				} else {
					ud.setSign_status(0);
				}
				userService.updateUserLevelByUserId(ud.getId());
				UserDetail u = userService.queryUserDetailById(ud.getId());
				Level le = baseService.queryLevelByLevel(u.getLevel());
				Float per = (u.getExperience().floatValue()/le.getExp().floatValue())*100;
				ud.setExperience(per.intValue());
				ud.setLevel(u.getLevel());
				param.put("User", ud);
				
					if(latitude!=null && longitude!=null){
						Location l = new Location();
						l.setUser_id(ud.getId());
						l.setLatitude(latitude);
						l.setLongitude(longitude);
						baseService.saveLocation(l);
					}
				response.setData(param);
				response.setMessage("登录成功！");
				request.getSession().setAttribute(Constants.SESSION_APP_LOGIN_USER, ud);
			}
		}
		return response;
	}

	/**
	 * 获取校验码
	 * 
	 * @param request
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/validate_code", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JsonResWrapper validateCode(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude, @RequestParam(required = false) String phone, @RequestParam(required = false) String type) {
		JsonResWrapper response = new JsonResWrapper();
		if (StringUtils.isBlank(phone)) {
			response.setMessage("手机号为空！");
			response.setStatus(ResponseStatus.FAILED_PARAM_LOST);
		} else if (StringUtils.isBlank(type)) {
			response.setMessage("获取验证码类型为空！");
			response.setStatus(ResponseStatus.FAILED_PARAM_LOST);
		} else if (!StringUtils.equals(type, "add") && !StringUtils.equals(type, "mod")) {
			response.setMessage("获取验证码类型错误！");
			response.setStatus(ResponseStatus.FAILED);
		} else {
			SysUsers user = userService.querySysUserByUPD(phone, null);
			if (StringUtils.equals(type, "mod")) {
				if (user == null) {
					response.setMessage("手机号暂未注册！");
					response.setStatus(ResponseStatus.FAILED);
					return response;
				}
			} else {
				if(user!=null){
					response.setMessage("手机号已经注册！");
					response.setStatus(ResponseStatus.FAILED);
					return response;
				}
			}
			/*Object vco = request.getSession().getAttribute("VC_" + phone);
			String vc="";
			if(vco!=null){
				vc = (String)vco;
			} else{*/
			String vc = DataUtil.generateRandomString(4);
			//}
			String result = SendTemplateSMS.sendSms(phone,vc,"3");
			if (result.equals("ok")) {
				request.getSession().setAttribute("VC_" + phone, vc);
				request.getSession().setAttribute("VC_" + phone+"_time", new Date());
				response.setMessage("短信发送成功！");
			} else {
				response.setMessage(result);
				response.setStatus(ResponseStatus.FAILED);
			}
		}
		return response;
	}

	
	
	
/**
	 * 验证验证码
	 * 
	 * @param request
	 * @param model
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/checkValicode", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper checkValicode(HttpServletRequest request, Model model, @RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,@RequestParam(required = false) String phone,
			@RequestParam(required = false) String checkValicode) {
		JsonResWrapper response = new JsonResWrapper();
		Object vco = request.getSession().getAttribute("VC_" + phone);
		Object et = request.getSession().getAttribute("VC_" + phone + "_time");
		System.out.println(vco+"--------"+checkValicode);
		if (vco == null) {
			response.setMessage("请先获取验证码！");
			response.setStatus(ResponseStatus.FAILED);
		} else if (!StringUtils.equalsIgnoreCase(checkValicode, (String) vco)) {
			response.setMessage("错误的验证码！");
			response.setStatus(ResponseStatus.FAILED);
		} else if (StringUtils.isBlank(phone)) {
			response.setMessage("手机号码为空！");
			response.setStatus(ResponseStatus.FAILED_PARAM_LOST);
		} else {
			if (et != null){
				Calendar ca = Calendar.getInstance();
				Calendar can = Calendar.getInstance();
				ca.setTime((Date)et);
				can.setTime(new Date());
				ca.add(Calendar.MINUTE, 3);
				if(ca.compareTo(can) < 0){
					request.getSession().removeAttribute("VC_" + phone);
					request.getSession().removeAttribute("VC_" + phone+"_time");
					response.setMessage("验证码已过期！");
					response.setStatus(ResponseStatus.FAILED);
					return response;
				}
			}
			/*SysUsers user = userService.querySysUserByUPD(phone, null);
			if (user != null) {
				response.setMessage("用户名已存在！");
				response.setStatus(ResponseStatus.FAILED);
				response.setData(null);
			} else {*/
				request.getSession().removeAttribute("VC_" + phone);
				request.getSession().removeAttribute("VC_" + phone+"_time");
				response.setMessage("验证成功！");
				response.setStatus("200");
			//}
		}
		return response;
	}

/**
 * 注册
 * @param request
 * @param model
 * @param phone
 * @param password
 * @param validateCode
 * @param user_type
 * @return
 */
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public @ResponseBody
	JsonResWrapper regQ(HttpServletRequest request, Model model, 
			@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,
						@RequestParam(required = false) String phone, 
						@RequestParam(required = false) String password, 
						@RequestParam(required = false) Integer type) {
		JsonResWrapper response = new JsonResWrapper();
		
		if (StringUtils.isBlank(phone)) {
			response.setMessage("手机号码为空！");
			response.setStatus(ResponseStatus.FAILED_PARAM_LOST);
		} else if (StringUtils.isBlank(password)) {
			response.setMessage("密码为空！");
			response.setStatus(ResponseStatus.FAILED_PARAM_LOST);
		}else if(type==null){
			response.setMessage("未指定账户类型！");
			response.setStatus(ResponseStatus.FAILED_PARAM_LOST);
		}else {		
			SysUsers user = userService.querySysUserByUPD(phone, null);
			if (user != null) {
					response.setMessage("用户名已存在！");
					response.setStatus(ResponseStatus.FAILED);
					response.setData(null);
				
			} else {
				PasswordEncoder spe = new BCryptPasswordEncoder();
				String pwd = spe.encode(password);
				SysUsers su = new SysUsers();
				su.setType(type);
				su.setPassword(pwd);
				su.setUsername(phone);
				su.setNick_name("用户");
					
				userService.saveUser(su);
				response.setMessage("注册成功！");
				response.setStatus("200");
			}
		}
		return response;
	}
	
	

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/change_pwd", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper changePwd(HttpServletRequest request, @RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,@RequestParam(required = false) String phone, @RequestParam(required = false) String password,
			@RequestParam(required = false) String validateCode) {
		JsonResWrapper response = new JsonResWrapper();
		Object vco = request.getSession().getAttribute("VC_" + phone);
		Object et = request.getSession().getAttribute("VC_" + phone + "_time");
		if (vco == null) {
			response.setMessage("请先获取验证码！");
			response.setStatus(ResponseStatus.FAILED);
		} else if (!StringUtils.equalsIgnoreCase(validateCode, (String) vco)) {// 修改错误
			response.setMessage("错误的验证码！");
			response.setStatus(ResponseStatus.FAILED);
		} else if (StringUtils.isBlank(phone)) {
			response.setMessage("手机号[phone]为空！");
			response.setStatus(ResponseStatus.FAILED_PARAM_LOST);
		} else if (StringUtils.isBlank(password)) {
			response.setMessage("新密码[password]为空！");
			response.setStatus(ResponseStatus.FAILED_PARAM_LOST);
		} else {
			if (et != null){
				Calendar ca = Calendar.getInstance();
				Calendar can = Calendar.getInstance();
				ca.setTime((Date)et);
				can.setTime(new Date());
				ca.add(Calendar.MINUTE, 3);
				if(ca.compareTo(can) < 0){
					request.getSession().removeAttribute("VC_" + phone);
					request.getSession().removeAttribute("VC_" + phone+"_time");
					response.setMessage("验证码已过期！");
					response.setStatus(ResponseStatus.FAILED);
					return response;
				}
			}
			SysUsers user = userService.querySysUserByUPD(phone, null);
			if (user == null) {
				response.setMessage("该手机[phone]未注册！");
				response.setStatus(ResponseStatus.FAILED);
			} else {
				PasswordEncoder spe = new BCryptPasswordEncoder();
				String pwd = spe.encode(password);
				userService.updatePassword(user.getId(), pwd);
				response.setMessage("密码修改成功！");
			}
		}
		return response;
	}

	
	/**
	 * 登录登录接口
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login_out", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JsonResWrapper loginOut(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude, Model model) {
		UserDetail users = (UserDetail) request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
		if(users != null) {
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(users.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		}
		request.getSession().removeAttribute(Constants.SESSION_APP_LOGIN_USER);
		request.getSession().invalidate();
		JsonResWrapper response = new JsonResWrapper();
		return response;
	}
	
	
	@RequestMapping(value = "/mainIndex", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper mainIndex(HttpServletRequest request, Model model,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude, @RequestParam(required = false) Integer section) {
		JsonResWrapper response = new JsonResWrapper();
		
		if (section == null) {
			response.setMessage("地区id不可为空！");
			response.setStatus(ResponseStatus.FAILED_PARAM_LOST);
		}else {		
			UserDetail users = (UserDetail) request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
			if(users != null) {
				if(latitude!=null && longitude!=null){
					Location l = new Location();
					l.setUser_id(users.getId());
					l.setLatitude(latitude);
					l.setLongitude(longitude);
					baseService.saveLocation(l);
				}
			}
			PageSupport ps = PageSupport.initPageSupport(request);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("section", section);
			ps.setPageSize(15);
			List<MainList> ml = advService.queryMainList(param,ps);
			if(!CollectionUtils.isEmpty(ml)){
				//循环改东西,加前缀
				Integer i =1;
				for(MainList m:ml){
					m.setAdv_img(SettingUtils.getCommonSetting("base.image.url") + m.getAdv_img());
					
					Long n_t = System.currentTimeMillis();
					Long a_t = m.getAdv_time().getTime();
					m.setAdv_status((a_t > n_t ? 0:1));
					m.setAdv_sub_time((a_t > n_t ? a_t-n_t:0));
					m.setGold_type(m.getAmount() > 5000.00? 3:m.getAmount() > 1000.00? 2:1);
					//随机地图的id号
					m.setSub_section_id(i++);
				}
				
			} else {
				ml = new ArrayList<MainList>();
			}
			response.setStatus("200");
			Map<String,Object> param1 = new HashMap<String,Object>();
			param1.put("MainList", ml);
			response.setData(param1);
		}
		return response;
	}
	
	@RequestMapping(value = "/sign", method = RequestMethod.POST)
	public @ResponseBody
	JsonResWrapper sign(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude, Model model) {
		JsonResWrapper response = new JsonResWrapper();
		UserDetail users = (UserDetail) request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
		if(users == null) {
			response.setMessage("请重新登陆！");
			response.setStatus("209");
			return response;
		}
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(users.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		SignRecord sr = userService.querySignStatusById(users.getId());
		if(sr!=null){
			response.setMessage("您今天已经签到,明天再来吧！");
			response.setStatus("201");
			return response;
		}
		UserDetail u = userService.queryUserDetailById(users.getId());
		SignTable st = userService.querySignTableById((u.getSign_count()+1));
		sr = new SignRecord();
		sr.setUser_id(u.getId());
		sr.setExp(st.getExp());
		sr.setGold_count(st.getGold());
		sr.setGrab(st.getGrab());
		sr.setLucky(st.getLucky());
		userService.updateSign(sr);
		response.setMessage("签到成功！");
		response.setStatus("200");
		return response;
	}
	
	@RequestMapping(value = "/setHead", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper setHead(HttpServletRequest request, Model model,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,@RequestParam(required=false) Integer user_id,@RequestParam(required=false) String head) {
		JsonResWrapper response = new JsonResWrapper();
		if(user_id == null) {
			response.setMessage("用户id不可为空！");
			response.setStatus("201");
			return response;
		} else if(StringUtils.isBlank(head)){
			response.setMessage("用户头像不可为空！");
			response.setStatus("201");
			return response;
		}
		UserDetail users = (UserDetail) request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
		if(users != null) {
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(users.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		}
		userService.updateUserHead(user_id,head);
		response.setMessage("保存成功！");
		response.setStatus("200");
		return response;
	}
	
	
	@RequestMapping(value = "/getGoldAdv", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper getGoldAdv(HttpServletRequest request, Model model,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,@RequestParam(required=false) Integer id) {
		JsonResWrapper response = new JsonResWrapper();
		if(id == null) {
			response.setMessage("id不可为空！");
			response.setStatus("201");
			return response;
		}
		UserDetail users = (UserDetail) request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
		if(users == null) {
			response.setMessage("请重新登陆！");
			response.setStatus("209");
			return response;
		}
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(users.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		Adv a = advService.queryAdvById(id);
		if(a.getAdv_status() == 2){
			response.setMessage("红包已抢完！");
			response.setStatus("201");
			return response;
		}
		AdvApp ap = new AdvApp();
		ap.setId(a.getId());
		ap.setCompany_name(a.getCompany_name());
		ap.setAdv_time(a.getAdv_time());
		ap.setAdv_img(SettingUtils.getCommonSetting("base.image.url") + a.getAdv_img());
		//ap.setAdv_img(a.getAdv_img());
		ap.setAdv_play_time(a.getAdv_play_time());
		ap.setAdv_pre_time(a.getAdv_pre_time());
		Long n_t = System.currentTimeMillis();
		Long a_t = a.getAdv_time().getTime();
		ap.setAdv_status((a_t > n_t ? 0:1));
		ap.setAdv_sub_time((a_t > n_t ? a_t-n_t:0));
		
		Income ic = advService.queryInComeByUserIdAndAdvId(users.getId(),id);
		if(ic!=null){
			if(ic.getUse_card()==1 && ap.getAdv_status()==0){
				ap.setUse_card(ic.getUse_card());
			} else {
				response.setMessage("您已经领取过该红包了！");
				response.setStatus("201");
				return response;
			}
		} else {
			ap.setUse_card(0);
		}
		
		//ap.setUse_card(0);
		
		advService.updateAdvClickCount(a);
		
		Map<String,Object> pa = new HashMap<String,Object>();
		pa.put("Adv", ap);
		
		Item item = userService.querySysUserItemById(users.getId());
		
		//第一次打开增加经验值
		AdvGrabRecord agr = advService.queryAdvGrabRecordByUserIdANDAdvId(users.getId(), id);
		if(agr == null){
			agr = new AdvGrabRecord();
			agr.setAdv_id(id);
			agr.setUser_id(users.getId());
			advService.insertAdvGrabRecord(agr);
			//更新经验值
			userService.updateUserExperience(users.getId(),5);
		}
		
		pa.put("Item", item);
		response.setStatus("200");
		response.setData(pa);
		return response;
	}
	
	
	
	@RequestMapping(value = "/getGold", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper getGold(HttpServletRequest request, Model model,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,@RequestParam(required=false) String grab,@RequestParam(required=false) Integer id,@RequestParam(required=false) String lucky) {
		JsonResWrapper response = new JsonResWrapper();
		if(id == null) {
			response.setMessage("id不可为空！");
			response.setStatus("201");
			return response;
		}
		UserDetail users = (UserDetail) request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
		if(users == null) {
			response.setMessage("请重新登陆！");
			response.setStatus("209");
			return response;
		}
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(users.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
			Income ic = advService.queryInComeByUserIdAndAdvId(users.getId(),id);
			if(ic!=null){
				
					response.setMessage("您已经领取过该红包了！");
					response.setStatus("201");
					return response;
			
			}
			
			Adv a = advService.queryAdvById(id);
			Long n_t = System.currentTimeMillis();
			Long a_t = a.getAdv_time().getTime();
			if(a_t > n_t){
				if((!StringUtils.isBlank(grab) &&  !grab.equals("0")) || (!StringUtils.isBlank(lucky) && !lucky.equals("0"))){
					
				} else {
				response.setMessage("红包还未开抢！");
				response.setStatus("201");
				return response;
				}
			}
			if(a.getAdv_status() == 2){
				response.setMessage("红包已抢完！");
				response.setStatus("201");
				return response;
			}
			UserDetail u = userService.queryUserDetailById(users.getId());
			if(!StringUtils.isBlank(grab)&&grab.equals("1")){
				if(u.getGrab() < 1){
					response.setMessage("抢位卡不够！");
					response.setStatus("201");
					return response;
				}
			}
			if(!StringUtils.isBlank(lucky)&&lucky.equals("1")){
				if(u.getLucky() < 1){
					response.setMessage("幸运卡不够！");
					response.setStatus("201");
					return response;
				}
			}
			advService.updateGrab(a,users.getId(),grab,lucky);
		response.setStatus("200");
		
		return response;
	}
	
	@RequestMapping(value = "/getGoldResult", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper getGoldResult(HttpServletRequest request, Model model,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,@RequestParam(required=false) Integer id) {
		JsonResWrapper response = new JsonResWrapper();
		if(id == null) {
			response.setMessage("id不可为空！");
			response.setStatus("201");
			return response;
		}
		UserDetail users = (UserDetail) request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
		if(users != null) {
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(users.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		}
		Map<String,Object> pa = new HashMap<String,Object>();
		response.setStatus("200");
		Income i = advService.queryInComeByUserIdAndAdvId(users.getId(), id);
		if(i==null){
			response.setMessage("未抢到红包！");
			response.setStatus("201");
			return response;
		}
		pa.put("gold", i.getGold_count());
		response.setData(pa);
		return response;
	}
	
}
