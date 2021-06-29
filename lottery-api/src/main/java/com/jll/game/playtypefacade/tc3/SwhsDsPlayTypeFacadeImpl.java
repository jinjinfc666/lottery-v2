package com.jll.game.playtypefacade.tc3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.jll.common.constants.Constants;
import com.jll.common.utils.MathUtil;
import com.jll.common.utils.StringUtils;
import com.jll.entity.Issue;
import com.jll.entity.OrderInfo;
import com.jll.entity.UserInfo;
import com.jll.game.playtypefacade.DefaultPlayTypeFacadeImpl;

/**
 * 龙      1 
 * 虎       0
 * @author Administrator
 *
 */
public class SwhsDsPlayTypeFacadeImpl extends DefaultPlayTypeFacadeImpl {
	
	private Logger logger = Logger.getLogger(SwhsDsPlayTypeFacadeImpl.class);
	
	protected String playTypeDesc = "ds|单双/swhs|三位和数/fs";
	
	private final int ODD = 1;
	
	private final int EVEN = 0;
	
	private String betNumOptions = "00,01";
		
	//private String[] oddsArray = {"01","03","05","07","09"};
	
	private String oddsStr = "13579";
	
	private String eventStr = "02468";
	
	//private String[] evenArray = {"00", "02","04","06","08"};
	
	@Override
	public boolean isMatchWinningNum(Issue issue, OrderInfo order) {
		//开奖号码的每一位
				String[] winNumSet = null;
				//投注号码的每个位的号码，可能多个号码
				//String[] betNumSet = new String[3];
				//每次点击选号按钮所选号码，多个所选号码以;分割
				String[] betNumMul= null;
				String betNum = null;
				String winNum = null;
				int winNumFinal = -1;
				
				winNum = issue.getRetNum();
				betNum = order.getBetNum();
				//winNum = winNum.substring(0,3);
				winNumSet = winNum.split(",");
				//betNumSet = betNum.split(",");
				betNumMul = betNum.split(";");
				Integer sum = Integer.valueOf(winNumSet[0])+ Integer.valueOf(winNumSet[1]) + Integer.valueOf(winNumSet[2]);
				if(sum % 2 != 0) {
					winNumFinal = ODD;
				}else {
					winNumFinal = EVEN;
				}
				
				for(String temp : betNumMul) {
					if(temp.contains("0" + String.valueOf(winNumFinal))) {
						return true;
					}
				}
						
				return false;
	}

	@Override
	public Map<String, Object> preProcessNumber(Map<String, Object> params, UserInfo user) {		
		Map<String, Object> ret = new HashMap<>();
		String betNum = (String)params.get("betNum");
		Integer times = (Integer)params.get("times");
		Float monUnit = (Float)params.get("monUnit");
		Integer playType = (Integer)params.get("playType");
		String lottoType = (String)params.get("lottoType");
		Float prizePattern = userServ.calPrizePattern(user, lottoType);
		BigDecimal winningRate = calWinningRate();
		BigDecimal singleBettingPrize = calSingleBettingPrize(prizePattern, winningRate);
		String[] betNumSet = null;
		int betTotal = 0;
		Float betAmount = 0F;
		Float maxWinAmount = 0F;
		int winBetTotal = 0;
		int maxWinBetTotal = 0;
		
		betNumSet = betNum.split(";");
		for(String subBetNum : betNumSet) {						
			int len = subBetNum.length() / 2;
			betTotal += MathUtil.combination(1, len);
		}
		
		maxWinBetTotal = betNumSet.length;
		winBetTotal = maxWinBetTotal;
		
		betAmount = MathUtil.multiply(betTotal, times, Float.class);
		betAmount = MathUtil.multiply(betAmount, monUnit, Float.class);
		maxWinAmount = MathUtil.multiply(winBetTotal, 
				times, 
				Float.class);
		maxWinAmount = MathUtil.multiply(maxWinAmount, 
				monUnit, 
				Float.class);
		maxWinAmount = MathUtil.multiply(maxWinAmount, 
				singleBettingPrize.floatValue(), 
				Float.class);
		
		ret.put("playType", playType);
		ret.put("betAmount", betAmount);
		ret.put("maxWinAmount", maxWinAmount);
		ret.put("betTotal", betTotal);
		ret.put("singleBettingPrize", singleBettingPrize);
		return ret;
	}

	

	@Override
	public String getPlayTypeDesc() {
		return playTypeDesc;
	}

	@Override
	public boolean validBetNum(OrderInfo order) {
		String betNum = null;
		String[] betNumSet = null;
		
		betNum = order.getBetNum();
		if(StringUtils.isBlank(betNum)) {
			return false;
		}
		
		betNumSet = betNum.split(";");
				
		for(String temp : betNumSet) {
			if(StringUtils.isBlank(temp)) {
				return false;
			}			
							
			Map<String, String> tempBits = splitBetNum(temp);
			if(temp.length() != 2) {
				return false;
			}
			
			Iterator<String> ite = tempBits.keySet().iterator();
			while(ite.hasNext()) {
				String key = ite.next();
				if(!betNumOptions.contains(key)) {
					return false;
				}
			}
			
		}
		
		return true;
	}

