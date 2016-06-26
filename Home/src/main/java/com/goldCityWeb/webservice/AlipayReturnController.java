/**
 * 
 */
package com.goldCityWeb.webservice;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.util.AlipayNotify;
import com.goldCityWeb.domain.ChargeRecord;
import com.goldCityWeb.domain.FeeRecord;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.util.DateFormatter;
import com.goldCityWeb.wechat.model.message.MessageUtils;

/**
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/services")
public class AlipayReturnController {

	@Autowired
	private BaseService baseService;

	/*@RequestMapping(value = "/cs_return_url", method = { RequestMethod.GET, RequestMethod.POST })
	public String cs_return_url(HttpServletRequest request, Model model) {
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

			if (verify_result) {// 验证成功
				// 请在这里加上商户的业务逻辑程序代码

				// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				if (trade_status.equals("TRADE_FINISHED")
						|| trade_status.equals("TRADE_SUCCESS")) {
					checkResultService.updateResultSolutionStatus(Integer.valueOf(out_trade_no.split("_")[1]), 2,1);
				}

				// 该页面可做页面美工编辑
			} else {
				// 该页面可做页面美工编辑
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return "redirect:/index";
	}*/
	
	@RequestMapping(value = "/charge_return_url_post", method = { RequestMethod.GET, RequestMethod.POST })
	public void charge_return_url_post(HttpServletRequest request, PrintWriter out,Model model) {
		System.out.println("-=-=-=-=-=-=-=-=-=-");
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
				System.out.println("-=-=-=-=-ok");
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
					out.println("success");
				}

				// 该页面可做页面美工编辑
			/*} else {
				System.out.println("-=-=-=-=-fail");
				out.println("fail");
				// 该页面可做页面美工编辑
			}*/
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("-=-=-=-=-failerror");
			out.println("fail");
		}

	}
	
	

	/*@RequestMapping(value = "/commodity_return_url", method = {RequestMethod.GET, RequestMethod.POST })
	public String commodity_return_url(HttpServletRequest request, Model model) {
		int buy = 0;
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
			if (verify_result) {// 验证成功
				// 请在这里加上商户的业务逻辑程序代码

				// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				if (trade_status.equals("TRADE_FINISHED")
						|| trade_status.equals("TRADE_SUCCESS")) {
					commodityService.updateOrderStatus(Integer.valueOf(out_trade_no.split("_")[1]), 1,null,null);
					buy = 1;
				}

				// 该页面可做页面美工编辑
			} else {
				// 该页面可做页面美工编辑
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "redirect:/index";
	}*/
	
	@RequestMapping(value = "/pay_return_url_post", method = {RequestMethod.GET, RequestMethod.POST })
	public void pay_return_url_post(HttpServletRequest request,HttpServletResponse response,PrintWriter out ,Model model) {
		int buy = 0;
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
					//commodityService.updateOrderStatus(Integer.valueOf(out_trade_no.split("_")[1]), 1,null,null);
					FeeRecord f = new FeeRecord();
					f.setFee_number(out_trade_no);
					f.setPay_number(trade_no);
					baseService.updateFeeRecordPayStatus(f);
					buy = 1;
					out.println("success");
				}

				// 该页面可做页面美工编辑
			/*} else {
				// 该页面可做页面美工编辑
				out.println("fail");
			}*/
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			out.println("fail");
		}
		
		//return "success";
	}
	
	/**
	 * 微信返回调用
	 * @param model
	 * @param request
	 * @param out
	 */
	@RequestMapping(value="/wx_fee_post_url", method=RequestMethod.POST)
	public void wx_fee_post_url(Model model, HttpServletRequest request,PrintWriter out) {
		//Customer customer = validateSession(request);
		try {
			Map<String, String> requestMap = MessageUtils.parseXmlFromRequest(request);
			if(!requestMap.get("return_code").equals("SUCCESS")) {
				out.println("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
			}
			
			if(!requestMap.get("result_code").equals("SUCCESS")) {
				out.println("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
			}
			Date end_t = DateFormatter.stringToDate(requestMap.get("time_end"), "yyyyMMddHHmmss");
			Integer total_fee = Integer.valueOf(requestMap.get("total_fee"));
			String out_trade_no = requestMap.get("out_trade_no");
			String transaction_id = requestMap.get("transaction_id");
			
			FeeRecord f = new FeeRecord();
			f.setFee_number(out_trade_no);
			f.setPay_number(transaction_id);
			baseService.updateFeeRecordPayStatus(f);
			
			
			out.println("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		out.println("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
	}
	
	
}
