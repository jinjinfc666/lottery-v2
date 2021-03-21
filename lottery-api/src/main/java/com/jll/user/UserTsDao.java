package com.jll.user;

import java.util.List;

import com.jll.entity.UserTs;

public interface UserTsDao
{

	List<UserTs> queryUserTsByUserId(Integer userId, String lotteryType);

	void saveOrUpdateUserTs(List<UserTs> userTses);

	UserTs queryUserTsByPlayTypeId(Integer userId, String lotteryType, Integer playTypeId);
}
