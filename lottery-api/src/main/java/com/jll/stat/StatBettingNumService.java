package com.jll.stat;

import com.jll.entity.OrderInfo;
import com.jll.entity.UserAccountDetails;

public interface StatBettingNumService
{
	/**
	 * statistic bet amount group by bet num
	 */
	void exeStatistic(OrderInfo order);
	
}
