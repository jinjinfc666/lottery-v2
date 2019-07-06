package com.jll.game.order;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.entity.OrderInfo;
import com.jll.entity.OrderInfoExt;

@Configuration
@PropertySource("classpath:sys-setting.properties")
@Service
@Transactional
public class OrderInfoExtServiceImpl implements OrderInfoExtService
{
	@Resource
	OrderInfoExtDao orderExtDAO;

	@Override
	public void saveOrderInfoExt(OrderInfo orderInfo) {
		orderExtDAO.saveOrderExt(orderInfo);
	}

	@Override
	public List<OrderInfoExt> queryOrderInfoExt(Integer orderId) {
		return orderExtDAO.queryOrderInfoExt(orderId);
	}
}
