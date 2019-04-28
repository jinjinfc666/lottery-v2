package com.jll.game.playtype;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;

import com.ehome.test.ServiceJunitBase;
import com.jll.common.constants.Constants;
import com.jll.common.utils.DateUtil;
import com.jll.common.utils.MathUtil;
import com.jll.entity.Issue;
import com.jll.entity.OrderInfo;
import com.jll.entity.UserInfo;
import com.jll.game.playtypefacade.PlayTypeFactory;

public class EleIn5QwZwPlayTypeFacadeImplTest extends ServiceJunitBase{
		
	public EleIn5QwZwPlayTypeFacadeImplTest(String name) {
		super(name);
	}	
	
	@Resource
	PlayTypeFacade playTypeFacade;
	
	final String facadeName = "qwx|趣味型/qwczw|趣味猜中位/fs";
	
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
		String betNum = "03";
		
		List<Map<String, String>> ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 3360);
		
		
		betNum = "09";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 3360);
		
		/*for(Map<String, String> temp : ret) {
			String tempNum = temp.get(Constants.KEY_FACADE_BET_NUM_SAMPLE);
			System.out.println(String.format("current value  %s", tempNum));
		}*/
		
		betNum = "04";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 7560);
		
		betNum = "05";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 10800);
		
		betNum = "06";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 12000);
		
		betNum = "07";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 10800);
		
		betNum = "08";
		
		ret = playTypeFacade.parseBetNumber(betNum);
		Assert.assertNotNull(ret);
		
		Assert.assertTrue(ret.size() == 7560);
	}
	
	public void ItestIsMatchWinningNum_winning(){
		String betNum = "010204";
		Issue issue = new Issue();
		issue.setRetNum("01,02,04,09,06");
		
		OrderInfo order = new OrderInfo();
		//order.setIssueId(issueId);
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.isMatchWinningNum(issue, order);
		Assert.assertNotNull(ret);
		
	}
	
	
	public void ItestValidBetNum_invalid_betnum_(){
		String betNum = "10";
		OrderInfo order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
		
		betNum = "02";
		order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
		
		betNum = " ";		
		order = new OrderInfo();
		
		order.setBetNum(betNum); 
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
		
		
		betNum = "0908070605040302";		
		order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
		
		
		betNum = "100908070605040302";		
		order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
				
		betNum = "06,05,04,03";
		order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
		
		betNum = "0605040303";
		order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
		
		betNum = "06050403";
		order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertFalse(ret);
		
	}
	
	public void ItestValidBetNum_valid_betnum_(){
		String betNum = "03";
		OrderInfo order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		boolean ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
		
		betNum = "04";
		order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
		
		betNum = "05";
		order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
		
		betNum = "06";
		order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
		
		betNum = "07";
		order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
		
		betNum = "08";
		order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
		
		betNum = "09";
		order = new OrderInfo();
		
		order.setBetNum(betNum);
		
		ret = playTypeFacade.validBetNum(order);
		Assert.assertTrue(ret);
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
	
	public void ItestPreProcessNumber(){
		Map<String, Object> params = new HashMap<>();
		String betNum = "03";
		Integer times = 1;
		Float monUnit = 1.0F;
		Integer playType = 1;
		String lottoType = "gd11x5";
				
		UserInfo user = new UserInfo();
		user.setId(14);
		user.setPlatRebate(new BigDecimal(14.0F));
		
		Float betAmount = null;
		Integer betTotal = null;
		Float maxWinAmount = null;
		Float maxWinAmountCompare = null;
		BigDecimal singleBettingPrize = null;
		
		params.put("betNum", betNum);
		params.put("times", times);
		params.put("monUnit", monUnit);
		params.put("playType", playType);
		params.put("lottoType", lottoType);
		
		Map<String, Object> ret = playTypeFacade.preProcessNumber(params, user);
		
		betAmount = (Float)ret.get("betAmount");
		betTotal = (Integer)ret.get("betTotal");
		maxWinAmount = (Float)ret.get("maxWinAmount");
		singleBettingPrize = (BigDecimal)ret.get("singleBettingPrize");
		Assert.assertTrue(new BigDecimal(betAmount).compareTo(new BigDecimal(1.0F)) == 0);
		Assert.assertTrue(betTotal == 1);
		
		maxWinAmountCompare = MathUtil.multiply(1, 
				times, 
				Float.class);
		maxWinAmountCompare = MathUtil.multiply(maxWinAmountCompare, 
				monUnit, 
				Float.class);
		maxWinAmountCompare = MathUtil.multiply(maxWinAmountCompare, 
				singleBettingPrize.floatValue(), 
				Float.class);
		
		Assert.assertTrue(new BigDecimal(maxWinAmount).compareTo(new BigDecimal(maxWinAmountCompare)) == 0);
		
		
		params = new HashMap<>();
		betNum = "04";
		times = 1;
		monUnit = 1.0F;
		playType = 1;
		lottoType = "gd11x5";
				
		user = new UserInfo();
		user.setId(14);
		user.setPlatRebate(new BigDecimal(14.0F));
		
		betAmount = null;
		betTotal = null;
		maxWinAmount = null;
		maxWinAmountCompare = null;
		singleBettingPrize = null;
		
		params.put("betNum", betNum);
		params.put("times", times);
		params.put("monUnit", monUnit);
		params.put("playType", playType);
		params.put("lottoType", lottoType);
		
		ret = playTypeFacade.preProcessNumber(params, user);
		
		betAmount = (Float)ret.get("betAmount");
		betTotal = (Integer)ret.get("betTotal");
		maxWinAmount = (Float)ret.get("maxWinAmount");
		singleBettingPrize = (BigDecimal)ret.get("singleBettingPrize");
		Assert.assertTrue(new BigDecimal(betAmount).compareTo(new BigDecimal(1.0F)) == 0);
		Assert.assertTrue(betTotal == 1);
		
		maxWinAmountCompare = MathUtil.multiply(1, 
				times, 
				Float.class);
		maxWinAmountCompare = MathUtil.multiply(maxWinAmountCompare, 
				monUnit, 
				Float.class);
		maxWinAmountCompare = MathUtil.multiply(maxWinAmountCompare, 
				singleBettingPrize.floatValue(), 
				Float.class);
		
		Assert.assertTrue(new BigDecimal(maxWinAmount).compareTo(new BigDecimal(maxWinAmountCompare)) == 0);
		
		
		params = new HashMap<>();
		betNum = "05";
		times = 1;
		monUnit = 1.0F;
		playType = 1;
		lottoType = "gd11x5";
				
		user = new UserInfo();
		user.setId(14);
		user.setPlatRebate(new BigDecimal(14.0F));
		
		betAmount = null;
		betTotal = null;
		maxWinAmount = null;
		maxWinAmountCompare = null;
		singleBettingPrize = null;
		
		params.put("betNum", betNum);
		params.put("times", times);
		params.put("monUnit", monUnit);
		params.put("playType", playType);
		params.put("lottoType", lottoType);
		
		ret = playTypeFacade.preProcessNumber(params, user);
		
		betAmount = (Float)ret.get("betAmount");
		betTotal = (Integer)ret.get("betTotal");
		maxWinAmount = (Float)ret.get("maxWinAmount");
		singleBettingPrize = (BigDecimal)ret.get("singleBettingPrize");
		Assert.assertTrue(new BigDecimal(betAmount).compareTo(new BigDecimal(1.0F)) == 0);
		Assert.assertTrue(betTotal == 1);
		
		maxWinAmountCompare = MathUtil.multiply(1, 
				times, 
				Float.class);
		maxWinAmountCompare = MathUtil.multiply(maxWinAmountCompare, 
				monUnit, 
				Float.class);
		maxWinAmountCompare = MathUtil.multiply(maxWinAmountCompare, 
				singleBettingPrize.floatValue(), 
				Float.class);
		
		Assert.assertTrue(new BigDecimal(maxWinAmount).compareTo(new BigDecimal(maxWinAmountCompare)) == 0);	
		
		params = new HashMap<>();
		betNum = "06";
		times = 1;
		monUnit = 1.0F;
		playType = 1;
		lottoType = "gd11x5";
				
		user = new UserInfo();
		user.setId(14);
		user.setPlatRebate(new BigDecimal(14.0F));
		
		betAmount = null;
		betTotal = null;
		maxWinAmount = null;
		maxWinAmountCompare = null;
		singleBettingPrize = null;
		
		params.put("betNum", betNum);
		params.put("times", times);
		params.put("monUnit", monUnit);
		params.put("playType", playType);
		params.put("lottoType", lottoType);
		
		ret = playTypeFacade.preProcessNumber(params, user);
		
		betAmount = (Float)ret.get("betAmount");
		betTotal = (Integer)ret.get("betTotal");
		maxWinAmount = (Float)ret.get("maxWinAmount");
		singleBettingPrize = (BigDecimal)ret.get("singleBettingPrize");
		Assert.assertTrue(new BigDecimal(betAmount).compareTo(new BigDecimal(1.0F)) == 0);
		Assert.assertTrue(betTotal == 1);
		
		maxWinAmountCompare = MathUtil.multiply(1, 
				times, 
				Float.class);
		maxWinAmountCompare = MathUtil.multiply(maxWinAmountCompare, 
				monUnit, 
				Float.class);
		maxWinAmountCompare = MathUtil.multiply(maxWinAmountCompare, 
				singleBettingPrize.floatValue(), 
				Float.class);
		
		Assert.assertTrue(new BigDecimal(maxWinAmount).compareTo(new BigDecimal(maxWinAmountCompare)) == 0);	
		
		params = new HashMap<>();
		betNum = "07";
		times = 1;
		monUnit = 1.0F;
		playType = 1;
		lottoType = "gd11x5";
				
		user = new UserInfo();
		user.setId(14);
		user.setPlatRebate(new BigDecimal(14.0F));
		
		betAmount = null;
		betTotal = null;
		maxWinAmount = null;
		maxWinAmountCompare = null;
		singleBettingPrize = null;
		
		params.put("betNum", betNum);
		params.put("times", times);
		params.put("monUnit", monUnit);
		params.put("playType", playType);
		params.put("lottoType", lottoType);
		
		ret = playTypeFacade.preProcessNumber(params, user);
		
		betAmount = (Float)ret.get("betAmount");
		betTotal = (Integer)ret.get("betTotal");
		maxWinAmount = (Float)ret.get("maxWinAmount");
		singleBettingPrize = (BigDecimal)ret.get("singleBettingPrize");
		Assert.assertTrue(new BigDecimal(betAmount).compareTo(new BigDecimal(1.0F)) == 0);
		Assert.assertTrue(betTotal == 1);
		
		maxWinAmountCompare = MathUtil.multiply(1, 
				times, 
				Float.class);
		maxWinAmountCompare = MathUtil.multiply(maxWinAmountCompare, 
				monUnit, 
				Float.class);
		maxWinAmountCompare = MathUtil.multiply(maxWinAmountCompare, 
				singleBettingPrize.floatValue(), 
				Float.class);
		
		Assert.assertTrue(new BigDecimal(maxWinAmount).compareTo(new BigDecimal(maxWinAmountCompare)) == 0);	
		
		
		params = new HashMap<>();
		betNum = "08";
		times = 1;
		monUnit = 1.0F;
		playType = 1;
		lottoType = "gd11x5";
				
		user = new UserInfo();
		user.setId(14);
		user.setPlatRebate(new BigDecimal(14.0F));
		
		betAmount = null;
		betTotal = null;
		maxWinAmount = null;
		maxWinAmountCompare = null;
		singleBettingPrize = null;
		
		params.put("betNum", betNum);
		params.put("times", times);
		params.put("monUnit", monUnit);
		params.put("playType", playType);
		params.put("lottoType", lottoType);
		
		ret = playTypeFacade.preProcessNumber(params, user);
		
		betAmount = (Float)ret.get("betAmount");
		betTotal = (Integer)ret.get("betTotal");
		maxWinAmount = (Float)ret.get("maxWinAmount");
		singleBettingPrize = (BigDecimal)ret.get("singleBettingPrize");
		Assert.assertTrue(new BigDecimal(betAmount).compareTo(new BigDecimal(1.0F)) == 0);
		Assert.assertTrue(betTotal == 1);
		
		maxWinAmountCompare = MathUtil.multiply(1, 
				times, 
				Float.class);
		maxWinAmountCompare = MathUtil.multiply(maxWinAmountCompare, 
				monUnit, 
				Float.class);
		maxWinAmountCompare = MathUtil.multiply(maxWinAmountCompare, 
				singleBettingPrize.floatValue(), 
				Float.class);
		
		Assert.assertTrue(new BigDecimal(maxWinAmount).compareTo(new BigDecimal(maxWinAmountCompare)) == 0);	
		
		
		
		params = new HashMap<>();
		betNum = "09";
		times = 1;
		monUnit = 1.0F;
		playType = 1;
		lottoType = "gd11x5";
				
		user = new UserInfo();
		user.setId(14);
		user.setPlatRebate(new BigDecimal(14.0F));
		
		betAmount = null;
		betTotal = null;
		maxWinAmount = null;
		maxWinAmountCompare = null;
		singleBettingPrize = null;
		
		params.put("betNum", betNum);
		params.put("times", times);
		params.put("monUnit", monUnit);
		params.put("playType", playType);
		params.put("lottoType", lottoType);
		
		ret = playTypeFacade.preProcessNumber(params, user);
		
		betAmount = (Float)ret.get("betAmount");
		betTotal = (Integer)ret.get("betTotal");
		maxWinAmount = (Float)ret.get("maxWinAmount");
		singleBettingPrize = (BigDecimal)ret.get("singleBettingPrize");
		Assert.assertTrue(new BigDecimal(betAmount).compareTo(new BigDecimal(1.0F)) == 0);
		Assert.assertTrue(betTotal == 1);
		
		maxWinAmountCompare = MathUtil.multiply(1, 
				times, 
				Float.class);
		maxWinAmountCompare = MathUtil.multiply(maxWinAmountCompare, 
				monUnit, 
				Float.class);
		maxWinAmountCompare = MathUtil.multiply(maxWinAmountCompare, 
				singleBettingPrize.floatValue(), 
				Float.class);
		
		Assert.assertTrue(new BigDecimal(maxWinAmount).compareTo(new BigDecimal(maxWinAmountCompare)) == 0);	
	}
	
	
	
	public void testCalPrize_03(){
		String winningNum = "06,02,01,03,04";
		String betNum = "03";
		Map<String, Object> ret;
		BigDecimal prize = null;
		
		Issue issue = new Issue();
		issue.setRetNum(winningNum);
		
		
		OrderInfo order = new OrderInfo();
		order.setIssueId(issue.getId());
		order.setBetNum(betNum);
		//hszux|后三组选|zsfs--组三复式
		order.setPlayType(13);
		order.setTimes(1);
		order.setIsZh(0);
		order.setPattern(new BigDecimal(1));
		
		
		UserInfo user = new UserInfo();
		user.setId(14);
		user.setUserName("test001");
		user.setUserType(Constants.UserType.PLAYER.getCode());
		user.setPlatRebate(new BigDecimal(14.0F));
		ret = playTypeFacade.calPrize(issue, order, user);
		prize = new BigDecimal((Float)ret.get(Constants.KEY_WIN_AMOUNT));
		Assert.assertNotNull(prize);
				
		Assert.assertTrue(prize.compareTo(new BigDecimal(16.5375F)) == 0);
		
		
		winningNum = "06,02,07,03,04";
		betNum = "04";		
		issue = new Issue();
		issue.setRetNum(winningNum);
		
		
		order = new OrderInfo();
		order.setIssueId(issue.getId());
		order.setBetNum(betNum);
		//hszux|后三组选|zsfs--组三复式
		order.setPlayType(13);
		order.setTimes(1);
		order.setIsZh(0);
		order.setPattern(new BigDecimal(1));
		
		
		user = new UserInfo();
		user.setId(14);
		user.setUserName("test001");
		user.setUserType(Constants.UserType.PLAYER.getCode());
		user.setPlatRebate(new BigDecimal(14.0F));
		ret = playTypeFacade.calPrize(issue, order, user);
		prize = new BigDecimal((Float)ret.get(Constants.KEY_WIN_AMOUNT));
		Assert.assertNotNull(prize);
				
		Assert.assertTrue(prize.compareTo(new BigDecimal(7.3473F)) == 0);
		
		
		winningNum = "06,02,07,03,05";
		betNum = "05";		
		issue = new Issue();
		issue.setRetNum(winningNum);
		
		
		order = new OrderInfo();
		order.setIssueId(issue.getId());
		order.setBetNum(betNum);
		//hszux|后三组选|zsfs--组三复式
		order.setPlayType(13);
		order.setTimes(1);
		order.setIsZh(0);
		order.setPattern(new BigDecimal(1));
		
		
		user = new UserInfo();
		user.setId(14);
		user.setUserName("test001");
		user.setUserType(Constants.UserType.PLAYER.getCode());
		user.setPlatRebate(new BigDecimal(14.0F));
		ret = playTypeFacade.calPrize(issue, order, user);
		prize = new BigDecimal((Float)ret.get(Constants.KEY_WIN_AMOUNT));
		Assert.assertNotNull(prize);
				
		Assert.assertTrue(prize.compareTo(new BigDecimal(5.1446F)) == 0);
		
		
		winningNum = "06,02,07,03,08";
		betNum = "06";		
		issue = new Issue();
		issue.setRetNum(winningNum);
		
		
		order = new OrderInfo();
		order.setIssueId(issue.getId());
		order.setBetNum(betNum);
		//hszux|后三组选|zsfs--组三复式
		order.setPlayType(13);
		order.setTimes(1);
		order.setIsZh(0);
		order.setPattern(new BigDecimal(1));
		
		
		user = new UserInfo();
		user.setId(14);
		user.setUserName("test001");
		user.setUserType(Constants.UserType.PLAYER.getCode());
		user.setPlatRebate(new BigDecimal(14.0F));
		ret = playTypeFacade.calPrize(issue, order, user);
		prize = new BigDecimal((Float)ret.get(Constants.KEY_WIN_AMOUNT));
		Assert.assertNotNull(prize);
				
		Assert.assertTrue(prize.compareTo(new BigDecimal(4.629F)) == 0);
		
		
		
		
		winningNum = "08,02,07,03,09";
		betNum = "07";		
		issue = new Issue();
		issue.setRetNum(winningNum);
		
		
		order = new OrderInfo();
		order.setIssueId(issue.getId());
		order.setBetNum(betNum);
		//hszux|后三组选|zsfs--组三复式
		order.setPlayType(13);
		order.setTimes(1);
		order.setIsZh(0);
		order.setPattern(new BigDecimal(1));
		
		
		user = new UserInfo();
		user.setId(14);
		user.setUserName("test001");
		user.setUserType(Constants.UserType.PLAYER.getCode());
		user.setPlatRebate(new BigDecimal(14.0F));
		ret = playTypeFacade.calPrize(issue, order, user);
		prize = new BigDecimal((Float)ret.get(Constants.KEY_WIN_AMOUNT));
		Assert.assertNotNull(prize);
				
		Assert.assertTrue(prize.compareTo(new BigDecimal(5.1446F)) == 0);
		
		
		
		
		winningNum = "08,02,07,10,09";
		betNum = "08";		
		issue = new Issue();
		issue.setRetNum(winningNum);
		
		
		order = new OrderInfo();
		order.setIssueId(issue.getId());
		order.setBetNum(betNum);
		//hszux|后三组选|zsfs--组三复式
		order.setPlayType(13);
		order.setTimes(1);
		order.setIsZh(0);
		order.setPattern(new BigDecimal(1));
		
		
		user = new UserInfo();
		user.setId(14);
		user.setUserName("test001");
		user.setUserType(Constants.UserType.PLAYER.getCode());
		user.setPlatRebate(new BigDecimal(14.0F));
		ret = playTypeFacade.calPrize(issue, order, user);
		prize = new BigDecimal((Float)ret.get(Constants.KEY_WIN_AMOUNT));
		Assert.assertNotNull(prize);
				
		Assert.assertTrue(prize.compareTo(new BigDecimal(7.3473F)) == 0);
		
		
		
		
		
		
		winningNum = "10,11,07,08,09";
		betNum = "09";		
		issue = new Issue();
		issue.setRetNum(winningNum);
		
		
		order = new OrderInfo();
		order.setIssueId(issue.getId());
		order.setBetNum(betNum);
		//hszux|后三组选|zsfs--组三复式
		order.setPlayType(13);
		order.setTimes(1);
		order.setIsZh(0);
		order.setPattern(new BigDecimal(1));
		
		
		user = new UserInfo();
		user.setId(14);
		user.setUserName("test001");
		user.setUserType(Constants.UserType.PLAYER.getCode());
		user.setPlatRebate(new BigDecimal(14.0F));
		ret = playTypeFacade.calPrize(issue, order, user);
		prize = new BigDecimal((Float)ret.get(Constants.KEY_WIN_AMOUNT));
		Assert.assertNotNull(prize);
				
		Assert.assertTrue(prize.compareTo(new BigDecimal(16.5375F)) == 0);
	}
	
}