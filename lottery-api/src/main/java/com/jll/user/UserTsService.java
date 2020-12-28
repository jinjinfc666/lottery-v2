package com.jll.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jll.dao.PageBean;
import com.jll.dao.PageQueryDao;
import com.jll.entity.SiteMessFeedback;
import com.jll.entity.SiteMessage;
import com.jll.entity.TbUser;
import com.jll.entity.UserAccount;
import com.jll.entity.UserAccountDetails;
import com.jll.entity.UserBankCard;
import com.jll.entity.UserInfo;
import com.jll.entity.UserTs;
import com.jll.entity.WithdrawApplication;

public interface UserTsService
{

	Map<String, Object> queryUserTs(String userName, String lotteryType);

	Map<String, Object> saveOrUpdateUserTs(List<UserTs> userTses);

	UserTs queryUserTsByPlayTypeId(String userId, String lotteryType, Integer playTypeId);
}
