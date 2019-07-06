package com.jll.game.order;

import java.util.List;

import com.jll.entity.OrderInfo;
import com.jll.entity.OrderInfoExt;

public interface OrderInfoExtDao
{	
	void saveOrderExt(OrderInfo order);

	List<OrderInfoExt> queryOrderInfoExt(Integer orderId);
}
