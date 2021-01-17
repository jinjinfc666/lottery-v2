package com.jll.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.common.constants.Message;
import com.jll.entity.PlayType;
import com.jll.entity.UserInfo;
import com.jll.entity.UserTs;
import com.jll.game.playtype.PlayTypeService;

@Configuration
@PropertySource("classpath:sys-setting.properties")
@Service
@Transactional
public class UserTsServiceImpl implements UserTsService
{

	@Resource
	private UserInfoService userServ;
	
	@Resource
	private UserTsDao userTsDao;
	
	@Resource
	private PlayTypeService playTypeServ;
	
	@Override
	public Map<String, Object> queryUserTs(String userName, String lotteryType) {
		Map<String, Object> ret = new HashMap<>();
		
		
		UserInfo userInfo = userServ.getUserByUserName(userName);
		if(userInfo == null){
			ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode());
			ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getErrorMes());
			return ret;
		}
		
		Integer userId = userInfo.getId();
		List<UserTs> userTses = userTsDao.queryUserTsByUserId(userId, lotteryType);
		/*if(CollectionUtils.isEmpty(userTses)){
			List<PlayType> playTypes = playTypeServ.queryPlayType(lotteryType);
			userTses = convertIntoBlankUserTs(playTypes, userId, lotteryType);
		}*/
		
		ret.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		ret.put(Message.KEY_DATA, userTses);
		
		return ret;
	}

	/*private List<UserTs> convertIntoBlankUserTs(List<PlayType> playTypes, Integer userId, String lotteryType) {
		List<UserTs> userTses = new ArrayList<>();
		BigDecimal zeroNum = new BigDecimal("0.00");
		
		for(PlayType playType : playTypes){
			UserTs userTs = new UserTs();
			userTs.setaTs(zeroNum);
			userTs.setPlayTypeBrief(playType.getBriefCla());
			userTs.setbTs(zeroNum);
			userTs.setcTs(zeroNum);
			userTs.setdTs(zeroNum);
			userTs.setLotteryType(lotteryType);
			userTs.setPlayTypeId(playType.getId());
			userTs.setSingleBetLimitAmount(zeroNum);
			userTs.setTotalBetLimitAmount(zeroNum);
			userTs.setUserId(userId);
			userTses.add(userTs);
			
		}
		return userTses;
	}*/

	@Override
	public Map<String, Object> saveOrUpdateUserTs(List<UserTs> userTses) {
		Map<String,Object> map = new HashMap<String,Object>();
		userTsDao.saveOrUpdateUserTs(userTses);
		map.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		return map;
	}

	@Override
	public UserTs queryUserTsByPlayTypeId(String userId, String lotteryType, Integer playTypeId) {
		return userTsDao.queryUserTsByPlayTypeId(userId, lotteryType, playTypeId);
	}
}
