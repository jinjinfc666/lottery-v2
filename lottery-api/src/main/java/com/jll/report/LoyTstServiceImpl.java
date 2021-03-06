package com.jll.report;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.dao.PageBean;
import com.jll.entity.SysCode;
import com.jll.user.UserInfoService;



@Service
@Transactional
public class LoyTstServiceImpl implements LoyTstService {
	@Resource
	LoyTstDao loyTstDao;
	@Resource
	CacheRedisService cacheRedisService;
	
	@Resource
	UserInfoService userInfoService;
	
	@Override
	public PageBean queryLoyTst(Map<String, Object> ret) {
		String lotteryType=(String)ret.get("lotteryType");
		Integer isZh=(Integer) ret.get("isZh");
		String zhTrasactionNum=(String) ret.get("zhTrasactionNum");
		Integer state=(Integer) ret.get("state");
		Integer terminalType=(Integer)ret.get("terminalType");
		String startTime=(String) ret.get("startTime");
		String endTime=(String) ret.get("endTime");
		String issueNum=(String)ret.get("issueNum");
		String userName=(String) ret.get("userName");
		String orderNum=(String) ret.get("orderNum");
		Integer orderId=(Integer) ret.get("orderId");
		Integer pageIndex=(Integer) ret.get("pageIndex");
		Integer pageSize=(Integer) ret.get("pageSize");
		String codeTypeName=Constants.SysCodeTypes.LOTTERY_TYPES.getCode();
		SysCode sysCode=cacheRedisService.getSysCode(codeTypeName,codeTypeName);
		Integer codeTypeNameId=sysCode.getId();
		return loyTstDao.queryLoyTst(codeTypeNameId,lotteryType,isZh,zhTrasactionNum,state,terminalType,startTime,endTime,issueNum,userName,orderNum,orderId,pageIndex,pageSize);
	}
	
	@Override
	public PageBean queryBettingRecBrief(Map<String, Object> ret) {
		userInfoService.getCurLoginInfo();
		String lotteryType=(String)ret.get("lotteryType");
		String userName=(String) ret.get("userName");
		Integer pageIndex=(Integer) ret.get("pageIndex");
		Integer pageSize=(Integer) ret.get("pageSize");
		//String codeTypeName=Constants.SysCodeTypes.LOTTERY_TYPES.getCode();
		//SysCode sysCode=cacheRedisService.getSysCode(codeTypeName,codeTypeName);
		//Integer codeTypeNameId=sysCode.getId();
		Integer userId = userInfoService.getUserId(userName);
		if(userId == null) {
			return null;
		}
		
		return loyTstDao.queryBettingRecBrief(lotteryType,userId,pageIndex,pageSize);
	}
	
	@Override
	public List<?> queryDetails(Integer id) {
		String codeTypeName=Constants.SysCodeTypes.LOTTERY_TYPES.getCode();
		SysCode sysCode=cacheRedisService.getSysCode(codeTypeName,codeTypeName);
		Integer codeTypeNameId=sysCode.getId();
		return loyTstDao.queryDetails(codeTypeNameId,id);
	}
	
}
