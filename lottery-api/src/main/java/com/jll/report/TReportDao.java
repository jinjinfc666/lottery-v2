package com.jll.report;

import java.util.Date;
import java.util.Map;

import com.jll.dao.PageBean;
import com.jll.entity.TeamPlReport;
import com.jll.entity.UserInfo;

public interface TReportDao {
	//团队盈亏报表
	public PageBean queryTeamAll(String startTime, String endTime, String userName,Integer pageIndex,Integer pageSize);
	//查找下级代理
	public Map<String,Object> queryNextTeamAll(String startTime, String endTime, UserInfo user);
	
	public void saveOrUpdateProfit(TeamPlReport profit);
	
	public TeamPlReport queryProfitByUser(Integer userId, 
			Date createTime, 
			Integer userType);
	
	public Map<String, Object> queryNextTeamAllSm(String startTime, 
			String endTime, 
			UserInfo userInfo);
	
	public Map<String,Object> queryDailySettlement(String startTime, String endTime, UserInfo userInfo,Integer pageIndex,Integer pageSize);
	
	public PageBean queryDailySettlementByUser(String startTime, String endTime, String userName, String settlementFlag,
			Integer pageIndex, Integer pageSize);
	
	public TeamPlReport queryDailySettlementById(Integer id);
	
}

