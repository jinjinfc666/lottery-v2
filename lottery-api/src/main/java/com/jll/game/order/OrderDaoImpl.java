package com.jll.game.order;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.alibaba.druid.util.StringUtils;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Constants.OrderState;
import com.jll.common.utils.Utils;
import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.dao.PageBean;
import com.jll.entity.OrderInfo;
import com.jll.entity.UserBankCard;

@Repository
public class OrderDaoImpl extends DefaultGenericDaoImpl<OrderInfo> implements OrderDao
{
	private Logger logger = Logger.getLogger(OrderDaoImpl.class);

	@Override
	public void saveOrders(OrderInfo order) {
		this.saveOrUpdate(order);
	}

	@Override
	public List<OrderInfo> queryOrdersByIssue(Integer issueId) {
		String sql = "from OrderInfo where issueId=?";
		List<Object> params = new ArrayList<>();
		params.add(issueId);
		
		return this.query(sql, params, OrderInfo.class);
	}

	@Override
	public double getUserBetTotalByDate(int walletId, int userId, Date start, Date end) {
		String sql = "select sum(betAmount) from OrderInfo where userId=? and walletId=? and createTime >= ? and createTime < ?  and ( state=? or state=?) ";
		List<Object> params = new ArrayList<>();
		params.add(userId);
		params.add(walletId);
		params.add(start);
		params.add(end);
		params.add(OrderState.WINNING.getCode());
		params.add(OrderState.LOSTING.getCode());
		
		 Query<Double> query = getSessionFactory().getCurrentSession().createQuery(sql, Double.class);
	    if(params != null) {
	    	int indx = 0;
	    	for(Object para : params) {
	    		query.setParameter(indx, para);
	    		
	    		indx++;
	    	}
	    }
		return Utils.toDouble(query.getSingleResult());
	}

	@Override
	public OrderInfo getOrderInfo(String orderNum) {
		String sql = "from OrderInfo where orderNum=?";
		List<Object> params = new ArrayList<>();
		params.add(orderNum);
		
		List<OrderInfo> ret =  this.query(sql, params, OrderInfo.class);
		if(ret != null && !ret.isEmpty()){
			return ret.get(0);
		}
		return null;
	}

	@Override
	public List<OrderInfo> queryWinOrdersByIssue(Integer issueId) {
		String sql = "from OrderInfo where issueId=? and state = ?";
		List<Object> params = new ArrayList<>();
		params.add(issueId);
		params.add(OrderState.WINNING.getCode());
		return this.query(sql, params, OrderInfo.class);
	
	}

	@Override
	public List<OrderInfo> getOrderInfoByPrams(Integer issueId, String userName, String orderNum,Integer delayPayoutFlag) {
		List<Object> params = new ArrayList<>();
		String sql = "select o from OrderInfo o,UserInfo u where u.id = o.userId and o.state = ? ";
		params.add(OrderState.WAITTING_PAYOUT.getCode());
		
		if(issueId > -1){
			sql += (" and o.issueId=?  ");
			params.add(issueId);
		}
		
		if(delayPayoutFlag > -1){
			sql += ("  and o.delayPayoutFlag = ? ");
			params.add(delayPayoutFlag);
		}
		
		if(!StringUtils.isEmpty(orderNum)){
			sql += ("  and o.orderNum = ? ");
			params.add(orderNum);
		}
		if(!StringUtils.isEmpty(userName)){
			sql += ("  and u.userName = ? ");
			params.add(userName);
		}
		return this.query(sql, params, OrderInfo.class);
	}

	@Override
	public PageBean<OrderInfo> queryOrdersByPage(PageBean<OrderInfo> page) {
		String sql = "from OrderInfo where issueId=?";
		List<Object> params = page.getParams();
		
		return this.queryByPagination(page, sql, params, OrderInfo.class);
	}

	@Override
	public List<OrderInfo> queryZhOrder(String transactionNum) {
		List<Object> params = new ArrayList<>();
		String sql = "from OrderInfo where zhTrasactionNum = ?";
		params.add(transactionNum);		
		
		return this.query(sql, params, OrderInfo.class);
	}

	@Override
	public List<OrderInfo> queryById(Integer id) {
		Integer delayPayoutFlag=Constants.OrderDelayState.DEPLAY.getCode();
		String sql="from OrderInfo where id=:id and delayPayoutFlag=:delayPayoutFlag";
		Query<OrderInfo> query = getSessionFactory().getCurrentSession().createQuery(sql,OrderInfo.class);
	    query.setParameter("id", id);
	    query.setParameter("delayPayoutFlag", delayPayoutFlag);
	    List<OrderInfo> list= query.list();
	    return list;
	}
	
}
