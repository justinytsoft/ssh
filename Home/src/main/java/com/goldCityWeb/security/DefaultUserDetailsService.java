/**
 * 
 */
package com.goldCityWeb.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.goldCityWeb.dao.SysUsersDao;
import com.goldCityWeb.domain.SysMenus;
import com.goldCityWeb.domain.UserInfo;

/**
 * @author randy
 *
 */
public class DefaultUserDetailsService implements UserDetailsService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private SysUsersDao sysUsersDao;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo su = sysUsersDao.queryUserinfoByUsername(username);
		
		try {
			if (su != null && su.getType()!=2) {
				List<SysMenus> menus = sysUsersDao.queryUserMenusByUsrId(su.getId());
				Map<Integer, SysMenus> map = new LinkedHashMap<Integer, SysMenus>();
				for (SysMenus sm : menus) {
					if (sm.getParent_id().intValue() == 0) {//一级菜单
						map.put(sm.getId(), sm);
						continue;
					}
					SysMenus psm = map.get(sm.getParent_id());
					psm.addSubMenus(sm);//设置二级菜单
				}
				
				List<SysMenus> newMenus = new ArrayList<SysMenus>();
				Iterator<SysMenus> is = map.values().iterator();
				while(is.hasNext()) {
					newMenus.add(is.next());
				}
				su.setMenus(newMenus);
				
				//设置权限
		        Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		        if(su.getType().intValue() == 1){
					auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
				}else if(su.getType().intValue() == 3 || su.getType().intValue() == 4){
					auths.add(new SimpleGrantedAuthority("ROLE_MERCHANT"));
				}
		        su.setAuthorities(auths);
			}else{ //app账号
				su = new UserInfo();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return su;
	}
}
