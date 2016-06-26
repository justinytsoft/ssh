/**
 * 
 */
package com.goldCityWeb.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @author randy
 * 
 */
public interface SysRolesDao {
	public void insertRoleUser(@Param(value = "role_id") Integer role_id, @Param(value = "user_id") Integer user_id);

	public void deleteRoleUser(@Param(value = "role_id") Integer role_id, @Param(value = "user_id") Integer user_id);
	
	public Integer queryRoleByUser(@Param(value = "user_id") Integer user_id);
}
