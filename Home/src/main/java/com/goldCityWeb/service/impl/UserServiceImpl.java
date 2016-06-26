/**
 * 
 */
package com.goldCityWeb.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldCityWeb.dao.SysRolesDao;
import com.goldCityWeb.dao.SysUsersDao;
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
import com.goldCityWeb.service.UserService;
import com.goldCityWeb.util.AbstractModuleSuport;
import com.goldCityWeb.util.DataUtil;
import com.goldCityWeb.util.PageSupport;


/**
 * @author Administrator
 *
 */
@Service(value="userService")
public class UserServiceImpl extends AbstractModuleSuport implements UserService {

	@Autowired
	private SysUsersDao sysUsersDao;
	
	@Autowired
	private SysRolesDao sysRolesDao;
	
	public void saveHeadPath(String path){
		sysUsersDao.insertHeadPath(path);
	}
	
	public void saveLoginImg(LoginImg loginImg){
		sysUsersDao.insertLoginImg(loginImg);
	}
	
	public void saveSection(Section section){
		sysUsersDao.insertSection(section);
	}
	
	public void saveCompType(CompanyType cType){
		sysUsersDao.insertCompType(cType);
	}
	
	 public void deleteHead(Integer id){
		 sysUsersDao.deleteHead(id);
	 }
	 
	 public void deleteloginimg(Integer id){
		 sysUsersDao.deleteloginimg(id);
	 }
	 public void deleteSection(Integer id){
		 sysUsersDao.deleteSection(id);
	 }
	 
	 public void deletecomptype(Integer id){
		 sysUsersDao.deletecomptype(id);
	 }
	 
	 
	public List<HeadList> queryHeadList(Map<String, Object> param, PageSupport ps) {
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.queryHeadList","com.goldCityWeb.dao.SysUsersDao.queryHeadList_count", param, ps);
	}
	
	public List<CompanyType> queryCompanyTypeList(Map<String, Object> param, PageSupport ps){
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.queryCompanyTypeList","com.goldCityWeb.dao.SysUsersDao.queryCompanyTypeList_count", param, ps);
	}
	public List<Section> querySectionList(Map<String, Object> param, PageSupport ps){
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.querySectionList","com.goldCityWeb.dao.SysUsersDao.querySectionList_count", param, ps);
	}
	
	public List<RobotGold> queryRobotGoldList(Map<String, Object> param, PageSupport ps){
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.queryRobotGoldList","com.goldCityWeb.dao.SysUsersDao.queryRobotGoldList_count", param, ps);
	}
	
	public List<SignRecord> querySignRecordList(Map<String, Object> param, PageSupport ps){
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.querySignRecordList","com.goldCityWeb.dao.SysUsersDao.querySignRecordList_count", param, ps);
	}
	@Override
	public List<SysUsers> queryUserList(Map<String, Object> param, PageSupport ps) {
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.queryUserList", "com.goldCityWeb.dao.SysUsersDao.queryUserList_count", param, ps);
	}
	
	@Override
	public List<FeeRecord> queryCostlist(Map<String, Object> param, PageSupport ps){
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.queryCostlist", "com.goldCityWeb.dao.SysUsersDao.queryCostlist_count", param, ps);
	}
	
