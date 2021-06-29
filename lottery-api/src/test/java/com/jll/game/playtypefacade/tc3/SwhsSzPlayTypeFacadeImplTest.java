package com.jll.game.playtypefacade.tc3;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;

import com.ehome.test.ServiceJunitBase;
import com.jll.common.constants.Constants;
import com.jll.common.utils.DateUtil;
import com.jll.entity.Issue;
import com.jll.entity.OrderInfo;
import com.jll.entity.UserInfo;
import com.jll.game.playtype.PlayTypeFacade;
import com.jll.game.playtypefacade.PlayTypeFactory;

public class SwhsSzPlayTypeFacadeImplTest extends ServiceJunitBase{
		
	public SwhsSzPlayTypeFacadeImplTest(String name) {
		super(name);
	}	
	
	@Resource
	PlayTypeFacade playTypeFacade;
	
	final String facadeName = "sz|数值/swhs|三位和数/fs";
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		playTypeFacade = PlayTypeFactory.getInstance().getPlayTypeFacade(facadeName);
	}

	@Override
	protected void tearDown() throws Exception {
		//super.tearDown();
	}
	
	public void ItestParseBetNumber(){
		String betNum = "0;1;2;3;4;5;6;7;8;9";
		Date startDate = new Date();
		List<Map<String, String>> ret = playTypeFacade.parseBetNumber(betNum);
		
		Date endDate = new Date();
		System.out.println(String.format("create Arragnge %s , take over %s ms", 
				ret.size(),
				endDate.getTime() - startDate.getTime()));
		
		Assert.assertNotNull(ret);
		 
		Assert.assertTrue(ret.size() == 2160);
		
		betNum = "0";
		startDate = new Date();
		ret = playTypeFacade.parseBetNumber(betNum);
		
		endDate = new Date();
		System.out.println(String.format("create Arragnge %s , take over %s ms", 
				ret.size(),
				endDate.getTime() - startDate.getTime()));
		
		Assert.assertNotNull(ret);
		 
		Assert.assertTrue(ret.size() == 216);
	}
	
	public void testValidBetNum_valid_0_6(){
		String betNum = "0-6";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
	}
	
	public void testValidBetNum_valid_10(){
		String betNum = "10";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
	}
		
	public void testValidBetNum_valid_21_27(){
		String betNum = "21-27";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
	}
	
	public void testValidBetNum_valid_wrong_num(){
		String betNum = "00";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
	}
	
	public void testValidBetNum_tooLong(){
		String betNum = "001";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
	}
	
	public void testValidBetNum_tooShort(){
		String betNum = "1";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
	}
	
	public void testValidBetNum_extend_up_limit(){
		String betNum = "28";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
	}
	
	public void testIsMatchWinningNum_winning_0_6(){
		Date startTime = new Date();
		String betNum = "0-6";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("0,1,2");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertTrue(ret);
		
	}
	
	public void testIsMatchWinningNum_lost_0_6(){
		Date startTime = new Date();
		String betNum = "0-6";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("7,1,2");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertFalse(ret);
		
	}
	
	public void testIsMatchWinningNum_win_10(){
		Date startTime = new Date();
		String betNum = "10";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("1,2,7");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertTrue(ret);
		
	}
	
	public void testIsMatchWinningNum_lost_10(){
		Date startTime = new Date();
		String betNum = "10";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("6,7,4");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertFalse(ret);
		
	}
	
	
	public void testIsMatchWinningNum_win_21_27(){
		Date startTime = new Date();
		String betNum = "21-27";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("9,8,7");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertTrue(ret);
		
	}
	
	public void testIsMatchWinningNum_lost_21_27(){
		Date startTime = new Date();
		String betNum = "21-27";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("6,7,4");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertFalse(ret);
		
	}
	
	public void testCalPrize_lost_0_6(){
		UserInfo user = new UserInfo();
		user.setId(4);
		user.setUserName("xy_user_001");
		Date startTime = new Date();
		String betNum = "0-6";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("0,1,9");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		order.setIssueId(2);
		order.setTimes(1);
		order.setPrizeRate(new BigDecimal(1.9));
		order.setPattern(new BigDecimal(1));
		Map<String, Object> ret = playTypeFacade.calPrize(issue, order, user);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(new BigDecimal(0).compareTo(new BigDecimal((Float)ret.get(Constants.KEY_WIN_AMOUNT))) == 0);
	}
	

	public void testCalPrize_win_0_6(){
		UserInfo user = new UserInfo();
		user.setId(4);
		user.setUserName("xy_user_001");
		Date startTime = new Date();
		String betNum = "0-6";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("0,1,2");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		order.setIssueId(2);
		order.setTimes(1);
		order.setPrizeRate(new BigDecimal(1.9));
		order.setPattern(new BigDecimal(1));
		Map<String, Object> ret = playTypeFacade.calPrize(issue, order, user);
		Assert.assertNotNull(ret);
		BigDecimal winAmount = new BigDecimal(String.valueOf(ret.get(Constants.KEY_WIN_AMOUNT)));
		winAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		Assert.assertTrue(new BigDecimal("1.9").compareTo(winAmount) == 0);
	}
	
	
	
	
	public void testCalPrize_lost_10(){
		UserInfo user = new UserInfo();
		user.setId(4);
		user.setUserName("xy_user_001");
		Date startTime = new Date();
		String betNum = "10";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("0,7,2");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		order.setIssueId(2);
		order.setTimes(1);
		order.setPrizeRate(new BigDecimal(1.9));
		order.setPattern(new BigDecimal(1));
		Map<String, Object> ret = playTypeFacade.calPrize(issue, order, user);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(new BigDecimal(0).compareTo(new BigDecimal((Float)ret.get(Constants.KEY_WIN_AMOUNT))) == 0);
	}
	

	public void testCalPrize_win_10(){
		UserInfo user = new UserInfo();
		user.setId(4);
		user.setUserName("xy_user_001");
		Date startTime = new Date();
		String betNum = "10";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("7,1,2");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		order.setIssueId(2);
		order.setTimes(1);
		order.setPrizeRate(new BigDecimal(1.9));
		order.setPattern(new BigDecimal(1));
		Map<String, Object> ret = playTypeFacade.calPrize(issue, order, user);
		Assert.assertNotNull(ret);
		BigDecimal winAmount = new BigDecimal(String.valueOf(ret.get(Constants.KEY_WIN_AMOUNT)));
		winAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		Assert.assertTrue(new BigDecimal("1.9").compareTo(winAmount) == 0);
	}
	
	

	
	public void testCalPrize_lost_21_26(){
		UserInfo user = new UserInfo();
		user.setId(4);
		user.setUserName("xy_user_001");
		Date startTime = new Date();
		String betNum = "21-26";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("1,7,9");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		order.setIssueId(2);
		order.setTimes(1);
		order.setPrizeRate(new BigDecimal(1.9));
		order.setPattern(new BigDecimal(1));
		Map<String, Object> ret = playTypeFacade.calPrize(issue, order, user);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(new BigDecimal(0).compareTo(new BigDecimal((Float)ret.get(Constants.KEY_WIN_AMOUNT))) == 0);
	}
	

	public void testCalPrize_win_21_26(){
		UserInfo user = new UserInfo();
		user.setId(4);
		user.setUserName("xy_user_001");
		Date startTime = new Date();
		String betNum = "21-26";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("7,6,9");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		order.setIssueId(2);
		order.setTimes(1);
		order.setPrizeRate(new BigDecimal(1.9));
		order.setPattern(new BigDecimal(1));
		Map<String, Object> ret = playTypeFacade.calPrize(issue, order, user);
		Assert.assertNotNull(ret);
		BigDecimal winAmount = new BigDecimal(String.valueOf(ret.get(Constants.KEY_WIN_AMOUNT)));
		winAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		Assert.assertTrue(new BigDecimal("1.9").compareTo(winAmount) == 0);
	}
}