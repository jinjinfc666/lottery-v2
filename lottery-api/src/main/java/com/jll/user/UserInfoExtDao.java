package com.jll.user;

import java.util.List;
import java.util.Map;

import com.jll.dao.PageBean;
import com.jll.entity.SiteMessage;
import com.jll.entity.UserInfo;

public interface UserInfoExtDao
{	
	void saveUserExt(UserInfo user);

	String queryFiledByName(Integer userId, String fieldName);
}
