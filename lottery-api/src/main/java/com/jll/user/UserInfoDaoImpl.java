package com.jll.user;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.type.DateType;
import org.springframework.stereotype.Repository;

import com.jll.common.constants.Constants;
import com.jll.common.constants.Constants.UserType;
import com.jll.common.utils.DateUtil;
import com.jll.common.utils.StringUtils;
import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.dao.PageBean;
import com.jll.entity.UserInfo;
import com.jll.entity.UserInfoExt;

@Repository
public class UserInfoDaoImpl extends DefaultGenericDaoImpl<UserInfo> implements UserInfoDao
{
	private Logger logger = Logger.getLogger(UserInfoDaoImpl.class);

 
	@Override
	public int getUserId(String userName) {
		UserInfo userInfo=null;
		String sql = "from UserInfo where userName=?";
	    Query<UserInfo> query = getSessionFactory().getCurrentSession().createQuery(sql);
	    query.setParameter(0, userName);
	    userInfo = query.getSingleResult();
	    int id=userInfo.getId();
	    return id;
	}

	@Override
	public long getCountUser(String userName) {
		Integer userType=Constants.UserType.SYS_ADMIN.getCode();
		String sql = "select count(*) from UserInfo where userName=? and userType !=?";
	    Query query = getSessionFactory().getCurrentSession().createQuery(sql);
//	    Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    query.setParameter(0, userName);
	    query.setParameter(1, userType);
	    long count = ((Number)query.iterate().next()).longValue();
	    return count;
	}

	@Override
	public UserInfo getUserByUserName(String userName) {
		String sql = "from UserInfo where userName = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(userName);
		
		List<UserInfo> rets = query(sql, params, UserInfo.class);
		
		return rets.size() > 0?rets.get(0) : null;
	}
	
	@Override
	public UserInfo getUserById(Integer userId) {
		String sql = "from UserInfo where id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		
		List<UserInfo> rets = query(sql, params, UserInfo.class);
		
		return rets.size() > 0?rets.get(0) : null;
	}

	@Override
	public boolean isUserExisting(UserInfo user) {
		StringBuffer sql = new StringBuffer();
		
		StringBuffer condition = new StringBuffer();
		
		List<Object> params = new ArrayList<>();
		
		sql.append("select count(*) from UserInfo ");
		
		if(!StringUtils.isBlank(user.getUserName())) {
			condition.append(" userName = ? or ");
			params.add(user.getUserName());
		}
		
		if(!StringUtils.isBlank(user.getEmail())) {
			condition.append(" email = ? or ");
			params.add(user.getEmail());
		}
		
		if(!StringUtils.isBlank(user.getPhoneNum())) {
			condition.append(" phoneNum = ? or ");
			params.add(user.getPhoneNum());
		}
		
		if(condition.length() > 0) {
			condition.insert(0, " where ");
			int indx = condition.lastIndexOf(" or ");
			condition.replace(indx, condition.length(), "");
			
			logger.debug("condition ::::" + condition.toString());
			
			sql.append(condition.toString());
		}
		
		long count = queryCount(sql.toString(), params);
		return count > 0;
	}

	@Override
	public void saveUser(UserInfo user) {
		saveOrUpdate(user);
	}

	@Override
	public UserInfo getGeneralAgency() {
		//user_type = 3 means general agency
		String sql = "from UserInfo where userType = 3";
		
		List<UserInfo> generalAgencys  = query(sql, null, UserInfo.class);
		if(generalAgencys == null || generalAgencys.size() == 0) {
			return null;
		}
		
		return generalAgencys.get(0);
	}

	@Override
	public String queryUnSystemUsers() {
		String sql = "select group_concat(id) from UserInfo where userType <> ?";
	    Query query = getSessionFactory().getCurrentSession().createQuery(sql);
	    query.setParameter(0, UserType.SYS_ADMIN.getCode());
	    return query.iterate().next().toString();
	}

