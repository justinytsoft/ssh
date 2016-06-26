package com.goldCityWeb.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.Location;
import com.goldCityWeb.domain.Product;
import com.goldCityWeb.domain.ProductCover;
import com.goldCityWeb.domain.ProductRecord;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.json.ResponseStatus;
import com.goldCityWeb.service.BaseService;
import com.goldCityWeb.service.IProductService;
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.Constants;
import com.goldCityWeb.util.DataUtil;
import com.goldCityWeb.util.PageSupport;
import com.goldCityWeb.util.SettingUtils;

@Controller
@RequestMapping("/services/product")
public class WS_Product {

	@Autowired
	private IProductService productService;
	
	@Autowired
	private BaseService baseService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 商品详情
	 * @param request
	 * @param id 商品ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = {RequestMethod.GET,RequestMethod.POST})
	public JsonResWrapper detail(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,
								@RequestParam(required=false) Integer id){
		JsonResWrapper jrw = new JsonResWrapper();
		UserDetail su = getSessionUser(request);
		if(su==null){
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			jrw.setMessage("session失效,请重新登录");
			return jrw;
		}
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(su.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		if(id==null){
			jrw.setStatus(ResponseStatus.FAILED_PARAM_LOST);
			jrw.setMessage("参数为空");
			return jrw;
		}
		Product p = productService.queryProductById(id);
		Map<String, Object> data = new HashMap<String, Object>();
		
		p = p==null ? new Product() : p;
		setPrefix(isEmpty(p.getCovers()));
		
		data.put("product", p);
		jrw.setData(data);
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	
	/**
	 * 商品列表
	 * @param request
	 * @param category 商品分类 0 道具 1 物品
	 * @param name 商品名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public JsonResWrapper index(HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude,
								@RequestParam(required=false) Integer category,
								@RequestParam(required=false) String name){
		JsonResWrapper jrw = new JsonResWrapper();
		UserDetail su = getSessionUser(request);
		if(su==null){
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			jrw.setMessage("session失效,请重新登录");
			return jrw;
		}
			if(latitude!=null && longitude!=null){
				Location l = new Location();
				l.setUser_id(su.getId());
				l.setLatitude(latitude);
				l.setLongitude(longitude);
				baseService.saveLocation(l);
			}
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		if(category!=null){
			param.put("category", category);
		}
		if(!StringUtils.isBlank(name)){
			param.put("name", name);
		}
		List<Product> products = productService.queryProducts(ps,param);
		Map<String, Object> data = new HashMap<String, Object>();
		
		products = isEmpty(products);
		for(Product p : products){
			setPrefix(isEmpty(p.getCovers()));
			p.setReceive_type(1);
		}
		
		data.put("products", products);
		jrw.setData(data);
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	
	/**
	 * 保存实体订单
	 * @param request
	 * @param id
	 * @param number
	 * @param receive_type
	 * @param address
	 * @return
	 */
	@RequestMapping(value="/save/entityorder", method=RequestMethod.POST)
	public @ResponseBody JsonResWrapper saveOrder(HttpServletRequest request, @RequestParam Integer id, @RequestParam Integer number,
			@RequestParam Integer receive_type, @RequestParam(required=false) String address, @RequestParam(required=false) String name, @RequestParam(required=false) String phone) {
		JsonResWrapper jrw = new JsonResWrapper();
		UserDetail su = getSessionUser(request);
		if(su==null){
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			jrw.setMessage("session失效,请重新登录");
			return jrw;
		}
		Product pd = productService.queryProductById(id);
		Float price = pd.getPrice() * number;
		UserDetail u = userService.queryUserDetailById(su.getId());
		if(u.getGold_count() < price){
			jrw.setMessage("黄金币数量不足!");
			jrw.setStatus("201");
			return jrw;
		}
		//Order order = new Order();
		ProductRecord pr = new ProductRecord();
		pr.setP_id(pd.getId());
		pr.setType(1);
		pr.setOrder_num("DH_"+System.currentTimeMillis()+DataUtil.generateRandomString(6));
		pr.setReceive_type(receive_type);
		pr.setP_name(pd.getName());
		pr.setP_path(pd.getCovers().get(0).getCover());
		pr.setU_id(su.getId());
		pr.setAddress(address);
		pr.setName(name);
		pr.setPhone(phone);
		pr.setCount(number);
		pr.setGold(price);
		productService.saveProductRecord(pr);
		
		jrw.setMessage("保存成功");
		jrw.setStatus(ResponseStatus.OK);
		return jrw;
	}
	
	/**
	 * 保存虚拟订单
	 * @param request
	 * @param id
	 * @param number
	 * @param receive_type
	 * @param address
	 * @return
	 */
	@RequestMapping(value="/save/dummyorder", method=RequestMethod.POST)
	public @ResponseBody JsonResWrapper saveDummyOrder(HttpServletRequest request, @RequestParam Integer id, @RequestParam Integer number) {
		JsonResWrapper jrw = new JsonResWrapper();
		UserDetail su = getSessionUser(request);
		if(su==null){
			jrw.setStatus(ResponseStatus.FAILED_SESSION_INVALID);
			jrw.setMessage("session失效,请重新登录");
			return jrw;
		}
		Product pd = productService.queryProductById(id);
		Float price = pd.getPrice() * number;
		UserDetail u = userService.queryUserDetailById(su.getId());
		if(u.getGold_count() < price){
			jrw.setMessage("黄金币数量不足!");
			jrw.setStatus("201");
			return jrw;
		}
		//Order order = new Order();
		ProductRecord pr = new ProductRecord();
		pr.setP_id(pd.getId());
		pr.setType(0);
		pr.setOrder_num("DH_"+System.currentTimeMillis()+DataUtil.generateRandomString(6));
		
		pr.setP_name(pd.getName());
		pr.setP_path(pd.getCovers().get(0).getCover());
		pr.setU_id(su.getId());
		pr.setCount(number);
		pr.setGold(price);
		productService.saveProductRecord(pr);
		return jrw;
	}
	
	/**
	 * 设置图片访问路径的前缀
	 * @return
	 */
	private void setPrefix(List<ProductCover> pcs){
		String prefix = SettingUtils.getCommonSetting("base.image.url");
		for(ProductCover pc : pcs){
			pc.setCover(prefix + pc.getCover());
		}
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
