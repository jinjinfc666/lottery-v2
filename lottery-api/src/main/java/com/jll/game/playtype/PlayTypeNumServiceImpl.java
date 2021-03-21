package com.jll.game.playtype;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Constants.LottoType;
import com.jll.entity.Issue;
import com.jll.entity.OrderInfo;
import com.jll.entity.PlayType;
import com.jll.entity.PlayTypeNum;
import com.jll.entity.UserInfo;
import com.jll.game.IssueService;

@Service
public class PlayTypeNumServiceImpl implements PlayTypeNumService{

	@Resource
	private PlayTypeNumDao playTypeNumDao;
	
	@Resource
	CacheRedisService cacheServ;
	
	@Resource
	private IssueService issueServ;
	
	@Resource
	private PlayTypeService playTypeServ;
	
	@Override
	public List<PlayTypeNum> queryPlayTypeNum(Long playTypeId) {
		return playTypeNumDao.queryPlayTypeNum(playTypeId);
	}


	public boolean isRateValid(List<OrderInfo> orders, UserInfo userInfo) {
		String codeTypeName = Constants.KEY_PLAY_TYPE_NUM;
		Map<String, Map<String, Map<String, PlayTypeNum>>> lotteryTypePlayTypeNums = cacheServ.queryPlayTypeNum(codeTypeName);
//		Map<String,Boolean> rateValid = new HashMap<>();
		if(MapUtils.isEmpty(lotteryTypePlayTypeNums)) {
			return false;
		}
		
		if(CollectionUtils.isEmpty(orders)){
			return false;
		}
		
//		Map<String, Map<String, Map<String, PlayTypeNum>>> lotteryTypePlayTypeNums_ = new HashMap<String, Map<String, Map<String, PlayTypeNum>>>();
		
		OrderInfo order = orders.stream().filter(order_->isRateDiff(order_, lotteryTypePlayTypeNums, userInfo)).findFirst().orElse(null);
		if(order != null){
			return false;
		}
		
		return true;
		
	}


	private boolean isRateDiff(OrderInfo order,Map<String, Map<String, Map<String, PlayTypeNum>>> lotteryTypePlayTypeNums_, UserInfo userInfo) {
		Integer issueId = order.getIssueId();
		Integer playTypeId = order.getPlayType();
		BigDecimal prizeRate = order.getPrizeRate();
		Issue issue = issueServ.getIssueById(issueId);
		PlayType playType = playTypeServ.queryById(playTypeId);
		String lotteryType = issue.getLotteryType();
		String classification = playType.getClassification();
		PlayTypeNum playTypeNum = lotteryTypePlayTypeNums_.get(lotteryType).get(classification).get(order.getBetNum());
		if(playTypeNum == null) {
			boolean isZx = isZx(playType);
			if(isZx) {
				playTypeNum = lotteryTypePlayTypeNums_.get(lotteryType).get(classification).get(String.valueOf(order.getBetNum().charAt(0)));
			}
		}
		
		Constants.CreditMarketEnum currMarket = Constants.CreditMarketEnum.getByCode(userInfo.getCurrentMarket().getMarketId());
		switch(currMarket){
			case MARKET_A:{
				if(playTypeNum.getaOdds().compareTo(prizeRate) != 0){
					return true;
				}
				return false;
			}
			case MARKET_B:{
				if(playTypeNum.getbOdds().compareTo(prizeRate) != 0){
					return true;
				}
				return false;
			}
			case MARKET_C:{
				if(playTypeNum.getcOdds().compareTo(prizeRate) != 0){
					return true;
				}
				return false;
			}
			default:{
				
			}
		}
		
		
		return false;
	}


	private boolean isZx(PlayType playType) {
		String classification = playType.getClassification();
		if(classification.contains(Constants.KEY_ZX)) {
			return true;
		}
		return false;
	}


	@Override
	public void updateUserCurrMarket(String userId, String currMarket) {
		playTypeNumDao.changeUserCurrMarket(userId, currMarket);
	}


	@Override
	public void initPlayTYpeNumData() {
		DecimalFormat format = new DecimalFormat("000");
		Long playTypeId = 245L;
		BigDecimal odds = new BigDecimal("1.99");
		for(int i = 10; i < 1000; i++){
			String betNum = format.format(i);
			PlayTypeNum playTypeNum = new PlayTypeNum();
			playTypeNum.setBetNum(betNum);
			playTypeNum.setaOdds(odds);
			playTypeNum.setBetNumDesc(betNum);
			playTypeNum.setbOdds(odds);
			playTypeNum.getcOdds();
			playTypeNum.setCreateTime(new Date());
			playTypeNum.setDisplayName(betNum);
			playTypeNum.setdOdds(odds);
			playTypeNum.setPlayTypeId(playTypeId);
			playTypeNumDao.updatePlayTypeNum(playTypeNum);
			
		}
	}
	
}
