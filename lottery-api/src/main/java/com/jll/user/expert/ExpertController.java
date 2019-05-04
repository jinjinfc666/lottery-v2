package com.jll.user.expert;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Message;
import com.jll.entity.UserInfo;
import com.jll.entity.display.UserPushCache;
import com.jll.user.UserInfoService;
import com.terran4j.commons.api2doc.annotations.Api2Doc;

@Api2Doc(id = "expert", name = "expert module")
@RestController
@RequestMapping({ "/experts" })
public class ExpertController {
	
	private Logger logger = Logger.getLogger(ExpertController.class);
		
	@Resource
	UserInfoService userInfoService;
			
	@Resource
	CacheRedisService cacheServ;
		
	@Resource
	ExpertService expertServ;
	
	@RequestMapping(value="/push-numbers", method = { RequestMethod.GET }, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> pushNumbers(@RequestParam(name = "lotteryType", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<String, Object>();
		Map<String, Object> data = null;
		UserInfo user = userInfoService.getCurLoginInfo();
		
		if(user == null){
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_USER_NO_VALID_USER.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_USER_NO_VALID_USER.getErrorMes());
			return resp;
		}
		
		UserPushCache userPushCache = cacheServ.getUserPushCache(user);
		
		if(userPushCache == null){
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_USER_NO_PUSH_CONFIG.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_USER_NO_PUSH_CONFIG.getErrorMes());
			return resp;
		}
		
		try {
			data = expertServ.produceExpertNum(lotteryType, userPushCache);
			
			if(data != null){
				Map<String, Object> pushStatus = userPushCache.getLotteryPushStatus();
				pushStatus.put(Constants.KEY_EXPERT_NUMBER + lotteryType, data);
				Integer currPushTimes = (Integer)pushStatus.get(Constants.KEY_EXPERT_CURRPUSHTIMES + lotteryType);
				/*if(currPushTimes == null){
					pushStatus.put(Constants.KEY_EXPERT_CURRPUSHTIMES + lotteryType, 1);
				}else{
					currPushTimes = currPushTimes.intValue() + 1;
					pushStatus.put(Constants.KEY_EXPERT_CURRPUSHTIMES + lotteryType, currPushTimes);
				}*/
				
				cacheServ.setUserPushCache(user, userPushCache);
			}
		}catch(Exception ex) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_USER_FAILED_REGISTER.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_USER_FAILED_REGISTER.getErrorMes());
			return resp;
		}
		
		resp.put(Message.KEY_DATA, data);
		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		return resp;
	}
}
