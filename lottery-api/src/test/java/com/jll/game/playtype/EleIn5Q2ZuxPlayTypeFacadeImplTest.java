package com.jll.game.playtype;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;

import com.ehome.test.ServiceJunitBase;
import com.jll.common.constants.Constants;
import com.jll.entity.Issue;
import com.jll.entity.OrderInfo;
import com.jll.game.playtypefacade.PlayTypeFactory;

public class EleIn5Q2ZuxPlayTypeFacadeImplTest extends ServiceJunitBase{
		
	public EleIn5Q2ZuxPlayTypeFacadeImplTest(String name) {
		super(name);
	}	
	
	@Resource
	PlayTypeFacade playTypeFacade;
	
	final String facadeName = "em|二码/qezux|前二组选/fs-ds";
	
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
		String betNum = "0102";
		
		List<Map<String, String>> ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 10080);
					
		betNum = "010203";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 23520);
		
		betNum = "01020304";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 36120);
		
		
		betNum = "0102030405";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 45720);
		
		betNum = "010203040506";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 51720);
		
		betNum = "01020304050607";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 54600);
		
		betNum = "0102030405060708";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 55440);
		
		betNum = "010203040506070809";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 55440);
		
		betNum = "01020304050607080910";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 55440);
		
		betNum = "0102030405060708091011";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 55440);
				
	}
	
	public void ItestIsMatchWinningNum_winning(){
		String betNum = "0102030405060708";
		Issue issue = new Issue();
		issue.setRetNum("01,02,04,03,05");
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertTrue(ret);
		
	}
	
	public void testIsMatchWinningNum_winning_10110902070605(){
		String betNum = "10110902070605";
		Issue issue = new Issue();
		issue.setRetNum("03,01,07,08,11");
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertFalse(ret);
		
	}
	
	
	public void ItestValidBetNum_invalid_betnum_(){
		String betNum = "12";
		OrderInfo order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
		
		betNum = " ";		
		order = new OrderInfo();
		
		order.setBetNum(betNum); 
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
		
		betNum = "00";
		order = new OrderInfo();
		
		order.setBetNum(betNum); 
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
		
		betNum = "01";
		order = new OrderInfo();
		
		order.setBetNum(betNum); 
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
		
		
		betNum = "060504030202";		
		order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
						
		betNum = "06,05,04,03,02,01";
		order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
		
	}
	
	public void ItestValidBetNum_valid_betnum_(){
		String betNum = "01020304050607";
		for(int i = 8; i < 12; i++) {
			if(i < 10) {
				betNum += "0" + Integer.toString(i);
			}else {
				betNum += Integer.toString(i);
			}
			OrderInfo order = new OrderInfo();
			
			order.setBetNum(betNum);
			
			boolean ret = playTypeFacade.validBetNum(order);
			Assert.assertTrue(ret);
			
			betNum = "01020304050607";
		}
	}
	 

	public void ItestObtainSampleBetNumber(){
		int counter = 0;
		int maxCounter = 1000;
		String betNum = null;
		boolean isWinning = false;
		boolean isValid = false;
		while(counter < maxCounter) {
			betNum = playTypeFacade.obtainSampleBetNumber();
			
			System.out.println(String.format("current bet number   %s", 
					betNum));
			
			String winningNum = obtainWinningNum(betNum);
			OrderInfo order = new OrderInfo();
			order.setBetNum(betNum);
			
			Issue issue = new Issue();
			issue.setRetNum(winningNum);
			
			isValid = playTypeFacade.validBetNum(order);
			isWinning = playTypeFacade.isMatchWinningNum(issue, order);
			
			System.out.println(String.format("winingNum  %s   current bet number   %s   isVliad  %s    isWnning  %s", 
					winningNum,
					betNum,
					isValid,
					isWinning));
			
			Assert.assertTrue(isValid);
			counter++;
		}
	}
	
	public void ItestObtainSampleBetNumber_050402(){
		int counter = 0;
		int maxCounter = 1000;
		String betNum = null;
		boolean isWinning = false;
		boolean isValid = false;
		//while(counter < maxCounter) {
			//betNum = playTypeFacade.obtainSampleBetNumber();
			betNum = "050402";
			System.out.println(String.format("current bet number   %s", 
					betNum));
			
			String winningNum = obtainWinningNum(betNum);
			OrderInfo order = new OrderInfo();
			order.setBetNum(betNum);
			
			Issue issue = new Issue();
			issue.setRetNum(winningNum);
			
			isValid = playTypeFacade.validBetNum(order);
			isWinning = playTypeFacade.isMatchWinningNum(issue, order);
			
			System.out.println(String.format("winingNum  %s   current bet number   %s   isVliad  %s    isWnning  %s", 
					winningNum,
					betNum,
					isValid,
					isWinning));
			
			Assert.assertTrue(isValid);
			counter++;
		//}
	}
	
	private String obtainWinningNum(String betNum) {
		StringBuffer winningNumBuffer = new StringBuffer();
		List<Map<String, String>> maps = playTypeFacade.parseBetNumber(betNum);
		if(maps != null && maps.size() > 0) {
			Map<String, String> row = maps.get(0);
			String winningNum = row.get(Constants.KEY_FACADE_BET_NUM_SAMPLE);
			for(int i = 0; i< winningNum.length();) { 
				String bit = winningNum.substring(i, i + 2);
				if(!",".equals(bit)) {
					winningNumBuffer.append(bit).append(",");
				}
				
				i += 2;
			}
			winningNumBuffer.delete(winningNumBuffer.length() - 1, winningNumBuffer.length());
		}
		
		return winningNumBuffer.toString();
	}
}