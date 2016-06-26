package com.goldCityWeb.timer;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.goldCityWeb.domain.MainList;
import com.goldCityWeb.domain.Remind;
import com.goldCityWeb.domain.SysUsers;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.push.PushServer;
import com.goldCityWeb.service.AdvService;
import com.goldCityWeb.service.IRemindService;
import com.goldCityWeb.service.UserService;







public class AutoTimer {

	@Autowired
	private AdvService advService;
	@Autowired
	private IRemindService remindService;
	@Autowired
	private UserService userService;
	
	protected Logger log = Logger.getLogger(AutoTimer.class);
	public static int i = 0;


	
	public AutoTimer() {
	}

	public void run() {
		//推送
		List<MainList> mls = advService.querySoonSendAdv();
		
		if(!CollectionUtils.isEmpty(mls)){
			
			//拼接advId
			StringBuffer advIds = new StringBuffer();
			for(int i = 0; i < mls.size(); i++){
				if(i!=0){
					advIds.append(",");
				}
				advIds.append(mls.get(i).getId()+"");
				
			}
			
			List<Remind> rs = remindService.queryRemindByAdvId(advIds.toString());
			
			if(!CollectionUtils.isEmpty(rs)){
				
				//拼接userId
				StringBuffer userIds = new StringBuffer();
				for(int i = 0; i < rs.size(); i++){
					if(i!=0){
						userIds.append(",");
					}
					userIds.append(rs.get(i).getUser_id()+"");
				}
				
				List<UserDetail> uds = userService.queryUserByIds(userIds.toString());
				
				if(!CollectionUtils.isEmpty(uds)){
					
					for(UserDetail ud : uds){
						if(!StringUtils.isBlank(ud.getToken()) && ud.getToken_type()!=null){
							//推送
							PushServer.pushActivityClientByToken("【黄金都市】", "您关注的红包即将发放", ud.getToken(), "", ud.getToken_type(), ud.getType());
						}
					}
				}
			}
		}
		
		//更新广告状态
		advService.updateAllAdvStatus();
		
		//List<MainList> mls = advService.queryAdv();
	}
	
	public void check(){
		
	}
}
