package com.jll.report;

import java.util.Date;
import java.util.Map;

import com.jll.dao.PageBean;
import com.jll.entity.TeamPlReport;

public interface TReportService {

	//团队盈亏报表
	public PageBean queryTeamAll(Map<String, Object> ret);
	//查找下级
	public Map<String,Object> queryNextTeamAll(Map<String, Object> ret);
	
	public TeamPlReport queryProfitByUser(Integer userId, Date createTime, Integer userType);
	
	public void saveOrUpdateProfit(TeamPlReport profit);
	
	public Map<String, Object> queryNextTeamAllSM(Map<String, Object> ret);
}
