package com.jll.stat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Constants.CreditMarketEnum;
import com.jll.common.constants.Constants.TrgUserAccDetailsFlag;
import com.jll.common.constants.Constants.UserType;
import com.jll.dao.PageBean;
import com.jll.entity.Issue;
import com.jll.entity.OrderInfo;
import com.jll.entity.PlayType;
import com.jll.entity.TeamPlReport;
//import com.jll.entity.TrgUserAccountDetails;
import com.jll.entity.UserAccountDetails;
import com.jll.entity.UserInfo;
import com.jll.entity.UserTs;
import com.jll.entity.display.CreditMarket;
import com.jll.game.IssueService;
import com.jll.game.mesqueue.kafka.KafkaConsumer;
import com.jll.game.order.OrderService;
import com.jll.game.playtype.PlayTypeService;
import com.jll.report.TReportService;
import com.jll.user.UserInfoService;
import com.jll.user.UserTsService;

@Service
@Transactional
public class StatProfitServiceImpl implements StatProfitService, KafkaConsumer
{
	private Logger logger = Logger.getLogger(StatProfitServiceImpl.class);

	/*@Resource
	TrgUserAccountDetailsService trgUserAccDetailServ;*/
	
	@Resource
	UserInfoService userServ;
	
	@Resource
	TReportService reportServ;
	
	@Resource
	CacheRedisService cacheServ;
	
	@Resource
	OrderService orderServ;
	
	@Resource
	IssueService issueServ;
	
	@Resource
	PlayTypeService playTypeServ;
	
	@Resource
	UserTsService userTsServ;
	
	@Override
	public void exeStatistic(UserAccountDetails accDetails) {
		// try {
		List<Object> params = new ArrayList<>();
		Integer flag = TrgUserAccDetailsFlag.pending.getCode();
		UserInfo superior = null;
		UserInfo user = null;
		Integer userType = null;

		params.add(flag);
		user = userServ.getUserById(accDetails.getUserId());
		userType = user.getUserType();

		if (Constants.AccOperationType.BETTING.getCode().equals(accDetails.getOperationType())
				&& userType.intValue() == Constants.UserType.AGENCY.getCode()) {
			userType = Constants.UserType.PLAYER.getCode();
		}

		handleProfit(accDetails, user, userType, false);

		// 鏅�氱帺瀹剁殑鐩堝埄鐩存帴绠椾笂绾э紝瀵逛簬浠ｇ悊绫诲瀷鐢ㄦ埛鐨勭泩鍒╅渶瑕佷粠鏈韩寮�濮嬬畻绱姞鐩堝埄
		if (user.getUserType() == Constants.UserType.PLAYER.getCode()
				|| user.getUserType() == Constants.UserType.SM_PLAYER.getCode()
				|| user.getUserType() == Constants.UserType.XY_PLAYER.getCode()
				|| user.getUserType() == Constants.UserType.ENTRUST_PLAYER.getCode()) {
			superior = userServ.querySuperior(user);
		} else if (user.getUserType() == Constants.UserType.AGENCY.getCode()
				|| user.getUserType() == Constants.UserType.GENERAL_AGENCY.getCode()
				|| user.getUserType() == Constants.UserType.SM_AGENCY.getCode()
				|| user.getUserType() == Constants.UserType.ENTRUST_AGENCY.getCode()) {
			superior = user;
		}

		if (!Constants.AccOperationType.TRANSFER.getCode().equals(accDetails.getOperationType())) {
			handleProfitInInherit(accDetails, superior);
		}

		/*
		 * } finally {
		 * 
		 * }
		 */

	}

	private void handleProfitInInherit(UserAccountDetails trg, UserInfo superior) {
		if(superior != null) {		
			handleProfit(trg, 
					superior, 
					superior.getUserType(),
					true);
			
			superior = userServ.querySuperior(superior);
			handleProfitInInherit(trg, superior);
		}
	}


