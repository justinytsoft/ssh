package com.goldCityWeb.webservice;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.Location;
import com.goldCityWeb.domain.MainList;
import com.goldCityWeb.domain.Mould;
import com.goldCityWeb.domain.Remind;
import com.goldCityWeb.domain.SeeRecord;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.json.ResponseStatus;
import com.goldCityWeb.service.AdvService;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.DateFormatter;
import com.goldCityWeb.util.ImgBean;
import com.goldCityWeb.util.PageSupport;
import com.goldCityWeb.util.SettingUtils;
import com.goldCityWeb.util.UserUtils;

@Controller
@RequestMapping(value = "/services/adv")
public class WS_Adv {
	@Autowired
	private AdvService advService;
	@Autowired
	private BaseService baseService;
	@Autowired
	private CompanyService companyService;
	
	public static final char[] array = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '0', '1', '2', '3', '4',
		'5', '6', '7', '8', '9', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M' };
	
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public @ResponseBody JsonResWrapper saveAdv(HttpServletRequest request, @RequestParam (required = false)String adv_img,
			@RequestParam String adv_time, @RequestParam Integer section_id, @RequestParam String title,
			@RequestParam String sub_title, @RequestParam Integer type, @RequestParam Float amount,
			@RequestParam(required = false) Integer number,
			@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude) {
		JsonResWrapper response = new JsonResWrapper();
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
		ud = userService.queryUserDetailById(ud.getId());
		Company company = companyService.queryCompanyByUserId(ud.getId());
		if(company == null) {
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("当前未认证商铺，不能发布广告");
			return response;
		}
		if(ud.getGold_count() < amount) {
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("金币数不能大于当前拥有量");
			return response;
		}
		/*if(amount < number) {
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("金币数不能小于发出的红包数量");
			return response;
		}*/
		if(amount < 500) {
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("金币数不能小于500");
			return response;
		}
		/*if(StringUtils.isBlank(adv_img)) {
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("广告图片不能为空");
			return response;
		}*/
		try {
			String sep = File.separator;
			String pre = SettingUtils.getCommonSetting("upload.image.path");// 存放文件文件夹名称
			Mould mould = advService.queryMouldById(type);
			
			if(mould==null){
				response.setStatus(ResponseStatus.FAILED);
				response.setMessage("未查询到对应模版");
				return response;
			}
			
			//创建2级目录
			StringBuffer subDir = new StringBuffer();
			for (int i = 0; i < 2; i++) {
				if (i != 0) {
					subDir.append(sep);
				}
				Random random = new Random();
				StringBuffer sb = new StringBuffer();
				sb.append(array[random.nextInt(array.length)]);
				sb.append(array[random.nextInt(array.length)]);

				subDir.append(sb.toString());
			}

			File dirPath = new File(pre + sep + subDir.toString());
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			
			String copy = pre + sep + mould.getOrigin(); //原图路径
			String merge = pre + sep + subDir.toString() + sep + new Date().getTime() + ".jpg"; //封面和文字合并后的路径
			adv_img = subDir.toString() + sep + new Date().getTime() + ".jpg";
			
			ImgBean imgBean = new ImgBean();
			BufferedImage d = imgBean.loadImageLocal(copy);
			imgBean.writeImageLocal(merge,imgBean.modifyImage(d,"",0,0));//复制原图
			BufferedImage dd = imgBean.loadImageLocal(merge);
			
			int w = dd.getWidth(); //图片宽
			int h = dd.getHeight(); //图片高
			int x1 = 0;
			int y1 = 0;
			int x2 = 0;
			int y2 = 0;
			
			if(type==1){
				x1 = 40;
				y1 = 100;
				x2 = w / 2;
				y2 = h - 100;
			}else if(type==2){
				x1 = 40;
				y1 = 100;
				x2 = 40;
				y2 = 150;
			}else{
				x1 = w / 2;
				y1 = h - 100;
				x2 = w / 2;
				y2 = h - 50;
			}
			
			//合并图片和文字
			imgBean.writeImageLocal(merge,imgBean.modifyImage(dd,title,x1,y1));
			imgBean.writeImageLocal(merge,imgBean.modifyImage(dd,sub_title,x2,y2));
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("图片名称错误");
			return response;
		}
		Date adv_date = DateFormatter.stringToDate(adv_time);
		if(adv_date == null) {
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("时间为空或时间格式错误");
			return response;
		}
		MainList adv = new MainList();
		adv.setAdv_img(adv_img);
		adv.setCompany_id(company.getId());
		adv.setCompany_name(company.getCompany_name());
		adv.setAdv_time(adv_date);
		adv.setSection_id(section_id);
		adv.setTitle(title);
		adv.setSub_title(sub_title);
		adv.setType(type);
		adv.setAmount(amount);
		adv.setNumber(amount.intValue()*2);
		
		adv.setAdv_play_time(3l);
		Calendar c = Calendar.getInstance();
		c.setTime(adv_date);
		if(amount > 5000){
			c.add(Calendar.MINUTE, -3);
			adv.setAdv_pre_time(c.getTime());
		} else if(amount > 1000){
			c.add(Calendar.MINUTE, -2);
			adv.setAdv_pre_time(c.getTime());
		} else{
			c.add(Calendar.MINUTE, -1);
			adv.setAdv_pre_time(c.getTime());
		}
		//测试传1
		adv.setVerify_status(0);
		
		advService.saveAdv(adv);
		ud.setGold_count(ud.getGold_count() - amount);
		userService.updateMerGoldCount(ud);
		response.setStatus(ResponseStatus.OK);
		response.setMessage("保存成功");
		
		return response;
	}
	
	
	
