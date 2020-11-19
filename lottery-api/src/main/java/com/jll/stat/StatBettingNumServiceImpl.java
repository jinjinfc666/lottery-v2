package com.jll.stat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jll.entity.Issue;
import com.jll.entity.OrderInfo;
import com.jll.entity.PlayType;
import com.jll.entity.StatBettingNumber;
import com.jll.game.IssueService;
import com.jll.game.mesqueue.kafka.KafkaConsumer;
import com.jll.game.playtype.PlayTypeService;
import com.jll.report.StatBettingNumberService;
import com.jll.report.TReportService;

@Service
@Transactional
public class StatBettingNumServiceImpl implements StatBettingNumService, KafkaConsumer {
	private Logger logger = Logger.getLogger(StatBettingNumServiceImpl.class);

	@Resource
	TReportService reportServ;

	@Resource
	StatBettingNumberService statBettingNumberServ;

	@Resource
	PlayTypeService playTypeServ;

	@Resource
	IssueService issueServ;

	@Override
	public void exeStatistic(OrderInfo order) {
		Integer playTypeId = order.getPlayType();
		PlayType playType = null;
		String playTypeName = null;
		String betNum = order.getBetNum();
		String lotteryType = null;
		Issue issue = null;
		
		playType = playTypeServ.queryById(playTypeId);
		lotteryType = playType.getLotteryType();
		playTypeName = playType.getClassification();
		Integer issueId = order.getIssueId();
		issue = issueServ.getIssueById(issueId);
		StatBettingNumber stat = statBettingNumberServ.queryStatByNumber(lotteryType, issueId, betNum);
		if (stat == null) {
			stat = new StatBettingNumber();
			stat.setBetAmount(new BigDecimal(order.getBetAmount()));
			stat.setBetNum(betNum);
			stat.setCreateTime(new Date());
			stat.setIssueId(issueId);
			stat.setIssueNum(issue.getIssueNum());
			stat.setLotteryType(lotteryType);
			stat.setPlayTypeId(playTypeId);
			stat.setPlayTypeName(playTypeName);
		} else {
			stat.setBetAmount(stat.getBetAmount().add(new BigDecimal(order.getBetAmount())));
		}

		statBettingNumberServ.saveOrUpdateStat(stat);
	}

	@Override
	public void onMessage(ConsumerRecord<Integer, String> arg0) {
		String orderInfoStr = arg0.value();
		if (StringUtils.isEmpty(orderInfoStr)) {
			return;
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			OrderInfo orderInfo = mapper.readValue(orderInfoStr, OrderInfo.class);
			exeStatistic(orderInfo);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Can't read the userAccountDetails from Kafka..");
		}
	}

}
