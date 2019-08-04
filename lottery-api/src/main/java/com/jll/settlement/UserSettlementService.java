package com.jll.settlement;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jll.dao.PageBean;
import com.jll.entity.UserInfo;
import com.jll.entity.UserSettlement;

public interface UserSettlementService
{	
	void saveSettlement(UserSettlement settlement);

	PageBean<UserSettlement> queryUserSettlement(Integer pageIndex, Integer pageSize, String userName, Integer setStatus, String startTime, String endTime);

	void performUserSettlement(Integer settlementId);

	boolean isSettleMentExisting(Integer settlementId);

	boolean isPendingExisting(UserInfo user);
	
	
}
