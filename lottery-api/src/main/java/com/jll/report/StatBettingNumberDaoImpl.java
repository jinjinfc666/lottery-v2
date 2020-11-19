package com.jll.report;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.dao.PageBean;
import com.jll.entity.StatBettingNumber;
import com.jll.user.UserInfoServiceImpl;

@Repository
public class StatBettingNumberDaoImpl extends DefaultGenericDaoImpl<StatBettingNumber> implements StatBettingNumberDao {
	private Logger logger = Logger.getLogger(UserInfoServiceImpl.class);
	
	@Override
	public PageBean queryStatBetNum(Integer pageIndex, Integer pageSize, String lotteryType) {
		StringBuffer buffer = new StringBuffer();
		Map<String,Object> map=new HashMap<String,Object>();
				
		if(lotteryType != null) {
			buffer.append("and lotteryType=:lotteryType ");
			map.put("lotteryType", lotteryType);
		}
		
		String sql = "from StatBettingNumber where 1=1 "+ buffer.toString() + " and issueId = (select max(issueId) from StatBettingNumber) order by betAmount desc";
		
		logger.debug(sql+"-----------------------------queryLoyTst----SQL--------------------------------");
		PageBean page=new PageBean();
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		PageBean pageBean=null;
		try {
			pageBean=queryByPagination(page, sql,map);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return pageBean;
	}

	@Override
	public StatBettingNumber queryStatByNumber(String lotteryType, Integer issueId, String betNum) {
		String sql = "from StatBettingNumber where lotteryType=:lotteryType and issueId=:issueId and betNum=:betNum";
		StatBettingNumber stat = null;
		Query<StatBettingNumber> query = currentSession().createQuery(sql, StatBettingNumber.class);
		query.setParameter("lotteryType", lotteryType);
		query.setParameter("issueId", issueId);
		query.setParameter("betNum", betNum);
		query.setMaxResults(1);
		try{
			stat = query.getSingleResult();
		}catch(Exception ex){
			
		}
		
		return stat;
	}

	@Override
	public void saveOrUpdateStat(StatBettingNumber stat) {
		saveOrUpdate(stat);
	}
}



































