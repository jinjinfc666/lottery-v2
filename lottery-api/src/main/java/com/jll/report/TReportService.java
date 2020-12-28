package com.jll.report;

import java.util.Date;
import java.util.Map;

import com.jll.dao.PageBean;
import com.jll.entity.PlayType;
import com.jll.entity.TeamPlReport;

public interface TReportService {

	//团队盈亏报表
	public PageBean queryTeamAll(Map<String, Object> ret);
	//查找下级
	public Map<String,Object> queryNextTeamAll(Map<String, Object> ret);
	
	public TeamPlReport queryProfitByUser(Integer userId, Date createTime, Integer userType, String lotteryType, PlayType playType);
	
	public void saveOrUpdateProfit(TeamPlReport profit);
	
	public Map<String, Object> queryNextTeamAllSM(Map<String, Object> ret);
	
	//团队盈亏报表
	public Map<String,Object> queryDailySettlement(Map<String, Object> ret);
	
	public PageBean<TeamPlReport> queryDailySettlementByUser(Map<String, Object> ret);
	
	public void updateSettlement(Map<String, Object> ret);
}
