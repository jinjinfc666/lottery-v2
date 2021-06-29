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

public class BdwSwPlayTypeFacadeImplTest extends ServiceJunitBase{
		
	public BdwSwPlayTypeFacadeImplTest(String name) {
		super(name);
	}	
	
	@Resource
	PlayTypeFacade playTypeFacade;
	
	final String facadeName = "sz|数值/sw|三位/bdw|不定位/fs";
	
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
	
	public void testValidBetNum_valid(){
		String betNum = "000";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
	}
	
	public void testValidBetNum_tooLong(){
		String betNum = "0000";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
	}
	
	public void testValidBetNum_shortLength(){
		String betNum = "00";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
	}
	public void testIsMatchWinningNum_winning(){
		Date startTime = new Date();
		String betNum = "111";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("1,2,4");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertTrue(ret);
		
	}
	
	public void testIsMatchWinningNum_winning_2bit(){
		Date startTime = new Date();
		String betNum = "122";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("1,2,4");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertTrue(ret);
		
	}
	
	public void testIsMatchWinningNum_winning_3bit(){
		Date startTime = new Date();
		String betNum = "124";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("1,2,4");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertTrue(ret);
		
	}
	
	public void testIsMatchWinningNum_lost(){
		Date startTime = new Date();
		String betNum = "001";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("1,2,4");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertFalse(ret);
		
	}
	
	public void testCalPrize_lost_000(){
		UserInfo user = new UserInfo();
		user.setId(4);
		user.setUserName("xy_user_001");
		Date startTime = new Date();
		String betNum = "000";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("3,1,2");
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
	

	public void testCalPrize_win_000(){
		UserInfo user = new UserInfo();
		user.setId(4);
		user.setUserName("xy_user_001");
		Date startTime = new Date();
		String betNum = "000";
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
	
	
	
	
	
	public void testCalPrize_lost_123(){
		UserInfo user = new UserInfo();
		user.setId(4);
		user.setUserName("xy_user_001");
		Date startTime = new Date();
		String betNum = "123";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("4,1,2");
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
	

	public void testCalPrize_win_123(){
		UserInfo user = new UserInfo();
		user.setId(4);
		user.setUserName("xy_user_001");
		Date startTime = new Date();
		String betNum = "123";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("3,1,2");
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