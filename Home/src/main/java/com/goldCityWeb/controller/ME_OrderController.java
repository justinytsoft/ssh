package com.goldCityWeb.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.FeeRecord;
import com.goldCityWeb.domain.Message;
import com.goldCityWeb.domain.MessageType;
import com.goldCityWeb.domain.SysUsers;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.service.IMessageService;
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.EasyuiPaging;

@Controller
@RequestMapping("/merchant/order")
public class ME_OrderController {
	
	
	@Autowired
	private UserService userService;
	@Autowired
	private BaseService baseService;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private CompanyService companyService;
	
	/**
	 * 提现
	 * @return
	 */
	@RequestMapping("/tx")
	@ResponseBody
	public Map<String, Object> tx(@RequestParam(required=false) String ids,
								  @RequestParam(required=false) Integer type){
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> map = new HashMap<String, Object>();
		if(user==null){
			map.put("flag", false);
			map.put("msg", "session失效，请重新登录");
			return map;
		}
		
		if(StringUtils.isBlank(ids) || type==null){
			map.put("flag", false);
			map.put("msg", "参数为空");
			return map;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		Company c = companyService.queryCompanyByUserId(user.getId());
		c = c==null ? new Company() : c;
		param.put("mid", c.getId());
		param.put("t_type", type);
		if(!StringUtils.isBlank(ids)){
			param.put("ids", ids);
		}
		
		if(type==1){ //黄金币
			
			param.put("status", 2); //黄金币不用审核
			
			float count = 0;
			UserDetail ud = userService.queryUserDetailById(user.getId());
			List<FeeRecord> frs = userService.queryFeeRecordsByIds(c.getId(),ids);
			for(FeeRecord fr : frs){
				count += fr.getFee_price();
			}
			ud.setGold_count(ud.getGold_count()+count);
			
			//更新黄金币
			userService.updateMerGoldCount(ud);
			
			//发送信息
			Message message = new Message();
			message.setUid(user.getId());
			message.setContent("黄金币提现成功");
			message.setType(MessageType.CLOSE_APPLY);
			messageService.insertMessage(message);
			
			map.put("flag", true);
			map.put("msg", "黄金币提现成功");
		}else{ //人民币
			
			param.put("status", 1); //人民币需要审核

			//发送信息
			Message message = new Message();
			message.setUid(user.getId());
			message.setContent("现金申请提现成功，等待审核中");
			message.setType(MessageType.CLOSE_APPLY);
			messageService.insertMessage(message);
			
			map.put("flag", true);
			map.put("msg", "现金申请提现成功，等待审核中");
		}
		
		//更新提现状态
		userService.updateFeeRecordStatusToApplyClose(param);
		
		return map;
	}
	
	/**
	 * 订单首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model,HttpServletRequest request){
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user==null){
			return "merchant/orderLog";
		}

		Map<String, Object> paramAll = new HashMap<String, Object>();
		paramAll.put("mid", user.getId());
		paramAll.put("pay_status", 2);
		
		List<FeeRecord> msAll = isEmpty(userService.queryFeeRecordsByStatus(null,paramAll));
		
		float feePriceCount = 0;//订单总价
		float unclosedCount = 0;//未结算订单总价
		float applyCount = 0;//申请结算订单总价
		float closedCount = 0;//已结算订单总价
		
		int feePriceNum = msAll.size();//订单总数
		int unclosedNum = 0;//未结算订单总数
		int applyNum = 0;//申请结算订单总数
		int closedNum = 0;//已结算订单总数
		
		for(FeeRecord fr : msAll){
			if(fr.getPay_status()==2){ //已付款
				//订单总价
				feePriceCount += fr.getFee_price();
				
				if(fr.getStatus() == 0){//未结算
					unclosedCount += fr.getFee_price();
					unclosedNum++;
				}else if(fr.getStatus() == 1){//申请结算
					applyCount += fr.getFee_price();
					applyNum++;
				}else if(fr.getStatus() == 2){//已结算
					closedCount += fr.getFee_price();
					closedNum++;
				}
			}
		}
		
		model.addAttribute("feePriceCount", feePriceCount);
		model.addAttribute("unclosedCount", unclosedCount);
		model.addAttribute("applyCount", applyCount);
		model.addAttribute("closedCount", closedCount);
		
		model.addAttribute("feeRecordCount", feePriceNum);
		model.addAttribute("unclosedNum", unclosedNum);
		model.addAttribute("applyNum", applyNum);
		model.addAttribute("closedNum", closedNum);
		
		return "merchant/orderLog";
	}
	
	/**
	 * 订单列表
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyuiPaging<FeeRecord> list(@RequestParam(required=false) Integer page,
										@RequestParam(required=false) Integer rows,
										@RequestParam(required=false) String sdate,
										@RequestParam(required=false) String edate,
										@RequestParam(required=false) Integer status){
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		EasyuiPaging<FeeRecord> ep = new EasyuiPaging<FeeRecord>();
		
		if(user==null){
			return ep;
		}
		
		try{
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("page", ((page-1)*rows));
			param.put("rows", rows);
			Company c = companyService.queryCompanyByUserId(user.getId());
			param.put("mid", c.getId());
			param.put("pay_status", 2);
			
			if(!StringUtils.isBlank(sdate)){ 
				sdate += " 00:00:00";
				param.put("sdate", sdf.parse(sdate));
			}
			if(!StringUtils.isBlank(edate)){
				edate += " 23:59:59";
				param.put("edate", sdf.parse(edate));
				/*String[] date = edate.split("-");
		        Calendar calendar = Calendar.getInstance();
		        calendar.set(Calendar.YEAR, Integer.parseInt(date[0]));
		        calendar.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
		        //获取某月最大天数
		        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		        //设置日历中月份的最大天数
		        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
		        Date theDate = calendar.getTime();
		        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		        String s = df.format(theDate);
		        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				param.put("edate", sdf.parse(str.toString()));*/
			}
			if(status != null && status.intValue() > -1){
				param.put("status", status);
			}
			
			List<FeeRecord> frs = isEmpty(userService.queryMerFeeRecords(param));
			int total = userService.queryMerFeeRecordsTotal(param);

			ep.setRows(frs);
			ep.setTotal(total);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ep;
	}
	

	/**
	 * 判断集合是否为 null
	 * @param param
	 * @return 如果不为 null 直接返回，否则创建一个新的 ArrayList 返回
	 */
	private <T> List<T> isEmpty(List<T> param){
		return CollectionUtils.isEmpty(param) ? new ArrayList<T>() : param;
	}
	
}
