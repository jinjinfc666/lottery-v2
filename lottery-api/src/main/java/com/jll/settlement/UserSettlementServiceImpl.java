package com.jll.settlement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.common.constants.Constants.SettlementState;
import com.jll.common.constants.Constants.UserType;
import com.jll.common.constants.Constants.WalletState;
import com.jll.dao.PageBean;
import com.jll.entity.UserAccount;
import com.jll.entity.UserInfo;
import com.jll.entity.UserSettlement;
import com.jll.user.UserInfoService;
import com.jll.user.wallet.WalletService;

@Service
@Transactional
public class UserSettlementServiceImpl implements UserSettlementService
{
	private Logger logger = Logger.getLogger(UserSettlementServiceImpl.class);

	@Resource
	UserSettlementDao settlementDao;
	
	@Resource
	WalletService walletServ;
	
	@Resource
	UserInfoService userServ;
	
	@Override
	public void saveSettlement(UserSettlement settlement) {
		settlementDao.saveSettlement(settlement);
	}

	@Override
	public PageBean<UserSettlement> queryUserSettlement(Integer pageIndex, Integer pageSize, String userName, Integer setStatus, String startTime, String endTime) {
		return settlementDao.queryUserSettlement(pageIndex, pageSize, userName, setStatus, startTime, endTime);
	}

	@Override
	public void performUserSettlement(Integer settlementId) {
		List<UserInfo> children = null;
		UserSettlement settlement = settlementDao.queryUserSettlementById(settlementId);
		settlement.setState(SettlementState.HANDLED.getCode());
		
		settlementDao.saveSettlement(settlement);
		
		Map<String, Object> param = new HashMap<>();
		param.put("userId", settlement.getUserId());
		Map<String, Object> accs = walletServ.queryByUserIdUserAccount(param);
		if(accs != null) {
			List accList = (List)accs.get("data");
			if(accList != null && accList.size() > 0) {
				for(Object accObj : accList) {
					UserAccount acc = (UserAccount)accObj;
					if(acc.getState().intValue() != WalletState.FROZEN.getCode()) {
						continue;
					}
					acc.setState(WalletState.NORMAL.getCode());
					acc.setBalance(settlement.getCreditLimit());
					walletServ.updateWallet(acc);
				}
			}
		}
		
		param.clear();
		param.put("proxyName", settlement.getUserName());
		param.put("pageSize", 10000);
		param.put("pageIndex", 1);
		Map<String,Object> childrenMap = userServ.queryAllUserInfo(param);
		
		if(childrenMap == null 
				|| childrenMap.size() == 0 
				|| childrenMap.get("data") == null 
				|| ((PageBean<UserInfo>)childrenMap.get("data")) == null
				|| ((PageBean<UserInfo>)childrenMap.get("data")).getContent() == null
				|| ((PageBean<UserInfo>)childrenMap.get("data")).getContent().size() == 0) {
			return ;
		}
		
		children = ((PageBean<UserInfo>)childrenMap.get("data")).getContent();
				
		for(UserInfo user : children) {
			if(user.getUserType().intValue() == UserType.XY_PLAYER.getCode()) {
				param = new HashMap<>();
				param.put("userId", user.getId());
				accs = walletServ.queryByUserIdUserAccount(param);
				if(accs != null) {
					List accList = (List)accs.get("data");
					if(accList != null && accList.size() > 0) {
						for(Object accObj : accList) {
							UserAccount acc = (UserAccount)accObj;
							if(acc.getState().intValue() != WalletState.FROZEN.getCode()) {
								continue;
							}
							acc.setState(WalletState.NORMAL.getCode());
							acc.setBalance(0D);
							walletServ.updateWallet(acc);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isSettleMentExisting(Integer settlementId) {
		return settlementDao.isIdExisting(settlementId);
	}

	@Override
	public UserSettlement queryPendingExisting(UserInfo user) {
		return settlementDao.queryPendingExisting(user);
	}

	
}
