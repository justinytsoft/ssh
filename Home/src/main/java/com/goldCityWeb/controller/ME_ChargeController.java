package com.goldCityWeb.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
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

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.goldCityWeb.domain.ChargeRecord;
import com.goldCityWeb.domain.SysUsers;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.service.CompanyService;
import com.goldCityWeb.util.DataUtil;
import com.goldCityWeb.util.EasyuiPaging;
import com.goldCityWeb.util.SettingUtils;

@Controller
@RequestMapping(value="/merchant/charge")
public class ME_ChargeController {
	@Autowired
	private CompanyService companyService;
	@Autowired
	private BaseService baseService;
	
	@RequestMapping("/check")
	@ResponseBody
	public Map<String, Object> check(@RequestParam String code){
		Map<String, Object> map = new HashMap<String, Object>();
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ChargeRecord cr = baseService.queryChargeRecordByFeeNum(code);
		
		if(cr==null || cr.getCharge_type()==1 || cr.getCompany_id() != user.getId()){
			map.put("flag", false);
			map.put("msg", "充值未完成");
		}else{
			map.put("flag", true);
		}
		
		return map;
	}
	
	/**
	 * 列表首页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index")
	public String index(HttpServletRequest request, Model model) {
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mid", user.getId());
		param.put("status", 2);
		
		List<ChargeRecord> crs = companyService.queryMerChargeRecord(param);
		
		float total = 0;
		for(ChargeRecord cr : crs){
			total += cr.getCharge_count();
		}
		
		model.addAttribute("total", total);
		return "merchant/charge_record";
	}
	
	/**
	 * 充值列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public EasyuiPaging<ChargeRecord> list(@RequestParam(required=false) Integer page,
										   @RequestParam(required=false) Integer rows,
										   @RequestParam(required=false) Integer from,
										   @RequestParam(required=false) String sdate,
										   @RequestParam(required=false) String edate) {
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		EasyuiPaging<ChargeRecord> ep = new EasyuiPaging<ChargeRecord>();
		
		if(user==null){
			return ep;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("page", ((page-1)*rows));
		param.put("rows", rows);
		param.put("mid", user.getId());
		param.put("status", 2);
		
		if(from!=null && from!=-1){
			param.put("from", from);
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
		
		List<ChargeRecord> crs = companyService.queryMerChargeRecord(param);
		int total = companyService.queryMerChargeRecordTotal(param);
		
		ep.setRows(crs);
		ep.setTotal(total);
		return ep;
	}
	
	@RequestMapping(value="/charge")
	public String charge(HttpServletRequest request, Model model,@RequestParam(required = false) Float chargeMoney,@RequestParam(required = false) Integer chargeType
			,
			@RequestParam(required=false) Integer receipt,
			@RequestParam(required=false) String name,
			@RequestParam(required=false) String fee_num,
			@RequestParam(required=false) String phone,
			@RequestParam(required=false) String address) throws UnsupportedEncodingException  {
		/*SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", user.getId());
		List<ChargeRecord> crList = companyService.queryChargeRecordByUser(param, ps);
		model.addAttribute("crList", crList);*/
		//if()
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user==null){
			return "redirect:/login";
		}
		if(chargeType!=null && chargeType==1){
			
			ChargeRecord cr = new ChargeRecord();
			cr.setCharge_count(chargeMoney);
			cr.setCharge_type(chargeType);
			cr.setCompany_id(user.getId());
			cr.setReceipt(receipt);
			cr.setStatus(1);
			cr.setFee_num(fee_num);
			cr.setAddress(address);
			cr.setName(name);
			cr.setPhone(phone);
			baseService.saveChargeRecord(cr);
			//支付类型
			//必填，不能修改
			String payment_type = "1";
			//服务器异步通知页面路径
			String notify_url = SettingUtils.getCommonSetting("charge_result.return_url_post");
			//需http://格式的完整路径，不能加?id=123这类自定义参数		
			//页面跳转同步通知页面路径
			String return_url = SettingUtils.getCommonSetting("charge_result.return_url");
			//卖家支付宝帐户
			String seller_email = SettingUtils.getCommonSetting("seller_email");
			//必填		
			//商户订单号
			String out_trade_no = new String(cr.getFee_num().getBytes("ISO-8859-1"),"UTF-8");
			//商户网站订单系统中唯一订单号，必填		
			//订单名称
			String subject = "充值";
			//必填		
			//付款金额
			String total_fee = new String(chargeMoney.toString().getBytes("ISO-8859-1"),"UTF-8");
			//必填		
			//订单描述		
			String body = "";
			//商品展示地址
			String show_url = "";
			//需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html		
			//防钓鱼时间戳
			String anti_phishing_key = "";
			//若要使用请调用类文件submit中的query_timestamp函数		
			//客户端的IP地址
			String exter_invoke_ip = "";
			//非局域网的外网IP地址，如：221.0.0.1
			
			
			//////////////////////////////////////////////////////////////////////////////////
			
			Map<String, String> sParaTemp = new HashMap<String, String>();
			
			//把请求参数打包成数组
			sParaTemp.put("service", "create_direct_pay_by_user");
	        sParaTemp.put("partner", AlipayConfig.partner);
	        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("payment_type", payment_type);
			sParaTemp.put("notify_url", notify_url);
			sParaTemp.put("return_url", return_url);
			sParaTemp.put("seller_email", seller_email);
			sParaTemp.put("out_trade_no", out_trade_no);
			sParaTemp.put("subject", subject);
			sParaTemp.put("total_fee", total_fee);
			sParaTemp.put("it_b_pay", "1h");
			sParaTemp.put("body", body);
			/*if(!payType.equals("alipay")){
				sParaTemp.put("paymethod", "bankPay");
				sParaTemp.put("defaultbank", payType);
			}*/
			sParaTemp.put("show_url", show_url);
			sParaTemp.put("anti_phishing_key", anti_phishing_key);
			sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
			
			String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
			model.addAttribute("sHtmlText", sHtmlText);
		}
		
		return "merchant/confirm";
	}
	
	
	@RequestMapping(value = "/charge_return_url", method = { RequestMethod.GET, RequestMethod.POST })
	public String charge_return_url(HttpServletRequest request,Model model) {
		try {
			
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			// 商户订单号
			String out_trade_no = new String(request.getParameter(
					"out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 支付宝交易号
			String trade_no = new String(request.getParameter("trade_no")
					.getBytes("ISO-8859-1"), "UTF-8");

			// 交易状态
			String trade_status = new String(request.getParameter(
					"trade_status").getBytes("ISO-8859-1"), "UTF-8");

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			// 计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);

			//if (verify_result) {// 验证成功
				// 请在这里加上商户的业务逻辑程序代码

				// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				if (trade_status.equals("TRADE_FINISHED")
						|| trade_status.equals("TRADE_SUCCESS")) {
					//baseService.updateResultSolutionStatus(Integer.valueOf(out_trade_no.split("_")[1]), 2,1);
					ChargeRecord cr = new ChargeRecord();
					cr.setFee_num(out_trade_no);
					cr.setPay_num(trade_no);
					ChargeRecord c = baseService.queryChargeRecordByFeeNum(out_trade_no);
					if(c.getStatus()==1){
						baseService.updateChargeRecordStatus(cr);
						cr = baseService.queryChargeRecordByFeeNum(out_trade_no);
						baseService.updateGoldCount(cr.getCompany_id(),cr.getCharge_count());
					}
					return "pages/chargeSuccess";
				}
				return "pages/chargeFail";
				// 该页面可做页面美工编辑
			//} else {
				//return "pages/chargeFail";
				// 该页面可做页面美工编辑
			//}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "pages/chargeFail";
		}

	}
	
	
	/*@RequestMapping(value="/record")
	public String chargeRecord(HttpServletRequest request, Model model) {
		SysUsers user = (SysUsers) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", user.getId());
		List<ChargeRecord> crList = companyService.queryChargeRecordByUser(param, ps);
		model.addAttribute("crList", crList);
		return "merchant/charge_record";
	}*/
}
