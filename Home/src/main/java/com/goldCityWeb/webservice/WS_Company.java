package com.goldCityWeb.webservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.Adv;
import com.goldCityWeb.domain.ChargeRecord;
import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.CompanyType;
import com.goldCityWeb.domain.Location;
import com.goldCityWeb.domain.SysUsers;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.json.ResponseStatus;
import com.goldCityWeb.service.AdvService;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.util.Constants;
import com.goldCityWeb.util.DataUtil;
import com.goldCityWeb.util.EasyuiPaging;
import com.goldCityWeb.util.PageSupport;
import com.goldCityWeb.util.SettingUtils;
import com.goldCityWeb.util.UserUtils;

@Controller
@RequestMapping(value="/services/company/")
public class WS_Company {
	@Autowired
	private CompanyService companyService;
	@Autowired
	private BaseService baseService;
	
	@Autowired
	private AdvService advService;
	
	@RequestMapping(value="getAllCompanyType", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JsonResWrapper getAllCompanyType(@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,HttpServletRequest request) {
		JsonResWrapper response = new JsonResWrapper();
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
		List<CompanyType> typeList = companyService.queryAllCompanyType();
		response.setStatus(ResponseStatus.OK);
		response.setData(typeList);
		return response;
	}
	
	
	@RequestMapping(value="listhotc", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JsonResWrapper listhotcomp(Model view,HttpServletRequest request,@RequestParam(required=false) Integer type,@RequestParam(required=false) String name){
		JsonResWrapper jrw=new JsonResWrapper();
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("verify_status", 1);
		
		if(!StringUtils.isBlank(name)) {
			param.put("name", name);
			//view.addAttribute("name", name);
		}
		if(type!=null && type.intValue() > 0) {
			param.put("type", type);
			//view.addAttribute("type", type);
		}

		List<Company> comList =  companyService.queryHotCompanyList(ps, param);
		if(!CollectionUtils.isEmpty(comList)){
			for(Company c:comList){
				if(!StringUtils.isBlank(c.getLogo())){
					c.setLogo(SettingUtils.getCommonSetting("base.image.url") + c.getLogo());
				}
			}
		} else {
			comList = new ArrayList<Company>();
		}
		
		Map<String,Object> data=new HashMap<String, Object>();
		data.put("hotcompany", comList);
		jrw.setData(data);
		return jrw;
	}
	
	@RequestMapping(value="companyDetail", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JsonResWrapper companyDetail(Model view,HttpServletRequest request,@RequestParam(required=false) Integer id){
		JsonResWrapper jrw=new JsonResWrapper();
		Company c =  companyService.queryCompanyById(id);
		if(!StringUtils.isBlank(c.getLogo())){
			c.setLogo(SettingUtils.getCommonSetting("base.image.url") + c.getLogo());
		}
		Adv a = advService.queryLatestAdvByCId(c.getId());
		if(a!=null){
			if(!StringUtils.isBlank(a.getAdv_img())){
				a.setAdv_img(SettingUtils.getCommonSetting("base.image.url") + a.getAdv_img());
			}
		} else {
			a = new Adv();
		}
		Map<String,Object> data=new HashMap<String, Object>();
		data.put("Company", c);
		
		data.put("Adv", a);
		jrw.setData(data);
		return jrw;
	}
	
	/**
	 * 保存商铺信息
	 * @param request
	 * @param company_name		商铺名称
	 * @param company_phone	店铺电话
	 * @param province_id	省
	 * @param city_id		市
	 * @param position_id	区
	 * @param address		详细地址
	 * @param phone_num		联系人电话
	 * @param web_link		店铺网址（可选）
	 * @param company_type		店铺类型
	 * @param licence		营业执照
	 * @param trade_license	特殊行业许可证
	 * @return
	 */
/*	@RequestMapping(value="save", method=RequestMethod.POST)
	public @ResponseBody JsonResWrapper saveCompany(HttpServletRequest request, @RequestParam String company_name,
			@RequestParam String company_phone, @RequestParam Integer province_id, @RequestParam Integer city_id,
			@RequestParam Integer position_id, @RequestParam String address, @RequestParam String phone_num,
			@RequestParam(required=false) String web_link, @RequestParam Integer company_type, @RequestParam String licence,
			@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,
			@RequestParam(required=false) String trade_license) {
		JsonResWrapper response = new JsonResWrapper();
		Company company = companyService.queryCompanyByName(company_name);
		if(company != null && company.getId().intValue() > 0) {
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("商铺名已存在");
			return response;
		}
		UserDetail ud = UserUtils.getSessionUser(request);
		if(ud == null || ud.getId().intValue() <= 0) {
			response.setStatus("209");
			response.setMessage("请先登录");
			return response;
		}
		
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(ud.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		company = companyService.queryCompanyByUserId(ud.getId());
		if(company != null && company.getId().intValue() > 0) {
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("该账号已关联商铺");
			return response;
		}
		if(StringUtils.isBlank(licence)) {
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("营业执照不能为空");
			return response;
		}
		company = new Company();
		String licensePath = null;
		String tradeLicensePath = null;
		try {
			licensePath = DataUtil.moveToDir(licence, true);
			if(!StringUtils.isBlank(trade_license)) {
				tradeLicensePath = DataUtil.moveToDir(trade_license, true);
			}
		} catch(Exception e) {
			e.printStackTrace();
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("图片地址错误");
			return response;
		}
		
		company.setCompany_name(company_name);
		company.setUser_id(ud.getId());
		company.setCompany_phone(company_phone);
		company.setProvince_id(province_id);
		company.setCity_id(city_id);
		company.setPosition_id(position_id);
		company.setAddress(address);
		company.setPhone_num(phone_num);
		company.setWeb_link(web_link);
		company.setCompany_type(company_type);
		company.setLicence(licensePath);
		company.setTrade_license(tradeLicensePath);
		
		companyService.addCompany (company);
		response.setMessage("保存成功");
		response.setStatus(ResponseStatus.OK);
		return response;
	}*/
	

	/**
	 * 保存商铺信息
	 * @param request
	 * @param company_name		商铺名称
	 * @param company_phone	店铺电话
	 * @param province_id	省
	 * @param city_id		市
	 * @param position_id	区
	 * @param address		详细地址
	 * @param link_man		联系人姓名
	 * @param phone_num		联系人电话
	 * @param web_link		店铺网址（可选）
	 * @param company_type		店铺类型
	 * @param licence		营业执照
	 * @param trade_license	特殊行业许可证
	 * @param logo 企业logo
	 * @param description 商家简介
	 * @param real_auth 实名认证
	 * @param bank_card_num 银行卡号
	 * @param bank_name 银行名称
	 * @param bank_phone 开卡手机号
	 * @return
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	public @ResponseBody JsonResWrapper saveCompany(HttpServletRequest request, Model model, 
							@RequestParam(required=false) String company_name,
							@RequestParam(required=false) String link_man,
							@RequestParam(required=false) String company_phone,
							@RequestParam(required=false) Integer province_id,
							@RequestParam(required=false) Integer city_id,
							@RequestParam(required=false) Integer position_id,
							@RequestParam(required=false) String address, 
							@RequestParam(required=false) String phone_num,
							@RequestParam(required=false) String web_link, 
							@RequestParam(required=false) Integer company_type,
							@RequestParam(required = false) Float latitude,
							@RequestParam(required = false) Float longitude,
							@RequestParam(required = false) String description,
							@RequestParam(required = false) String bank_card_num,
							@RequestParam(required = false) String bank_name,
							@RequestParam(required = false) String bank_phone,
							@RequestParam(required = false) String trade_license,
							@RequestParam(required = false) String real_auth,
							@RequestParam(required = false) String logo,
							@RequestParam(required = false) String licence) {
		
		JsonResWrapper response = new JsonResWrapper();
		Company company = companyService.queryCompanyByName(company_name);
		UserDetail ud = UserUtils.getSessionUser(request);
		
		if(ud == null || ud.getId().intValue() <= 0) {
			response.setStatus("209");
			response.setMessage("请先登录");
			return response;
		}
		
		if(latitude!=null && longitude!=null){
			Location l = new Location();
			l.setUser_id(ud.getId());
			l.setLatitude(latitude);
			l.setLongitude(longitude);
			baseService.saveLocation(l);
		}
		
		company = companyService.queryCompanyByUserId(ud.getId());
		
		if(company == null) {
			company = companyService.queryCompanyByName(company_name);
			if(company != null) {
				response.setStatus(ResponseStatus.FAILED);
				response.setMessage("此商家已存在");
				return response;
			} else {
				company = new Company();
			}
		}			
		
		String licencePath = null;
		String tradeLicensePath = null;
		String logoPath = null;
		String realAuthPath = null;
		List<String> temp = null;
		try {
			
			/*if(!StringUtils.isBlank(licencePath)) company.setLicence(licencePath);
			if(!StringUtils.isBlank(realAuthPath)) company.setReal_auth(realAuthPath);
			if(!StringUtils.isBlank(tradeLicensePath)) company.setTrade_license(tradeLicensePath);
			if(!StringUtils.isBlank(logoPath)) company.setLogo(logoPath);*/
			
			
			if(company_type.intValue()==3){ //微商
				if(!StringUtils.isBlank(real_auth)){
					String path = DataUtil.moveToDir(real_auth, true);
					company.setReal_auth(path);
				}
				/*temp = DataUtil.uploadImg(request, "real_auth"); //实名认证
				if((temp == null || temp.size() == 0 || StringUtils.isBlank(temp.get(0))) && StringUtils.isBlank(company.getReal_auth())) {
					response.setStatus(ResponseStatus.FAILED);
					response.setMessage("实名认证不能为空");
					return response;
				}
				realAuthPath = temp.get(0);*/
			}else{ //商家
				/*temp = DataUtil.uploadImg(request, "licence"); //营业执照
				if((temp == null || temp.size() == 0 || StringUtils.isBlank(temp.get(0))) && StringUtils.isBlank(company.getLicence())) {
					response.setStatus(ResponseStatus.FAILED);
					response.setMessage("营业执照不能为空");
					return response;
				}
				licencePath = temp.get(0);*/
				if(!StringUtils.isBlank(licence)){
					String path = DataUtil.moveToDir(licence, true);
					company.setLicence(path);
				}
			}
			if(!StringUtils.isBlank(trade_license)){
				String path = DataUtil.moveToDir(trade_license, true);
				company.setTrade_license(path);
			}
			if(!StringUtils.isBlank(logo)){
				String path = DataUtil.moveToDir(logo, true);
				company.setLogo(path);
			}
		} catch(Exception e) {
			e.printStackTrace();
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("文件上传错误");
			return response;
		}
		
		company.setCompany_name(company_name);
		company.setUser_id(ud.getId());
		company.setCompany_phone(company_phone);
		company.setProvince_id(province_id);
		company.setCity_id(city_id);
		company.setPosition_id(position_id);
		company.setAddress(address);
		company.setPhone_num(phone_num);
		company.setWeb_link(web_link);
		company.setCompany_type(company_type);
		company.setBank_card_num(bank_card_num);
		company.setBank_name(bank_name);
		company.setBank_phone(bank_phone);
		company.setDescription(description);
		company.setLink_man(link_man);
		company.setLatitude(latitude);
		company.setLongitude(longitude);
		
		
		companyService.addCompany(company);
		response.setMessage("保存成功");
		response.setStatus(ResponseStatus.OK);
		return response;
	}
	
	
	/**
	 * 充值列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/chargeList", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody JsonResWrapper chargeList(HttpServletRequest request) {
		JsonResWrapper response = new JsonResWrapper();
		UserDetail ud = UserUtils.getSessionUser(request);
		
		if(ud == null || ud.getId().intValue() <= 0) {
			response.setStatus("209");
			response.setMessage("请先登录");
			return response;
		}
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> param = new HashMap<String, Object>();
		//param.put("page", ((page-1)*rows));
		//param.put("rows", rows);
		param.put("mid", ud.getId());
		param.put("status", 2);
		
		/*if(from!=null && from!=-1){
			param.put("from", from);
		}*/
		
		/*try{
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
		}*/
		PageSupport ps = PageSupport.initPageSupport(request);
		List<ChargeRecord> crs = companyService.queryChargeRecord(param,ps);
		if(CollectionUtils.isEmpty(crs)){
			crs = new ArrayList<ChargeRecord>();
		}
		
		Map<String,Object> data=new HashMap<String, Object>();
		data.put("ChargeRecord", crs);
		response.setData(data);
		response.setStatus("200");
		return response;
	}
	
	
	
}
