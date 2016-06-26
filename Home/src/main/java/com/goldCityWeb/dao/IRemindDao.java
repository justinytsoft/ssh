package com.goldCityWeb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.goldCityWeb.domain.Remind;

public interface IRemindDao {

	List<Remind> queryRemindByAdvId(@Param("ids") String ids);

	void updateRemindSendStatus(@Param("ids") String string);

}
