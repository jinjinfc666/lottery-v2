package com.jll.report;


import java.util.List;

import com.jll.dao.PageBean;

public interface LoyTstDao {
	public PageBean queryLoyTst(Integer codeTypeNameId,String lotteryType,Integer isZh,String zhTrasactionNum,Integer state,Integer terminalType,String startTime,String endTime,String issueNum,String userName,String orderNum,Integer orderId,Integer pageIndex,Integer pageSize);
	
	public PageBean queryBettingRecBrief(String lotteryType,Integer userId,Integer pageIndex,Integer pageSize);
	
	public List<?> queryDetails(Integer codeTypeNameId,Integer id);
}