	@Override
	public boolean checkUserIds(String userIds) {
		String sql = "select count(1) from UserInfo where userType <> :type and id in(:ids)";
	    Query query = getSessionFactory().getCurrentSession().createQuery(sql);
	    String[] ids = userIds.split(StringUtils.COMMA);
	    List<Integer> intTemp = new ArrayList<>();
	    for (int index = 0; index < ids.length; index++) {
	    	intTemp.add(Integer.valueOf(ids[index]));
		}
	    query.setParameter("type", UserType.SYS_ADMIN.getCode());
	    query.setParameter("ids", intTemp);
	    long count = ((Number)query.iterate().next()).longValue();
		return count ==  ids.length;
	}

	/* (non-Javadoc)
	 * @see com.jll.user.UserInfoDao#queryAllUserInfo(java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Map<String,Object> queryAllUserInfo(Integer id,String userName,Integer proxyId,String startTime,String endTime,Integer pageIndex,Integer pageSize) {
		Map<String,Object> map=new HashMap<String,Object>();
		Integer userType=Constants.UserType.SYS_ADMIN.getCode();
		Integer userTypea=Constants.UserType.DEMO_PLAYER.getCode();
		map.put("userType", userType);
		map.put("userTypea", userTypea);
		String hql="";
		if(proxyId!=null) {
			String timeSql="";
			if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
				timeSql=" and a.create_time>=:startTime and a.create_time<:endTime";
				Date beginDate = DateUtil.fmtYmdHisToDate(startTime);
			    Date endDate = DateUtil.fmtYmdHisToDate(endTime);
				map.put("startTime", beginDate);
				map.put("endTime", endDate);
			}
			hql=("select a.* from (select *,FIND_IN_SET(:proxyId,superior) as aa from user_info)a where a.aa=1 and a.user_type !=:userType and a.user_type!=:userTypea"+timeSql);
			map.put("proxyId", proxyId);
			PageBean<UserInfo> page=new PageBean<>();
			page.setPageIndex(pageIndex);
			page.setPageSize(pageSize);
			page.setPageIndex(pageIndex);
			PageBean<UserInfo> pageBean=queryBySqlClazzPagination(page,hql,map,UserInfo.class);
			map.clear();
			map.put("data", pageBean);
			return map;
		}else {
			String userNameSql="";
			String idSql="";
			if(id!=null) {
				idSql=" and id=:id";
				map.put("id", id);
			}
			if(!StringUtils.isBlank(userName)) {
				userNameSql=" and LOCATE(:userName, userName)>0";
				map.put("userName", userName);
			}
			String timeSql="";
			if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
				timeSql=" and createTime >=:startTime and createTime <:endTime";
				Date beginDate = DateUtil.fmtYmdHisToDate(startTime);
			    Date endDate = DateUtil.fmtYmdHisToDate(endTime);
				map.put("startTime", beginDate);
				map.put("endTime", endDate);
			}
			hql=("from UserInfo where userType !=:userType and userType !=:userTypea"+timeSql+idSql+userNameSql);
			PageBean page=new PageBean();
			page.setPageIndex(pageIndex);
			page.setPageSize(pageSize);
			PageBean pageBean=queryByPagination(page,hql,map);
			map.clear();
			map.put("data", pageBean);
			return map;
		}
	}

	//查询总代下面的所有一级代理
	@Override
	public List<UserInfo> queryAllAgent(Integer id) {
		String sql="select * from(select *,FIND_IN_SET(:id,superior) as aa from user_info)a where a.aa=1";
		Query<UserInfo> query1 = getSessionFactory().getCurrentSession().createNativeQuery(sql,UserInfo.class);
	    query1.setParameter("id", id);
	    List<UserInfo> list=query1.list();
	    return list;
	}
	//点击代理查询下一级代理
	@Override
	public PageBean queryAgentByAgent(Integer id,String startTime,String endTime,Integer pageSize,Integer pageIndex, String sonUserName) {
		//Map<String,Object> map=new HashMap<String,Object>();
		List<Object> params = new ArrayList<>();
		
		String timeSql="";
		
		if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			timeSql=" and createTime >=? and createTime <=? ";
			
		}
		
		if(!StringUtils.isBlank(sonUserName)) {
			timeSql += " and userName=? ";
			params.add(sonUserName);
		}
		
		Integer userType=Constants.UserType.SYS_ADMIN.getCode();
		Integer userTypea=Constants.UserType.DEMO_PLAYER.getCode();
		Integer userTypeb=Constants.UserType.GENERAL_AGENCY.getCode();
		//String sql="select * from (select *,FIND_IN_SET(?,superior) as aa from user_info where user_type !=? and user_type !=? and user_type !=? "+timeSql+")a where a.aa=1";
		String sql="from UserInfo where userType !=? and userType !=? and userType !=? "+timeSql+" and FIND_IN_SET(?,superior) = 1";
		
		params.add(userType);
		params.add(userTypea);
		params.add(userTypeb);
		
	    if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			Date beginDate = DateUtil.fmtYmdHisToDate(startTime);
		    Date endDate = DateUtil.fmtYmdHisToDate(endTime);
		    
		    /*java.sql.Date bDate = new java.sql.Date(beginDate.getTime());
		    java.sql.Date eDate = new java.sql.Date(endDate.getTime());*/
	    	params.add(beginDate);
	    	params.add(endDate);
		}
	    params.add(id);
	    
	    PageBean<UserInfo> page= new PageBean<>();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		PageBean<UserInfo> pageBean = queryByPagination(page, sql, params, UserInfo.class); //queryBySqlClazzPagination(page,sql,map,UserInfo.class);
		return pageBean;
	} 
	//查询总代
	@Override
	public UserInfo querySumAgent() {
		List<Object> params = new ArrayList<>();
		String sql="from UserInfo where userType=?";
		params.add(Constants.UserType.GENERAL_AGENCY.getCode());
		List<UserInfo> list=query(sql, params, UserInfo.class);
		return list.get(0);
	}
	
	@Override
	public PageBean<UserInfo> queryAllUserInfoByPage(PageBean<UserInfo> reqPage) {
		String sql = "from UserInfo order by id";
		List<Object> params = new ArrayList<>();
		
		return this.queryByPagination(reqPage, sql, params, UserInfo.class);
	}

	@Override
	public List<?> queryByAll() {
		List<Object> params = new ArrayList<>();
		String sql="from UserInfo where userType=?";
		params.add(Constants.UserType.GENERAL_AGENCY.getCode());
		List<UserInfo> list=query(sql, params, UserInfo.class);
		String sql1="select a.user_name from(select *,FIND_IN_SET(:id,superior) as aa from user_info)a where a.aa=1";
	    Query<?> query1 = getSessionFactory().getCurrentSession().createNativeQuery(sql1);
	    query1.setParameter("id", list.get(0).getId());
	    List<?> userNameList=query1.list();
	    return userNameList;
	}
	//查询所有的代理
	@Override
	public Map<String, Object> queryAllAgent(Map<String, Object> params, UserInfo loginUser) {
				
		String userName = (String) params.get("userName");
		Integer userType = (Integer) params.get("userType");
		String startTime = (String) params.get("startTime");
		String endTime = (String) params.get("endTime");
		Integer pageIndex = (Integer) params.get("pageIndex");
		Integer pageSize = (Integer) params.get("pageSize");
		Integer searchType = (Integer)params.get("searchType");
		
		Map<String,Object> map=new HashMap<String,Object>();
		Integer generalAgency = Constants.UserType.GENERAL_AGENCY.getCode();
		UserInfo superior = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("from UserInfo where 1 = 1");
		
		
		
		if(loginUser.getUserType() ==  UserType.SYS_ADMIN.getCode()
				&& !StringUtils.isBlank(userName) 
				&& searchType != null 
				&& searchType.intValue() == 0) {
			buffer.append(" and userName=:userName ");
			map.put("userName", userName);
			if(userType != null) {
				UserType agency = UserType.getStateByCode(userType);
				UserType player = UserType.getPlayerByAgency(agency);
				buffer.append(" and (userType=:agency or userType=:player or userType=:generalAngecy)");
				map.put("agency", agency.getCode());
				map.put("player", player.getCode());
				map.put("generalAngecy", UserType.GENERAL_AGENCY.getCode());
			}
		}else if(loginUser.getUserType() ==  UserType.SYS_ADMIN.getCode()
				&& !StringUtils.isBlank(userName) 
				&& searchType != null 
				&& searchType.intValue() == 1) {
			superior = getUserByUserName(userName);
			if(superior != null) {
				buffer.append(" and  FIND_IN_SET(:superior,superior) = 1");
				
				map.put("superior", superior.getId());				
			}
			
			if(userType != null) {
				UserType agency = UserType.getStateByCode(userType);
				UserType player = UserType.getPlayerByAgency(agency);
				buffer.append(" and (userType=:agency or userType=:player)");
				map.put("agency", agency.getCode());
				map.put("player", player.getCode());
			}
			
		}else if(loginUser.getUserType() ==  UserType.SYS_ADMIN.getCode()
				&& StringUtils.isBlank(userName) ){			
			
			if(userType != null) {
				UserType agency = UserType.getStateByCode(userType);
				UserType player = UserType.getPlayerByAgency(agency);
				buffer.append(" and (userType=:agency or userType=:player)");
				map.put("agency", agency.getCode());
				map.put("player", player.getCode());
			}else {
				buffer.append(" and userType =:generalAgency ");
				map.put("generalAgency", generalAgency);
			}
		}else {
			superior = loginUser;
			buffer.append(" and FIND_IN_SET(:superior,superior) = 1 ");
			map.put("superior", superior.getId());
			
			if(!StringUtils.isBlank(userName) ) {
				buffer.append(" and userName=:userName ");
				map.put("userName", userName);
			}
			
		}
		/*if(loginUser.getUserType() ==  UserType.SYS_ADMIN.getCode()) {
			if(!StringUtils.isBlank(userName)) {
				//查用户
				if(searchType.intValue() == 0) {
					buffer.append(" and userName=:userName ");
					map.put("userName", userName);
					if(userType != null) {
						buffer.append(" and userType=:userType ");
						map.put("userType", userType);
					}
				}else {//查下级
					superior = getUserByUserName(userName);
					if(superior != null) {
						buffer.append(" and  FIND_IN_SET(:superior,superior) = 1");
						
						map.put("superior", superior.getId());
						
						if(userType != null) {
							buffer.append(" and userType=:userType ");
							map.put("userType", userType);
						}
					}
				}
			}else {
				buffer.append(" and userType =:generalAgency ");
				map.put("generalAgency", generalAgency);
			}
		}else {
			superior = loginUser; 
			if(!StringUtils.isBlank(userName)) {
				buffer.append(" and FIND_IN_SET(:superior,superior) = 1 and userName=:userName ");
				map.put("superior", superior.getId());
				map.put("userName", userName);
			}else {
				buffer.append(" and FIND_IN_SET(:superior,superior) = 1 ");
				map.put("superior", superior.getId());
			}
		}*/
		
		
		if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			buffer.append(" and createTime>=:startTime and createTime < :endTime ");
			Date beginDate = DateUtil.fmtYmdHisToDate(startTime);
		    Date endDate = DateUtil.fmtYmdHisToDate(endTime);
			map.put("startTime", beginDate);
			map.put("endTime", endDate);
		}
		
		buffer.append(" order by createTime desc");
		
		PageBean page=new PageBean();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		PageBean pageBean=queryByPagination(page,buffer.toString(),map);
		map.clear();
		map.put("data", pageBean);
		return map;
		
	}
	//通过用户Id查询用户银行卡数量
	@Override
	public long queryUserBankCount(Integer userId) {
		String sql="select count(*) from UserBankCard where userId=:userId and state=:state";
		Session session = null;
		boolean needClose = false;
		long count = 0;
				
		try {
			session = getSessionFactory().getCurrentSession();			
		}catch(HibernateException ex) {
			session = getSessionFactory().openSession();
			needClose = true;
		}finally {
			
			Query query = session.createQuery(sql);
			query.setParameter("userId", userId);
			query.setParameter("state", Constants.BankCardState.ENABLED.getCode());
			count = ((Number)query.iterate().next()).longValue();
			
			if(needClose && session != null && session.isOpen()) {
				session.close();
			}
		}
	    
	    return count;
	}

	@Override
	public long getCountUser(UserInfo user) {
		String hql = "select count(*) from UserInfo where userType=? ";
		List<Object> params = new ArrayList<>();
		params.add(user.getUserType());
		
		if(!StringUtils.isEmpty(user.getRegIp())){
			hql +=" and regIp = ?";
			params.add(user.getRegIp());
		}
		
		if(null != user.getCreateTime()){
			hql +=" and createTime >= ? and createTime <= ? ";
			params.add(DateUtil.getDateDayStart(user.getCreateTime()));
			params.add(DateUtil.getDateDayEnd(user.getCreateTime()));
		}
	    return queryCount(hql, params);
	}

	@Override
	public List<UserInfo> queryAgentByAgentHou(Integer id, String startTime, String endTime) {
		String timeSql="";
		if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			timeSql=" and create_time >=:startTime and create_time <:endTime";
		}
		Integer userType=Constants.UserType.SYS_ADMIN.getCode();
		Integer userTypea=Constants.UserType.DEMO_PLAYER.getCode();
		Integer userTypeb=Constants.UserType.GENERAL_AGENCY.getCode();
		String sql="select * from(select *,FIND_IN_SET(:id,superior) as aa from user_info where user_type !=:userType and user_type !=:userTypea and user_type !=:userTypeb "+timeSql+")a where a.aa=1";
		Query<UserInfo> query1 = getSessionFactory().getCurrentSession().createNativeQuery(sql,UserInfo.class);
	    query1.setParameter("id", id);
	    query1.setParameter("userType", userType);
	    query1.setParameter("userTypea", userTypea);
	    query1.setParameter("userTypeb", userTypeb);
	    if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
	    	Date beginDate = java.sql.Date.valueOf(startTime);
		    Date endDate = java.sql.Date.valueOf(endTime);
		    query1.setParameter("startTime", beginDate,DateType.INSTANCE);
		    query1.setParameter("endTime", endDate,DateType.INSTANCE);
		}
	    List<UserInfo> list=query1.list();
	    return list;
	}

	@Override
	public PageBean<UserInfo> querySiteMsgRec(String sql, 
			PageBean<UserInfo> page) {
		return queryByPagination(page, sql, page.getParams(), UserInfo.class);
	}

	@Override
	public Map<String, Object> queryAllAgentSMByAdmin(Integer searchType , String userName, String startTime, String endTime, Integer pageIndex,
			Integer pageSize) {
		Map<String,Object> map=new HashMap<String,Object>();
		Integer generalAgency = Constants.UserType.GENERAL_AGENCY.getCode();
		Integer smAgency = Constants.UserType.SM_AGENCY.getCode();		
		Integer smUser = Constants.UserType.SM_PLAYER.getCode();
		Integer xyAgency = Constants.UserType.XY_AGENCY.getCode();
		Integer xyUser = Constants.UserType.XY_PLAYER.getCode();
		Integer demoUser = Constants.UserType.DEMO_PLAYER.getCode();
		UserInfo superior = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("from UserInfo where 1 = 1");
		if(!StringUtils.isBlank(userName)) {
			//查用户
			if(searchType.intValue() == 0) {
				buffer.append(" and (userType =:generalAgency or userType =:smAgency or userType =:smUser) and userName=:userName ");
				map.put("generalAgency", generalAgency);
				map.put("smAgency", smAgency);
				map.put("userName", userName);
				map.put("smUser", smUser);
			}else {//查下级
				superior = getUserByUserName(userName);
				if(superior != null) {
					buffer.append(" and (userType =:generalAgency or userType =:smAgency or userType =:smUser  or userType =:xyUser or userType =:demoUser )  and FIND_IN_SET(:superior,superior) = 1");
					map.put("superior", superior.getId());
					map.put("generalAgency", generalAgency);
					map.put("smAgency", smAgency);
					map.put("smUser", smUser);
					map.put("xyUser", xyUser);
					map.put("demoUser", demoUser);
				}
			}
		}else {
			buffer.append(" and userType =:generalAgency ");
			map.put("generalAgency", generalAgency);
		}
		if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			buffer.append(" and createTime>=:startTime and createTime < :endTime ");
			Date beginDate = DateUtil.fmtYmdHisToDate(startTime);
		    Date endDate = DateUtil.fmtYmdHisToDate(endTime);
			map.put("startTime", beginDate);
			map.put("endTime", endDate);
		}
		
		buffer.append(" order by createTime desc");
		
		PageBean page=new PageBean();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		PageBean pageBean=queryByPagination(page,buffer.toString(),map);
		map.clear();
		map.put("data", pageBean);
		return map;
	}

	@Override
	public Map<String, Object> queryAllAgentSMByAgency(Integer superior, String userName, String startTime, String endTime,
			Integer pageIndex, Integer pageSize) {
		Map<String,Object> map=new HashMap<String,Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("from UserInfo where 1 = 1 ");
		if(!StringUtils.isBlank(userName)) {
			buffer.append(" and FIND_IN_SET(:superior,superior) > 0 and userName=:userName ");
			map.put("superior", superior);
			map.put("userName", userName);
		}else {
			buffer.append(" and FIND_IN_SET(:superior,superior) = 1 ");
			map.put("superior", superior);
		}
		if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			buffer.append(" and createTime>=:startTime and createTime < :endTime ");
			Date beginDate = DateUtil.fmtYmdHisToDate(startTime);
		    Date endDate = DateUtil.fmtYmdHisToDate(endTime);
			map.put("startTime", beginDate);
			map.put("endTime", endDate);
		}
		
		buffer.append(" order by createTime desc");
		
		PageBean page=new PageBean();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		PageBean pageBean=queryByPagination(page,buffer.toString(),map);
		map.clear();
		map.put("data", pageBean);
		return map;
	}

	@Override
	public PageBean<UserInfo> queryXYUsers(String sql, PageBean<UserInfo> page) {
		PageBean pageBean = queryByPagination(page, sql, null);
		
		return pageBean;
	}

	@Override
	public Map<String, Object> queryAllAgentXYByAdmin(Integer searchType, String userName, String startTime,
			String endTime, Integer pageIndex, Integer pageSize) {
		Map<String,Object> map=new HashMap<String,Object>();
		Integer generalAgency = Constants.UserType.GENERAL_AGENCY.getCode();
		Integer smAgency = Constants.UserType.SM_AGENCY.getCode();		
		Integer smUser = Constants.UserType.SM_PLAYER.getCode();
		Integer xyAgency = Constants.UserType.XY_AGENCY.getCode();
		Integer xyUser = Constants.UserType.XY_PLAYER.getCode();
		Integer demoUser = Constants.UserType.DEMO_PLAYER.getCode();
		UserInfo superior = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("from UserInfo where 1 = 1");
		if(!StringUtils.isBlank(userName)) {
			//查用户
			if(searchType.intValue() == 0) {
				buffer.append(" and (userType =:generalAgency or userType =:xyAgency or userType =:xyUser) and userName=:userName ");
				map.put("generalAgency", generalAgency);
				map.put("xyAgency", xyAgency);
				map.put("userName", userName);
				map.put("xyUser", xyUser);
			}else {//查下级
				superior = getUserByUserName(userName);
				if(superior != null) {
					buffer.append(" and (userType =:generalAgency or userType =:xyAgency or userType =:xyUser  or userType =:demoUser )  and FIND_IN_SET(:superior,superior) = 1");
					map.put("superior", superior.getId());
					map.put("generalAgency", generalAgency);
					map.put("xyAgency", xyAgency);
					map.put("xyUser", xyUser);
					map.put("demoUser", demoUser);
				}
			}
		}else {
			buffer.append(" and userType =:generalAgency ");
			map.put("generalAgency", generalAgency);
		}
		if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			buffer.append(" and createTime>=:startTime and createTime < :endTime ");
			Date beginDate = DateUtil.fmtYmdHisToDate(startTime);
		    Date endDate = DateUtil.fmtYmdHisToDate(endTime);
			map.put("startTime", beginDate);
			map.put("endTime", endDate);
		}
		
		buffer.append(" order by createTime desc");
		
		PageBean page=new PageBean();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		PageBean pageBean=queryByPagination(page,buffer.toString(),map);
		map.clear();
		map.put("data", pageBean);
		return map;
	}

	@Override
	public Map<String, Object> queryAllAgentXYByAgency(Integer superior, String userName, String startTime, String endTime,
			Integer pageIndex, Integer pageSize) {
		Map<String,Object> map=new HashMap<String,Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("from UserInfo where 1 = 1 ");
		if(!StringUtils.isBlank(userName)) {
			buffer.append(" and FIND_IN_SET(:superior,superior) > 0 and userName=:userName ");
			map.put("superior", superior);
			map.put("userName", userName);
		}else {
			buffer.append(" and FIND_IN_SET(:superior,superior) = 1 ");
			map.put("superior", superior);
		}
		if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			buffer.append(" and createTime>=:startTime and createTime < :endTime ");
			Date beginDate = DateUtil.fmtYmdHisToDate(startTime);
		    Date endDate = DateUtil.fmtYmdHisToDate(endTime);
			map.put("startTime", beginDate);
			map.put("endTime", endDate);
		}
		
		buffer.append(" order by createTime desc");
		
		PageBean page=new PageBean();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		PageBean pageBean=queryByPagination(page,buffer.toString(),map);
		map.clear();
		map.put("data", pageBean);
		return map;
	}

	@Override
	public Map<String, Object> queryAllAgentEntrustByAdmin(Integer searchType, String userName, String startTime,
			String endTime, Integer pageIndex, Integer pageSize) {
		Map<String,Object> map=new HashMap<String,Object>();
		Integer generalAgency = Constants.UserType.GENERAL_AGENCY.getCode();
		
		Integer entrustAgency = Constants.UserType.ENTRUST_AGENCY.getCode();
		Integer entrustUser = Constants.UserType.ENTRUST_PLAYER.getCode();
		Integer demoUser = Constants.UserType.DEMO_PLAYER.getCode();
		UserInfo superior = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("from UserInfo where 1 = 1");
		if(!StringUtils.isBlank(userName)) {
			//查用户
			if(searchType.intValue() == 0) {
				buffer.append(" and (userType =:generalAgency or userType =:entrustAgency or userType =:entrustUser) and userName=:userName ");
				map.put("generalAgency", generalAgency);
				map.put("entrustAgency", entrustAgency);
				map.put("userName", userName);
				map.put("entrustUser", entrustUser);
			}else {//查下级
				superior = getUserByUserName(userName);
				if(superior != null) {
					buffer.append(" and (userType =:generalAgency or userType =:entrustAgency or userType =:entrustUser  or userType =:demoUser )  and FIND_IN_SET(:superior,superior) = 1");
					map.put("superior", superior.getId());
					map.put("generalAgency", generalAgency);
					map.put("entrustAgency", entrustAgency);
					map.put("entrustUser", entrustUser);
					map.put("demoUser", demoUser);
				}
			}
		}else {
			buffer.append(" and userType =:generalAgency ");
			map.put("generalAgency", generalAgency);
		}
		if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			buffer.append(" and createTime>=:startTime and createTime < :endTime ");
			Date beginDate = DateUtil.fmtYmdHisToDate(startTime);
		    Date endDate = DateUtil.fmtYmdHisToDate(endTime);
			map.put("startTime", beginDate);
			map.put("endTime", endDate);
		}
		
		buffer.append(" order by createTime desc");
		
		PageBean page=new PageBean();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		PageBean pageBean=queryByPagination(page,buffer.toString(),map);
		map.clear();
		map.put("data", pageBean);
		return map;
	}

	@Override
	public Map<String, Object> queryAllAgentEntrustByAgency(Integer superior, String userName, String startTime,
			String endTime, Integer pageIndex, Integer pageSize) {
		Map<String,Object> map=new HashMap<String,Object>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("from UserInfo where 1 = 1 ");
		if(!StringUtils.isBlank(userName)) {
			buffer.append(" and FIND_IN_SET(:superior,superior) > 0 and userName=:userName ");
			map.put("superior", superior);
			map.put("userName", userName);
		}else {
			buffer.append(" and FIND_IN_SET(:superior,superior) = 1 ");
			map.put("superior", superior);
		}
		if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			buffer.append(" and createTime>=:startTime and createTime < :endTime ");
			Date beginDate = DateUtil.fmtYmdHisToDate(startTime);
		    Date endDate = DateUtil.fmtYmdHisToDate(endTime);
			map.put("startTime", beginDate);
			map.put("endTime", endDate);
		}
		
		buffer.append(" order by createTime desc");
		
		PageBean page=new PageBean();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		PageBean pageBean=queryByPagination(page,buffer.toString(),map);
		map.clear();
		map.put("data", pageBean);
		return map;
	}

	@Override
	public Map<String, Object> queryAllUserInfo(Integer id, String userName, Integer proxyId, String startTime,
			String endTime, Integer userType, Integer userStatus, Integer pageIndex, Integer pageSize) {

		Map<String,Object> map=new HashMap<String,Object>();
		Integer userTypeAdmin = Constants.UserType.SYS_ADMIN.getCode();
		Integer userTypeaDemo = Constants.UserType.DEMO_PLAYER.getCode();
		
		String hql="";
		StringBuffer sql = new StringBuffer();
		sql.append("from UserInfo where 1=1 ");
		
		if(proxyId!=null) {			
			sql.append("and FIND_IN_SET(:proxyId,superior) = 1 ");
			map.put("proxyId", proxyId);
			
			/*String timeSql="";
			if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
				timeSql=" and a.create_time>=:startTime and a.create_time<:endTime";
				Date beginDate = DateUtil.fmtYmdHisToDate(startTime);
			    Date endDate = DateUtil.fmtYmdHisToDate(endTime);
				map.put("startTime", beginDate);
				map.put("endTime", endDate);
			}
			hql=("select a.* from (select *,FIND_IN_SET(:proxyId,superior) as aa from user_info)a where a.aa=1 and a.user_type !=:userType and a.user_type!=:userTypea"+timeSql);
			map.put("proxyId", proxyId);
			PageBean<UserInfo> page=new PageBean<>();
			page.setPageIndex(pageIndex);
			page.setPageSize(pageSize);
			page.setPageIndex(pageIndex);
			PageBean<UserInfo> pageBean=queryBySqlClazzPagination(page,hql,map,UserInfo.class);
			map.clear();
			map.put("data", pageBean);
			return map;*/
		}/*else {
			String userNameSql="";
			String idSql="";
			if(id!=null) {
				idSql=" and id=:id";
				map.put("id", id);
			}
			if(!StringUtils.isBlank(userName)) {
				userNameSql=" and LOCATE(:userName, userName)>0";
				map.put("userName", userName);
			}
			String timeSql="";
			if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
				timeSql=" and createTime >=:startTime and createTime <:endTime";
				Date beginDate = DateUtil.fmtYmdHisToDate(startTime);
			    Date endDate = DateUtil.fmtYmdHisToDate(endTime);
				map.put("startTime", beginDate);
				map.put("endTime", endDate);
			}
			hql=("from UserInfo where userType !=:userType and userType !=:userTypea"+timeSql+idSql+userNameSql);
			PageBean page=new PageBean();
			page.setPageIndex(pageIndex);
			page.setPageSize(pageSize);
			PageBean pageBean=queryByPagination(page,hql,map);
			map.clear();
			map.put("data", pageBean);
			return map;
		}*/	
		
		if(id != null) {
			sql.append("and id=:id ");
			map.put("id", id);
		}
		
		if(userType!=null) {
			sql.append("and userType=:userType ");
			map.put("userType", userType);
		}else {
			sql.append("and userType !=:userType and userType !=:userTypea ");
			map.put("userType", userTypeAdmin);
			map.put("userTypea", userTypeaDemo);
		}
		
		if(userStatus!=null) {
			sql.append("and state=:userStatus ");
			map.put("userStatus", userStatus);
		}
		
		
		if(!StringUtils.isBlank(userName)) {
			sql.append("and LOCATE(:userName, userName)>0 ");
			map.put("userName", userName);
		}
		
		if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			sql.append("and create_time>=:startTime and create_time<:endTime ");
			Date beginDate = DateUtil.fmtYmdHisToDate(startTime);
		    Date endDate = DateUtil.fmtYmdHisToDate(endTime);
			map.put("startTime", beginDate);
			map.put("endTime", endDate);
		}
		
		PageBean page=new PageBean();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		PageBean pageBean=queryByPagination(page, sql.toString(), map);
		map.clear();
		map.put("data", pageBean);
		return map;
		
	}
}
