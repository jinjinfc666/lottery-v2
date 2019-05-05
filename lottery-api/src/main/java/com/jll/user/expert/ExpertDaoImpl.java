package com.jll.user.expert;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.entity.UserInfo;
import com.jll.entity.UserPushConfig;

@Repository
public class ExpertDaoImpl extends DefaultGenericDaoImpl<UserInfo> implements ExpertDao
{
	private Logger logger = Logger.getLogger(ExpertDaoImpl.class);

	@Override
	public List<UserPushConfig> queryUserPushConfigs(UserInfo user) {
		List<UserPushConfig> userPushConfigs = null;
		String sql = "from UserPushConfig where userId=?";
	    Query<UserPushConfig> query = getSessionFactory().getCurrentSession().createQuery(sql);
	    query.setParameter(0, user.getId());
	    userPushConfigs = query.list();
	    return userPushConfigs;
	}

 
}