	private void handleProfit(UserAccountDetails trg, 
			UserInfo userInfo, 
			Integer userType,
			boolean isInherit) {
		TeamPlReport profit = null;
		BigDecimal profitVal = null;
		BigDecimal settlementAmount = null;
		
		Integer orderId = trg.getOrderId();
		if(orderId == null){
			return;
		}
		
		Integer playTypeId = trg.getPlayTypeId();
		PlayType playType = playTypeServ.queryById(playTypeId);
		
		String lotteryType = trg.getLotteryType();
		
		UserTs userTs = userTsServ.queryUserTsByPlayTypeId(userInfo.getUserId(), lotteryType, playTypeId);
		
		profit = reportServ.queryProfitByUser(userInfo.getId(), 
				trg.getCreateTime(), 
				userType,
				lotteryType,
				userTs);
		
		if(profit == null) {
			profit = new TeamPlReport();
			profit.setCreateTime(trg.getCreateTime());
			profit.setUserId(userInfo.getId());
			profit.setUserName(userInfo.getUserName());
			profit.setUserType(userType);
			profit.setSettlementFlag(Constants.SettlementState.pending.getCode());
			profit.setLotteryType(lotteryType);
			profit.setPlayType(playType.getClassification());
		}
		
		if(trg.getOperationType().equals(Constants.AccOperationType.BETTING.getCode())) {
			BigDecimal consumption = profit.getConsumption() == null?
					new BigDecimal(trg.getAmount()):
						profit.getConsumption().add(new BigDecimal(trg.getAmount()));
					
			profit.setConsumption(consumption);
		}else if(trg.getOperationType().equals(Constants.AccOperationType.PAYOUT.getCode())) {
			BigDecimal returnPrize = profit.getReturnPrize() == null?
					new BigDecimal(trg.getAmount()):
						profit.getReturnPrize().add(new BigDecimal(trg.getAmount()));
					
			profit.setReturnPrize(returnPrize);
		}else if(trg.getOperationType().equals(Constants.AccOperationType.TS.getCode())) {
			BigDecimal tsAmount = profit.getTsAmount() == null?
					new BigDecimal(trg.getAmount()):
						profit.getTsAmount().add(new BigDecimal(trg.getAmount()));
					
			profit.setTsAmount(tsAmount);
		}else if(trg.getOperationType().equals(Constants.AccOperationType.ZC.getCode())) {
			BigDecimal zcAmount = profit.getZcAmount() == null?
					new BigDecimal(trg.getAmount()):
						profit.getZcAmount().add(new BigDecimal(trg.getAmount()));
					
			profit.setZcAmount(zcAmount);
		}
		
		profit.setUsedCreditLimit(userInfo.getUsedCreditAmount());
		BigDecimal usedCreditAmount = userInfo.getUsedCreditAmount()==null?new BigDecimal(0):userInfo.getUsedCreditAmount();
		Double xyAmount = userInfo.getXyAmount();
		if(xyAmount == null){
			logger.debug("");
		}
		profit.setRemainCreditLimit(new BigDecimal(xyAmount).subtract(usedCreditAmount));
		if(userType.intValue() == UserType.XY_AGENCY.getCode()){				
			profitVal = profit.getTsAmount() == null?
					new BigDecimal(0):
						profit.getTsAmount();
			
			profitVal = profitVal.add(profit.getZcAmount() == null?
					new BigDecimal(0):
						profit.getZcAmount());			
			profit.setProfit(profitVal);
			
			settlementAmount = profit.getTsAmount() == null?new BigDecimal(0):profit.getTsAmount();
			BigDecimal tempVal = profitVal.add(profit.getConsumption() == null?
					new BigDecimal(0):
						profit.getConsumption());
			tempVal = tempVal.subtract(profit.getCancelAmount() == null?
					new BigDecimal(0):
						profit.getCancelAmount());
			
			//agent team ts
//			UserTs userTs = userTsServ.queryUserTsByPlayTypeId(userInfo.getUserId(), lotteryType, playTypeId);
			BigDecimal userTsAmount = parseUserTsAmount(userTs, userInfo);
			settlementAmount = settlementAmount.add(tempVal.multiply(userTsAmount));
			
			tempVal = tempVal.subtract(profit.getReturnPrize() == null?
					new BigDecimal(0):
						profit.getReturnPrize());
			//agent team prize
			settlementAmount = settlementAmount.add(tempVal.multiply(new BigDecimal(-1)));
			tempVal = tempVal.multiply(userInfo.getZcAmount() == null?
					new BigDecimal(0):
						userInfo.getZcAmount());
			//agent team zc
			settlementAmount = settlementAmount.add(tempVal);
			
			profit.setSettlementAmount(settlementAmount);
		}else if(userType.intValue() == UserType.XY_PLAYER.getCode()){
			profitVal = profit.getReturnPrize() == null?
					new BigDecimal(0):
						profit.getReturnPrize();
			profitVal = profitVal.add(profit.getCancelAmount() == null?
											new BigDecimal(0):
												profit.getCancelAmount());
			profitVal = profitVal.subtract(profit.getConsumption() == null?
					new BigDecimal(0):
						profit.getConsumption());		
			profitVal = profitVal.add(profit.getTsAmount() == null?
					new BigDecimal(0):
						profit.getTsAmount());
			
			profit.setProfit(profitVal);
			profit.setSettlementAmount(profitVal);
		}		
		
		
		reportServ.saveOrUpdateProfit(profit);
	}

	private BigDecimal parseUserTsAmount(UserTs userTs, UserInfo userInfo) {
		CreditMarket market = userInfo.getCurrentMarket();
		CreditMarketEnum marketEnum = Constants.CreditMarketEnum.getByCode(market.getMarketId());
		BigDecimal ret = null;
		switch(marketEnum){
		case MARKET_A:{
			ret = userTs.getaTs();
			break;
		}
		case MARKET_B:{
			ret = userTs.getbTs();
			break;
		}
		case MARKET_C:{
			ret = userTs.getcTs();
			break;
		}
		case MARKET_D:{
			ret = userTs.getdTs();
			break;
		}default:{
			ret = userTs.getaTs();
		}
		}
		
		return ret;
	}

	@Override
	public void onMessage(ConsumerRecord<Integer, String> arg0) {
		String userAccDetailStr = arg0.value();
		if(StringUtils.isEmpty(userAccDetailStr)){
			return ;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			UserAccountDetails accDetails = mapper.readValue(userAccDetailStr, UserAccountDetails.class);
			exeStatistic(accDetails);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Can't read the userAccountDetails from Kafka..");
		}
	}
	
}
