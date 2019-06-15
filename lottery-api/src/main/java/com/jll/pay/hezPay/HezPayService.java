package com.jll.pay.hezPay;

import java.util.Map;

import com.jll.entity.display.HezPayNotices;

public interface HezPayService 
{
	 
	/**
	 * @param params
	 * @return
	 */
	String processScanPay(Map<String, Object> params);
	
	/**
	 * @param params
	 * @return
	 */
	String processOnlineBankPay(Map<String, Object> params);
	
	/**
	 * @param params
	 * @return
	 */
	String receiveNoties(Map<String, Object> params);


	boolean isValid(Map<String, Object> params);
	
	boolean isNoticesValid(HezPayNotices notices);	
	
}
