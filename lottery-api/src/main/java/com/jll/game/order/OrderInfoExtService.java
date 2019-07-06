package com.jll.game.order;

import java.util.List;

import com.jll.entity.OrderInfo;
import com.jll.entity.OrderInfoExt;

public interface OrderInfoExtService
{
	void saveOrderInfoExt(OrderInfo OrderInfo);

	List<OrderInfoExt> queryOrderInfoExt(Integer id);	
}
