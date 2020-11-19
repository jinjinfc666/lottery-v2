package com.jll.report;

import com.jll.dao.PageBean;
import com.jll.entity.StatBettingNumber;

public interface StatBettingNumberDao {

	PageBean queryStatBetNum(Integer pageIndex, Integer pageSize, String lotteryType);

	StatBettingNumber queryStatByNumber(String lotteryType, Integer issueId, String betNum);

	void saveOrUpdateStat(StatBettingNumber stat);

}
