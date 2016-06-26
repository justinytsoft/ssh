/**
 * 
 */
package com.goldCityWeb.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping(value = "/upload")
public class UploadAction {
	

	
	@RequestMapping(value = "/ckeditorUpload", method = RequestMethod.POST)
	public void listTdc(HttpServletRequest request, Model model, @RequestParam String CKEditorFuncNum, PrintWriter out) {
		try {
			String fileName = DataUtil.uploadImg(request, "upload").get(0);
			
			out.println("<script type=\"text/javascript\">");  
			out.println("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'"  + request.getContextPath() + "/img/" + fileName + "','')");   
			out.println("</script>");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<script type=\"text/javascript\">");    
		    out.println("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",''," + "'文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");   
		    out.println("</script>");  
		}
		
	}
	
	/**
	 * 文件上传，用于处理大文件上传
	 * 
	 * @param model
	 * @param request
	 * @param name
	 * @param chunk
	 * @param chunks
	 * @return
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(Model model, HttpServletRequest request, @RequestParam String name, @RequestParam Integer chunk, @RequestParam Integer chunks, PrintWriter pw) {
		String sessionId = request.getSession().getId();
		name = sessionId + "_" + name;
		DataUtil.uploadSegmentedFile(request, name);

		pw.write(name);
		pw.flush();
	}
	
	
	
	
	
	/**
	 * 上传图片
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	public @ResponseBody String uploadImg(Model model, HttpServletRequest request) {
		
		try {
			/*List<String> fileNames = DataUtil.uploadImg(request, "file", true,144,115);
			name = sessionId + "_" + name;*/
			String name = DataUtil.uploadImgdFile(request);
			return name+";"+SettingUtils.getCommonSetting("base.temp.url")+name;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "false";
	}
	
	/**
	 * 上传图片到临时文件
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public @ResponseBody JsonResWrapper uploadImage(Model model, HttpServletRequest request) {
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
	
	/**
	 * 图片上传测试
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/uploadtest")
	public String uploadTest(Model model) {
		return "pages/album/uploadImg";
	}
	
	
}
