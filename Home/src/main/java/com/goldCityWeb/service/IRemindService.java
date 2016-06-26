package com.goldCityWeb.service;

import java.util.List;

import com.goldCityWeb.domain.Remind;

public interface IRemindService {

	List<Remind> queryRemindByAdvId(String ids);
}
