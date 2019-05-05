package com.jll.user.expert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.entity.UserInfo;
import com.jll.entity.UserPushConfig;
import com.jll.entity.UserPushNumRec;
import com.jll.entity.display.UserPushCache;
import com.jll.user.UserInfoDao;

@Service
@Transactional
public class ExpertServiceImpl implements ExpertService
{
	private Logger logger = Logger.getLogger(ExpertServiceImpl.class);
		

	String[] dwdBettingNumbers = {"{betNum},,,,,,,,,",",{betNum},,,,,,,,",",,{betNum},,,,,,,",",,,{betNum},,,,,,",",,,,{betNum},,,,,",",,,,,{betNum},,,,",",,,,,,{betNum},,,",",,,,,,,{betNum},,",",,,,,,,,{betNum},",",,,,,,,,,{betNum}"};
	
	String[][] xyftDs = {{"双","00"},{"单","01"}};
	
	String[][] xyftDx = {{"小","00"},{"大","01"}};
	
	@Resource
	CacheRedisService cacheServ;
	
	@Resource
	ExpertDao expertDao;
	
	@Override
	public Map<String, Object> produceExpertNum(String lotteryType, UserPushCache userPushCache) {
		Map<String, Object> ret = new HashMap<>();
		//Map<String, Object> numbers = new HashMap<>();
		
		List<UserPushConfig> pushConfigs = userPushCache.getUserPushConfig();
		//Integer raceLaneIndex = null;
		
		
		for(UserPushConfig pushConfig : pushConfigs){
			if(pushConfig.getLotteryType().equals(lotteryType)){
				String playTypeName = pushConfig.getPlayTypeName();
				Integer pushTimes = pushConfig.getPushTimes();
				Double pushAmount = pushConfig.getPushAmount();
				Integer pushNumbers = pushConfig.getPushNumbers();
				Integer raceLaneIndex = null;
				Integer betNum = null;
				String numbers = "";
				Double numbersAmount = pushAmount;
				String ds = "";
				Double dxAmount = pushAmount;
				String dx = "";
				Double dsAmount = pushAmount;
				String raceLane = "";
				
				ret.put("dxAmount", dxAmount);
				ret.put("dsAmount", dsAmount);
				ret.put("numbersAmount", numbersAmount);
				
				// {"ds":"单","dx":"小","numbers":"01,03","amount":100,"raceLane":"冠军"}
				if(lotteryType.equals(Constants.LottoType.XYFT.getCode())){
					raceLaneIndex = new Random().nextInt(10);
					if(raceLaneIndex == 0){
						raceLane = "冠军";
					}else if(raceLaneIndex == 1){
						raceLane = "亚军";
					}else if(raceLaneIndex == 2){
						raceLane = "季军";
					}else{
						raceLane = "第" + (raceLaneIndex  + 1) + "名";
					}
					if(ret.get("raceLane") == null) {
						ret.put("raceLane", raceLane);						
					}
					if(playTypeName.startsWith("dwd|")){
						int counter = 0;
						while(counter < pushNumbers){
							while(true){
								betNum = new Random().nextInt(10) + 1;
								String tempBetBum = "";
								if(betNum < 10){
									tempBetBum = "0" + betNum;
								}else{
									tempBetBum = "" + betNum;
								}
								
								if(numbers.contains(tempBetBum)){
									continue;
								}
								
								numbers += "," + tempBetBum;
								break;
							}						
							
							counter++;
						}
						
						if(numbers.startsWith(",")){
							numbers = numbers.substring(1);
						}
						
						ret.put("numbers", numbers);
					}else if(playTypeName.startsWith("dx|")){
						betNum = new Random().nextInt(2);
						if(betNum == 0){
							dx = "小";
						}else if(betNum == 1){
							dx = "大";
						}
						
						ret.put("dx", dx);
					}else if(playTypeName.startsWith("ds|")){
						betNum = new Random().nextInt(2);
						if(betNum == 0){
							ds = "双";
						}else if(betNum == 1){
							ds = "单";
						}
						
						ret.put("ds", ds);
					}
				}
			}
		}
		
		return ret;
	}

	@Override
	public void updateExpertPushTimes(UserInfo user, String lotteryType) {
		UserPushCache userPushCache = cacheServ.getUserPushCache(user);
		if(userPushCache == null
				|| userPushCache.getUserPushConfig() == null
				|| userPushCache.getUserPushConfig().size() == 0){
			return;
		}
		
		Map<String, Object> pushStatus = userPushCache.getLotteryPushStatus();
		if(pushStatus == null){
			pushStatus = new HashMap<>();			
		}
		
		String currPushTimesKey = Constants.KEY_EXPERT_CURRPUSHTIMES + lotteryType;
		String maxPushTimesKey = Constants.KEY_EXPERT_MAX_PUSH_TIMES + lotteryType;
		if(pushStatus.get(currPushTimesKey) == null){
			pushStatus.put(currPushTimesKey, 1);
		}else{
			Integer currPushTimes = (Integer)pushStatus.get(currPushTimesKey);
			Integer maxPushTimes = (Integer)pushStatus.get(maxPushTimesKey);
			if(currPushTimes.intValue() + 1 >=  maxPushTimes){
				pushStatus.put(currPushTimesKey, 1);
				//TODO 重新计算投注金额
			}
		}
	}

	@Override
	public void cacheUserPushConfig(UserInfo user) {
		List<UserPushConfig> userPushConfigs = expertDao.queryUserPushConfigs(user);
		
		if(userPushConfigs == null
				|| userPushConfigs.size() == 0) {
			return;
		}
		
		UserPushCache userPushCache = cacheServ.getUserPushCache(user);
		if(userPushCache == null){
			userPushCache = new UserPushCache();
		}
		
		userPushCache.setUserPushConfig(userPushConfigs);
		
		cacheServ.setUserPushCache(user, userPushCache);
	}
}