	@Override
	public Map<String, Object> calPrize(Issue issue, OrderInfo order, UserInfo user) {
		Map<String, Object> ret = new HashMap<String, Object>();
		// 开奖号码的每一位
		String[] winNumSet = null;
		// 投注号码的每个位的号码，可能多个号码
		String[] betNumSet = new String[3];
		// 每次点击选号按钮所选号码，多个所选号码以;分割
		String[] betNumMul = null;
		String betNum = null;
		String winNum = null;
		int winningBetAmount = 0;
		Float betAmount = 0F;
		Float maxWinAmount = 0F;
		Integer times = order.getTimes();
		BigDecimal monUnit = order.getPattern();
		BigDecimal singleBettingPrize = null;
		int winNumFinal = -1;
		
		//1700 --- 1960
//		Float prizePattern = userServ.calPrizePattern(user, issue.getLotteryType());
		BigDecimal winningRate = order.getPrizeRate();
//		singleBettingPrize =  calSingleBettingPrize(prizePattern, winningRate);
		
		winNum = issue.getRetNum();
		betNum = order.getBetNum();
		//winNum = winNum.substring(0, 5);
		winNumSet = winNum.split(",");
		betNumMul = betNum.split(";");
		
		Integer sum = Integer.valueOf(winNumSet[0])+ Integer.valueOf(winNumSet[1]) + Integer.valueOf(winNumSet[2]);
		if(sum % 2 != 0) {
			winNumFinal = ODD;
		}else {
			winNumFinal = EVEN;
		}
		
		for(String singleSel : betNumMul) {
			//betNumSet = singleSel.split(",");
			if(singleSel.contains("0" + String.valueOf(winNumFinal))) {
				winningBetAmount++;
			}
		}
		
		betAmount = MathUtil.multiply(winningBetAmount, times, Float.class);
		betAmount = MathUtil.multiply(betAmount, monUnit.floatValue(), Float.class);
		maxWinAmount = MathUtil.multiply(betAmount, winningRate.floatValue(), Float.class);
		
		ret.put(Constants.KEY_WINNING_BET_TOTAL, winningBetAmount);
		ret.put(Constants.KEY_WIN_AMOUNT, maxWinAmount);
		ret.put(Constants.KEY_SINGLE_BETTING_PRIZE, singleBettingPrize);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.jll.game.playtype.PlayTypeFacade#calWinningRate()
	 * 
	 * 1/1000
	 */
	@Override
	public BigDecimal calWinningRate() {
		BigDecimal winningRate = null;
		BigDecimal winCount = null;
		BigDecimal totalCount = null;
		Double tempVal = Double.parseDouble(Long.toString(MathUtil.combination(1, 5)));
		Double tempVal1 = Double.parseDouble(Long.toString(MathUtil.combination(1, 10)));
						
		winCount = new BigDecimal(tempVal);
		totalCount = new BigDecimal(tempVal1);
		winningRate = winCount.divide(totalCount, 5, BigDecimal.ROUND_HALF_UP);
		return winningRate;
	}
	
	@Override
	public String obtainSampleBetNumber(){
		String[] optionsArray = {"00","01"};
		Random random = new Random();
		StringBuffer betNum = new StringBuffer();
		
		int bitLen = random.nextInt(2) + 1;
		
		for(int i = 0 ; i < bitLen; i++) {
			int bitIndx = random.nextInt(2);
			
			while(true) {
				if(betNum.toString().contains(optionsArray[bitIndx])) {
					bitIndx = random.nextInt(2);
					continue;
				}
				betNum.append(optionsArray[bitIndx]);
				break;
			}
		}
		
		return betNum.toString();
	}
	
	
	private Map<String, String> splitBetNum(String temp) {
		Map<String, String> bits = new HashMap<String, String>();
		int len = temp.length();
						
		if(len % 2 != 0) {
			return bits;
		}
		
		for(int i = 0; i < len;) {
			String bit = temp.substring(i, i + 2);
			bits.put(bit, bit);
			
			i += 2;
		}
		
		return bits;
	}
	
	
	@Override
	public List<Map<String, String>> parseBetNumber(String betNum){
		Date currDate = new Date();
		List<Map<String, String>> betNumList = new ArrayList<>();
		String[] betNumArray = betNum.split(";");
		Map<String, String> bitBetNumMap = null;
		StringBuffer buffer = new StringBuffer();
		
		for(String singleBetNumArray : betNumArray) {
			bitBetNumMap = splitBetNum(singleBetNumArray);
			Iterator<String> ite = bitBetNumMap.keySet().iterator();
			while(ite.hasNext()) {
				String key = ite.next();
				for(int i = 0; i < 10; i++) {				
					for(int ii = 0; ii < 10;ii++){
						if(i == ii)
							continue;
						for(int iii = 0; iii < 10;iii++){
							if(iii == ii || iii == i)							
								continue;
									buffer.delete(0, buffer.length());								
									
									boolean isOdds = (i + ii + iii) % 2  == 0;
									boolean isOddsBetNum = Integer.parseInt(key) == ODD;
									if((isOdds && isOddsBetNum)
											|| (!isOdds && !isOddsBetNum)){
										buffer.append(i).append(ii).append(iii);	
										
										Map<String, String> row = new HashMap<String, String>();
										row.put(Constants.KEY_FACADE_BET_NUM, buffer.toString());
										row.put(Constants.KEY_FACADE_PATTERN, buffer.toString());
										row.put(Constants.KEY_FACADE_BET_NUM_SAMPLE, buffer.toString());
										betNumList.add(row);
										continue;
									}
						}
					}
				}
			}			
		}
		
		Date lastDate = new Date();
		logger.debug(String.format("totally take over  %s  MS, total records %s", 
				(lastDate.getTime() - currDate.getTime()),betNumList.size()));
		return betNumList;
	}
	
}
