package com.goldCityWeb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.goldCityWeb.domain.Commodity;
import com.goldCityWeb.domain.CommodityType;
import com.goldCityWeb.domain.MainList;
import com.goldCityWeb.domain.Mould;
import com.goldCityWeb.domain.Product;
import com.goldCityWeb.domain.ProductCover;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.json.ResponseStatus;
import com.goldCityWeb.service.CommodityService;
import com.goldCityWeb.service.IProductService;
import com.goldCityWeb.util.DataUtil;
import com.goldCityWeb.util.PageSupport;
import com.goldCityWeb.util.SettingUtils;

@Controller
@RequestMapping(value="/commodity")
public class CommodityController {
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private IProductService iProductService;
	
	
	@RequestMapping(value="/add")
	public String addCommodity(HttpServletRequest request, Model model, @RequestParam(required=false) Integer status) {
		if(status != null && status.intValue() == 1) {
			model.addAttribute("msg", "保存成功");
		}
		//List<ProductCover> ctList = iProductService.;
		//model.addAttribute("ctList", ctList);
		
		return "pages/commodity/add";
	}
	
	/**
	 * 新窗口打开来编辑...
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/update", method={RequestMethod.GET, RequestMethod.POST})
	public String commodityupdate(HttpServletRequest request,Model model,@RequestParam Integer id) {
		Product com =iProductService.queryProductById(id);
		if(com == null) {
			return "pages/commodity/list";
		}
		model.addAttribute("com", com);
		
		return "pages/commodity/update";
	}
	
	
	@RequestMapping(value="/save_commodity", method=RequestMethod.POST)
	public String saveCommodity(HttpServletRequest request, Model model, @RequestParam String name,
			@RequestParam Integer category,@RequestParam Integer stock, @RequestParam String desc, @RequestParam Float price,
			@RequestParam(required=false) Integer receive_type, @RequestParam(required=false) String receive_address, @RequestParam(required=false)Integer limit_level,
			@RequestParam(required=false) String use_way, @RequestParam(required=false) String special_work, @RequestParam(required=false) String work_description,
			@RequestParam(required=false) String use_description) {
		Product com = new Product();
		com.setCategory(category);
		com.setDesc(desc);
		com.setName(name);
		
		String path = DataUtil.uploadImgdFile(request);
		if(StringUtils.isBlank(path)) {
			model.addAttribute("msg", "图片上传失败");
			return "pages/commodity/add";
		}
		
		List<ProductCover> covers = com.getCovers();

		String baseImageUrl = SettingUtils.getCommonSetting("base.image.url");
		if(covers != null && covers.size() > 0) {
			for(ProductCover pc : covers) {
				pc.setCover(baseImageUrl + path);
			}
		} else {
			covers = new ArrayList<ProductCover>();
			ProductCover pc = new ProductCover();
			pc.setCover(path);
		}
		com.setCovers(covers);
		
		com.setPrice(price);
		com.setReceive_address(receive_address);
		com.setLimit_level(limit_level);
		com.setUse_way(use_way);
		com.setSpecial_work(special_work);
		com.setWork_description(work_description);
		com.setUse_description(use_description);
		com.setStock(stock);
		
		iProductService.saveProduct(com);

		return "redirect:/commodity/add?status=1";
	}
	
	/**
	 * 保存商品
	 * @param request
	 * @param name
	 * @param image
	 * @param category
	 * @param desc
	 * @param price
	 * @param id
	 * @param receive_type
	 * @param receive_address
	 * @param limit_level
	 * @param use_way
	 * @param special_work
	 * @param work_description
	 * @param use_description
	 * @return
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public @ResponseBody JsonResWrapper saveCommodity(HttpServletRequest request, @RequestParam String name, @RequestParam(required=false) String image,
			@RequestParam Integer category, @RequestParam String desc, @RequestParam Float price, @RequestParam(required=false) Integer id,
			@RequestParam(required=false) Integer receive_type, @RequestParam(required=false) String receive_address, @RequestParam(required=false)Integer limit_level,
			@RequestParam(required=false) String use_way, @RequestParam(required=false) String special_work, @RequestParam(required=false) String work_description,
			@RequestParam(required=false) String use_description) {
		JsonResWrapper response  = new JsonResWrapper();
		Product com = null;
		if(id != null && id.intValue() > 0) {
			com = iProductService.queryProductById(id);
		}
		if(com == null) {
			com = new Product();
		}
//		if(com!=null && com.getCategory().intValue() != 2) {
//			if(id==1 || id==2)category=0;
//			com.setCategory(category);
//			com.setDesc(desc);
//			com.setName(name);
//		}
		if(!StringUtils.isBlank(image) && !image.equals("null")) {
			String path = null;
			try {
				path = DataUtil.moveToDir(image, false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.setMessage("图片错误");
				response.setStatus(ResponseStatus.FAILED);
				return response;
			}
			try{
				DataUtil.deleteByUploadImg(image);
			}catch(Exception e) {
				e.printStackTrace();
			}
			List<ProductCover> covers = com.getCovers();

			String baseImageUrl = SettingUtils.getCommonSetting("base.image.url");
			if(covers != null && covers.size() > 0) {
				for(ProductCover pc : covers) {
					pc.setCover(baseImageUrl + path);
				}
			} else {
				covers = new ArrayList<ProductCover>();
				ProductCover pc = new ProductCover();
				pc.setCover(baseImageUrl +path);
				covers.add(pc);
			}
			com.setCovers(covers);
		}
		com.setCategory(category);
		com.setDesc(desc);
		com.setName(name);
		com.setPrice(price);
		com.setReceive_address(receive_address);
		com.setLimit_level(limit_level);
		com.setUse_way(use_way);
		com.setSpecial_work(special_work);
		com.setWork_description(work_description);
		com.setUse_description(use_description);
		
		iProductService.saveProduct(com);
		response.setMessage("保存成功");
		response.setStatus("200");
		response.setData(com);
		return response;
	}
	
	
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String commodityList(HttpServletRequest request, Model model, @RequestParam(required=false) Integer type, @RequestParam(required=false) String name) {
		PageSupport ps = PageSupport.initPageSupport(request);
		Map<String, Object> param = new HashMap<String, Object>();
		if(type != null && type.intValue() >= 0) {
			param.put("category", type);
			model.addAttribute("type", type);
		}
		if(!StringUtils.isBlank(name)) {
			param.put("name", name);
			model.addAttribute("name", name);
		}
		
		List<Product> allList = iProductService.queryProducts(ps, param);
		model.addAttribute("allList",allList);
		List<Product> commList = new ArrayList<Product>();
		List<Product> specialList = new ArrayList<Product>();//活动商品单独列出来
		if(allList != null && allList.size() > 0) {
			for(Product cm : allList) {
				if(cm.getCategory().intValue() == 0) {//0是道具类,1是物品类,么有2...
					specialList.add(cm);
				} else {
					commList.add(cm);
				}
			}
		}
		
		model.addAttribute("commList", commList);
		model.addAttribute("specialList", specialList);
		
		//List<CommodityType> ctList = commodityService.queryCommodityType();
		//model.addAttribute("ctList", ctList);
		return "pages/commodity/list";
	}
}
