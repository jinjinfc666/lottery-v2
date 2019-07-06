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
import com.jll.entity.OrderInfoExt;
import com.jll.entity.UserInfo;
import com.jll.entity.UserInfoExt;

@Repository
public class UserInfoExtDaoImpl extends DefaultGenericDaoImpl<UserInfoExt> implements UserInfoExtDao
{
	private Logger logger = Logger.getLogger(UserInfoExtDaoImpl.class);

	

	@Override
	public void saveUserExt(UserInfo user) {
		Date today = new Date();
		UserInfoExt ext = new UserInfoExt();
		ext.setUserId(user.getId());
		ext.setExtFieldName("panKou");
		ext.setExtFieldVal(String.valueOf(user.getPanKou()));
		ext.setCreateTime(today);
		
		this.saveOrUpdate(ext);
		
		ext = new UserInfoExt();
		ext.setUserId(user.getId());
		ext.setExtFieldName("xyAmount");
		ext.setExtFieldVal(String.valueOf(user.getXyAmount()));
		ext.setCreateTime(today);
			
		this.saveOrUpdate(ext);
		
		ext = new UserInfoExt();
		ext.setUserId(user.getId());
		ext.setExtFieldName("xyPayoutRate");
		ext.setExtFieldVal(String.valueOf(user.getXyPayoutRate()));
		ext.setCreateTime(today);
			
		this.saveOrUpdate(ext);
	}



	@Override
	public String queryFiledByName(Integer userId, String fieldName) {
		String sql = "from UserInfoExt where userId=? and extFieldName=?";
		List<Object> params = new ArrayList<>();
		params.add(userId);
		params.add(fieldName);
		
		List<UserInfoExt> exts = this.query(sql, params, UserInfoExt.class);
		if(exts != null && exts.size() > 0) {
			return exts.get(0).getExtFieldVal();
		}else {
			return null;
		}
	}

 
	
  
}
