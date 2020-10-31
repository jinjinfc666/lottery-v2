package com.jll.game.playtypefacade.tc3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.jll.common.constants.Constants;
import com.jll.common.utils.MathUtil;
import com.jll.common.utils.StringUtils;
import com.jll.common.utils.Utils;
import com.jll.entity.Issue;
import com.jll.entity.OrderInfo;
import com.jll.entity.UserInfo;
import com.jll.game.playtypefacade.DefaultPlayTypeFacadeImpl;

public class Kd4kPlayTypeFacadeImpl extends DefaultPlayTypeFacadeImpl {
	
	private Logger logger = Logger.getLogger(Kd4kPlayTypeFacadeImpl.class);
	
	protected String playTypeDesc = "4k|4跨/kd|跨度/fs";
		
//	private String betNumOptions = "0-6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21-27";
		
	@Override
	public boolean isMatchWinningNum(Issue issue, OrderInfo order) {
		//开奖号码的每一位
		String[] winNumSet = null;
		//每次点击选号按钮所选号码，多个所选号码以;分割
		String[] betNumMul= null;
		String betNum = null;
		String winNum = null;
		
		winNum = issue.getRetNum();
		betNum = order.getBetNum();
		winNumSet = winNum.split(",");
		betNumMul = betNum.split(";");
		
		for(String temp : betNumMul) {
			if(StringUtils.isBlank(temp)) {
				continue;
			}
			
			if(temp.contains(winNumSet[0])
					|| temp.contains(winNumSet[1])
					|| temp.contains(winNumSet[2])) {
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
		int betTotal = 1;
		Float betAmount = 0F;
		Float maxWinAmount = 0F;
		int winBetTotal = 0;
		
		betNumSet = betNum.split(",");
		for(String subBetNum : betNumSet) {
			int len = subBetNum.length();
			betTotal *= MathUtil.combination(1, len);
		}
		
		if(betTotal > 3) {
			winBetTotal = 3;
		}else {
			winBetTotal = betTotal;
		}
		
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
		}
		
		return true;
	}

	@Override
	public Map<String, Object> calPrize(Issue issue, OrderInfo order, UserInfo user) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		// 每次点击选号按钮所选号码，多个所选号码以;分割
		String[] betNumMul = null;
		String betNum = null;
		String winNum = null;
		int winningBetAmount = 0;
		Float betAmount = 0F;
		Float maxWinAmount = 0F;
		Integer times = order.getTimes();
		BigDecimal monUnit = order.getPattern();
//		BigDecimal singleBettingPrize = null;
		
		//1700 --- 1960
//		Float prizePattern = userServ.calPrizePattern(user, issue.getLotteryType());
		BigDecimal winningRate = order.getPrizeRate();
//		singleBettingPrize =  calSingleBettingPrize(prizePattern, winningRate);
		
		winNum = issue.getRetNum();
		betNum = order.getBetNum();
//		winNum = winNum.substring(4, 9);
		//winNumSet = winNum.split(",");
		betNumMul = betNum.split(";");		
		
		for(String singleSel : betNumMul) {
			if(winNum.contains(singleSel)) {
				winningBetAmount++;
			}
			/*for(int i = 0; i < singleSel.length(); i++) {
				String numBit = singleSel.substring(i, i + 1);
				if(winNum.contains(numBit)) {
					winningBetAmount++;
				}
			}	*/		
		}
		
		betAmount = MathUtil.multiply(winningBetAmount, times, Float.class);
		betAmount = MathUtil.multiply(betAmount, monUnit.floatValue(), Float.class);
		maxWinAmount = MathUtil.multiply(betAmount, winningRate, Float.class);
		
		ret.put(Constants.KEY_WINNING_BET_TOTAL, winningBetAmount);
		ret.put(Constants.KEY_WIN_AMOUNT, maxWinAmount);
		ret.put(Constants.KEY_SINGLE_BETTING_PRIZE, winningRate);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.jll.game.playtype.PlayTypeFacade#calWinningRate()
	 * 
	 * 1/1000
	 */
	@Override
	public BigDecimal calWinningRate() {
		/*BigDecimal winningRate = null;
		BigDecimal winCount = new BigDecimal(3);
		BigDecimal totalCount = null;
		Double tempVal = Double.parseDouble(Long.toString(MathUtil.combination(1, 10)));
		
		//tempVal = Math.pow(tempVal, 3);
		totalCount = new BigDecimal(tempVal);
		winningRate = winCount.divide(totalCount);
		return winningRate;*/
		return null;
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
									
									Integer sumVal = i + ii + iii;
									boolean isMatch = isSumVal(sumVal, key);
									if(isMatch){
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
	
	private boolean isSumVal(Integer sumVal, String key) {
		/*String[] betNumOptionsArray = betNumOptions.split(",");
		if(key.equals(betNumOptionsArray[0])) {
			if(sumVal <= 6) {
				return true;
			}
			
			return false;
		}
		
		if(key.equals(betNumOptionsArray[15])) {
			if(sumVal >= 21 && sumVal <= 27) {
				return true;
			}
			
			return false;
		}
		
		Integer keyInt = Integer.valueOf(key);
		if(sumVal.equals(keyInt)) {
			return true;
		}*/
			
		return false;
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
	public String obtainSampleBetNumber(){
		Random random = new Random();
		StringBuffer betNum = new StringBuffer();
		
		int bit = random.nextInt(10);
		
		betNum.append(Integer.toString(bit));
				
		return betNum.toString();
	}
}
