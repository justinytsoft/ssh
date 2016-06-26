package com.goldCityWeb.webservice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.Feedback;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.json.JsonResWrapper;
import com.goldCityWeb.service.FeedbackService;
import com.goldCityWeb.util.Constants;

@Controller
@RequestMapping(value="/services/feedback")
public class WS_Feedback {
	@Autowired
	private FeedbackService feedbackService;
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public @ResponseBody JsonResWrapper saveFeedback(HttpServletRequest request, @RequestParam String content, @RequestParam String phone) {
		JsonResWrapper response = new JsonResWrapper();
		UserDetail ud = (UserDetail)request.getSession().getAttribute(Constants.SESSION_APP_LOGIN_USER);
		Feedback fb = new Feedback();
		if(ud != null) {
			fb.setUser_id(ud.getId());
		}
		fb.setContent(content);
		fb.setPhone(phone);
		feedbackService.saveFeedback(fb);
		response.setMessage("保存成功");
		response.setStatus("200");
		return response;
	}
}
