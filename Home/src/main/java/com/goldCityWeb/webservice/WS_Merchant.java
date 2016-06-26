package com.goldCityWeb.webservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.ChargeRecord;
import com.goldCityWeb.domain.Company;
import com.goldCityWeb.domain.FeeRecord;
import com.goldCityWeb.domain.Location;
import com.goldCityWeb.domain.PayReqData;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.json.ResponseStatus;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.Constants;
import com.goldCityWeb.util.DataUtil;
import com.goldCityWeb.util.DateFormatter;
import com.goldCityWeb.util.PageSupport;
import com.goldCityWeb.util.SettingUtils;
import com.goldCityWeb.util.WxPayUtil;
import com.goldCityWeb.wechat.PaySign;
import com.goldCityWeb.wechat.Sign;
import com.goldCityWeb.wechat.model.message.MessageUtils;



@Controller
@RequestMapping("/services/merchant")
public class WS_Merchant {
	
	@Autowired
	private UserService userService;
	@Autowired
	private BaseService baseService;
	@Autowired
	private CompanyService companyService;

	/**
	 * 申请结算消费清单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/applyClose", method = {RequestMethod.GET,RequestMethod.POST})
	public JsonResWrapper applyClose(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,
									@RequestParam(required=false) String ids){
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
		if(ids==null){
			jrw.setStatus(ResponseStatus.FAILED_PARAM_LOST);
			jrw.setMessage("参数为空");
			return jrw;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		Company c = companyService.queryCompanyByUserId(ud.getId());
		param.put("mid", c.getId());
		if(ids.startsWith(",")){
			ids = ids.substring(1);
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0,ids.lastIndexOf(","));
		}
		param.put("ids", ids);
		
		userService.updateFeeRecordStatusToApplyClose(param);
		
		return jrw;
	}

	/**
	 * 获取消费记录列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/feeRecord", method = {RequestMethod.GET,RequestMethod.POST})
	public JsonResWrapper feeRecord(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,
									@RequestParam(required=false) Integer status){
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
		param.put("mid", ud.getId());
		param.put("pay_status", "2");
		param.put("status", status==null ? 0 : status);
		Map<String, Object> paramAll = new HashMap<String, Object>();
		paramAll.put("mid", ud.getId());
		paramAll.put("pay_status","2");
		
		List<FeeRecord> ms = isEmpty(userService.queryFeeRecordsByStatus(ps,param));
		List<FeeRecord> msAll = isEmpty(userService.queryFeeRecordsByStatus(null,paramAll));
		
		float feePriceCount = 0;
		for(FeeRecord fr : msAll){
			feePriceCount += fr.getFee_price();
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("feeRecord", ms);
		data.put("feeRecordCount", msAll.size());
		data.put("feePriceCount", feePriceCount);
		
		jrw.setData(data);
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	
	
	/**
	 * 充值接口
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/charge_fee", method = {RequestMethod.GET,RequestMethod.POST})
	public JsonResWrapper charge_fee(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,
									@RequestParam(required=false) Integer user_id,
									@RequestParam(required=false) Float charge_count,
									@RequestParam(required=false) Integer charge_type,
									@RequestParam(required=false) Integer receipt,
									@RequestParam(required=false) String name,
									@RequestParam(required=false) String phone,
									@RequestParam(required=false) String address
									){
		//UserDetail ud = getSessionUser(request);
		JsonResWrapper jrw = new JsonResWrapper();
		
		/*if(ud==null){
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
			}*/
		ChargeRecord cr = new ChargeRecord();
		cr.setCharge_count(charge_count);
		cr.setCharge_type(charge_type);
		cr.setCompany_id(user_id);
		cr.setReceipt(receipt);
		cr.setStatus(1);
		cr.setFee_num("CZ_"+System.currentTimeMillis()+DataUtil.generateRandomString(6));
		cr.setAddress(address);
		cr.setName(name);
		cr.setPhone(phone);
		baseService.saveChargeRecord(cr);
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("FeeNumber", cr.getFee_num());
		
