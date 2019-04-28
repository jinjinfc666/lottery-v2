package com.jll.user.data;

import com.jll.dao.PageBean;
import com.jll.entity.OldUserCard;
import com.jll.entity.TbUser;

public interface DataMigrateDao
{
	
	TbUser getUserByUserName(String userName);

	PageBean<Object[]> queryUserByPage(PageBean<Object[]> page);

	PageBean<OldUserCard> queryUserCardByPagination(PageBean<OldUserCard> page);
}