	@Override
	public List<UserDetail> queryUserdetailList(Map<String, Object> param, PageSupport ps) {
		if(ps==null){
			return this.getList("com.goldCityWeb.dao.SysUsersDao.queryUserdetailList", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.queryUserdetailList", "com.goldCityWeb.dao.SysUsersDao.queryUserdetailList_count", param, ps);
		}
	}
	
	public List<LoginImg>queryLoginImgList(Map<String, Object> param, PageSupport ps){
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.queryLoginImgList", "com.goldCityWeb.dao.SysUsersDao.queryLoginImgList_count", param, ps);
	}
	@Override
	public List<Report> queryReportType(){
		return sysUsersDao.queryReportType();
	}
	
	@Override
	public void savereport(Report rep){
		sysUsersDao.insertreport(rep);
	}
	
	@Override
	public SysUsers querySysUserByUPD(String username, String password) {
		return sysUsersDao.querySysUserByUPD(username, password);
	}

	@Override
	public void updatePassword(Integer userId, String password) {
		sysUsersDao.updatePassword(userId, password);
	}

	@Override
	public void updateValidateState( Integer id,Integer state) {
		sysUsersDao.updateValidateState(id ,state);
	}

	@Override
	public void updateUser(SysUsers su) {
		sysUsersDao.updateUser(su);
	}
	
	@Override
	public String querySysUserNickNameById(Integer id) {
		return sysUsersDao.querySysUserNickNameById(id);
	}

	@Override
	public void quitGroup(Map<String, Object> param) {
		sysUsersDao.quitGroup(param);
	}

	@Override
	public SysUsers queryUserByUsername(String username) {
		return sysUsersDao.queryUserByUsername(username);
	}

	@Override
	public SysUsers querySysUserById(Integer id) {
		return sysUsersDao.querySysUserById(id);
	}

	@Override
	public List<String> queryMoreUserPicByUserId(Integer id) {
		return sysUsersDao.queryMoreUserPicByUserId(id);
	}
	
	@Override
	public void updateOverdue(Integer id, boolean overdue) {
		sysUsersDao.updateOverdue(id, overdue);
	}
	
	@Override
	public Integer queryRoleByUser(Integer id) {
		return sysRolesDao.queryRoleByUser(id);
	}

	@Override
	public void saveUser(SysUsers sysUsers) {
		if(sysUsers.getId() != null){
			
		} else{
			sysUsersDao.insertUsers(sysUsers);
		}
			
	}

	@Override
	public void updateStudent(SysUsers su) {
		SysUsers s = sysUsersDao.querySysUserById(su.getId());
		if(!StringUtils.isBlank(s.getHead())){
			if(!StringUtils.isBlank(su.getHead())){
				DataUtil.deleteByUploadImg(s.getHead());
			} else{
				su.setHead(s.getHead());
			}
		}
		sysUsersDao.updateStudent(su);
		
	}
	
	@Override
	public void updateCompany(SysUsers su) {
		SysUsers s = sysUsersDao.querySysUserById(su.getId());
		if(!StringUtils.isBlank(s.getHead())){
			if(!StringUtils.isBlank(su.getHead())){
				DataUtil.deleteByUploadImg(s.getHead());
			} else{
				su.setHead(s.getHead());
			}
		}
		sysUsersDao.updateCompany(su);
		
	}

	@Override
	public void deleteSysUsers(Integer userId) {
		SysUsers su = sysUsersDao.querySysUserById(userId);
		if(su!=null){
			if(!StringUtils.isBlank(su.getHead())){
				DataUtil.deleteByUploadImg(su.getHead());
			}
			sysUsersDao.deleteSysUsers(userId);
			sysRolesDao.deleteRoleUser(null, userId);
		}
	}

	@Override
	public void updateuserEnabled(Integer id, Integer enabled) {
		sysUsersDao.updateuserEnabled(id,enabled);
	}

	@Override
	public void saveHouQin(SysUsers sysUsers) {
		if(sysUsers.getId()!=null && sysUsers.getId().intValue() > 0){
			sysUsersDao.updateHouQin(sysUsers);
		} else {
			sysUsersDao.insertHouQin(sysUsers);
			sysRolesDao.insertRoleUser(2, sysUsers.getId());
		}
	}

	@Override
	public List<SysUsers> queryHouQinList(Map<String, Object> param,
			PageSupport ps) {
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.queryHouQinList", "com.goldCityWeb.dao.SysUsersDao.queryHouQinList_count", param, ps);
	}

	@Override
	public List<Report> queryReportlist(Map<String, Object> param, PageSupport ps){
		return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.queryReportlist", "com.goldCityWeb.dao.SysUsersDao.queryReportlist_count", param, ps);
	}
	
	@Override
	public UserDetail queryUserDetailById(Integer id) {
		return sysUsersDao.queryUserDetailById(id);
	}

	@Override
	public List<Message> queryMessages(PageSupport ps, Map<String, Object> param) {
		if(ps==null){
			return this.getList("com.goldCityWeb.dao.SysUsersDao.queryMessages", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.queryMessages", "com.goldCityWeb.dao.SysUsersDao.queryMessagesTotal", param, ps);
		}
	}

	@Override
	public List<Income> queryIncomes(PageSupport ps, Map<String, Object> param) {
		if(ps==null){
			return this.getList("com.goldCityWeb.dao.SysUsersDao.queryIncomes", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.queryIncomes", "com.goldCityWeb.dao.SysUsersDao.queryIncomesTotal", param, ps);
		}
	}

	@Override
	public List<FeeRecord> queryFeeRecords(PageSupport ps, Map<String, Object> param) {
		if(ps==null){
			return this.getList("com.goldCityWeb.dao.SysUsersDao.queryFeeRecords", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.queryFeeRecords", "com.goldCityWeb.dao.SysUsersDao.queryFeeRecordsTotal", param, ps);
		}
	}

	@Override
	public List<FeeRecord> queryFeeRecordsByStatus(PageSupport ps, Map<String, Object> param) {
		if(ps==null){
			return this.getList("com.goldCityWeb.dao.SysUsersDao.queryFeeRecordsByStatus", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.SysUsersDao.queryFeeRecordsByStatus", "com.goldCityWeb.dao.SysUsersDao.queryFeeRecordsByStatusTotal", param, ps);
		}
	}
	
	@Override
	public void updateFeeRecordStatusToApplyClose(Map<String, Object> param) {
		sysUsersDao.updateFeeRecordStatusToApplyClose(param);
	}

	@Override
	public void updateUserHead(Integer user_id, String head) {
		sysUsersDao.updateUserHead(user_id,head);
		
	}

	@Override
	public Item querySysUserItemById(Integer id) {
		return sysUsersDao.querySysUserItemById(id);
	}

	@Override
	public void updateUserDetail(UserDetail u) {
		sysUsersDao.updateUserDetail(u);
	}
	

	@Override
	public List<FeeRecord> queryMerFeeRecords(Map<String, Object> param) {
		return sysUsersDao.queryMerFeeRecords(param);
	}

	@Override
	public int queryMerFeeRecordsTotal(Map<String, Object> param) {
		return sysUsersDao.queryMerFeeRecordsTotal(param);
	}

	@Override
	public void updateMerGoldCount(UserDetail ud) {
		sysUsersDao.updateMerGoldCount(ud);
	}
	

	@Override
	public SignRecord querySignStatusById(Integer id) {
		return sysUsersDao.querySignStatusById(id);
	}

	@Override
	public SignTable querySignTableById(Integer id) {
		return sysUsersDao.querySignTableById(id);
	}

	@Override
	public void updateSign(SignRecord sr) {
		sysUsersDao.insertSignRecord(sr);
		sysUsersDao.updateUserSignRecord(sr);
	}

	@Override
	public void updateFeeRecordStatusToSettlement(Integer id) {
		sysUsersDao.updateFeeRecordStatusToSettlement(id);
	}
	
	@Override
	public void updateUserExperience(Integer user_id, Integer exp) {
		sysUsersDao.updateUserExperience(user_id,exp);
	}

	@Override
	public void updateUserLevelByUserId(Integer id) {
		sysUsersDao.updateUserLevelByUserId(id);
	}

	@Override
	public List<FeeRecord> queryFeeRecordsByIds(Integer mid,String ids) {
		return sysUsersDao.queryFeeRecordsByIds(mid,ids);
	}

	@Override
	public List<UserDetail> queryUserByIds(String ids) {
		return sysUsersDao.queryUserByIds(ids);
	}
}
