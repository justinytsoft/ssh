package com.goldCityWeb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldCityWeb.dao.FeedbackDao;
import com.goldCityWeb.domain.Feedback;
import com.goldCityWeb.service.FeedbackService;
import com.goldCityWeb.util.AbstractModuleSuport;
import com.goldCityWeb.util.PageSupport;

@Service
public class FeedbackServiceImpl extends AbstractModuleSuport implements FeedbackService{
	@Autowired
	private FeedbackDao feedbackDao;
	
	@Override
	public void saveFeedback(Feedback feedback) {
		// TODO Auto-generated method stub
		feedbackDao.saveFeedback(feedback);
	}

	@Override
	public List<Feedback> queryFeedbacks(PageSupport ps,Map<String, Object> param) {
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.FeedbackDao.queryFeedbacks", "com.goldCityWeb.dao.FeedbackDao.queryFeedbacks_count", param, ps);
	}

}
