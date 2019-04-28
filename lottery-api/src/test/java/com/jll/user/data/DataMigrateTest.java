package com.jll.user.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.eclipse.jetty.util.StringUtil;

import com.ehome.test.ServiceJunitBase;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Constants.UserState;
import com.jll.common.constants.Constants.UserType;
import com.jll.common.utils.StringUtils;
import com.jll.dao.PageBean;
import com.jll.entity.OldUserCard;
import com.jll.entity.TbUser;
import com.jll.entity.UserBankCard;
import com.jll.entity.UserInfo;
import com.jll.entity.UserRebate;
import com.jll.user.UserInfoService;

public class DataMigrateTest extends ServiceJunitBase{
		
	public DataMigrateTest(String name) {
		super(name);
	}	
	
	@Resource
	DataMigrateService dataMigrateServ;
	
	@Resource
	UserInfoService userServ;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		userServ = (UserInfoService)ctx.getBean("userInfoServiceImpl");
		dataMigrateServ = (DataMigrateService)ctx.getBean("dataMigrateServiceImpl");
	}

	@Override
	protected void tearDown() throws Exception {
		//super.tearDown();
	}
	
	public void testMigrateUsers(){
		PageBean<Object[]> page = new PageBean<>();
		page.setPageIndex(0);
		page.setPageSize(1000);
		
		migrateUser(page);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ItestParseSuperior(){
		/*String regFrom = "&中国龙888&&笨蛋马1688&&第七野战队&&qinghua888&&admin&"; 
		regFrom = regFrom.replace("admin", "general_agency");
		String superiors = querySuperior(regFrom);
		
		System.out.println(superiors);*/
		
		PageBean<UserInfo> userInfoPage = new PageBean<>();
		userInfoPage.setPageIndex(0);
		userInfoPage.setPageSize(1000);
		updateSuperior(userInfoPage);
	}
	
	public void ItestBindingUserCard() {
		PageBean<OldUserCard> userInfoPage = new PageBean<>();
		userInfoPage.setPageIndex(0);
		userInfoPage.setPageSize(1000);
		Map<String, Object> statInfo = new HashMap<>();
		updateUserCard(userInfoPage, statInfo);
		
		Integer totaly = (Integer)statInfo.get("totaly");
		Integer noRealName = (Integer)statInfo.get("noRealName");
		Integer noExisting = (Integer)statInfo.get("noExisting");
		System.out.println(String.format("totaly  %s", totaly));
		System.out.println(String.format("noRealName  %s", noRealName));
		System.out.println(String.format("noExisting  %s", noExisting));
	}
	
	private void updateUserCard(PageBean<OldUserCard> page, Map<String, Object> statInfo) {
		page = dataMigrateServ.queryUserCardByPagination(page);
		List<OldUserCard> users = page.getContent();
		
		for(OldUserCard userCard : users) {
			//UserInfo user = (UserInfo)single[0];
			String userName = userCard.getUserName().trim();
			String realName = null;
			
			Integer totaly = (Integer)statInfo.get("totaly");
			if(totaly == null) {
				statInfo.put("totaly", 1);
			}else {
				statInfo.put("totaly", totaly + 1);
			}
			
			if(StringUtils.isBlank(userName)) {
				Integer noRealName = (Integer)statInfo.get("noRealName");
				if(noRealName == null) {
					statInfo.put("noRealName", 1);
				}else {
					statInfo.put("noRealName", noRealName + 1);
				}
				continue;
			}
			
			String[] userNames = userName.split("--");
			if(userNames == null || userNames.length != 2) {
				Integer noRealName = (Integer)statInfo.get("noRealName");
				if(noRealName == null) {
					statInfo.put("noRealName", 1);
				}else {
					statInfo.put("noRealName", noRealName + 1);
				}
				continue;
			}
			
			userName = userNames[0].trim();
			realName = userNames[1].trim();
			UserInfo userInfo = userServ.getUserByUserName(userName);
			if(userInfo == null) {
				Integer noExisting = (Integer)statInfo.get("noExisting");
				if(noExisting == null) {
					statInfo.put("noExisting", 1);
				}else {
					statInfo.put("noExisting", noExisting + 1);
				}
				continue;
			}
			
			
			UserBankCard card = new UserBankCard();
			card.setCardNum(userCard.getBankAcc().trim());
			card.setCreateTime(new Date());
			card.setCreator(3);
			card.setState(Constants.BankCardState.ENABLED.getCode());
			card.setUserId(userInfo.getId());
			
			userInfo.setRealName(realName);
			userServ.updateUser(userInfo);
			userServ.addUserBank(userInfo.getId(), card);
		}
		
		if(page.getPageIndex() < (page.getTotalPages() - 1)) {
			page.setPageIndex(page.getPageIndex() + 1);
			updateUserCard(page, statInfo);
		}
	}

	private void migrateUser(PageBean<Object[]> page){
		page = dataMigrateServ.queryTbUserByPagination(page);
		List<Object[]> users = page.getContent();
		Date date = new Date();
		Map<String, Object> extraParams = new HashMap<>();
		
		System.out.println(String.format("current recourd count  %s", 
				users.size()));
		
		TbUser preOne = null;
		for(Object[] single : users) {
			
			TbUser tbUser = (TbUser)single[0];
			UserRebate userRebate = (UserRebate)single[1];
			Constants.UserType userType = UserType.PLAYER;
			Constants.UserState userStatus = UserState.NORMAL;
			UserInfo user_ = new UserInfo();
			String isAgent = tbUser.getIsdaili();
			String isLocked = tbUser.getIslock();
			
			
			System.out.println(String.format("userName  %s, userId  %s,  interval id  %s", 
					tbUser.getUserName(),
					tbUser.getId(),
					preOne == null?0:tbUser.getId().intValue() - preOne.getId().intValue()));
			
			preOne = tbUser;
			
			/*if(tbUser.getUserName().equals("大BOOS")) {
				System.out.println("");
			}else {
				continue;
			}*/
			
			if(!StringUtils.isBlank(isAgent)
					&& "1".equals(isAgent)) {
				userType = UserType.AGENCY;
			}
			
			if(StringUtils.isBlank(isLocked)
					&& "1".equals(isLocked)) {
				userStatus = UserState.LOCKING;
			}
			
			
			user_.setCreateTime(date);
			user_.setCreator(3);
			if(userRebate == null) {
				user_.setPlatRebate(new BigDecimal(0));
			}else if(userRebate.getRebate() >= 1) {
				user_.setPlatRebate(new BigDecimal(userRebate.getRebate() - 1));
			}else {
				user_.setPlatRebate(new BigDecimal(userRebate.getRebate()));
			}
			user_.setLoginPwd("aaa123");
			user_.setFundPwd("aaa123");
			user_.setUserName(tbUser.getUserName());
			user_.setUserType(StringUtil.isBlank(tbUser.getIsdaili())?
					0:Integer.parseInt(tbUser.getIsdaili()));
			user_.setRegIp(tbUser.getIp());
			user_.setUserType(userType.getCode());
			user_.setState(userStatus.getCode());
			if(userStatus.getCode() == UserState.LOCKING.getCode()) {
				user_.setUnlockTime(DateUtils.addYears(date, 100));
			}
			
			extraParams.put("reqIp", tbUser.getIp());
			extraParams.put("activitymoney", tbUser.getActivitymoney());
			extraParams.put("icemoney", tbUser.getIcemoney());
			extraParams.put("usermoney", tbUser.getUsermoney());
			
			
			userServ.migrateUser(user_, extraParams);
		}
		
		if(page.getPageIndex() < (page.getTotalPages() - 1)) {
			page.setPageIndex(page.getPageIndex() + 1);
			migrateUser(page);
		}
	}

	
	private void updateSuperior(PageBean<UserInfo> page){
		page = userServ.queryUserInfoByPagination(page);
		List<UserInfo> users = page.getContent();
		
		for(UserInfo user : users) {
			//UserInfo user = (UserInfo)single[0];
			
			TbUser tbUser = dataMigrateServ.queryTbuserByUsername(user.getUserName());
			if(tbUser == null) {
				continue;
			}
			
			Map<String, Object> ret = querySuperior(tbUser.getRegfrom());
			
			String superiors = ret.get("superiors") == null?null:(String)ret.get("superiors");
			UserInfo superior = ret.get("superior") == null?null:(UserInfo)ret.get("superior");
			if(StringUtils.isBlank(superiors) || superior == null) {
				System.out.println(tbUser.getRegfrom());
				continue;
			}
			
			BigDecimal rebate = superior.getPlatRebate().subtract(user.getPlatRebate());
			user.setRebate(rebate);
			user.setSuperior(superiors);
			userServ.updateUser(user);
		}
		
		if(page.getPageIndex() < (page.getTotalPages() - 1)) {
			page.setPageIndex(page.getPageIndex() + 1);
			updateSuperior(page);
		}
	}
	
	
	private Map<String, Object> querySuperior(String regfrom) {
		//&凯迪拉克战队&&qinghua888&&admin&
		regfrom = regfrom.replace("admin", "general_agency");
		String[] superiorUserNames = regfrom.split("\\&");
		StringBuffer superiorUserIds = new StringBuffer();
		Map<String, Object> ret = new HashMap<String, Object>();
		
		if(superiorUserNames != null && superiorUserNames.length > 0) {
			for(String userName : superiorUserNames) {
				if(StringUtils.isBlank(userName)) {
					continue;
				}
				UserInfo userInfo = userServ.getUserByUserName(userName);
				if(userInfo == null) {
					return ret;
				}
				
				if(ret.get("superior") == null) {
					ret.put("superior", userInfo);
				}
				
				if(superiorUserIds.length() == 0) {
					superiorUserIds.append(userInfo.getId());				
				}else {
					superiorUserIds.append(",").append(userInfo.getId());	
				}
			}
		}
		
		ret.put("superiors", superiorUserIds.toString());
		return ret;
	}
	
	
	
	
	
}