/**
 * 
 */
package com.goldCityWeb.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.goldCityWeb.domain.CompanyType;
import com.goldCityWeb.domain.FeeRecord;
import com.goldCityWeb.domain.Item;
import com.goldCityWeb.domain.LoginImg;
import com.goldCityWeb.domain.Report;
import com.goldCityWeb.domain.Section;
import com.goldCityWeb.domain.SignRecord;
import com.goldCityWeb.domain.SignTable;
import com.goldCityWeb.domain.SysMenus;
import com.goldCityWeb.domain.SysUsers;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.domain.UserInfo;

/**
 * @author randy
 * 
 */
public interface SysUsersDao {

	/**
	 * 根据登录名与密码查询用户信息，用于安全认证
	 * 
	 * @param username
	 * @return
	 */
	public UserInfo queryUserinfoByUsername(@Param(value = "username") String username);
	
	
	public void deleteHead(Integer id);
	
	public void deleteloginimg(Integer id);
	
	public void deleteSection(Integer id);
	
	public void deletecomptype(Integer id);

	/**
	 * 查询举报类型
	 * @param 
	 * @return
	 */
	public  List<Report> queryReportType();
	
	/**
	 * 保存上传头像路径
	 * @param path
	 */
	public void insertHeadPath(String path);
	
	public void insertLoginImg(LoginImg loginImg);
	
	/**
	 * 保存添加的地区信息
	 * @param path
	 */
	public void insertSection(Section section);
	
	/**
	 * 保存添加的公司类型
	 * @param cType
	 */
	public void insertCompType(CompanyType cType);
	/**
	 * 保存举报信息
	 * @param  Report
	 * @return
	 */
	public void insertreport(Report rep);
	
	/**
	 * 查询用户信息，用于查看是否存在该用户
	 * @param username
	 * @return
	 */
	public SysUsers queryUserByUsername(@Param(value = "username") String username);

	/**
	 * 根据用户ID查询出该用户的所有菜单
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysMenus> queryUserMenusByUsrId(@Param(value = "userId") Integer userId);

	/**
	 * 更新用户的最近一次登录ID地址
	 * 
	 * @param userId
	 * @param ipAdrr
	 */
	public void updateLoginInfor(@Param(value = "userId") Integer userId, @Param(value = "ipAdrr") String ipAdrr);

	/**
	 * 根据登录名与密码查询用户信息
	 * 
	 * @param username
	 * @param password
	 *            可为空
	 * @return
	 */
	public SysUsers querySysUserByUPD(@Param(value = "username") String username, @Param(value = "password") String password);

	/**
	 * 根据用户的名称判断Name是否存在，而且不包含登录用户自己的ID
	 * 
	 * @param userId
	 *            可为空
	 * @param name
	 * @return 该名称的数量
	 */
	public Integer querySysUserByNameID(@Param(value = "userId") Integer userId, @Param(value = "name") String name);

	/**
	 * 根据用户ID查询用户
	 * 
	 * @param userId
	 * @return
	 */
	public SysUsers querySysUserByUserId(@Param(value = "userId") Integer userId);

	/**
	 * 更新用户的密码
	 * 
	 * @param userId
	 * @param password
	 */
	public void updatePassword(@Param(value = "userId") Integer userId, @Param(value = "password") String password);

	public Integer queryOne();


	/**
	 * 验证username是否存在
	 * 
	 * @param userId
	 * @param username
	 * @return
	 */
	public Integer querySysUserByUsernameID(@Param(value = "id") Integer userId, @Param(value = "username") String username);

	

	public SysUsers querySysUserByID(@Param(value = "id") Integer userId);
	
	public List<SysUsers> queryUserList();
	
	/**
	 * 怎加新用户
	 * @param user
	 */
	public void insertUser(SysUsers user);
	
	public void insertCompany(SysUsers user);
	
	public void insertStudent(SysUsers user);
	
	public void updateStudent(SysUsers su);
	
	public void insertCompanySuperiority(@Param(value = "user_id")Integer user_id,@Param(value = "label_id")Integer label_id);
	
	/**
	 * 改变验证状态
	 * @param Integer id
	 * @param state
	 */
	public void updateValidateState(@Param(value = "id") Integer id,@Param(value = "state")Integer state);

	
	/**
	 * 更新用户资料
	 * @param su
	 */
	public void updateUser(SysUsers su);
	
	/**
	 * 通过id查找用户昵称
	 * @param id
	 * @return
	 */
	public String querySysUserNickNameById(@Param(value = "id") Integer id);
	

	
	/**
	 * 退出小组
	 * @param param
	 */
	public void quitGroup(Map<String, Object> param);
	
	/**
	 * 查询用户详细信息
	 * @param user
	 */
	public SysUsers querySysUserById(@Param(value = "id") Integer id);
	
	
	/**
	 * 获取用户存储的更多图片
	 * @param id
	 * @return
	 */
	public List<String> queryMoreUserPicByUserId(@Param(value = "id") Integer id);
	
	/**
	 * 修改用户是否可用
	 * @param id
	 */
	public void updateOverdue(@Param(value = "id") Integer id, @Param(value="overdue") boolean overdue);
	
	public void updateCompany(SysUsers su);
	
	public void deleteSysUsers(Integer userId);
	
	public void updateuserEnabled(@Param(value = "id")Integer id, @Param(value = "enabled")Integer enabled);
	
	public void insertHouQin(SysUsers sysUsers);
	
	public void insertUsers(SysUsers sysUsers);
	
	public void updateHouQin(SysUsers sysUsers);
	
	public UserDetail queryUserDetailById(Integer id);

	public void updateFeeRecordStatusToApplyClose(@Param("param") Map<String, Object> param);
	
	public void updateUserHead(@Param(value = "id")Integer user_id,@Param(value = "head")String head);
	
	public Item querySysUserItemById(Integer id);
	
	public void updateUserDetail(UserDetail u);
	

	public List<FeeRecord> queryMerFeeRecords(@Param("param") Map<String, Object> param);

	public int queryMerFeeRecordsTotal(@Param("param") Map<String, Object> param);

	public void updateMerGoldCount(UserDetail ud);
	
	public SignRecord querySignStatusById(Integer id);
	
	public SignTable querySignTableById(Integer id);
	
	public void updateUserItem(UserDetail u);
	
	public void updateUserLevelByUserId(Integer id);
	
	public void insertSignRecord(SignRecord sr);
	
	public void updateUserSignRecord(SignRecord sr);

	public void updateFeeRecordStatusToSettlement(@Param("mid") Integer id);
	
	public void updateUserExperience(@Param(value="user_id")Integer user_id,@Param(value="exp")Integer exp);

	public List<FeeRecord> queryFeeRecordsByIds(@Param("mid") Integer mid, @Param("ids") String ids);

	public List<UserDetail> queryUserByIds(@Param("ids") String ids);
}