	/**
	 * 获取商家的广告
	 * @param request
	 * @param status 获取类型：1、已发布广告，2、即将发布广告（不填则获取全部）
	 * @Param company_id 商铺id （不填时自动获取当前商铺的广告）
	 * @return
	 */
	@RequestMapping(value="/getAdvList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody JsonResWrapper getAdvList(HttpServletRequest request, @RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,@RequestParam(required=false) Integer status, 
			@RequestParam(required=false) Integer company_id) {
		JsonResWrapper response = new JsonResWrapper();
		UserDetail ud = UserUtils.getSessionUser(request);
		if(ud == null) {
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
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> params = new HashMap<String, Object>();
		if(company_id != null && company_id.intValue() > 0) {
			params.put("company_id", company_id);
		}
		if(status != null && status.intValue() > 0) {
			params.put("status", status);
		}
		params.put("uid", ud.getId());
		List<MainList> advList = advService.queryAdvList(params, ps);
		if(CollectionUtils.isEmpty(advList)){
			advList = new ArrayList<MainList>();
		} else {
			for(MainList m:advList){
				m.setAdv_img(SettingUtils.getCommonSetting("base.image.url") + m.getAdv_img());
				m.setLogo(SettingUtils.getCommonSetting("base.image.url") + m.getLogo());
			}
		}
		response.setStatus(ResponseStatus.OK);
		response.setData(advList);
		
		return response;
	}
	
	@RequestMapping(value = "/getMould", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper getMould(HttpServletRequest request
			) {
		JsonResWrapper response = new JsonResWrapper();
		
		
		Map<String,Object> cosp = new HashMap<String,Object>();
		List<Mould> m = advService.queryAllMoould();
		for(Mould l:m){
			l.setOrigin(SettingUtils.getCommonSetting("base.image.url") + l.getOrigin());
		}
		cosp.put("mould", m);
		response.setData(cosp);
		return response;
	}
	
	
	@RequestMapping(value = "/remind", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper remind(HttpServletRequest request,@RequestParam(required = false) Integer adv_id
			) {
		JsonResWrapper response = new JsonResWrapper();
		UserDetail ud = UserUtils.getSessionUser(request);
		if(ud == null || ud.getId().intValue() <= 0) {
			response.setStatus("209");
			response.setMessage("请先登录");
			return response;
		}
		if(adv_id == null || adv_id.intValue() < 0){
			response.setStatus("201");
			response.setMessage("广告id不可为空!");
			return response;
		}
		Remind r = advService.queryAdvRemindByUserIdANDAdvId(ud.getId(),adv_id);
		if(r!=null){
			//取消推送
			
			//删除数据
			advService.deleteAdvRemindById(r.getId());
		} else {
			//增加推送
			
			//插入数据
			r = new Remind();
			r.setAdv_id(adv_id);
			r.setUser_id(ud.getId());
			advService.insertAdvRemind(r);
		}
		
		response.setMessage("提交成功!");
		response.setStatus("200");
		return response;
	}
	
	
	@RequestMapping(value = "/see", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	JsonResWrapper see(HttpServletRequest request,@RequestParam(required = false) Integer adv_id
			) {
		JsonResWrapper response = new JsonResWrapper();
		UserDetail ud = UserUtils.getSessionUser(request);
		if(ud == null || ud.getId().intValue() <= 0) {
			response.setStatus("209");
			response.setMessage("请先登录");
			return response;
		}
		if(adv_id == null || adv_id.intValue() < 0){
			response.setStatus("201");
			response.setMessage("广告id不可为空!");
			return response;
		}
		SeeRecord sr = advService.queryAdvSeeRecordByUserIdANDAdvId(ud.getId(),adv_id);
		if(sr==null){
			//插入数据
			sr = new SeeRecord();
			sr.setAdv_id(adv_id);
			sr.setUser_id(ud.getId());
			advService.insertAdvSeeRecord(sr);
			//更新经验值
			userService.updateUserExperience(ud.getId(),5);
		}
		
		response.setMessage("提交成功!");
		response.setStatus("200");
		return response;
	}
	
}
