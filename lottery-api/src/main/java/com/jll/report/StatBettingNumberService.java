package com.jll.report;

import java.util.Map;

import com.jll.dao.PageBean;
import com.jll.entity.StatBettingNumber;


public interface StatBettingNumberService {
	public PageBean queryStatBetNum(Map<String, Object> ret);

	public StatBettingNumber queryStatByNumber(String lotteryType, Integer issueId, String betNum);

	public void saveOrUpdateStat(StatBettingNumber stat);
}
