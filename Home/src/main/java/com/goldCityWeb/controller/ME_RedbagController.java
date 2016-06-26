package com.goldCityWeb.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.MainList;
import com.goldCityWeb.domain.Message;
import com.goldCityWeb.domain.MessageType;
import com.goldCityWeb.domain.Mould;
import com.goldCityWeb.domain.SysUsers;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.service.AdvService;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.service.IMessageService;
import com.goldCityWeb.service.IRedbagService;
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.DataUtil;
import com.goldCityWeb.util.EasyuiPaging;
import com.goldCityWeb.util.ImgBean;
import com.goldCityWeb.util.SensitivewordFilter;
import com.goldCityWeb.util.SettingUtils;

@Controller
@RequestMapping("/merchant/redbag")
public class ME_RedbagController {

	@Autowired
	private IRedbagService redbagService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private AdvService advService;
	@Autowired
	private UserService userService;
	@Autowired
	private IMessageService messageService;
	
	public static final char[] array = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '0', '1', '2', '3', '4',
		'5', '6', '7', '8', '9', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M' };
	
	/**
	 * 通过ID 获取广告详情
	 * @return
	 */
	@RequestMapping("/getAdvById")
	@ResponseBody
	public Map<String, Object> getAdvById(@RequestParam(required=false) Integer id){
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
		
		MainList m = redbagService.queryAdvById(id);
		
		if(m==null){
			map.put("flag", false);
			map.put("msg", "未查询到对应广告");
			return map;
		}
		
		map.put("flag", true);
		map.put("data", m);
		return map;
	}
	
	/**
	 * 检查商家是否通过认证
	 * @return
	 */
	@RequestMapping("/isAuth")
	@ResponseBody
	public Map<String, Object> isAuth(){
		Map<String, Object> map = new HashMap<String, Object>();
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(user==null){
			map.put("flag", false);
			map.put("msg", "session失效，请重新登录");
			return map;
		}
		
		Company c = companyService.queryCompanyByUserId(user.getId());
		
		if(c!=null && c.getVerify_status()==1){
			map.put("flag", true);
		}else{
			map.put("flag", false);
			map.put("msg", "只有通过认证审核后才能使用该功能,您还未通过认证审核");
			return map;
		}
		
		return map;
	}
	
