package com.jll.user.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jll.dao.PageBean;
import com.jll.dao.PageQueryDao;
import com.jll.entity.OldUserCard;
import com.jll.entity.SiteMessFeedback;
import com.jll.entity.SiteMessage;
import com.jll.entity.TbUser;
import com.jll.entity.UserAccount;
import com.jll.entity.UserAccountDetails;
import com.jll.entity.UserBankCard;
import com.jll.entity.UserInfo;
import com.jll.entity.WithdrawApplication;

public interface DataMigrateService
{	
	PageBean<Object[]> queryTbUserByPagination(PageBean<Object[]> page);

	TbUser queryTbuserByUsername(String userName);

	PageBean<OldUserCard> queryUserCardByPagination(PageBean<OldUserCard> page);
}
