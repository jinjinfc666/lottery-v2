package com.jll.user;

import java.util.List;
import java.util.Map;

import com.jll.dao.PageBean;
import com.jll.entity.SiteMessage;
import com.jll.entity.UserInfo;

public interface UserInfoDao
{
	/**
	 * query the receiver bank card
	 * the bank card should be activated
	 * one bank only one bank account existing in the table
	 * @param bankCode
	 * @return
	 */
	int getUserId(String userName);
	long getCountUser(String userName);
	long getCountUser(UserInfo user);
	  
	UserInfo getUserByUserName(String userName);
	
	UserInfo getUserById(Integer userId);
	
	/**
	 * if the user is existing in database
	 * @param user
	 * @return
	 */
	boolean isUserExisting(UserInfo user);
	
	/**
	 * persist the user entity
	 * @param user
	 */
	void saveUser(UserInfo user);
	
	/**
	 * the general agency is unique in the system
	 * @return
	 */
	UserInfo getGeneralAgency();
	
	String queryUnSystemUsers();
	
	boolean checkUserIds(String UserIds);
	//查询所有的用户
	Map<String,Object>  queryAllUserInfo(Integer id,String userName,Integer proxyId,String startTime,String endTime,Integer pageIndex,Integer pageSize);
	//查询所有的代理
	Map<String,Object>  queryAllAgent(Map<String, Object> params, UserInfo loginUser);
	
	//查询总代下面的所有一级代理
	List<UserInfo> queryAllAgent(Integer id);
	//点击代理查询下一级代理
	PageBean queryAgentByAgent(Integer id,String startTime,String endTime,Integer pageSize,Integer pageIndex, String sonUserName);
	List<UserInfo> queryAgentByAgentHou(Integer id,String startTime,String endTime);
	//查询总代
	UserInfo querySumAgent();
	
	PageBean<UserInfo> queryAllUserInfoByPage(PageBean<UserInfo> reqPage);
	
	List<?> queryByAll();
	//通过用户Id查询用户银行卡数量
	long queryUserBankCount(Integer userId);
	
	/**
	 * query the receiver of site msg
	 * @param sql
	 * @param page
	 * @return
	 */
	PageBean<UserInfo> querySiteMsgRec(String sql, 
			PageBean<UserInfo> page);
	
	Map<String, Object> queryAllAgentSMByAdmin(Integer searchType, String userName, String startTime, String endTime, Integer pageIndex,
			Integer pageSize);
	
	Map<String, Object> queryAllAgentSMByAgency(Integer id, 
			String userName, 
			String startTime, 
			String endTime,
			Integer pageIndex, 
			Integer pageSize);
	
	PageBean<UserInfo> queryXYUsers(String string, PageBean<UserInfo> page);
	
	Map<String, Object> queryAllAgentXYByAdmin(Integer searchType, String userName, String startTime, String endTime,
			Integer pageIndex, Integer pageSize);
	
	Map<String, Object> queryAllAgentXYByAgency(Integer id, String userName, String startTime, String endTime,
			Integer pageIndex, Integer pageSize);
	
	Map<String, Object> queryAllAgentEntrustByAdmin(Integer searchType, String userName, String startTime,
			String endTime, Integer pageIndex, Integer pageSize);
	
	Map<String, Object> queryAllAgentEntrustByAgency(Integer id, String userName, String startTime, String endTime,
			Integer pageIndex, Integer pageSize);
	
	Map<String, Object> queryAllUserInfo(Integer id, String userName, Integer proxyId, String startTime, String endTime,
			Integer userType, Integer userStatus, Integer pageIndex, Integer pageSize);
}
