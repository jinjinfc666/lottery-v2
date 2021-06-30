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

import com.fasterxml.jackson.core.JsonProcessingException;
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

		if (!Constants.AccOperationType.TRANSFER.getCode().equals(accDetails.getOperationType()) 
				&& !Constants.AccOperationType.TS.getCode().equals(accDetails.getOperationType())) {
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
		
		UserTs userTs = userTsServ.queryUserTsByPlayTypeId(userInfo.getId(), lotteryType, playTypeId);
		if(userTs == null){
			return ;
		}
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
			profit.setPlayType(playType.getBriefCla());
		}
		
		if(trg.getOperationType().equals(Constants.AccOperationType.BETTING.getCode())) {
			BigDecimal consumption = profit.getConsumption() == null?
					new BigDecimal(trg.getAmount()).setScale(4, BigDecimal.ROUND_HALF_UP):
						profit.getConsumption().add(new BigDecimal(trg.getAmount())).setScale(4, BigDecimal.ROUND_HALF_UP);
					
			profit.setConsumption(consumption);
		}else if(trg.getOperationType().equals(Constants.AccOperationType.PAYOUT.getCode())) {
			BigDecimal returnPrize = profit.getReturnPrize() == null?
					new BigDecimal(trg.getAmount()).setScale(4, BigDecimal.ROUND_HALF_UP):
						profit.getReturnPrize().add(new BigDecimal(trg.getAmount())).setScale(4, BigDecimal.ROUND_HALF_UP);
					
			profit.setReturnPrize(returnPrize);
		}else if(trg.getOperationType().equals(Constants.AccOperationType.TS.getCode())) {
			if(userType.intValue() == UserType.XY_AGENCY.getCode()){
				BigDecimal userTsAmountRate = parseUserTsAmount(userTs, userInfo);
				BigDecimal consumptionAmount = profit.getConsumption();
				BigDecimal userTsAmount = consumptionAmount.multiply(userTsAmountRate).setScale(4, BigDecimal.ROUND_HALF_UP);
				profit.setTsAmount(userTsAmount);
				
				BigDecimal currTsAmount = new BigDecimal(trg.getAmount());
				BigDecimal zcAmount = profit.getZcAmount()==null?new BigDecimal(0):profit.getZcAmount();
				//利润 =代理退水 - 本次退水 + 占成
				profitVal = userTsAmount.add(zcAmount).subtract(currTsAmount).setScale(4, BigDecimal.ROUND_HALF_UP);
				profit.setProfit(profitVal);
				
			}else{
				BigDecimal tsAmount = profit.getTsAmount() == null?
						new BigDecimal(trg.getAmount()).setScale(4, BigDecimal.ROUND_HALF_UP):
							profit.getTsAmount().add(new BigDecimal(trg.getAmount())).setScale(4, BigDecimal.ROUND_HALF_UP);
				profit.setTsAmount(tsAmount);
			}
			
		}else if(trg.getOperationType().equals(Constants.AccOperationType.ZC.getCode())) {
			BigDecimal zcAmount = profit.getZcAmount() == null?
					new BigDecimal(trg.getAmount()).setScale(4, BigDecimal.ROUND_HALF_UP):
						profit.getZcAmount().add(new BigDecimal(trg.getAmount())).setScale(4, BigDecimal.ROUND_HALF_UP);
					
			profit.setZcAmount(zcAmount);
			
		}
		
		profit.setUsedCreditLimit(userInfo.getUsedCreditAmount());
		BigDecimal usedCreditAmount = userInfo.getUsedCreditAmount()==null?new BigDecimal(0):userInfo.getUsedCreditAmount();
		Double xyAmount = userInfo.getXyAmount();
		profit.setRemainCreditLimit(new BigDecimal(xyAmount).subtract(usedCreditAmount));
		if(userType.intValue() == UserType.XY_AGENCY.getCode()){
			if(!trg.getOperationType().equals(Constants.AccOperationType.TS.getCode())){
				profitVal = profit.getTsAmount() == null?
						new BigDecimal(0):
							profit.getTsAmount().setScale(4, BigDecimal.ROUND_HALF_UP);
				
				profitVal = profitVal.add(profit.getZcAmount() == null?
						new BigDecimal(0):
							profit.getZcAmount()).setScale(4, BigDecimal.ROUND_HALF_UP);			
				profit.setProfit(profitVal);
			}
			
			
			BigDecimal tsAmount = profit.getTsAmount() == null?new BigDecimal(0):profit.getTsAmount().setScale(4, BigDecimal.ROUND_HALF_UP);
//			logger.info(String.format("init ts amount to settlement  %s", settlementAmount));
			/*BigDecimal tempVal = profitVal.add(profit.getConsumption() == null?
					new BigDecimal(0):
						profit.getConsumption()).setScale(4, BigDecimal.ROUND_HALF_UP);*/
			/*tempVal = tempVal.subtract(profit.getCancelAmount() == null?
					new BigDecimal(0):
						profit.getCancelAmount()).setScale(4, BigDecimal.ROUND_HALF_UP);*/
			
			//agent team ts
//			UserTs userTs = userTsServ.queryUserTsByPlayTypeId(userInfo.getUserId(), lotteryType, playTypeId);
			/*BigDecimal userTsAmount = parseUserTsAmount(userTs, userInfo);
			settlementAmount = settlementAmount.add(tempVal.multiply(userTsAmount).setScale(4, BigDecimal.ROUND_HALF_UP)).setScale(4, BigDecimal.ROUND_HALF_UP);
			logger.info(String.format("plus new ts amount to settlement  %s, new ts amount = consumption %s x tsRate %s = %s", settlementAmount, tempVal, userTsAmount,tempVal.multiply(userTsAmount).setScale(4, BigDecimal.ROUND_HALF_UP)));*/
			/*BigDecimal tempVal = profit.getReturnPrize() == null?
					new BigDecimal(0):
						profit.getReturnPrize().setScale(4, BigDecimal.ROUND_HALF_UP);*/
			BigDecimal returnPrize = profit.getReturnPrize() == null?
					new BigDecimal(0):
						profit.getReturnPrize().setScale(4, BigDecimal.ROUND_HALF_UP);
			BigDecimal consumption = profit.getConsumption() == null?
					new BigDecimal(0):
						profit.getConsumption().setScale(4, BigDecimal.ROUND_HALF_UP);
			
			BigDecimal zcAmount = profit.getZcAmount() == null?new BigDecimal(0).setScale(4, BigDecimal.ROUND_HALF_UP):profit.getZcAmount().setScale(4, BigDecimal.ROUND_HALF_UP);
//			settlementAmount = returnPrize.subtract(consumption).setScale(4, BigDecimal.ROUND_HALF_UP);
			//agent team prize
//			settlementAmount = settlementAmount.add(tempVal.multiply(new BigDecimal(-1))).setScale(4, BigDecimal.ROUND_HALF_UP);
//			logger.info(String.format("plus (return prize - consumption) to settlement  %s, return prize %s - consumption %s = %s", settlementAmount, tempVal.multiply(new BigDecimal(-1)).setScale(4, BigDecimal.ROUND_HALF_UP)));
//			BigDecimal zcAmount = new BigDecimal(tempVal.doubleValue());
			/*tempVal = tempVal.multiply(userInfo.getZcAmount() == null?
					new BigDecimal(0):
						userInfo.getZcAmount()).setScale(4, BigDecimal.ROUND_HALF_UP);*/
			//agent team zc
			settlementAmount = zcAmount.add(tsAmount).add(returnPrize).subtract(consumption).setScale(4, BigDecimal.ROUND_HALF_UP);
			logger.info(String.format("settlementAmount %s = zcAmount %s + tsAmount %s + returnPrize %s - consumption %s", settlementAmount, zcAmount,tsAmount, returnPrize,consumption));
			profit.setSettlementAmount(settlementAmount);
		}else if(userType.intValue() == UserType.XY_PLAYER.getCode()){
			profitVal = profit.getReturnPrize() == null?
					new BigDecimal(0):
						profit.getReturnPrize().setScale(4, BigDecimal.ROUND_HALF_UP);
			profitVal = profitVal.add(profit.getCancelAmount() == null?
											new BigDecimal(0):
												profit.getCancelAmount()).setScale(4, BigDecimal.ROUND_HALF_UP);
			profitVal = profitVal.subtract(profit.getConsumption() == null?
					new BigDecimal(0):
						profit.getConsumption()).setScale(4, BigDecimal.ROUND_HALF_UP);		
			profitVal = profitVal.add(profit.getTsAmount() == null?
					new BigDecimal(0):
						profit.getTsAmount()).setScale(4, BigDecimal.ROUND_HALF_UP);
			
			profit.setProfit(profitVal);
			profit.setSettlementAmount(profitVal);
		}		
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			String userAccountDetails = mapper.writeValueAsString(trg);
			String profitStr = mapper.writeValueAsString(profit);
			logger.info(userAccountDetails);
			logger.info(profitStr);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
