package com.jll.report;

import java.util.Date;

import com.jll.common.constants.Constants.WithdrawOrderState;
import com.jll.entity.WithdrawApplication;

public interface WithdrawApplicationDao {
	public WithdrawApplication queryDetails(Integer id);
	long getUserWithdrawCount(int userId, Date start, Date end);
	public void updateState(Integer id,Integer state,String remark);
	public double getUserWithdrawAmountTotal(int userId, int walletId,Date start, Date end);
	public WithdrawApplication queryUserWithdrawAmountTotal(Integer userId, 
			Integer walletId,
			WithdrawOrderState orderEnd);
}
