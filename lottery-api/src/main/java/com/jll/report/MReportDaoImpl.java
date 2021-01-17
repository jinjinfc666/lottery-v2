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
import com.jll.common.utils.DateUtil;
import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.dao.PageBean;
import com.jll.entity.MemberPlReport;
import com.jll.entity.UserInfo;
import com.jll.user.UserInfoServiceImpl;

@Repository
public class MReportDaoImpl extends DefaultGenericDaoImpl<MemberPlReport> implements MReportDao {
	private Logger logger = Logger.getLogger(UserInfoServiceImpl.class);
	//会员盈亏报表
	@Override
	public PageBean queryAll(String startTime,String endTime,String userName,Integer pageIndex,Integer pageSize, Integer userType) {
		//String userNameSql="";
		StringBuffer buffer = new StringBuffer();
		Map<String,Object> map=new HashMap<String,Object>();
		if(!StringUtils.isBlank(userName)) {
			buffer.append("and user_name=:userName ");
			map.put("userName", userName);
		}
		if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			buffer.append("and create_time >=:startTime and create_time <=:endTime ");
			Date beginDate = DateUtil.fmtYmdToDate(startTime);
		    Date endDate = DateUtil.fmtYmdToDate(endTime);
			map.put("startTime", beginDate);
			map.put("endTime", endDate);
		}
		
		/*if(userType != null) {
			buffer.append("and user_type=:userType ");
			map.put("userType", userType);
		}*/
		
		
		//String sql = "select user_name,SUM(deposit) as deposit,SUM(withdrawal) as withdrawal ,SUM(transfer) as transfer ,SUM(transfer_out) as transfer_out,SUM(deduction) as deduction,sum(consumption) as consumption,SUM(cancel_amount) as cancel_amount,SUM(return_prize) as return_prize,SUM(rebate) as rebate,sum(profit) as profit,user_type from member_pl_report "+timeSql+userNameSql+" GROUP BY user_name,user_type";
//		String sql = "select user_name,deposit,withdrawal,transfer,transfer_out,deduction,consumption,cancel_amount,return_prize,rebate,profit,user_type,create_time,sys_bonus from team_pl_report where 1=1 "+ buffer.toString() + " order by create_time desc";
//		String sql = "select user_name,SUM(deposit) as deposit,SUM(withdrawal) as withdrawal,SUM(transfer) as transfer ,SUM(transfer_out) as transfer_out,SUM(deduction) as deduction,sum(consumption) as consumption,SUM(cancel_amount) as cancel_amount,SUM(return_prize) as return_prize,SUM(rebate) as rebate,SUM(recharge_member) AS recharge_member,SUM(new_members) as new_members,sum(profit) as profit,user_type from team_pl_report where 1=1 "+buffer.toString()+" GROUP BY user_name";
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select userInfo.user_name user_name,userInfo.user_type, t.* from ")
		.append("(")
		.append("select ")
		.append("lottery_type,")
		.append("user_id,")
		.append("SUM(consumption) as consumption,")
		.append("SUM(cancel_amount) as cancel_amount,")
		.append("SUM(return_prize) as return_prize,")
		.append("SUM(zc_amount) as zc,")
		.append("SUM(ts_amount) as ts,")
		.append("SUM(profit)*(-1) as profit ")
		.append("from team_pl_report ")
		.append("where user_name=:userName ")
		.append("and create_time >=:startTime and create_time <=:endTime ")
		
		.append(")t ")
		.append("left join ")
		.append("user_info userInfo on t.user_id = userInfo.id ")
		.append("order by t.user_id ");
//		logger.debug(sql+"-----------------------------queryLoyTst----SQL--------------------------------");
		PageBean page=new PageBean();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		PageBean pageBean=null;
		try {
			pageBean=queryBySqlPagination(page, sqlBuffer.toString(),map);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return pageBean;
	}
}



