		jrw.setData(data);
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	
	/**
	 * 支付接口
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/pay_fee", method = {RequestMethod.GET,RequestMethod.POST})
	public JsonResWrapper pay_fee(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,
									@RequestParam(required=false) Integer user_id,
									@RequestParam(required=false) Integer mid,
									@RequestParam(required=false) Integer fee_type,
									@RequestParam(required=false) Float fee_price,
									@RequestParam(required=false) Float use_gold){
		//UserDetail ud = getSessionUser(request);
		JsonResWrapper jrw = new JsonResWrapper();
		
		/*if(ud==null){
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
			}*/
		System.out.println(use_gold + "-=-=-=-="+fee_price);
		//暂时屏蔽
		/*if(use_gold > fee_price){
			jrw.setStatus("201");
			jrw.setMessage("黄金币不可超过付款金额!");
			return jrw;
		}*/
		
		UserDetail u = userService.queryUserDetailById(user_id);
		if(u.getGold_count() < use_gold){
			jrw.setStatus("201");
			jrw.setMessage("黄金币数量不足!");
			return jrw;
		}
		FeeRecord f = new FeeRecord();
		f.setUse_gold(use_gold);
		
		//暂时修改
		//f.setFee_price(fee_price);
		//f.setReal_fee(fee_price - use_gold);
		f.setFee_price(fee_price + use_gold);
		f.setReal_fee(fee_price);
		
		f.setFee_type(fee_type);
		f.setUid(user_id);
		f.setMid(mid);
		Company c =  companyService.queryCompanyById(mid);
		f.setMerchant_name(c.getCompany_name());
		if(fee_price == 0){
			f.setPay_status(2);
			jrw.setMessage("支付完成!");
		} else {
			f.setPay_status(1);
		}
			f.setFee_number("FK_"+System.currentTimeMillis()+DataUtil.generateRandomString(6));

			baseService.saveFeeRecord(f);
			Map<String, Object> data = new HashMap<String, Object>();
			if(fee_price != 0){
				if(fee_type==2){
					Calendar ca = Calendar.getInstance();
					Calendar ca1 = Calendar.getInstance();
					ca.setTime(new Date());
					ca1.setTime(new Date());
					ca.setTimeZone(TimeZone.getTimeZone("GMT+8"));
					ca1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
					ca.add(Calendar.MINUTE, 30);
					
					PayReqData p = new PayReqData("APP", "付款","", "testattach", f.getFee_number(), Float.valueOf(fee_price*100).intValue(), "", "8.8.8.8", DateFormatter.dateToString(ca1.getTime(), "yyyyMMddHHmmss"), DateFormatter.dateToString(ca.getTime(), "yyyyMMddHHmmss"), SettingUtils.getCommonSetting("base.url")+"/services/wx_fee_post_url");
					try {
						WxPayUtil wx = new WxPayUtil();
						String reqXml = wx.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", p);
						
						Map<String, String> requestMap = MessageUtils.parseXml(reqXml);
						
						
						if(!requestMap.get("return_code").equals("SUCCESS")) {
							jrw.setStatus("201");
							jrw.setMessage(URLEncoder.encode(requestMap.get("return_msg"),"utf-8"));
							return jrw;
						}
						
						if(!requestMap.get("result_code").equals("SUCCESS")) {
							jrw.setStatus("201");
							jrw.setMessage(URLEncoder.encode(requestMap.get("err_code_des"),"utf-8"));
							return jrw;
						}
						//orderService.updateWxMsgByOrderId(order.getId(),requestMap.get("prepay_id"),ca.getTime(),null,null,null);
						Map<String,String> param =  PaySign.getSign(requestMap.get("prepay_id"));
						jrw.setData(param);
						jrw.setStatus("200");
						return jrw;
						
					} catch (UnrecoverableKeyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (KeyManagementException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (KeyStoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					data.put("FeeNumber", f.getFee_number());
					jrw.setData(data);
				}
			}
			
		
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
