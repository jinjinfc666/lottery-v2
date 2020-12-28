package com.jll.user;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.entity.UserTs;

@Repository
public class UserTsDaoImpl extends DefaultGenericDaoImpl<UserTs> implements UserTsDao
{
	private Logger logger = Logger.getLogger(UserTsDaoImpl.class);
	
	@Override
	public List<UserTs> queryUserTsByUserId(Integer userId, String lotteryType) {
		String hql = "from UserTs where userId=? and lotteryType=?";
		List<Object> params = new ArrayList<>();
		params.add(userId);
		params.add(lotteryType);
		return query(hql, params, UserTs.class);
	}

	@Override
	public void saveOrUpdateUserTs(List<UserTs> userTses) {
		for(UserTs userTs : userTses){
			this.saveOrUpdate(userTs);
		}
	}

	@Override
	public UserTs queryUserTsByPlayTypeId(String userId, String lotteryType, Integer playTypeId) {
		String hql = "from UserTs where userId=? and lotteryType=? and playTypeId=?";
		List<Object> params = new ArrayList<>();
		params.add(userId);
		params.add(lotteryType);
		params.add(playTypeId);
		return queryLast(hql, params, UserTs.class);
	}
}
