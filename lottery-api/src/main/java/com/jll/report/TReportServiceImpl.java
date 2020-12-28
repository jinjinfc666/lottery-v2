package com.jll.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.common.constants.Constants;
import com.jll.dao.PageBean;
import com.jll.entity.PlayType;
import com.jll.entity.TeamPlReport;
import com.jll.entity.UserInfo;
import com.jll.user.UserInfoService;



@Service
@Transactional
public class TReportServiceImpl implements TReportService {
	@Resource
	TReportDao tReportDao;
	
	@Resource
	UserInfoService userServ;
	
	//团队盈亏报表
	@Override
	public PageBean queryTeamAll(Map<String, Object> ret) {
		String startTime=(String) ret.get("startTime");
		String endTime=(String) ret.get("endTime");
		String userName=(String) ret.get("userName");
		Integer pageIndex=(Integer) ret.get("pageIndex");
		Integer pageSize=(Integer) ret.get("pageSize");
		return tReportDao.queryTeamAll(startTime, endTime, userName,pageIndex,pageSize);
	}
	//查找下级
	@Override
	public Map<String,Object> queryNextTeamAll(Map<String, Object> ret) {
		String startTime=(String) ret.get("startTime");
		String endTime=(String) ret.get("endTime");
		String userName=(String) ret.get("userName");
		
		UserInfo userInfo = userServ.getUserByUserName(userName);
		
		return tReportDao.queryNextTeamAll(startTime, endTime, userInfo);
	}
	
	@Override
	public TeamPlReport queryProfitByUser(Integer userId, Date createTime, Integer userType, String lotteryType, PlayType playType) {		
		return tReportDao.queryProfitByUser(userId, createTime, userType, lotteryType, playType);
	}
	
	@Override
	public void saveOrUpdateProfit(TeamPlReport profit) {
		tReportDao.saveOrUpdateProfit(profit);
	}
	
	@Override
	public Map<String, Object> queryNextTeamAllSM(Map<String, Object> ret) {
		String startTime=(String) ret.get("startTime");
		String endTime=(String) ret.get("endTime");
		String userName=(String) ret.get("userName");
		
		UserInfo userInfo = userServ.getUserByUserName(userName);
		
		return tReportDao.queryNextTeamAllSm(startTime, endTime, userInfo);
	}
	
	//团队盈亏报表
		@Override
		public Map<String,Object> queryDailySettlement(Map<String, Object> ret) {
			String startTime=(String) ret.get("startTime");
			String endTime=(String) ret.get("endTime");
			String userName=(String) ret.get("userName");
			Integer pageIndex=(Integer) ret.get("pageIndex");
			Integer pageSize=(Integer) ret.get("pageSize");
			
			UserInfo userInfo = userServ.getUserByUserName(userName);
			if(userInfo == null){
				return null;
			}
			return tReportDao.queryDailySettlement(startTime, endTime, userInfo,pageIndex,pageSize);
		}
		@Override
		public PageBean queryDailySettlementByUser(Map<String, Object> ret) {
			String startTime=(String) ret.get("startTime");
			String endTime=(String) ret.get("endTime");
			String userName=(String) ret.get("userName");
			String settlementFlag=(String) ret.get("settlementFlag");
			Integer pageIndex=(Integer) ret.get("pageIndex");
			Integer pageSize=(Integer) ret.get("pageSize");
			return tReportDao.queryDailySettlementByUser(startTime, endTime, userName,settlementFlag, pageIndex,pageSize);
		}
		@Override
		public void updateSettlement(Map<String, Object> ret) {
			Integer id = (Integer)ret.get("id");
			Map<String, Object> params = new HashMap<>();
			TeamPlReport report = tReportDao.queryDailySettlementById(id);
			report.setSettlementFlag(Constants.SettlementState.HANDLED.getCode());
			tReportDao.saveOrUpdateProfit(report);
		}
}
