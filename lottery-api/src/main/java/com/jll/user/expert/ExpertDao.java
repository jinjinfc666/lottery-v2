package com.jll.user.expert;

import java.util.List;
import java.util.Map;

import com.jll.dao.PageBean;
import com.jll.entity.SiteMessage;
import com.jll.entity.UserInfo;
import com.jll.entity.UserPushConfig;

public interface ExpertDao
{
		
	List<UserPushConfig> queryUserPushConfigs(UserInfo user);
}
