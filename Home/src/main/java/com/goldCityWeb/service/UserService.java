/**
 * 
 */
package com.goldCityWeb.service;

import java.util.List;
import java.util.Map;

import com.goldCityWeb.domain.CompanyType;
import com.goldCityWeb.domain.FeeRecord;
import com.goldCityWeb.domain.HeadList;
import com.goldCityWeb.domain.Income;
import com.goldCityWeb.domain.Item;
import com.goldCityWeb.domain.LoginImg;
import com.goldCityWeb.domain.Message;
import com.goldCityWeb.domain.Report;
import com.goldCityWeb.domain.RobotGold;
import com.goldCityWeb.domain.Section;
import com.goldCityWeb.domain.SignRecord;
import com.goldCityWeb.domain.SignTable;
import com.goldCityWeb.domain.SysUsers;
import com.goldCityWeb.domain.UserDetail;
import com.goldCityWeb.util.PageSupport;

/**
 * @author Administrator
 *
 */
public interface UserService {
	
	/**
	 * 查询用户信息
	 * @param username
	 * @return
	 */
	public SysUsers queryUserByUsername(String username);
	
	public SignRecord querySignStatusById(Integer id);
	
	public SignTable querySignTableById(Integer id);
	
	public void updateSign(SignRecord sr);
	
	public void updateUserLevelByUserId(Integer id);
	
	public void updateUserExperience(Integer user_id,Integer exp);
	
	public List<SysUsers> queryUserList(Map<String, Object> param, PageSupport ps);
	
	public List<SysUsers> queryHouQinList(Map<String, Object> param, PageSupport ps);
	
	public List<UserDetail> queryUserdetailList(Map<String, Object> param, PageSupport ps);
	
	public List<LoginImg>queryLoginImgList(Map<String, Object> param, PageSupport ps);
	
	public void saveLoginImg(LoginImg loginImg);
	
	public void saveHeadPath(String path);
	
	public void saveSection(Section section);
	
	public void saveCompType(CompanyType cType);
	
	public void deleteloginimg(Integer id);
	
	/**
	 * 删除头像
	 * @param id
	 */
	public void deleteHead(Integer id);
	
	/**
	 * 删除地区
	 */
	public void deleteSection(Integer id);
	
	/**
	 * 删除公司类型
	 */
	public void deletecomptype(Integer id);
	/**
	 * 查询头像列表
	 * @return
	 */
	public List<HeadList> queryHeadList(Map<String, Object> param, PageSupport ps);
	
	/**
	 * 商家类型列表
	 * @param param
	 * @param ps
	 * @return
	 */
	public List<CompanyType> queryCompanyTypeList(Map<String, Object> param, PageSupport ps);
	
	/**
	 * 查询头像列表
	 * @return
	 */
	public List<Section> querySectionList(Map<String, Object> param, PageSupport ps);
	
	/**
	 * 机器人抢金币流水
	 * @param param
	 * @param ps
	 * @return
	 */
	public List<RobotGold> queryRobotGoldList(Map<String, Object> param, PageSupport ps);
	
	public List<SignRecord> querySignRecordList(Map<String, Object> param, PageSupport ps);
	
	/**
	 * 查询消费记录
	 * @param param
	 * @param ps
	 * @return
	 */
	public List<FeeRecord> queryCostlist(Map<String, Object> param, PageSupport ps);
	/**
	 * 查询举报类型
	 * @param 
	 * @return
	 */
	public List<Report> queryReportType();
	
	/**
	 * 保存举报数据
	 * @param  Report
	 * @return
	 */
	public void savereport(Report rep);
	
	/**
	 * 查询举报信息
	 * @param 
	 * @return
	 */
	public List<Report> queryReportlist(Map<String, Object> param, PageSupport ps);
	
