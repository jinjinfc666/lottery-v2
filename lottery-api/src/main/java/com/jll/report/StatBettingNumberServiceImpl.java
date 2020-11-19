package com.jll.report;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.dao.PageBean;
import com.jll.entity.StatBettingNumber;



@Service
@Transactional
public class StatBettingNumberServiceImpl implements StatBettingNumberService {
	@Resource
	StatBettingNumberDao statBettingNumberDao;

	@Override
	public PageBean queryStatBetNum(Map<String, Object> ret) {
		Integer pageIndex=(Integer) ret.get("pageIndex");
		Integer pageSize=(Integer) ret.get("pageSize");
		String lotteryType = (String) ret.get("lotteryType");
		return statBettingNumberDao.queryStatBetNum(pageIndex,pageSize, lotteryType);
	}

	@Override
	public StatBettingNumber queryStatByNumber(String lotteryType, Integer issueId, String betNum) {
		return statBettingNumberDao.queryStatByNumber(lotteryType, issueId, betNum);
	}

	@Override
	public void saveOrUpdateStat(StatBettingNumber stat) {
		statBettingNumberDao.saveOrUpdateStat(stat);
	}
	
}
