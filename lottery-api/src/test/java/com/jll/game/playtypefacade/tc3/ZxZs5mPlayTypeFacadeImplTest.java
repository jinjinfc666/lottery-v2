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

public class ZxZs5mPlayTypeFacadeImplTest extends ServiceJunitBase{
		
	public ZxZs5mPlayTypeFacadeImplTest(String name) {
		super(name);
	}	
	
	@Resource
	PlayTypeFacade playTypeFacade;
	
	final String facadeName = "5m|5码/zx3|组选3/zx|组选/fs";
	
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
	
	public void testValidBetNum_valid_01234(){
		String betNum = "01234";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
	}
	
	public void testValidBetNum_valid_56789(){
		String betNum = "56789";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
	}
	
	public void testValidBetNum_valid_wrong_num(){
		String betNum = "02";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
	}
	
	public void testValidBetNum_tooLong(){
		String betNum = "012345";
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
	}
	
	
	public void testIsMatchWinningNum_winning_01234(){
		Date startTime = new Date();
		String betNum = "01234";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("0,1,1");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertTrue(ret);
		
	}
	
	public void testIsMatchWinningNum_winning_56789(){
		Date startTime = new Date();
		String betNum = "56789";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("6,7,7");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertTrue(ret);
		
	}
	
	public void testIsMatchWinningNum_lost_01234(){
		Date startTime = new Date();
		String betNum = "01234";
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
	
	public void testIsMatchWinningNum_lost_56789(){
		Date startTime = new Date();
		String betNum = "56789";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("0,6,4");
		issue.setStartTime(startTime);
		issue.setState(Constants.IssueState.LOTTO_DARW.getCode());
		issue.setEndTime(DateUtil.addMinutes(startTime, 10));
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertFalse(ret);
		
	}
	
	public void testIsMatchWinningNum_lost_zs(){
		Date startTime = new Date();
		String betNum = "56789";
		Issue issue = new Issue();
		issue.setIssueNum("");
		issue.setLotteryType(Constants.LottoType.TC3.getCode());
		issue.setRetNum("4,6,6");
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