	/**
	 * 广告列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyuiPaging<MainList> list(@RequestParam(required=false) Integer page,
									   @RequestParam(required=false) Integer rows,
									   @RequestParam(required=false) Integer verify_status,
									   @RequestParam(required=false) Integer adv_status,
									   @RequestParam(required=false) String sdate,
									   @RequestParam(required=false) String edate){
		EasyuiPaging<MainList> ep = new EasyuiPaging<MainList>();
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(user==null){
			return ep;
		}
		
		Company company = companyService.queryCompanyByUserId(user.getId());
		
		if(company==null){
			return ep;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("page", ((page-1)*rows));
		param.put("rows", rows);
		param.put("id", company.getId());
		
		if(verify_status!=null && verify_status.intValue()>-2){
			param.put("verify_status", verify_status);
		}
		if(adv_status!=null && adv_status.intValue()>-1){
			param.put("adv_status", adv_status);
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
		
		List<MainList> lists = redbagService.queryAdv(param);	
		int total = redbagService.queryAdvTotal(param);	
		
		for(MainList m : lists){
			if(m.getAdv_status()!=2){
				Long n_t = System.currentTimeMillis();
				Long a_t = m.getAdv_time().getTime();
				m.setAdv_status((a_t > n_t ? 0:1));
			}
		}
		
		ep.setRows(lists);
		ep.setTotal(total);
		return ep;
	}
	
	/**
	 * 发放红包首页
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, @RequestParam(required=false) Integer id){
		List<Mould> ms = advService.queryAllMoould();
		model.addAttribute("ms", ms);
		if(id!=null){
			MainList m = redbagService.queryAdvById(id);
			model.addAttribute("m", m);
		}
		return "merchant/givePackets";
	}
	
	/**
	 * 广告系统封面预览
	 * @param mid
	 * @param title
	 * @param subTitle
	 * @return
	 */
	@RequestMapping("/pre")
	@ResponseBody
	public Map<String, Object> pre(@RequestParam(required=false) Integer mid,
								   @RequestParam(required=false) String title,
								   @RequestParam(required=false) String subTitle){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(mid==null || StringUtils.isBlank(title) || StringUtils.isBlank(subTitle)){
			map.put("flag", false);
			map.put("msg", "参数为空");
			return map;
		}
		
		String sep = File.separator;
		String pre = SettingUtils.getCommonSetting("upload.image.path");
		String temp = SettingUtils.getCommonSetting("upload.file.temp.path");
		String tempPath = SettingUtils.getCommonSetting("base.temp.url");
		Mould mould = advService.queryMouldById(mid);
		
		if(mould==null){
			map.put("flag", false);
			map.put("msg", "未查询到对应模版");
			return map;
		}
		
		String copy = pre + sep + mould.getOrigin(); //原图路径
		String imgName = new Date().getTime() + ".jpg";
		String merge = temp + sep + imgName; //封面和文字合并后的路径
		
		ImgBean imgBean = new ImgBean();
		BufferedImage d = imgBean.loadImageLocal(copy);
		imgBean.writeImageLocal(merge,imgBean.modifyImage(d,"",0,0));//复制原图
		BufferedImage dd = imgBean.loadImageLocal(merge);
		
		if(dd==null){
			map.put("flag", false);
			map.put("msg", "模版不存在");
			return map;
		}
		
		int w = dd.getWidth(); //图片宽
		int h = dd.getHeight(); //图片高
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		
		if(mid==1){
			x1 = 40;
			y1 = 100;
			x2 = w / 2;
			y2 = h - 100;
		}else if(mid==2){
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
		SensitivewordFilter sw = new SensitivewordFilter();
		title=sw.replaceSensitiveWord(title, 2, "*");
		subTitle=sw.replaceSensitiveWord(subTitle, 2, "*");
		//合并图片和文字
		imgBean.writeImageLocal(merge,imgBean.modifyImage(dd,title,x1,y1));
		imgBean.writeImageLocal(merge,imgBean.modifyImage(dd,subTitle,x2,y2));
		
		map.put("flag", true);
		map.put("path", tempPath+imgName);
		return map;
	}
	
	/**
	 * 保存广告
	 * @param section_id
	 * @param send_time
	 * @param money
	 * @param cover
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String,Object> save(@RequestParam(required=false) Integer id,
								   @RequestParam(required=false) Integer section_id,
								   @RequestParam(required=false) String send_time,
								   @RequestParam(required=false) Float money,
								   @RequestParam(required=false) String title,
								   @RequestParam(required=false) String sub_title,
								   /*@RequestParam(required=false) Integer adv_play_time,*/
								   @RequestParam(required=false) Integer type,
								   @RequestParam(required=false) Integer model,
								   @RequestParam(required=false) String cover){
		Map<String, Object> map = new HashMap<String,Object>();
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		if(user==null){
			map.put("flag", false);
			map.put("msg", "session失效，请重新登录");
			return map;
		}
		
		if(section_id==null || send_time==null || money==null ||/* adv_play_time==null ||*/ model==null){
			map.put("flag", false);
			map.put("msg", "参数为空");
			return map;
		}
		
		if(money < 500){
			map.put("flag", false);
			map.put("msg", "红包金额不能小于500");
			return map;
		}
		
		try {
			if(new Date().getTime() > sdf.parse(send_time).getTime()){
				map.put("flag", false);
				map.put("msg", "发放时间不能小于当前时间");
				return map;
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		if(model==0){ // 0 自定义模版  1 系统模版
			if(cover==null){
				map.put("flag", false);
				map.put("msg", "参数为空");
				return map;
			}
		}else{
			if(title==null || sub_title==null || type==null){
				map.put("flag", false);
				map.put("msg", "参数为空");
				return map;
			}
		}
		
		Company company = companyService.queryCompanyByUserId(user.getId());
		
		if(company==null){
			map.put("flag", false);
			map.put("msg", "未查询到公司信息");
			return map;
		}
		
		if(company.getVerify_status()!=1){
			map.put("flag", false);
			map.put("msg", "只有通过认证审核后才能使用该功能,您还未通过认证审核");
			return map;
		}
		
		UserDetail ud = userService.queryUserDetailById(user.getId());
		
		if(ud!=null && ud.getGold_count() < money){
			map.put("flag", false);
			map.put("msg", "余额不足请充值");
			return map;
		}
		
		try {
			
			if(model==0){
				String pre = SettingUtils.getCommonSetting("upload.file.temp.path") + File.separator + cover;// 临时文件夹
				File file = new File(pre);
				if(file.exists()){
					cover = DataUtil.moveToDir(cover, true);
				}
			}else if(model==1){
				String sep = File.separator;
				String pre = SettingUtils.getCommonSetting("upload.image.path");// 存放文件文件夹名称
				Mould mould = advService.queryMouldById(type);
				
				if(mould==null){
					map.put("flag", false);
					map.put("msg", "未查询到对应模版");
					return map;
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
				cover = subDir.toString() + sep + new Date().getTime() + ".jpg";
				
				ImgBean imgBean = new ImgBean();
				BufferedImage d = imgBean.loadImageLocal(copy);
				imgBean.writeImageLocal(merge,imgBean.modifyImage(d,"",0,0));//复制原图
				BufferedImage dd = imgBean.loadImageLocal(merge);
				
				if(dd==null){
					map.put("flag", false);
					map.put("msg", "模版不存在");
					return map;
				}
				
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
				
				SensitivewordFilter sw = new SensitivewordFilter();
				title=sw.replaceSensitiveWord(title, 2, "*");
				sub_title=sw.replaceSensitiveWord(sub_title, 2, "*");
				//合并图片和文字
				imgBean.writeImageLocal(merge,imgBean.modifyImage(dd,title,x1,y1));
				imgBean.writeImageLocal(merge,imgBean.modifyImage(dd,sub_title,x2,y2));
			}
			
			//保存广告
			MainList adv = new MainList();
			/*adv.setAdv_play_time(adv_play_time.longValue());*/
			adv.setSection_id(section_id);
			adv.setAdv_time(sdf.parse(send_time));
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(send_time));
			if(money > 5000){
				c.add(Calendar.MINUTE, -3);
				adv.setAdv_pre_time(c.getTime());
			} else if(money > 1000){
				c.add(Calendar.MINUTE, -2);
				adv.setAdv_pre_time(c.getTime());
			} else{
				c.add(Calendar.MINUTE, -1);
				adv.setAdv_pre_time(c.getTime());
			}
			
			
			adv.setAdv_img(cover);
			adv.setCompany_id(company.getId());
			adv.setCompany_name(company.getCompany_name());
			adv.setAmount(money);
			adv.setNumber(money.intValue()*2);
			adv.setTitle(title);
			adv.setSub_title(sub_title);
			adv.setType(type);
			adv.setVerify_status(0);
			if(id!=null){ //如果是再次发布则直接通过审核
				adv.setVerify_status(1);
			}else{
				adv.setVerify_status(0);
			}
			advService.saveAdv(adv);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//更新余额
		ud.setGold_count(ud.getGold_count()-money);
		userService.updateMerGoldCount(ud);
		
		//发送信息
		Message message = new Message();
		message.setUid(user.getId());
		message.setContent("广告创建成功");
		message.setType(MessageType.CREATE_REDBAG);
		messageService.insertMessage(message);
		
		map.put("flag", true);
		map.put("msg", "保存成功");
		return map;
	}
}
