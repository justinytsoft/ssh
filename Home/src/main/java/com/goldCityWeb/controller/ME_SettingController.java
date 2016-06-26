package com.goldCityWeb.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.City;
import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.CompanyType;
import com.goldCityWeb.domain.Position;
import com.goldCityWeb.domain.SysUsers;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.util.DataUtil;
import com.goldCityWeb.util.GeoCodeMapUtils;
import com.goldCityWeb.util.SettingUtils;

@Controller
@RequestMapping(value="/merchant/setting")
public class ME_SettingController {
	@Autowired
	private CompanyService companyService;
	@Autowired
	private BaseService baseService;
	
	/**
	 * 设置首页
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/index")
	public String renZheng(Model model, HttpServletRequest request) {
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Company company = companyService.queryCompanyByUserId(user.getId());
		model.addAttribute("company", company);
		List<CompanyType> ctList = companyService.queryAllCompanyType();
		model.addAttribute("ctList", ctList);

		/*if(company!=null){
			String pre = SettingUtils.getCommonSetting("base.image.url");
			if(!StringUtils.isBlank(company.getLicence())){
				company.setLicence(pre + company.getLicence());
			}
			if(!StringUtils.isBlank(company.getLogo())){
				company.setLogo(pre + company.getLogo());
			}
			if(!StringUtils.isBlank(company.getReal_auth())){
				company.setReal_auth(pre + company.getReal_auth());
			}
			if(!StringUtils.isBlank(company.getTrade_license())){
				company.setTrade_license(pre + company.getTrade_license());
			}
		}*/
		
		return "merchant/renZheng";
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
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveCompany(HttpServletRequest request, Model model, 
							@RequestParam(required=false) String company_name,
							@RequestParam(required=false) String company_phone,
							@RequestParam(required=false) Integer company_type,
							@RequestParam(required=false) String link_man,
							@RequestParam(required=false) Integer province_id,
							@RequestParam(required=false) Integer city_id,
							@RequestParam(required=false) Integer position_id,
							@RequestParam(required=false) String address, 
							@RequestParam(required=false) String phone_num,
							@RequestParam(required=false) String web_link, 
							@RequestParam(required = false) Float latitude,
							@RequestParam(required = false) Float longitude,
							@RequestParam(required = false) String description,

							@RequestParam(required = false) String licensePath,
							@RequestParam(required = false) String realAuthPath,
							@RequestParam(required = false) String logoPath,
							@RequestParam(required = false) String trade_licensePath,
							
							@RequestParam(required = false) String bank_card_num,
							@RequestParam(required = false) String bank_name,
							@RequestParam(required = false) String bank_phone) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Company company = companyService.queryCompanyByUserId(user.getId());
		Company com = companyService.queryCompanyByName(company_name);
		
		if(StringUtils.isBlank(company_name)||StringUtils.isBlank(link_man)||StringUtils.isBlank(company_phone)||province_id==null
		   ||city_id==null||position_id==null||StringUtils.isBlank(address)||StringUtils.isBlank(bank_card_num)
		   ||StringUtils.isBlank(bank_name)||StringUtils.isBlank(bank_phone)){
			map.put("msg", "参数为空");
			map.put("flag", false);
			return map;
		}
		
		if(company == null) { //判断新增公司信息时 公司名称是否已被使用
			if(com != null) {
				map.put("msg", "商家名称["+company_name+"]已存在");
				map.put("flag", false);
				return map;
			} else {
				company = new Company();
			}
		}else{ //判断修改公司信息时 公司名称是否已被使用
			if(com!=null && com.getId()!=company.getId()){
				map.put("msg", "商家名称["+company_name+"]已存在");
				map.put("flag", false);
				return map;
			}
		}
		
		City c = baseService.queryCityById(city_id);
		Position p = baseService.queryPositionById(position_id);
		String cpa = c.getName() + p.getName() + address;
		float [] data = GeoCodeMapUtils.addressToGPS(cpa);
		if(data==null){
			map.put("msg", "未查询到对应地址");
			map.put("flag", false);
			return map;
		}
		longitude = data[0];
		latitude = data[1];
		
