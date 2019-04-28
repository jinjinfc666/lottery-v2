package com.jll.user.data;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.type.DateType;
import org.springframework.stereotype.Repository;

import com.jll.common.constants.Constants;
import com.jll.common.constants.Constants.UserType;
import com.jll.common.utils.DateUtil;
import com.jll.common.utils.StringUtils;
import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.dao.PageBean;
import com.jll.entity.OldUserCard;
import com.jll.entity.TbUser;
import com.jll.entity.UserInfo;

@Repository
public class DataMigrateDaoImpl extends DefaultGenericDaoImpl<TbUser> implements DataMigrateDao
{
	private Logger logger = Logger.getLogger(DataMigrateDaoImpl.class);

	@Override
	public TbUser getUserByUserName(String userName) {
		String sql = "from TbUser where userName = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(userName);
		
		List<TbUser> rets = query(sql, params, TbUser.class);
		
		return rets.size() > 0?rets.get(0) : null;
	}

	@Override
	public PageBean queryUserByPage(PageBean page) {
		String HQL = "from TbUser user left join UserRebate rebate on user.userName= rebate.userName order by user.id";
		Map<String, Object> params = new HashMap<>();
		
		return queryByPagination(page, HQL, params);
	}

	@Override
	public PageBean<OldUserCard> queryUserCardByPagination(PageBean<OldUserCard> page) {
		String HQL = "from OldUserCard order by id";
		Map<String, Object> params = new HashMap<>();
		
		return queryByPagination(page, HQL, params);
	}
	
}
