package com.jll.game.playtype;

import java.math.BigDecimal;
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


	public boolean isRateValid(List<OrderInfo> orders) {
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
		
		OrderInfo order = orders.stream().filter(order_->isRateDiff(order_, lotteryTypePlayTypeNums)).findFirst().orElse(null);
		if(order != null){
			return false;
		}
		
		return true;
		
	}


	private boolean isRateDiff(OrderInfo order,Map<String, Map<String, Map<String, PlayTypeNum>>> lotteryTypePlayTypeNums_) {
		Integer issueId = order.getIssueId();
		Integer playTypeId = order.getPlayType();
		BigDecimal prizeRate = order.getPrizeRate();
		Issue issue = issueServ.getIssueById(issueId);
		PlayType playType = playTypeServ.queryById(playTypeId);
		String lotteryType = issue.getLotteryType();
		String classification = playType.getClassification();
		PlayTypeNum playTypeNum = lotteryTypePlayTypeNums_.get(lotteryType).get(classification).get(order.getBetNum());
		if(playTypeNum.getaOdds().compareTo(prizeRate) != 0){
			return true;
		}
		
		return false;
	}
	
}
