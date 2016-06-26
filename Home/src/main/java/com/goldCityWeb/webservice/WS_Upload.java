/**
 * 
 */
package com.goldCityWeb.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.json.ResponseStatus;
import com.goldCityWeb.util.DataUtil;
import com.goldCityWeb.util.SettingUtils;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/services/upload")
public class WS_Upload {
	

	/**
	 * 上传图片到临时目录
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/image", method = RequestMethod.POST)
	public @ResponseBody JsonResWrapper commodityImageUpload(HttpServletRequest request, Model model,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude, @RequestParam(required=false) String picture) {
		JsonResWrapper response = new JsonResWrapper();
		/*Object obj = request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
		if (obj == null) {
			response.setMessage("请先登录！");
			response.setStatus(ResponseStatus.FAILED);
			return response;
		}*/
		
		if (!StringUtils.isBlank(picture)) {
			String path = DataUtil.uploadImageToTmp(DataUtil._10_to_62(System.currentTimeMillis()), picture);
			if (StringUtils.equals(path, "-1")) {
				response.setStatus(ResponseStatus.FAILED);
				response.setMessage("服务器仅支持jpg、png格式图片！");
				return response;
			} else if (StringUtils.isBlank(path)) {
				response.setMessage("服务器图片解析异常，请确认上传的图片是否正确！");
				response.setStatus(ResponseStatus.FAILED);
				return response;
			} else {
				String base = SettingUtils.getCommonSetting("base.temp.url");
				Map<String ,Object> param = new HashMap<String,Object>();
				String[] fileNames = path.split("\\.");
				param.put("path", base+fileNames[0]+"_S"+"."+fileNames[1]);
				param.put("fileName", path);
				response.setData(param);
			}
		} else {
			response.setMessage("图片信息为空！");
			response.setStatus(ResponseStatus.FAILED);
		}
		
		return response;
	}
	
	/**
	 * 上传图片到正式目录
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/headImage", method = RequestMethod.POST)
	public @ResponseBody JsonResWrapper headImage(HttpServletRequest request, Model model,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude, @RequestParam(required=false) String picture, @RequestParam(required=false) Integer userId) {
		JsonResWrapper response = new JsonResWrapper();
		/*Object obj = request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
		if (obj == null) {
			response.setMessage("请先登录！");
			response.setStatus(ResponseStatus.FAILED);
			return response;
		}*/
		if(userId == null || userId.intValue() < 0){
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("用户id为空,或不存在!");
			return response;
		}
		
		if (!StringUtils.isBlank(picture)) {
			String path = DataUtil.uploadImage(DataUtil._10_to_62(System.currentTimeMillis()), picture);
			if (StringUtils.equals(path, "-1")) {
				response.setStatus(ResponseStatus.FAILED);
				response.setMessage("服务器仅支持jpg、png格式图片！");
				return response;
			} else if (StringUtils.isBlank(path)) {
				response.setMessage("服务器图片解析异常，请确认上传的图片是否正确！");
				response.setStatus(ResponseStatus.FAILED);
				return response;
			} else {
				//更新头像
				//resumeService.updateResumeHeadByUserId(userId,path);
				response.setStatus("200");
				response.setMessage("上传成功!");
			}
		} else {
			response.setMessage("图片信息为空！");
			response.setStatus(ResponseStatus.FAILED);
		}
		
		return response;
	}
	
	/**
	 * 上传图片到正式目录
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/uploadHeadImage", method = RequestMethod.POST)
	public @ResponseBody JsonResWrapper headImage(HttpServletRequest request, Model model,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude, @RequestParam(required=false) Integer userId) {
		JsonResWrapper response = new JsonResWrapper();
		/*Object obj = request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
		if (obj == null) {
			response.setMessage("请先登录！");
			response.setStatus(ResponseStatus.FAILED);
			return response;
		}*/
		if(userId == null || userId.intValue() < 0){
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage("用户id为空,或不存在!");
			return response;
		}
		
		//if (!StringUtils.isBlank(picture)) {
			List<String> paths;
			try {
				paths = DataUtil.uploadImg(request,"file",true,200,200);
				//更新头像
				//resumeService.updateResumeHeadByUserId(userId,paths.get(0));
				response.setStatus("200");
				response.setMessage("上传成功!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.setStatus(ResponseStatus.FAILED);
				response.setMessage("服务器仅支持jpg、png格式图片！");
				return response;
			}
			
			
		
		
		return response;
	}
	
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public @ResponseBody JsonResWrapper uploadImage(Model model, HttpServletRequest request,@RequestParam(required = false) Float latitude,@RequestParam(required = false) Float longitude) {
		JsonResWrapper response = new JsonResWrapper();
		try {
			String path = DataUtil.uploadImgToTempDir(request, "file",true);
			String base = SettingUtils.getCommonSetting("base.temp.url");
			Map<String ,Object> param = new HashMap<String,Object>();
			String[] fileNames = path.split("\\.");
			param.put("path", base+fileNames[0]+"_S"+"."+fileNames[1]);
			param.put("fileName", path);
			response.setData(param);
			return response;
			//pw.write(fileName);
		} catch (Exception e) {
			//pw.write("上传失败！" + e.getMessage());
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage(e.getMessage());
			return response;
		}

		//pw.flush();
	}
}
