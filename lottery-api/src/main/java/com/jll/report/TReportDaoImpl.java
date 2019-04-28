package com.jll.report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.DateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.jll.common.constants.Constants;
import com.jll.common.constants.Message;
import com.jll.common.utils.DateUtil;
import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.dao.PageBean;
import com.jll.entity.MemberPlReport;
import com.jll.entity.TeamPlReport;
import com.jll.entity.UserInfo;
import com.jll.user.UserInfoServiceImpl;

@Repository
public class TReportDaoImpl extends DefaultGenericDaoImpl<TeamPlReport> implements TReportDao {
	//团队盈亏报表
	@Override
	public PageBean queryTeamAll(String startTime, String endTime, String userName,Integer pageIndex,Integer pageSize) {
		String userNameSql="";
		String timeSql="";
		Map<String,Object> map=new HashMap<String,Object>();
		if(!StringUtils.isBlank(userName)) {
			userNameSql=" and user_name=:userName";
			map.put("userName", userName);
		}else {
			userNameSql=" and  user_type=:userName";
			map.put("userName", Constants.UserType.AGENCY.getCode());
		}
		if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			timeSql="where create_time >=:startTime and create_time <=:endTime";
			Date beginDate = DateUtil.fmtYmdToDate(startTime);
		    Date endDate = DateUtil.fmtYmdToDate(endTime);
			map.put("startTime", beginDate);
			map.put("endTime", endDate);
		}
		String sql=null;
		sql="select user_name,SUM(deposit) as deposit,SUM(withdrawal) as withdrawal,SUM(transfer) as transfer ,SUM(transfer_out) as transfer_out,SUM(deduction) as deduction,sum(consumption) as consumption,SUM(cancel_amount) as cancel_amount,SUM(return_prize) as return_prize,SUM(rebate) as rebate,SUM(recharge_member) AS recharge_member,SUM(new_members) as new_members,sum(profit) as profit,user_type from team_pl_report "+timeSql+userNameSql+" GROUP BY user_name,user_type";
		PageBean page=new PageBean();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		PageBean pageBean=queryBySqlPagination(page, sql,map);
		return pageBean;
	}
	//查找下级代理
	@Override
	public Map<String,Object> queryNextTeamAll(String startTime, String endTime, UserInfo user) {
		Map<String,Object> map = new HashMap<String,Object>();
		TeamPlReport selfProfit = querySelfProfit(startTime, endTime, user);
		List<TeamPlReport> nextAgencyLevelProfit = queryAgencyNextLevelProfit(startTime, endTime, user);
		List<TeamPlReport> nextUserLevelProfit = queryUserNextLevelProfit(startTime, endTime, user);
		
		if(nextAgencyLevelProfit == null) {
			nextAgencyLevelProfit = new ArrayList<>();
		}
		
		
		if(nextUserLevelProfit != null && nextUserLevelProfit.size() > 0) {
			/*for(TeamPlReport report : nextUserLevelProfit) {
				nextAgencyLevelProfit.add(0, report);
			}*/
			for(int i = nextUserLevelProfit.size() - 1;i >= 0; i--) {
				nextAgencyLevelProfit.add(0, nextUserLevelProfit.get(i));
			}
		}
		
		if(selfProfit != null) {
			nextAgencyLevelProfit.add(0, selfProfit);
		}
		
		if(nextAgencyLevelProfit.size() > 0) {
			String userName = nextAgencyLevelProfit.get(0).getUserName();
			userName = userName.replace("|------", "");
			nextAgencyLevelProfit.get(0).setUserName(userName);
			
		}
		map.put("data", nextAgencyLevelProfit);
		map.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		return map;
	}
	
	private List<TeamPlReport> queryAgencyNextLevelProfit(String startTime, String endTime, UserInfo user) {
		Integer id = user.getId();
		StringBuffer sqlBuffer = new StringBuffer();
		PageBean<Object[]> page = new PageBean<>();
		List<Object> params = new ArrayList<>();
		
		page.setPageIndex(0);
		page.setPageSize(100000);		
		
		sqlBuffer.append("select CONCAT('',userInfo.user_name) user_name, t.* from ")
		.append("(")
		.append("select ")
		.append("user_id,")
		.append("SUM(deposit) as deposit,")
		.append("SUM(withdrawal) as withdrawal, ")
		.append("SUM(transfer) as transfer ,")
		.append("SUM(transfer_out) as transfer_out,")
		.append("SUM(deduction) as deduction,")
		.append("SUM(consumption) as consumption,")
		.append("SUM(cancel_amount) as cancel_amount,")
		.append("SUM(return_prize) as return_prize,")
		.append("SUM(rebate) as rebate,")
		.append("SUM(recharge_member) as recharge_member,")
		.append("SUM(new_members) as new_members,")
		.append("SUM(profit) as profit ")
		//.append("user_type ")
		.append("from team_pl_report ")
		.append("where user_id in (select id from user_info where FIND_IN_SET(?,superior) = 1 and user_type = 1) and user_type = 1 ")
		//.append(" or ")
		//.append("(user_id in (select id from user_info where FIND_IN_SET(?,superior) = 1 and user_type = 0) and user_type = 0) ")
		//.append(" or ")
		//.append("(user_id = ? and user_type = 0)) ")
		.append("and create_time >= ? and create_time <= ? ")
		.append("group by user_id ")
		
		.append(")t ")
		.append("left join ")
		.append("user_info userInfo on t.user_id = userInfo.id ")
		.append("order by t.user_id ");
		
	    params.add(id);
	    /*params.add(id);
	    params.add(id);*/
	    Date beginDate = DateUtil.fmtYmdToDate(startTime);
	    Date endDate = DateUtil.fmtYmdToDate(endTime);
	    params.add(beginDate);
	    params.add(endDate);
	    
    	List<?> memberPlReportList=null;
    	
    	memberPlReportList = queryNativeSQL(sqlBuffer.toString(), params);
	    Iterator<?> it=memberPlReportList.iterator();
	    List<TeamPlReport> listRecord=new ArrayList<TeamPlReport>();
		while(it.hasNext()) {
			TeamPlReport m=new TeamPlReport();
			Object[] obj=(Object[]) it.next();
			m.setUserName((String)obj[0]);
			BigDecimal bd1 = (BigDecimal) obj[2];
			m.setDeposit(bd1);
			BigDecimal bd2 = (BigDecimal) obj[3];
			m.setWithdrawal(bd2);
			BigDecimal bd3 = (BigDecimal) obj[4];
			m.setTransfer(bd3);
			BigDecimal bd4 = (BigDecimal) obj[5];
			m.setTransferOut(bd4);
			BigDecimal bd5 = (BigDecimal) obj[6];
			m.setDeduction(bd5);
			BigDecimal bd6 = (BigDecimal) obj[7];
			m.setConsumption(bd6);
			BigDecimal bd7 = (BigDecimal) obj[8];
			m.setCancelAmount(bd7);
			BigDecimal bd8 = (BigDecimal) obj[9];
			m.setReturnPrize(bd8);
			BigDecimal bd9 = (BigDecimal) obj[10];
			m.setRebate(bd9);
			BigDecimal bd10=(BigDecimal)obj[11];
			//bd10 = bd10 == null?null:bd10;
			m.setRechargeMember(bd10 == null?null:bd10.intValue());
			BigDecimal bd11=(BigDecimal)obj[12];
			//bd11 = bd11 == null?null:bd11;
			m.setNewMembers(bd11 == null?null:bd11.intValue());
			BigDecimal bd12 = (BigDecimal) obj[13];
			m.setProfit(bd12);
			//m.setUserType((Integer)obj[14]);
		    listRecord.add(m);
		}
		return listRecord;
	}
	
	
	private TeamPlReport querySelfProfit(String startTime, String endTime, UserInfo user) {
		Integer id = user.getId();
		StringBuffer sqlBuffer = new StringBuffer();
		PageBean<Object[]> page = new PageBean<>();
		List<Object> params = new ArrayList<>();
		List<TeamPlReport> listRecord = new ArrayList<TeamPlReport>();
		List<?> memberPlReportList = null;
		
		page.setPageIndex(0);
		page.setPageSize(100000);		
		
		sqlBuffer.append("select userInfo.user_name, t.* from ")
		.append("(")
		.append("select ")
		.append("user_id,")
		.append("SUM(deposit) as deposit,")
		.append("SUM(withdrawal) as withdrawal, ")
		.append("SUM(transfer) as transfer ,")
		.append("SUM(transfer_out) as transfer_out,")
		.append("SUM(deduction) as deduction,")
		.append("SUM(consumption) as consumption,")
		.append("SUM(cancel_amount) as cancel_amount,")
		.append("SUM(return_prize) as return_prize,")
		.append("SUM(rebate) as rebate,")
		.append("SUM(recharge_member) as recharge_member,")
		.append("SUM(new_members) as new_members,")
		.append("SUM(profit) as profit,")
		.append("user_type ")
		.append("from team_pl_report ")
		.append("where 1=1 ")
		.append("and user_type = 1 and user_id = ? ")
		.append("and create_time >= ? and create_time <= ? ")
		//.append("group by user_id ")
		//.append("order by user_id ")
		.append(")t ")
		.append("left join ")
		.append("user_info userInfo on t.user_id = userInfo.id");
		
	    params.add(id);
	    //params.add(id);
	    Date beginDate = DateUtil.fmtYmdToDate(startTime);
	    Date endDate = DateUtil.fmtYmdToDate(endTime);
	    params.add(beginDate);
	    params.add(endDate);    	
    	
    	memberPlReportList = queryNativeSQL(sqlBuffer.toString(), params);
	    Iterator<?> it=memberPlReportList.iterator();
	    
		while(it.hasNext()) {
			TeamPlReport m=new TeamPlReport();
			Object[] obj=(Object[]) it.next();
			m.setUserName((String)obj[0]);
			BigDecimal bd1 = (BigDecimal) obj[2];
			m.setDeposit(bd1);
			BigDecimal bd2 = (BigDecimal) obj[3];
			m.setWithdrawal(bd2);
			BigDecimal bd3 = (BigDecimal) obj[4];
			m.setTransfer(bd3);
			BigDecimal bd4 = (BigDecimal) obj[5];
			m.setTransferOut(bd4);
			BigDecimal bd5 = (BigDecimal) obj[6];
			m.setDeduction(bd5);
			BigDecimal bd6 = (BigDecimal) obj[7];
			m.setConsumption(bd6);
			BigDecimal bd7 = (BigDecimal) obj[8];
			m.setCancelAmount(bd7);
			BigDecimal bd8 = (BigDecimal) obj[9];
			m.setReturnPrize(bd8);
			BigDecimal bd9 = (BigDecimal) obj[10];
			m.setRebate(bd9);
			BigDecimal bd10=(BigDecimal)obj[11];
			//bd10 = bd10 == null?null:bd10;
			m.setRechargeMember(bd10 == null?null:bd10.intValue());
			BigDecimal bd11=(BigDecimal)obj[12];
			//bd11 = bd11 == null?null:bd11;
			m.setNewMembers(bd11 == null?null:bd11.intValue());
			BigDecimal bd12 = (BigDecimal) obj[13];
			m.setProfit(bd12);
			m.setUserType((Integer)obj[14]);
			
			if(m.getUserId() == null) {
				continue;
			}
			
		    listRecord.add(m);
		}
		
		if(listRecord != null && listRecord.size() > 0) {
			return listRecord.get(0);
		}
		
		return null;
	}
	
	private List<TeamPlReport> queryUserNextLevelProfit(String startTime, String endTime, UserInfo user) {
		Integer id = user.getId();
		StringBuffer sqlBuffer = new StringBuffer();
		PageBean<Object[]> page = new PageBean<>();
		List<Object> params = new ArrayList<>();
		
		page.setPageIndex(0);
		page.setPageSize(100000);		
		
		sqlBuffer.append("select CONCAT('',userInfo.user_name) user_name, t.* from ")
		.append("(")
		.append("select ")
		.append("user_id,")
		.append("SUM(deposit) as deposit,")
		.append("SUM(withdrawal) as withdrawal, ")
		.append("SUM(transfer) as transfer ,")
		.append("SUM(transfer_out) as transfer_out,")
		.append("SUM(deduction) as deduction,")
		.append("SUM(consumption) as consumption,")
		.append("SUM(cancel_amount) as cancel_amount,")
		.append("SUM(return_prize) as return_prize,")
		.append("SUM(rebate) as rebate,")
		.append("SUM(recharge_member) as recharge_member,")
		.append("SUM(new_members) as new_members,")
		.append("SUM(profit)*(-1) as profit ")
		.append("from team_pl_report ")
		.append("where ( ")
		.append("(user_id in (select id from user_info where FIND_IN_SET(?,superior) = 1 and user_type = 0) and user_type = 0) ")
		.append(" or ")
		.append("(user_id = ? and user_type = 0) ")
		.append(") ")
		.append("and create_time >= ? and create_time <= ? ")
		.append("group by user_id ")
		
		.append(")t ")
		.append("left join ")
		.append("user_info userInfo on t.user_id = userInfo.id ")
		.append("order by t.user_id ");
		
	    params.add(id);
	    params.add(id);
	    //params.add(id);
	    Date beginDate = DateUtil.fmtYmdToDate(startTime);
	    Date endDate = DateUtil.fmtYmdToDate(endTime);
	    params.add(beginDate);
	    params.add(endDate);
	    
    	List<?> memberPlReportList=null;
    	
    	memberPlReportList = queryNativeSQL(sqlBuffer.toString(), params);
	    Iterator<?> it=memberPlReportList.iterator();
	    List<TeamPlReport> listRecord=new ArrayList<TeamPlReport>();
		while(it.hasNext()) {
			TeamPlReport m=new TeamPlReport();
			Object[] obj=(Object[]) it.next();
			m.setUserName((String)obj[0]);
			BigDecimal bd1 = (BigDecimal) obj[2];
			m.setDeposit(bd1);
			BigDecimal bd2 = (BigDecimal) obj[3];
			m.setWithdrawal(bd2);
			BigDecimal bd3 = (BigDecimal) obj[4];
			m.setTransfer(bd3);
			BigDecimal bd4 = (BigDecimal) obj[5];
			m.setTransferOut(bd4);
			BigDecimal bd5 = (BigDecimal) obj[6];
			m.setDeduction(bd5);
			BigDecimal bd6 = (BigDecimal) obj[7];
			m.setConsumption(bd6);
			BigDecimal bd7 = (BigDecimal) obj[8];
			m.setCancelAmount(bd7);
			BigDecimal bd8 = (BigDecimal) obj[9];
			m.setReturnPrize(bd8);
			BigDecimal bd9 = (BigDecimal) obj[10];
			m.setRebate(bd9);
			BigDecimal bd10=(BigDecimal)obj[11];
			//bd10 = bd10 == null?null:bd10;
			m.setRechargeMember(bd10 == null?null:bd10.intValue());
			BigDecimal bd11=(BigDecimal)obj[12];
			//bd11 = bd11 == null?null:bd11;
			m.setNewMembers(bd11 == null?null:bd11.intValue());
			BigDecimal bd12 = (BigDecimal) obj[13];
			m.setProfit(bd12);
			//m.setUserType((Integer)obj[14]);
		    listRecord.add(m);
		}
		return listRecord;
	}
	
	
	
	@Override
	public void saveOrUpdateProfit(TeamPlReport profit) {
		this.saveOrUpdate(profit);
	}
	
	@Override
	public TeamPlReport queryProfitByUser(Integer userId, Date createTime, Integer userType) {
		String sql = "from TeamPlReport t where t.userId=? and t.createTime=? and t.userType=?";
		List<Object> params = new ArrayList<>();
		TeamPlReport profit = null;
		List<TeamPlReport> profits = null;
		
		params.add(userId);
		params.add(createTime);
		params.add(userType);
		
		profits = query(sql, params, TeamPlReport.class);
		
		if(profits == null || profits.size() == 0) {
			return profit;
		}
		
		profit = profits.get(0);
		
		return profit;
	}
	
	@Override
	public Map<String, Object> queryNextTeamAllSm(String startTime, String endTime, UserInfo userInfo) {
		Map<String,Object> map = new HashMap<String,Object>();
		TeamPlReport selfProfit = querySelfProfitSm(startTime, endTime, userInfo);
		List<TeamPlReport> nextAgencyLevelProfit = queryAgencyNextLevelProfitSm(startTime, endTime, userInfo);
		List<TeamPlReport> nextUserLevelProfit = queryUserNextLevelProfitSm(startTime, endTime, userInfo);
		
		if(nextAgencyLevelProfit == null) {
			nextAgencyLevelProfit = new ArrayList<>();
		}
		
		
		if(nextUserLevelProfit != null && nextUserLevelProfit.size() > 0) {
			/*for(TeamPlReport report : nextUserLevelProfit) {
				nextAgencyLevelProfit.add(0, report);
			}*/
			for(int i = nextUserLevelProfit.size() - 1;i >= 0; i--) {
				nextAgencyLevelProfit.add(0, nextUserLevelProfit.get(i));
			}
		}
		
		if(selfProfit != null) {
			nextAgencyLevelProfit.add(0, selfProfit);
		}
		
		if(nextAgencyLevelProfit.size() > 0) {
			String userName = nextAgencyLevelProfit.get(0).getUserName();
			userName = userName.replace("|------", "");
			nextAgencyLevelProfit.get(0).setUserName(userName);
			
		}
		map.put("data", nextAgencyLevelProfit);
		map.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		return map;
	}
	
	private TeamPlReport querySelfProfitSm(String startTime, String endTime, UserInfo userInfo) {
		Integer id = userInfo.getId();
		StringBuffer sqlBuffer = new StringBuffer();
		PageBean<Object[]> page = new PageBean<>();
		List<Object> params = new ArrayList<>();
		List<TeamPlReport> listRecord = new ArrayList<TeamPlReport>();
		List<?> memberPlReportList = null;
		
		page.setPageIndex(0);
		page.setPageSize(100000);		
		
		sqlBuffer.append("select userInfo.user_name, t.* from ")
		.append("(")
		.append("select ")
		.append("user_id,")
		.append("SUM(deposit) as deposit,")
		.append("SUM(withdrawal) as withdrawal, ")
		.append("SUM(transfer) as transfer ,")
		.append("SUM(transfer_out) as transfer_out,")
		.append("SUM(deduction) as deduction,")
		.append("SUM(consumption) as consumption,")
		.append("SUM(cancel_amount) as cancel_amount,")
		.append("SUM(return_prize) as return_prize,")
		.append("SUM(rebate) as rebate,")
		.append("SUM(recharge_member) as recharge_member,")
		.append("SUM(new_members) as new_members,")
		.append("SUM(profit) as profit, ")
		.append("? user_type ")
		.append("from team_pl_report ")
		.append("where 1=1 ")
		.append("and (user_type = 3 or user_type = 6) and user_id = ? ")
		.append("and create_time >= ? and create_time <= ? ")
		//.append("group by user_id ")
		//.append("order by user_id ")
		.append(")t ")
		.append("left join ")
		.append("user_info userInfo on t.user_id = userInfo.id");
		
		params.add(userInfo.getUserType());
	    params.add(id);
	    //params.add(id);
	    Date beginDate = DateUtil.fmtYmdToDate(startTime);
	    Date endDate = DateUtil.fmtYmdToDate(endTime);
	    params.add(beginDate);
	    params.add(endDate);    	
    	
    	memberPlReportList = queryNativeSQL(sqlBuffer.toString(), params);
	    Iterator<?> it=memberPlReportList.iterator();
	    
		while(it.hasNext()) {
			TeamPlReport m=new TeamPlReport();
			Object[] obj=(Object[]) it.next();
			m.setUserName((String)obj[0]);
			m.setUserId((Integer) obj[1]);
			
			BigDecimal bd1 = (BigDecimal) obj[2];
			m.setDeposit(bd1);
			BigDecimal bd2 = (BigDecimal) obj[3];
			m.setWithdrawal(bd2);
			BigDecimal bd3 = (BigDecimal) obj[4];
			m.setTransfer(bd3);
			BigDecimal bd4 = (BigDecimal) obj[5];
			m.setTransferOut(bd4);
			BigDecimal bd5 = (BigDecimal) obj[6];
			m.setDeduction(bd5);
			BigDecimal bd6 = (BigDecimal) obj[7];
			m.setConsumption(bd6);
			BigDecimal bd7 = (BigDecimal) obj[8];
			m.setCancelAmount(bd7);
			BigDecimal bd8 = (BigDecimal) obj[9];
			m.setReturnPrize(bd8);
			BigDecimal bd9 = (BigDecimal) obj[10];
			m.setRebate(bd9);
			BigDecimal bd10=(BigDecimal)obj[11];
			//bd10 = bd10 == null?null:bd10;
			m.setRechargeMember(bd10 == null?null:bd10.intValue());
			BigDecimal bd11=(BigDecimal)obj[12];
			//bd11 = bd11 == null?null:bd11;
			m.setNewMembers(bd11 == null?null:bd11.intValue());
			BigDecimal bd12 = (BigDecimal) obj[13];
			m.setProfit(bd12);
			m.setUserType((Integer)obj[14]);
			
			if(m.getUserId() == null) {
				continue;
			}
			
		    listRecord.add(m);
		}
		
		if(listRecord != null && listRecord.size() > 0) {
			return listRecord.get(0);
		}
		
		return null;
	}
	private List<TeamPlReport> queryUserNextLevelProfitSm(String startTime, String endTime, UserInfo userInfo) {
		Integer id = userInfo.getId();
		StringBuffer sqlBuffer = new StringBuffer();
		PageBean<Object[]> page = new PageBean<>();
		List<Object> params = new ArrayList<>();
		
		page.setPageIndex(0);
		page.setPageSize(100000);		
		
		sqlBuffer.append("select CONCAT('',userInfo.user_name) user_name, t.*,userInfo.user_type  from ")
		.append("(")
		.append("select ")
		.append("user_id,")
		.append("SUM(deposit) as deposit,")
		.append("SUM(withdrawal) as withdrawal, ")
		.append("SUM(transfer) as transfer ,")
		.append("SUM(transfer_out) as transfer_out,")
		.append("SUM(deduction) as deduction,")
		.append("SUM(consumption) as consumption,")
		.append("SUM(cancel_amount) as cancel_amount,")
		.append("SUM(return_prize) as return_prize,")
		.append("SUM(rebate) as rebate,")
		.append("SUM(recharge_member) as recharge_member,")
		.append("SUM(new_members) as new_members,")
		.append("SUM(profit)*(-1) as profit ")
		.append("from team_pl_report ")
		.append("where ( ")
		.append("(user_id in (select id from user_info where FIND_IN_SET(?,superior) = 1 and user_type = 5) and user_type = 5) ")
		.append(" or ")
		.append("(user_id = ? and user_type = 0) ")
		.append(") ")
		.append("and create_time >= ? and create_time <= ? ")
		.append("group by user_id ")
		
		.append(")t ")
		.append("left join ")
		.append("user_info userInfo on t.user_id = userInfo.id ")
		.append("order by t.user_id ");
		
	    params.add(id);
	    params.add(id);
	    //params.add(id);
	    Date beginDate = DateUtil.fmtYmdToDate(startTime);
	    Date endDate = DateUtil.fmtYmdToDate(endTime);
	    params.add(beginDate);
	    params.add(endDate);
	    
    	List<?> memberPlReportList=null;
    	
    	memberPlReportList = queryNativeSQL(sqlBuffer.toString(), params);
	    Iterator<?> it=memberPlReportList.iterator();
	    List<TeamPlReport> listRecord=new ArrayList<TeamPlReport>();
		while(it.hasNext()) {
			TeamPlReport m=new TeamPlReport();
			Object[] obj=(Object[]) it.next();
			m.setUserName((String)obj[0]);
			BigDecimal bd1 = (BigDecimal) obj[2];
			m.setDeposit(bd1);
			BigDecimal bd2 = (BigDecimal) obj[3];
			m.setWithdrawal(bd2);
			BigDecimal bd3 = (BigDecimal) obj[4];
			m.setTransfer(bd3);
			BigDecimal bd4 = (BigDecimal) obj[5];
			m.setTransferOut(bd4);
			BigDecimal bd5 = (BigDecimal) obj[6];
			m.setDeduction(bd5);
			BigDecimal bd6 = (BigDecimal) obj[7];
			m.setConsumption(bd6);
			BigDecimal bd7 = (BigDecimal) obj[8];
			m.setCancelAmount(bd7);
			BigDecimal bd8 = (BigDecimal) obj[9];
			m.setReturnPrize(bd8);
			BigDecimal bd9 = (BigDecimal) obj[10];
			m.setRebate(bd9);
			BigDecimal bd10=(BigDecimal)obj[11];
			//bd10 = bd10 == null?null:bd10;
			m.setRechargeMember(bd10 == null?null:bd10.intValue());
			BigDecimal bd11=(BigDecimal)obj[12];
			//bd11 = bd11 == null?null:bd11;
			m.setNewMembers(bd11 == null?null:bd11.intValue());
			BigDecimal bd12 = (BigDecimal) obj[13];
			m.setProfit(bd12);
			m.setUserType((Integer)obj[14]);
		    listRecord.add(m);
		}
		return listRecord;
	}
	private List<TeamPlReport> queryAgencyNextLevelProfitSm(String startTime, String endTime, UserInfo userInfo) {
		Integer id = userInfo.getId();
		StringBuffer sqlBuffer = new StringBuffer();
		PageBean<Object[]> page = new PageBean<>();
		List<Object> params = new ArrayList<>();
		
		page.setPageIndex(0);
		page.setPageSize(100000);		
		
		sqlBuffer.append("select CONCAT('',userInfo.user_name) user_name, t.*,userInfo.user_type from ")
		.append("(")
		.append("select ")
		.append("user_id,")
		.append("SUM(deposit) as deposit,")
		.append("SUM(withdrawal) as withdrawal, ")
		.append("SUM(transfer) as transfer ,")
		.append("SUM(transfer_out) as transfer_out,")
		.append("SUM(deduction) as deduction,")
		.append("SUM(consumption) as consumption,")
		.append("SUM(cancel_amount) as cancel_amount,")
		.append("SUM(return_prize) as return_prize,")
		.append("SUM(rebate) as rebate,")
		.append("SUM(recharge_member) as recharge_member,")
		.append("SUM(new_members) as new_members,")
		.append("SUM(profit) as profit ")
		.append("from team_pl_report ")
		.append("where user_id in (select id from user_info where FIND_IN_SET(?,superior) = 1 and user_type = 6) and user_type = 6 ")
		.append("and create_time >= ? and create_time <= ? ")
		.append("group by user_id ")
		
		.append(")t ")
		.append("left join ")
		.append("user_info userInfo on t.user_id = userInfo.id ")
		.append("order by t.user_id ");
		
	    params.add(id);
	    /*params.add(id);
	    params.add(id);*/
	    Date beginDate = DateUtil.fmtYmdToDate(startTime);
	    Date endDate = DateUtil.fmtYmdToDate(endTime);
	    params.add(beginDate);
	    params.add(endDate);
	    
    	List<?> memberPlReportList=null;
    	
    	memberPlReportList = queryNativeSQL(sqlBuffer.toString(), params);
	    Iterator<?> it=memberPlReportList.iterator();
	    List<TeamPlReport> listRecord=new ArrayList<TeamPlReport>();
		while(it.hasNext()) {
			TeamPlReport m=new TeamPlReport();
			Object[] obj=(Object[]) it.next();
			m.setUserName((String)obj[0]);
			BigDecimal bd1 = (BigDecimal) obj[2];
			m.setDeposit(bd1);
			BigDecimal bd2 = (BigDecimal) obj[3];
			m.setWithdrawal(bd2);
			BigDecimal bd3 = (BigDecimal) obj[4];
			m.setTransfer(bd3);
			BigDecimal bd4 = (BigDecimal) obj[5];
			m.setTransferOut(bd4);
			BigDecimal bd5 = (BigDecimal) obj[6];
			m.setDeduction(bd5);
			BigDecimal bd6 = (BigDecimal) obj[7];
			m.setConsumption(bd6);
			BigDecimal bd7 = (BigDecimal) obj[8];
			m.setCancelAmount(bd7);
			BigDecimal bd8 = (BigDecimal) obj[9];
			m.setReturnPrize(bd8);
			BigDecimal bd9 = (BigDecimal) obj[10];
			m.setRebate(bd9);
			BigDecimal bd10=(BigDecimal)obj[11];
			//bd10 = bd10 == null?null:bd10;
			m.setRechargeMember(bd10 == null?null:bd10.intValue());
			BigDecimal bd11=(BigDecimal)obj[12];
			//bd11 = bd11 == null?null:bd11;
			m.setNewMembers(bd11 == null?null:bd11.intValue());
			BigDecimal bd12 = (BigDecimal) obj[13];
			m.setProfit(bd12);
			m.setUserType((Integer)obj[14]);
		    listRecord.add(m);
		}
		return listRecord;
	}
}



































