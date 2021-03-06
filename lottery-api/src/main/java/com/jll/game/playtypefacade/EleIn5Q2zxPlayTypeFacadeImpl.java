package com.jll.game.playtypefacade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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

public class EleIn5Q2zxPlayTypeFacadeImpl  extends DefaultPlayTypeFacadeImpl {
	
	private Logger logger = Logger.getLogger(EleIn5Q2zxPlayTypeFacadeImpl.class);
	
	protected String playTypeDesc = "em|二码/qezx|前二直选/fs";
	
	private String betNumOptions = "01,02,03,04,05,06,07,08,09,10,11";
	
	String[] optionsArray = {"01","02","03","04","05","06","07","08","09","10","11"};
	
	@Override
	public boolean isMatchWinningNum(Issue issue, OrderInfo order) {
		//开奖号码的每一位
		String[] winNumSet = null;
		//投注号码的每个位的号码，可能多个号码
		String[] betNumSet = new String[2];
		//每次点击选号按钮所选号码，多个所选号码以;分割
		String[] betNumMul= null;
		String betNum = null;
		String winNum = null;
		
		winNum = issue.getRetNum();
		betNum = order.getBetNum();
		winNum = winNum.substring(0,5);
		winNumSet = winNum.split(",");
		betNumMul = betNum.split(";");
		
		//logger.debug("proced bet number is :: " + Arrays.asList(betNumSet));
		
		for(String temp : betNumMul) {
			betNumSet = temp.split(",");
			if(betNumSet[0].contains(winNumSet[0]) 
					&& betNumSet[1].contains(winNumSet[1])) {
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
		
		
		/*betNumSet = betNum.split(",");
		for(String subBetNum : betNumSet) {
			int len = subBetNum.length() / 2;
			betTotal *= MathUtil.combination(1, len);
		}*/
		
		betTotal = calBetTotal(betNum);
		

		if(betTotal > 1) {
			winBetTotal = 1;
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
		String[] betNumMul = null;
		Map<String, String> allBetNumBit = new HashMap<>();
		int betTotal = 0;
		
		betNum = order.getBetNum();
		if(StringUtils.isBlank(betNum)) {
			return false;
		}
		
		betNumMul = betNum.split(";");
		for(String betNumTemp : betNumMul) {
			betNumSet = betNumTemp.split(",");
			if(betNumSet == null || betNumSet.length != 2) {
				return false;
			}
			
			for(String betNumBit : betNumSet) {
				Map<String, String> tempBits = splitBetNum(betNumBit);
				allBetNumBit.clear();
				
				if(tempBits.size() < 1
						|| tempBits.size() > 11
						|| !Utils.validateEleIn5Num(betNumBit)
						|| tempBits.size() != (betNumBit.length() / 2)) {
					return false;
				}
				
				Iterator<String> ite = tempBits.keySet().iterator();
				while(ite.hasNext()) {
					String key = ite.next();
					if(!betNumOptions.contains(key)) {
						return false;
					}
					
					if(allBetNumBit.containsKey(key)) {
						return false;
					}
					
					allBetNumBit.put(key, key);
				}
			}
		}
		
		betTotal = calBetTotal(betNum);
		if(betTotal == 0) {
			return false;
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
		
		//1700 --- 1960
		Float prizePattern = userServ.calPrizePattern(user, issue.getLotteryType());
		BigDecimal winningRate = calWinningRate();
		singleBettingPrize =  calSingleBettingPrize(prizePattern, winningRate);
		
		winNum = issue.getRetNum();
		betNum = order.getBetNum();
		winNum = winNum.substring(0, 5);
		winNumSet = winNum.split(",");
		betNumMul = betNum.split(";");
		
		
		for(String singleSel : betNumMul) {
			betNumSet = singleSel.split(",");
			if(betNumSet[0].contains(winNumSet[0])
					&& betNumSet[1].contains(winNumSet[1])) {
				winningBetAmount++;
			}
		}
		
		betAmount = MathUtil.multiply(winningBetAmount, times, Float.class);
		betAmount = MathUtil.multiply(betAmount, monUnit.floatValue(), Float.class);
		maxWinAmount = MathUtil.multiply(betAmount, singleBettingPrize.floatValue(), Float.class);
		
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
		BigDecimal winCount = new BigDecimal(1);
		BigDecimal totalCount = null;
		Double tempVal = Double.parseDouble(Long.toString(MathUtil.combination(1, 11)));
		Double tempVal1 = Double.parseDouble(Long.toString(MathUtil.combination(1, 10)));
		
		tempVal = MathUtil.multiply(tempVal, tempVal1, Double.class);
		totalCount = new BigDecimal(tempVal);
		winningRate = winCount.divide(totalCount, 5, BigDecimal.ROUND_HALF_UP);
		return winningRate;
	}
	
	private Map<String, String> splitBetNum(String temp) {
		Map<String, String> bits = new HashMap<String, String>();
		int len = temp.length();
		
		if(len % 2 != 0) {
			return bits;
		}
		
		for(int i = 0; i < temp.length();) {
			String bit = temp.substring(i, i + 2);
			bits.put(bit, bit);
			i += 2;
		}
		
		return bits;
	}
	
	@Override
	public List<Map<String, String>> parseBetNumber(String betNum){
		List<Map<String, String>> betNumList = new ArrayList<>();
		List<String[]> excludingResults = null;
		int excludingCounter = 3;
		Map<String, String> betNumCombination = new HashMap<>();
		List<Map<String, String>> betNumCombinations = new ArrayList<>();		
		String[] betNumArray = betNum.split(";");
		
		for(String singleBetNumArray : betNumArray) {
			String[] betNumBits = singleBetNumArray.split(",");
			
			for(int i = 0 ; i < betNumBits[0].length();) {
				String a = betNumBits[0].substring(i, i + 2);
				for(int ii = 0; ii < betNumBits[1].length();) {
					String aa = betNumBits[1].substring(ii, ii + 2);
					
					ii += 2;
					if(aa.equals(a)) {
						continue;
					}
					
					String[] excludingArray = obtainExcludingArray(new String[] {a,aa});
					excludingResults = new ArrayList<String[]>();
					MathUtil.combinationSelect(excludingArray, excludingCounter, excludingResults);
					for(String[] excludingResult : excludingResults) {
						betNumCombination = new HashMap<>();
						StringBuffer buffer = new StringBuffer();
						
						buffer.append(a).append(aa);
						betNumCombination.put(a, a);
						betNumCombination.put(aa, aa);
												
						for(String bit : excludingResult) {
							buffer.append(bit);
							betNumCombination.put(bit, bit);
						}
						
						if(isBetNumCombinationExisting(betNumCombinations, betNumCombination)) {
							continue;
						}
						
						betNumCombinations.add(betNumCombination);
						List<String> arrangementSel = arrangementSelect(buffer.toString());
						for(String sel : arrangementSel) {
							
							
							Map<String, String> row = new HashMap<String, String>();
							row.put(Constants.KEY_FACADE_BET_NUM, sel);
							row.put(Constants.KEY_FACADE_PATTERN, sel);
							row.put(Constants.KEY_FACADE_BET_NUM_SAMPLE, sel);
							betNumList.add(row);
						}
					}
				}
				
				i += 2;
			}
			
		}
		
		
		return betNumList;
	}
	
	@Override
	public String obtainSampleBetNumber(){
		
		Random random = new Random();
		StringBuffer betNum = new StringBuffer();
		
		int bitLen = random.nextInt(3) + 1;
		
		for(int i = 0 ; i < bitLen; i++) {
			int bitIndx = random.nextInt(11);
			
			while(true) {
				if(betNum.toString().contains(optionsArray[bitIndx])) {
					bitIndx = random.nextInt(11);
					continue;
				}
				betNum.append(optionsArray[bitIndx]);
				break;
			}
		}
		
		betNum.append(",");
		
		bitLen = random.nextInt(3) + 1;
		
		for(int i = 0 ; i < bitLen; i++) {
			int bitIndx = random.nextInt(11);
			
			while(true) {
				if(betNum.toString().contains(optionsArray[bitIndx])) {
					bitIndx = random.nextInt(11);
					continue;
				}
				betNum.append(optionsArray[bitIndx]);
				break;
			}
		}
		
		return betNum.toString();
	}
	
	private String[] obtainExcludingArray(String[] key) {
		String[] ret = new String[11 - key.length];
		int indx = 0 ;
		for(String temp : optionsArray) {
			boolean isSame = false;
			for(String keyTemp : key) {
				if(temp.equals(keyTemp)) {
					isSame = true;
					break;
				}
			}
			
			if(!isSame) {
				ret[indx++] = temp;
			}
		}
		return ret;
	}
	
	private boolean isBetNumCombinationExisting(List<Map<String, String>> betNumCombinations,
			Map<String, String> betNumCombination) {
		for(Map<String, String> temp : betNumCombinations) {
			int existingCounter = 0;
			Iterator<String> ite = temp.keySet().iterator();
			while(ite.hasNext()) {
				String key = ite.next();
				if(betNumCombination.get(key) != null) {
					existingCounter++;
				}
			}
			
			if(existingCounter == temp.size()) {
				return true;
			}
		}
		return false;
	}
	
	private List<String> arrangementSelect(String betNum) {
		List<String> ret = new ArrayList<>();
		List<String[]> results = new ArrayList<>();
		String[] selArray = new String[betNum.length() / 2];
		for(int i = 0, j = 0; i< betNum.length();j++) {
			String result = betNum.substring(i, i + 2);
			selArray[j] = result;
			
			i += 2;
		}
		MathUtil.arrangementSelect(selArray, selArray.length, results);
		
		for(String[] result : results) {
			StringBuffer buffer = new StringBuffer();
			for(String bit : result) {
				buffer.append(bit).append(",");;
			}
			buffer.delete(buffer.length() - 1, buffer.length());
			ret.add(buffer.toString());
		}
		return ret;
	}
	private int calBetTotal(String betNum){
		String[] betNumArray = betNum.split(";");
		int result = 0;
		for(String singleBetNumArray : betNumArray) {
			String[] betNumBits = singleBetNumArray.split(",");			
			
			for(int i = 0 ; i < betNumBits[0].length();) {
				String a = betNumBits[0].substring(i, i + 2);
				for(int ii = 0; ii < betNumBits[1].length();) {
					String aa = betNumBits[1].substring(ii, ii + 2);
					
					ii += 2;
					
					if(aa.equals(a)) {
						continue;
					}
					
					result++;
				}
				
				i += 2;
			}
		}
		
		return result;
	}
}
