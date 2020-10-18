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
import com.jll.common.constants.Constants.TrgUserAccDetailsFlag;
import com.jll.common.constants.Constants.UserType;
import com.jll.dao.PageBean;
import com.jll.entity.TeamPlReport;
import com.jll.entity.TrgUserAccountDetails;
import com.jll.entity.UserAccountDetails;
import com.jll.entity.UserInfo;
import com.jll.game.mesqueue.kafka.KafkaConsumer;
import com.jll.report.TReportService;
import com.jll.user.UserInfoService;

@Service
@Transactional
public class StatProfitServiceImpl implements StatProfitService, KafkaConsumer
{
	private Logger logger = Logger.getLogger(StatProfitServiceImpl.class);

	@Resource
	TrgUserAccountDetailsService trgUserAccDetailServ;
	
	@Resource
	UserInfoService userServ;
	
	@Resource
	TReportService reportServ;
	
	@Resource
	CacheRedisService cacheServ;
	
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

		// 普通玩家的盈利直接算上级，对于代理类型用户的盈利需要从本身开始算累加盈利
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
		
		profit = reportServ.queryProfitByUser(userInfo.getId(), 
				trg.getCreateTime(), 
				userType);
		
		if(profit == null) {
			profit = new TeamPlReport();
			profit.setCreateTime(trg.getCreateTime());
			profit.setUserId(userInfo.getId());
			profit.setUserName(userInfo.getUserName());
			profit.setUserType(userType);
			profit.setSettlementFlag(Constants.SettlementState.pending.getCode());
		}
		
		if(trg.getOperationType().equals(Constants.AccOperationType.DEPOSIT.getCode())) {
			BigDecimal deposit = profit.getDeposit() == null?
					new BigDecimal(trg.getAmount()):
						profit.getDeposit().add(new BigDecimal(trg.getAmount()));
					
			profit.setDeposit(deposit);
		}else if(trg.getOperationType().equals(Constants.AccOperationType.WITHDRAW.getCode())) {
			BigDecimal withdraw = profit.getWithdrawal() == null?
					new BigDecimal(trg.getAmount()):
						profit.getWithdrawal().add(new BigDecimal(trg.getAmount()));
			profit.setWithdrawal(withdraw);
		}else if(trg.getOperationType().equals(Constants.AccOperationType.BETTING.getCode())) {
			BigDecimal consumption = profit.getConsumption() == null?
					new BigDecimal(trg.getAmount()):
						profit.getConsumption().add(new BigDecimal(trg.getAmount()));
					
			profit.setConsumption(consumption);
		}else if(trg.getOperationType().equals(Constants.AccOperationType.TRANSFER.getCode())) {
			if(trg.getRemark().equals(Constants.Transfer.IN.getCode())) {
				BigDecimal transfer = profit.getTransfer() == null?
						new BigDecimal(trg.getAmount()):
							profit.getTransfer().add(new BigDecimal(trg.getAmount()));
						
				profit.setTransfer(transfer);
			}else if(trg.getRemark().equals(Constants.Transfer.OUT.getCode())) {
				BigDecimal transferOut = profit.getTransferOut() == null?
						new BigDecimal(trg.getAmount()):
							profit.getTransferOut().add(new BigDecimal(trg.getAmount()));
						
				profit.setTransferOut(transferOut);
			}
			
		}else if(trg.getOperationType().equals(Constants.AccOperationType.PAYOUT.getCode())) {
			BigDecimal returnPrize = profit.getReturnPrize() == null?
					new BigDecimal(trg.getAmount()):
						profit.getReturnPrize().add(new BigDecimal(trg.getAmount()));
					
			profit.setReturnPrize(returnPrize);
		}else if(trg.getOperationType().equals(Constants.AccOperationType.REBATE.getCode())) {
			BigDecimal rebate = profit.getRebate() == null?
					new BigDecimal(trg.getAmount()):
						profit.getRebate().add(new BigDecimal(trg.getAmount()));
					
			profit.setRebate(rebate);
		}else if(trg.getOperationType().equals(Constants.AccOperationType.REFUND.getCode())) {
			BigDecimal cancelAmount = profit.getCancelAmount() == null?
					new BigDecimal(trg.getAmount()):
						profit.getCancelAmount().add(new BigDecimal(trg.getAmount()));
					
			profit.setCancelAmount(cancelAmount);
		}else if(trg.getOperationType().equals(Constants.AccOperationType.SYS_DEDUCTION.getCode())) {
			BigDecimal deduction = profit.getDeduction() == null?
					new BigDecimal(trg.getAmount()):
						profit.getDeduction().add(new BigDecimal(trg.getAmount()));
					
			profit.setDeduction(deduction);
		}else if(trg.getOperationType().equals(Constants.AccOperationType.DEPOSIT_CASH.getCode())
				&& (!isInherit || (isInherit && userType.intValue() != UserType.SM_AGENCY.getCode()))) {
			BigDecimal sysBonus = profit.getSysBonus() == null?
					new BigDecimal(trg.getAmount()):
						profit.getSysBonus().add(new BigDecimal(trg.getAmount()));
					
			profit.setSysBonus(sysBonus);
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
			settlementAmount = settlementAmount.add(tempVal.multiply(userInfo.getTsAmount() == null?
					new BigDecimal(0):
						userInfo.getTsAmount()));
			
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
