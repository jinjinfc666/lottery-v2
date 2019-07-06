package com.jll.game.order;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.entity.OrderInfo;
import com.jll.entity.OrderInfoExt;

@Repository
public class OrderInfoExtDaoImpl extends DefaultGenericDaoImpl<OrderInfoExt> implements OrderInfoExtDao
{
	private Logger logger = Logger.getLogger(OrderInfoExtDaoImpl.class);

	

	@Override
	public void saveOrderExt(OrderInfo order) {
		Date today = new Date();
		OrderInfoExt ext = new OrderInfoExt();
		ext.setOrderId(order.getId());
		ext.setExtFieldName("isPrize");
		ext.setExtFieldVal(String.valueOf(order.getIsPrize()));
		ext.setCreateTime(today);
		
		this.saveOrUpdate(ext);
		
	}



	@Override
	public List<OrderInfoExt> queryOrderInfoExt(Integer orderId) {
		String sql = "from OrderInfoExt where orderId=?";
		List<Object> params = new ArrayList<>();
		params.add(orderId);
		
		return this.query(sql, params, OrderInfoExt.class);
	}

 
	
  
}
