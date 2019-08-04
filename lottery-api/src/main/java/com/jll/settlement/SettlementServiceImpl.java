package com.jll.settlement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Constants.SettlementState;
import com.jll.common.constants.Constants.UserType;
import com.jll.common.constants.Constants.WalletState;
import com.jll.common.constants.Constants.WalletType;
import com.jll.common.utils.DateUtil;
import com.jll.dao.PageBean;
import com.jll.entity.UserAccount;
import com.jll.entity.UserInfo;
import com.jll.entity.UserSettlement;
import com.jll.report.TReportService;
import com.jll.user.UserInfoExtService;
import com.jll.user.UserInfoService;
import com.jll.user.wallet.WalletService;

@Service
@Transactional
public class SettlementServiceImpl implements SettlementService
{
	private Logger logger = Logger.getLogger(SettlementServiceImpl.class);

	@Resource
	UserSettlementService userSettlementServ;
	
	@Resource
	UserInfoService userServ;
	
	@Resource
	TReportService reportServ;
	
	@Resource
	CacheRedisService cacheServ;
	
	@Resource
	WalletService walletServ;
	
	@Resource
	UserInfoExtService userExtServ;
	
	@Override
	public void exeSettlement() {
		String keyLock = Constants.KEY_LOCK_SETTLEMENT;
		Date lockStart = null;
		Date lockEnd = null;
		
		logger.debug(String.format("Thread ID %s try to settlement  , waitting for locker ", 
				Thread.currentThread().getId()));
		//加互斥锁，防止多进程同步执行
		if(cacheServ.lock(keyLock, keyLock, Constants.LOCK_SETTLEMENT_EXPIRED)) {
			lockStart = new Date();
			
			logger.debug(String.format("Thread ID %s try to settlement, successfully obtain locker ", 
					Thread.currentThread().getId()));
			try {
				PageBean<UserInfo> page = new PageBean<>();
				List<UserInfo> users = null;
				
				page = userServ.queryXYUsers(page);
				
				
				while((page.getPageIndex() - 1) < page.getTotalPages()) {
					users = page.getContent();
					
					for(UserInfo user : users) {
						boolean isPenndingExisting = userSettlementServ.isPendingExisting(user);
						if(isPenndingExisting) {
							continue;
						}
						
						produceStatement(user);
					}
					
					page.setPageIndex(page.getPageIndex()+1);
					page = userServ.queryXYUsers(page);
				}
				
				
				
			}finally {
				lockEnd = new Date();
				logger.debug(String.format("Thread ID %s release locker , consume %s ms", 
						Thread.currentThread().getId(),
						lockEnd.getTime() - lockStart.getTime()));
				cacheServ.releaseLock(keyLock);
			}
		}else {
			logger.debug(String.format("Thread ID %s try to stat profit, but failed to obtain locker ", Thread.currentThread().getId()));
		}
		
	}

	private void produceStatement(UserInfo user) {
		if(user.getUserType().intValue() == UserType.XY_AGENCY.getCode()) {
			Date today = new Date();
			Date yesterday = DateUtil.addDay(today, -1);
			UserAccount mainAcc = walletServ.queryUserAccount(user.getId(), WalletType.MAIN_WALLET.getCode());
			String xyAmount = userExtServ.queryFiledByName(user.getId(), "xyAmount");
			UserSettlement settlement = new UserSettlement();
			settlement.setAvailableCredit(mainAcc.getBalance());
			settlement.setCreateTime(today);
			settlement.setSetDate(yesterday);
			settlement.setState(SettlementState.pending.getCode());
			settlement.setCreditLimit(Double.parseDouble(xyAmount));
			settlement.setUserId(user.getId());
			settlement.setUserName(user.getUserName());
			
			userSettlementServ.saveSettlement(settlement);
		}		
		
		Map<String, Object> param = new HashMap<>();
		param.put("userId", user.getId());
		Map<String, Object> accs = walletServ.queryByUserIdUserAccount(param);
		if(accs != null) {
			List accList = (List)accs.get("data");
			if(accList != null && accList.size() > 0) {
				for(Object accObj : accList) {
					UserAccount acc = (UserAccount)accObj;
					if(acc.getState().intValue() == WalletState.FROZEN.getCode()) {
						continue;
					}
					acc.setState(WalletState.FROZEN.getCode());
					
					walletServ.updateWallet(acc);
				}
			}
		}
	}
}
