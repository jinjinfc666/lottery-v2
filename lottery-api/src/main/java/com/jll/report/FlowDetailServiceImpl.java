package com.jll.report;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.entity.SysCode;



@Service
@Transactional
public class FlowDetailServiceImpl implements FlowDetailService {
	@Resource
	FlowDetailDao flowDetailDao;
	@Resource
	CacheRedisService cacheRedisService;
	@Override
	public Map<String,Object> queryUserAccountDetails(Map<String, Object> ret) {
		String userName=(String)ret.get("userName");
		Integer dataItemType = (Integer)ret.get("dataItemType");
		String operationType=(String)ret.get("operationType");
		String startTime=(String) ret.get("startTime");
		String endTime=(String) ret.get("endTime");
		Integer pageIndex=(Integer) ret.get("pageIndex");
		Integer pageSize=(Integer) ret.get("pageSize");
		Integer orderId = (Integer) ret.get("orderId");
		
		String codeTypeName=Constants.SysCodeTypes.FLOW_TYPES.getCode();
		SysCode sysCode=cacheRedisService.getSysCode(codeTypeName,codeTypeName);
		Integer codeTypeNameId=sysCode.getId();
		return flowDetailDao.queryUserAccountDetails(codeTypeNameId,userName,dataItemType,operationType,startTime,endTime,pageIndex,pageSize,orderId);
	}
	//代理的转账记录查询  
	@Override
	public Map<String, Object> queryAgentTransfer(Integer agentId, String startTime, String endTime) {
		return flowDetailDao.queryAgentTransfer(agentId, startTime, endTime);
	}
}
