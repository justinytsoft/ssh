package com.goldCityWeb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.goldCityWeb.dao.IRemindDao;
import com.goldCityWeb.domain.Remind;
import com.goldCityWeb.service.IRemindService;

@Service
public class RemindServiceImpl implements IRemindService {

	@Autowired
	private IRemindDao remindDao;

	@Override
	public List<Remind> queryRemindByAdvId(String ids) {
		List<Remind> rs = remindDao.queryRemindByAdvId(ids);
		if(!CollectionUtils.isEmpty(rs)){
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < rs.size(); i++){
				if(i!=0){
					sb.append(",");
				}
				sb.append(rs.get(i).getId()+"");
			}
			remindDao.updateRemindSendStatus(sb.toString());
		}
		return rs;
	}
}
