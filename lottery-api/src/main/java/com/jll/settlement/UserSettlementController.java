package com.jll.settlement;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jll.common.constants.Constants;
import com.jll.common.constants.Message;
import com.jll.common.utils.DateUtil;
import com.jll.dao.PageBean;
import com.jll.entity.UserAccountDetails;
import com.jll.entity.UserSettlement;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;

@Api2Doc(id = "userSettlement", name = "User Settlement")
@ApiComment(seeClass = UserAccountDetails.class)
@RestController
@RequestMapping({"/user-settlements"})
public class UserSettlementController {
	private Logger logger = Logger.getLogger(UserSettlementController.class);
	
	@Resource
	UserSettlementService settlementService;
	
		
	@RequestMapping(method={RequestMethod.GET}, produces={"application/json"})
	public Map<String, Object> queryUserSettlement(@RequestParam(name = "userName", required = false) String userName,
			  @RequestParam(name = "state", required = false) Integer setStatus,
			  @RequestParam(name = "startTime", required = false) String startTime,
			  @RequestParam(name = "endTime", required = false) String endTime,
			  @RequestParam(name = "pageIndex", required = true) Integer pageIndex) {
		Map<String, Object> ret = new HashMap<>();
		PageBean<UserSettlement> data = null;
		Integer pageSize = Constants.Pagination.SUM_NUMBER.getCode();	
		
		if(!StringUtils.isBlank(startTime)||!StringUtils.isBlank(endTime)) {
			if(!DateUtil.isValidDate(startTime)||!DateUtil.isValidDate(endTime)) {
				ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
				ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode());
				ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getErrorMes());
		    	return ret;
			}
		}
			
		try {
			data = settlementService.queryUserSettlement(pageIndex, pageSize, userName, setStatus, startTime, endTime);
			ret.put(Message.KEY_DATA, data);
			ret.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
			return ret;
		}catch(Exception e){
			ret.clear();
			ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_OTHERS.getCode());
			ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_OTHERS.getErrorMes());
			return ret;
		}
	}
	
	
	@RequestMapping(value="/{id}", method={RequestMethod.PUT}, produces={"application/json"})
	public Map<String, Object> performUserSettlement(@PathVariable(name="id", required = true) Integer settlementId) {
		Map<String, Object> ret = new HashMap<>();
		boolean isExisting = settlementService.isSettleMentExisting(settlementId);
		
		if(!isExisting) {
			ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_SETTLEMENT_NON_EXISTING.getCode());
			ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_SETTLEMENT_NON_EXISTING.getErrorMes());
			return ret;
		}
		
		try {
			settlementService.performUserSettlement(settlementId);
			ret.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
			return ret;
		}catch(Exception e){
			ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_OTHERS.getCode());
			ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_OTHERS.getErrorMes());
			return ret;
		}
	}
}
