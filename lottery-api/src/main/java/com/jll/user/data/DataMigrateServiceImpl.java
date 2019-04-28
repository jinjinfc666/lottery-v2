package com.jll.user.data;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.dao.PageBean;
import com.jll.entity.OldUserCard;
import com.jll.entity.TbUser;
import com.jll.user.UserInfoDao;

@Configuration
@PropertySource("classpath:sys-setting.properties")
@Service
@Transactional
public class DataMigrateServiceImpl implements DataMigrateService
{
	private Logger logger = Logger.getLogger(DataMigrateServiceImpl.class);

	@Resource
	DataMigrateDao dataDao;
	
	@Override
	public PageBean<Object[]> queryTbUserByPagination(PageBean<Object[]> page) {		
		PageBean<Object[]> page_ = dataDao.queryUserByPage(page);
		return page_;
	}

	@Override
	public TbUser queryTbuserByUsername(String userName) {
		return dataDao.getUserByUserName(userName);
	}

	@Override
	public PageBean<OldUserCard> queryUserCardByPagination(PageBean<OldUserCard> page) {
		return dataDao.queryUserCardByPagination(page);
	}
	
}
