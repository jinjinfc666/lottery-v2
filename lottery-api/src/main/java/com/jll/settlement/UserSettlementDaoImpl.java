package com.jll.settlement;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.jll.common.utils.DateUtil;
import com.jll.common.utils.StringUtils;
import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.dao.PageBean;
import com.jll.entity.UserInfo;
import com.jll.entity.UserSettlement;

@Repository
public class UserSettlementDaoImpl 
				extends DefaultGenericDaoImpl<UserSettlement> 
					implements UserSettlementDao
{
	private Logger logger = Logger.getLogger(UserSettlementDaoImpl.class);


	@Override
	public void saveSettlement(UserSettlement settlement) {
		this.saveOrUpdate(settlement);
	}


	@Override
	public PageBean<UserSettlement> queryUserSettlement(Integer pageIndex, Integer pageSize, String userName, Integer setStatus, String startTime, String endTime) {
		List<Object> params = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		PageBean<UserSettlement> page= new PageBean<>();
		
		sql.append("from UserSettlement t where 1=1 ");
		
		if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			sql.append(" and setDate>=? and setDate<=?");
			Date beginDate = DateUtil.fmtYmdToDate(startTime);
		    Date endDate = DateUtil.fmtYmdToDate(endTime);
		    
	    	params.add(beginDate);
	    	params.add(endDate);
		}
		
		if(setStatus != null) {
			sql.append(" and state=?");
			params.add(setStatus);
		}
		
		if(!StringUtils.isBlank(userName)) {
			sql.append(" and userName=?");
			params.add(userName);
		}
		  
		sql.append(" order by createTime desc ");
		
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		
		PageBean<UserSettlement> pageBean = queryByPagination(page, sql.toString(), params, UserSettlement.class); 
		return pageBean;
	}


	@Override
	public boolean isIdExisting(Integer settlementId) {
		String sql = "select count(*) from UserSettlement where id=?";
		List<Object> params = new ArrayList<>();
		Long count = null;
		
		params.add(settlementId);
		
		count = queryCount(sql, params);
		
		if(count == null || count.longValue() == 0) {
			return false;
		}
		
		return true;
	}


	@Override
	public UserSettlement queryUserSettlementById(Integer settlementId) {
		String sql = "from UserSettlement where id=?";
		List<Object> params = new ArrayList<>();
		List<UserSettlement> ret = null;
		
		params.add(settlementId);
		
		ret = query(sql, params, UserSettlement.class);
		
		if(ret == null || ret.size() == 0) {
			return null;
		}
		
		return ret.get(0);
	}


	@Override
	public boolean isPendingExisting(UserInfo user) {
		String sql = "select count(*) from UserSettlement where state = 0 and userId=?";
		List<Object> params = new ArrayList<>();
		Long count = null;
		
		params.add(user.getId());
		
		count = queryCount(sql, params);
		
		if(count == null || count.longValue() == 0) {
			return false;
		}
		
		return true;
	}
}
