/**
 * 
 */
package com.goldCityWeb.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.FeeRecord;
import com.goldCityWeb.domain.Income;
import com.goldCityWeb.domain.Item;
import com.goldCityWeb.domain.Level;
import com.goldCityWeb.domain.Location;
import com.goldCityWeb.domain.LoginImg;
import com.goldCityWeb.domain.Message;
import com.goldCityWeb.domain.Product;
import com.goldCityWeb.domain.Report;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.json.ResponseStatus;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.service.IProductService;
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.Constants;
import com.goldCityWeb.util.PageSupport;
import com.goldCityWeb.util.SettingUtils;

/**
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/services/user")
public class WS_User {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BaseService baseService;
	
	@Autowired
	private IProductService productService;
	
	
	/**
	 * 背包列表
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/bagList",method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResWrapper bagList(HttpServletRequest request){
		UserDetail ud = getSessionUser(request);
		JsonResWrapper jrw = new JsonResWrapper();
		if(ud==null){
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			jrw.setMessage("session失效,请重新登录");
			return jrw;
		}
		Item i = userService.querySysUserItemById(ud.getId());
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("category", 0);
		
		List<Product> products = productService.queryProducts(ps,param);
		List<Product> p = new ArrayList<Product>();
		if(i.getGrab() != 0){
			products.get(0).setNumber(i.getGrab());
			products.get(0).getCovers().get(0).setCover(SettingUtils.getCommonSetting("base.image.url") + products.get(0).getCovers().get(0).getCover());
			p.add(products.get(0));
		}
		if(i.getLucky() != 0){
			products.get(1).setNumber(i.getLucky());
			products.get(1).getCovers().get(0).setCover(SettingUtils.getCommonSetting("base.image.url") + products.get(1).getCovers().get(0).getCover());
			p.add(products.get(1));
		}
		/*List<LoginImg> logimgs=userService.queryLoginImgList(param, ps);
		
		if(!CollectionUtils.isEmpty(logimgs)){
			for(LoginImg li:logimgs){
				li.setPath(SettingUtils.getCommonSetting("base.image.url") + li.getPath());
			}
		}*/
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("BagList", p);
		jrw.setData(data);
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	
	/**
	 * 查询金币
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/checkGold",method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResWrapper checkGold(HttpServletRequest request){
		UserDetail ud = getSessionUser(request);
		JsonResWrapper jrw = new JsonResWrapper();
		if(ud==null){
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			jrw.setMessage("session失效,请重新登录");
			return jrw;
		}
		UserDetail u = userService.queryUserDetailById(ud.getId());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("gold", u.getGold_count());
		jrw.setData(data);
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	
	/**
	 * 查询等级
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/checkLevel",method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResWrapper checkLevel(HttpServletRequest request){
		UserDetail ud = getSessionUser(request);
		JsonResWrapper jrw = new JsonResWrapper();
		if(ud==null){
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			jrw.setMessage("session失效,请重新登录");
			return jrw;
		}
		
		userService.updateUserLevelByUserId(ud.getId());
		
		UserDetail u = userService.queryUserDetailById(ud.getId());
		Level l = baseService.queryLevelByLevel(u.getLevel());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("level", u.getLevel());
		Float per = (u.getExperience().floatValue()/l.getExp().floatValue())*100;
		data.put("exp", per.intValue());
		jrw.setData(data);
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	
	/**
	 * 获取所有举报信息类型
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/reporttype",method={RequestMethod.GET,RequestMethod.POST})
	public JsonResWrapper listreporttype(HttpServletRequest request){
		
		JsonResWrapper jrw=new JsonResWrapper();
		
		List<Report> reports=userService.queryReportType();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("reportTypeDetail", isEmpty(reports));
		jrw.setData(data);
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	

	/**
	 * 举报信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/report",method={RequestMethod.GET,RequestMethod.POST})
	public JsonResWrapper doreport(HttpServletRequest request,@RequestParam(required=false) Integer company_id,@RequestParam(required=false) String type,@RequestParam(required=false) String content){
		UserDetail ud=getSessionUser(request);
		JsonResWrapper jrw=new JsonResWrapper();
		if(ud==null){
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			jrw.setMessage("session失效,请重新登录"); 
			return jrw;
		}
		Report rep=new Report();
		//session总是取不到,设个值方便测试
		/*if(ud==null){
			rep.setUser_id(1);
		}else {*/
			rep.setUser_id(ud.getId());
		//}
		if(content==null){
			jrw.setMessage("请输入举报内容!");
			return jrw;
		}
		rep.setContent(content);
		if(type==null){
			jrw.setMessage("请选择举报类型!");
			return jrw;
		}
		rep.setType(type);
		if(company_id==null){
			jrw.setMessage("公司的id没传");
			return jrw;
		}
		rep.setCompany_id(company_id);
		userService.savereport(rep);
		jrw.setStatus(ResponseStatus.OK);
		jrw.setMessage("举报成功");
		return jrw;
	}
	
	
	/**
	 * 获取消费记录列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/feeRecord", method = {RequestMethod.GET,RequestMethod.POST})
	public JsonResWrapper feeRecord(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude){
		UserDetail ud = getSessionUser(request);
		JsonResWrapper jrw = new JsonResWrapper();
		
		if(ud==null){
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			jrw.setMessage("session失效,请重新登录");
			return jrw;
		}
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(ud.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uid", ud.getId());
		param.put("pay_status", "2");
		List<FeeRecord> ms = userService.queryFeeRecords(ps,param);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("feeRecord", isEmpty(ms));
		jrw.setData(data);
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	
	/**
	 * login_img list
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/loginimglist",method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResWrapper loginimgpage(HttpServletRequest request){
		UserDetail ud = getSessionUser(request);
		JsonResWrapper jrw = new JsonResWrapper();
		/*if(ud==null){
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			jrw.setMessage("session失效,请重新登录");
			return jrw;
		}*/
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		List<LoginImg> logimgs=userService.queryLoginImgList(param, ps);
		Map<String, Object> data = new HashMap<String, Object>();
		if(!CollectionUtils.isEmpty(logimgs)){
			for(LoginImg li:logimgs){
				li.setPath(SettingUtils.getCommonSetting("base.image.url") + li.getPath());
			}
		}
		data.put("loginImg", isEmpty(logimgs));
		jrw.setData(data);
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	
	/**
	 * 获取我的消息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/message", method = {RequestMethod.GET,RequestMethod.POST})
	public JsonResWrapper message(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude){
		UserDetail ud = getSessionUser(request);
		JsonResWrapper jrw = new JsonResWrapper();
		
		if(ud==null){
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			jrw.setMessage("session失效,请重新登录");
			return jrw;
		}
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(ud.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uid", ud.getId());
		List<Message> ms = userService.queryMessages(ps,param);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", isEmpty(ms));
		jrw.setData(data);
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	
	/**
	 * 获取我的收益
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/myIncome", method = {RequestMethod.GET,RequestMethod.POST})
	public JsonResWrapper myIncome(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude){
		UserDetail ud = getSessionUser(request);
		JsonResWrapper jrw = new JsonResWrapper();
		
		if(ud==null){
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			jrw.setMessage("session失效,请重新登录");
			return jrw;
		}
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(ud.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uid", ud.getId());
		List<Income> ms = userService.queryIncomes(ps,param);
		if(!CollectionUtils.isEmpty(ms)){
			for(Income i:ms){
				if(!StringUtils.isBlank(i.getLogo())){
					i.setLogo(SettingUtils.getCommonSetting("base.image.url") + i.getLogo());
				}
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("income", isEmpty(ms));
		jrw.setData(data);
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/saveInfo", method = {RequestMethod.GET,RequestMethod.POST})
	public JsonResWrapper saveInfo(HttpServletRequest request,
			@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,
			@RequestParam(required = false) String head,
			@RequestParam(required = false) String nick_name,
			@RequestParam(required = false) Integer age,
			@RequestParam(required = false) Integer sex,
			@RequestParam(required = false) String address,
			@RequestParam(required = false) String work,
			@RequestParam(required = false) Float income
			){
		UserDetail ud = getSessionUser(request);
		JsonResWrapper jrw = new JsonResWrapper();
		
		if(ud==null){
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			jrw.setMessage("session失效,请重新登录");
			return jrw;
		}
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(ud.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		UserDetail u = new UserDetail();
		u.setId(ud.getId());
		if(!StringUtils.isBlank(head)){
			u.setHead(head);
		}
		if(!StringUtils.isBlank(nick_name)){
			u.setNick_name(nick_name);
		}
		if(age!=null && age.intValue() > 0){
			u.setAge(age);
		}
		if(sex!=null && sex.intValue() > 0){
			u.setSex(sex);
		}
		if(!StringUtils.isBlank(address)){
			u.setAddress(address);
		}
		if(!StringUtils.isBlank(work)){
			u.setWork(work);
		}
		if(income!=null){
			u.setIncome(income);
		}
		userService.updateUserDetail(u);
		jrw.setMessage("保存成功!");
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	

	/**
	 * 判断集合是否为 null
	 * @param param
	 * @return 如果不为 null 直接返回，否则创建一个新的 ArrayList 返回
	 */
	private <T> List<T> isEmpty(List<T> param){
		return CollectionUtils.isEmpty(param) ? new ArrayList<T>() : param;
	}
	
	/**
	 * 获取session里的用户
	 * @param request
	 * @return
	 */
	private UserDetail getSessionUser(HttpServletRequest request){
		return (UserDetail)request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
	}
}
