package com.jll.user;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.entity.UserInfo;

@Configuration
@PropertySource("classpath:sys-setting.properties")
@Service
@Transactional
public class UserInfoExtServiceImpl implements UserInfoExtService
{
	@Resource
	UserInfoExtDao userExtDAO;

	@Override
	public void saveUserInfoExt(UserInfo userInfo) {
		userExtDAO.saveUserExt(userInfo);
	}

	@Override
	public String queryFiledByName(Integer userId, String fieldName) {
		return userExtDAO.queryFiledByName(userId, fieldName);
	}
}