		/*if(latitude!=null && longitude!=null){
			Location l = new Location();
			l.setUser_id(user.getId());
			l.setLatitude(latitude);
			l.setLongitude(longitude);
			baseService.saveLocation(l);
		}*/
		
		try{
			String pre = SettingUtils.getCommonSetting("upload.file.temp.path");
			String path = null;
			File file = null;
			
			if(company_type.intValue()==3){ //微商
				if(StringUtils.isBlank(realAuthPath)){
					map.put("msg", "实名认证不能为空");
					map.put("flag", false);
					return map;
				}
				path = pre + File.separator + realAuthPath;// 临时文件夹
				file = new File(path);
				if(file.exists()){
					realAuthPath = DataUtil.moveToDir(realAuthPath, true);
				}
				company.setReal_auth(realAuthPath);
			}else{ //商家
				if(StringUtils.isBlank(licensePath)){
					map.put("msg", "营业执照不能为空");
					map.put("flag", false);
					return map;
				}
				path = pre + File.separator + licensePath;// 临时文件夹
				file = new File(path);
				if(file.exists()){
					licensePath = DataUtil.moveToDir(licensePath, true);
				}
				company.setLicence(licensePath);
			}
			
			if(!StringUtils.isBlank(logoPath)){
				path = pre + File.separator + logoPath;// 临时文件夹
				file = new File(path);
				if(file.exists()){
					logoPath = DataUtil.moveToDir(logoPath, true);
				}
				company.setLogo(logoPath);
			}
			
			if(!StringUtils.isBlank(trade_licensePath)){
				path = pre + File.separator + trade_licensePath;// 临时文件夹
				file = new File(path);
				if(file.exists()){
					trade_licensePath = DataUtil.moveToDir(trade_licensePath, true);
				}
				company.setTrade_license(trade_licensePath);
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "文件上传错误");
			map.put("flag", false);
			return map;
		}
		
		/*String licencePath = null;
		String tradeLicensePath = null;
		String logoPath = null;
		String realAuthPath = null;
		List<String> temp = null;
		try {
			if(company_type.intValue()==3){ //微商
				temp = DataUtil.uploadImg(request, "real_auth"); //营业执照
				if((temp == null || temp.size() == 0 || StringUtils.isBlank(temp.get(0))) && StringUtils.isBlank(company.getReal_auth())) {
					map.put("msg", "实名认证不能为空");
					map.put("flag", false);
					return map;
				}
				realAuthPath = temp.get(0);
			}else{ //商家
				temp = DataUtil.uploadImg(request, "licence"); //营业执照
				if((temp == null || temp.size() == 0 || StringUtils.isBlank(temp.get(0))) && StringUtils.isBlank(company.getLicence())) {
					map.put("msg", "营业执照不能为空");
					map.put("flag", false);
					return map;
				}
				licencePath = temp.get(0);
			}
			temp = DataUtil.uploadImg(request, "trade_license");//临时存放特种行业许可证
			if(temp != null && temp.size() > 0 && !StringUtils.isBlank(temp.get(0))) {
				tradeLicensePath = temp.get(0);
			}
			temp = DataUtil.uploadImg(request, "logo");//logo
			if(temp != null && temp.size() > 0 && !StringUtils.isBlank(temp.get(0))) {
				logoPath = temp.get(0);
			}
		} catch(Exception e) {
			e.printStackTrace();
			map.put("msg", "文件上传错误");
			map.put("flag", false);
			return map;
		}*/
		
		company.setCompany_name(company_name);
		company.setUser_id(user.getId());
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
		company.setLongitude(longitude);
		company.setLatitude(latitude);
		
		/*if(!StringUtils.isBlank(licencePath)) company.setLicence(licencePath);
		if(!StringUtils.isBlank(realAuthPath)) company.setReal_auth(realAuthPath);
		if(!StringUtils.isBlank(tradeLicensePath)) company.setTrade_license(tradeLicensePath);
		if(!StringUtils.isBlank(logoPath)) company.setLogo(logoPath);*/
		
		companyService.addCompany(company);
		map.put("msg", "已提交");
		map.put("flag", true);
		return map;
	}
	
	/**
	 * 打开标注地理位置
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/mapMark", method = RequestMethod.GET)
	public String mapMark(HttpServletRequest request, Model model) {
		return "merchant/map";
	}
}
