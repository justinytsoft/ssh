package com.goldCityWeb.service;

import java.util.List;
import java.util.Map;

import com.goldCityWeb.domain.Feedback;
import com.goldCityWeb.util.PageSupport;

public interface FeedbackService {

	public void saveFeedback(Feedback feedback);

	public List<Feedback> queryFeedbacks(PageSupport ps, Map<String, Object> param);
}