	/**
	 * 根据登录名与密码查询用户信息
	 * 
	 * @param username
	 * @param password
	 *            可为空
	 * @return
	 */
	public SysUsers querySysUserByUPD(String username, String password);
	
	
	public UserDetail queryUserDetailById(Integer id);
	
	public Item querySysUserItemById(Integer id);

	/**
	 * 更新用户的密码
	 * 
	 * @param userId
	 * @param password
	 */
	public void updatePassword(Integer userId, String password);
	
	
	public void updateUserDetail(UserDetail u);
	
	
	
	/**
	 * 改变验证状态
	 * @param state
	 */
	public void updateValidateState(Integer id,Integer state);
	
	
	/**
	 * 更新用户资料
	 * @param su
	 */
	public void updateUser(SysUsers su);
	
	public void updateUserHead(Integer user_id,String head);
	
	public void updateStudent(SysUsers su);
	
	public void updateCompany(SysUsers su);
	
	/**
	 * 通过id查找用户
	 * @param id
	 * @return
	 */
	public String querySysUserNickNameById(Integer id);
	
	
	
	/**
	 * 获取用户存储的更多图片
	 * @param id
	 * @return
	 */
	public List<String> queryMoreUserPicByUserId(Integer id);
	
	
	
	
	/**
	 * 退出小组
	 * @param param
	 */
	public void quitGroup(Map<String, Object> param);
	
	/**
	 * 查询用户详细信息
	 * @param id
	 */
	public SysUsers querySysUserById(Integer id);
	
	/**
	 * 修改用户是否可用
	 * @param id
	 * @param enabled
	 */
	public void updateOverdue(Integer id, boolean overdue);
	
	/**
	 * 查询用户角色
	 * @param id
	 * @return
	 */
	public Integer queryRoleByUser(Integer id);
	
	public void saveUser(SysUsers sysUsers);
	
	public void saveHouQin(SysUsers sysUsers);
	
	public void deleteSysUsers(Integer userId);
	
	public void updateuserEnabled(Integer id,Integer enabled);

	//===================================================
	//===================================================
	//===================================================
	
	/**
	 * 获取消息列表
	 * @param ps
	 * @param param
	 * @return
	 */
	public List<Message> queryMessages(PageSupport ps, Map<String,Object> param);

	/**
	 * 获取收益列表
	 * @param ps
	 * @param param
	 * @return
	 */
	public List<Income> queryIncomes(PageSupport ps, Map<String, Object> param);

	/**
	 * 获取消费记录列表
	 * @param ps
	 * @param param
	 * @return
	 */
	public List<FeeRecord> queryFeeRecords(PageSupport ps, Map<String, Object> param);

	/**
	 * 通过状态查询消费记录
	 * @param ps
	 * @param param
	 * @return
	 */
	public List<FeeRecord> queryFeeRecordsByStatus(PageSupport ps, Map<String, Object> param);

	/**
	 * 更新消费记录的状态为申请结算
	 * @param param
	 */
	public void updateFeeRecordStatusToApplyClose(Map<String, Object> param);

	/**
	 * 获取消费记录列表
	 * @param ps
	 * @param param
	 * @return
	 */
	public List<FeeRecord> queryMerFeeRecords(Map<String, Object> param);

	/**
	 * 获取消费记录列表总数
	 * @param ps
	 * @param param
	 * @return
	 */
	public int queryMerFeeRecordsTotal(Map<String, Object> param);

	/**
	 * 更新商户余额
	 * @param ud
	 */
	public void updateMerGoldCount(UserDetail ud);

	/**
	 * 将商家所有申请结算的消费记录更新为已结算
	 * @param id
	 */
	public void updateFeeRecordStatusToSettlement(Integer id);

	/**
	 * 通过ID数组查询消费记录
	 * @param mid 
	 * @param ids
	 * @return
	 */
	public List<FeeRecord> queryFeeRecordsByIds(Integer mid, String ids);

	public List<UserDetail> queryUserByIds(String ids);

}
