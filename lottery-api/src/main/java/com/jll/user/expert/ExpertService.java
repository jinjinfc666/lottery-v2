package com.jll.user.expert;

import java.util.Map;

import com.jll.entity.UserInfo;
import com.jll.entity.display.UserPushCache;

public interface ExpertService
{	
	Map<String, Object> produceExpertNum(String lotteryType, UserPushCache userPushCache);
	
	/**
	 * 更新专家推荐号码的次数
	 * 如果推荐次数到达配置的最大次数,那么需要循环重置
	 */
	void updateExpertPushTimes(UserInfo user, String lotteryType);

	void cacheUserPushConfig(UserInfo dbInfo);
}
