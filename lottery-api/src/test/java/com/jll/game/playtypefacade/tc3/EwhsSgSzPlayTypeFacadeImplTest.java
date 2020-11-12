package com.jll.game.playtypefacade.tc3;

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
import com.jll.game.playtype.PlayTypeFacade;
import com.jll.game.playtypefacade.PlayTypeFactory;

public class EwhsSgSzPlayTypeFacadeImplTest extends ServiceJunitBase{
		
	public EwhsSgSzPlayTypeFacadeImplTest(String name) {
		super(name);
	}	
	
	@Resource
	PlayTypeFacade playTypeFacade;
	
	final String facadeName = "sz|数值/sg|十个/ewhs|二位和数/fs";
	
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
	
	public void testValidBetNum_valid_0_4(){
		String betNum = "0-4";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
	}
	
	public void testValidBetNum_valid_5(){
		String betNum = "5";
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
	
	public void testValidBetNum_valid_14_18(){
		String betNum = "14-18";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
	}
	
	public void testValidBetNum_valid_wrong_num_uplimit(){
		String betNum = "19";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
	}
	
	public void testValidBetNum_valid_wrong_num_downlimit(){
		String betNum = "0";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
	}
	
	public void testValidBetNum_tooLong(){
		String betNum = "0001";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
	}
	
	
	public void testIsMatchWinningNum_winning_0_4(){
		Date startTime = new Date();
		String betNum = "0-4";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("2,1,0");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertTrue(ret);
		
	}
	
	public void testIsMatchWinningNum_winning_14_18(){
		Date startTime = new Date();
		String betNum = "14-18";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("0,8,9");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertTrue(ret);
		
	}
	
	public void testIsMatchWinningNum_winning_5(){
		Date startTime = new Date();
		String betNum = "5";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("2,1,4");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertTrue(ret);
		
	}
	
	public void testIsMatchWinningNum_winning_13(){
		Date startTime = new Date();
		String betNum = "13";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("5,4,9");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertTrue(ret);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void testIsMatchWinningNum_lost_0_4(){
		Date startTime = new Date();
		String betNum = "0-4";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("2,6,9");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertFalse(ret);
		
	}
	
	public void testIsMatchWinningNum_lost_14_18(){
		Date startTime = new Date();
		String betNum = "14-18";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("0,3,9");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertFalse(ret);
		
	}
	
	public void testIsMatchWinningNum_lost_5(){
		Date startTime = new Date();
		String betNum = "5";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("2,9,4");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertFalse(ret);
		
	}
	
	public void testIsMatchWinningNum_lost_13(){
		Date startTime = new Date();
		String betNum = "13";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("6,8,9");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertFalse(ret);
		
	}
	
	
}