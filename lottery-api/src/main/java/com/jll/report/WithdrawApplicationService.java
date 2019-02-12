package com.jll.report;

import java.util.Date;
import java.util.Map;

import com.jll.common.constants.Constants.WithdrawOrderState;
import com.jll.entity.WithdrawApplication;

public interface WithdrawApplicationService {
	WithdrawApplication queryDetails(Integer id);
	long getUserWithdrawCount(int userId, Date start, Date end);
	public Map<String,Object> updateState(Map<String,Object> ret);
	double getUserWithdrawAmountTotal(int userId,int walletId,Date start,Date end);
	
	//判断是否存在
    boolean isNullById(Integer id);
    
	/**
	 * 查询最后一条充值申请记录
	 * @param id
	 * @param id2
	 * @param orderEnd
	 * @return
	 */
	WithdrawApplication queryLastWithdrawApplication(Integer id, Integer id2, WithdrawOrderState orderEnd);
}
