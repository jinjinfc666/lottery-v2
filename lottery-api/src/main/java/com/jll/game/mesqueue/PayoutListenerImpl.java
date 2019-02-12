package com.jll.game.mesqueue;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.jll.common.cache.CacheRedisService;
import com.jll.common.cache.CacheRedisServiceImpl;
import com.jll.common.constants.Constants;
import com.jll.common.threadpool.ThreadPoolManager;
import com.jll.common.utils.StringUtils;
import com.jll.entity.Issue;
import com.jll.game.IssueService;
import com.jll.game.LotteryTypeFactory;
import com.jll.game.LotteryTypeService;

@Configuration
@PropertySource("classpath:sys-setting.properties")
public class PayoutListenerImpl implements MessageDelegateListener {

	private Logger logger = Logger.getLogger(PayoutListenerImpl.class);
	
	@Resource
	IssueService issueServ;
	
	@Resource
	CacheRedisService cacheServ;
	
	@Value("${sys_lottery_type_impl}")
	private String lotteryTypeImpl;
	
	@Override
	public void handleMessage(Serializable message) {
		logger.debug(String.format("Thread ID %s try to process winning number, thread pool queue size %s, active thread count %s", 
				Thread.currentThread().getId(),
				ThreadPoolManager.getInstance().getTotalTaskSize(),
				ThreadPoolManager.getInstance().getActiveThreads()
				));
		
		ThreadPoolManager.getInstance().exeThread(new Runnable() {

			@Override
			public void run() {
				String[] lottoTypeAndIssueNum = null;
				Issue issue = null;
				String lottoType = null;
				String issueNum = null;
				String keyLock = Constants.KEY_LOCK_PAY_OUT;
				Date lockStart = null;
				Date lockEnd = null;
				
				lottoTypeAndIssueNum = ((String)message).split("\\|");
				lottoType = lottoTypeAndIssueNum[0];
				issueNum = lottoTypeAndIssueNum[1];
				issue = issueServ.getIssueByIssueNum(lottoType, issueNum);
				if(issue == null || StringUtils.isBlank(issue.getRetNum())) {
					return ;
				}
				
				lottoType = issue.getLotteryType();
				
				if(StringUtils.isBlank(lotteryTypeImpl)) {
					return ;
				}
				
				keyLock = keyLock.replace("{lottoType}", lottoType);
				keyLock = keyLock.replace("{issue}", issueNum);
				
				logger.debug(String.format("Thread ID %s try to process winning number, waitting for locker ", 
						Thread.currentThread().getId()));
				
				if(cacheServ.lock(keyLock, keyLock, Constants.LOCK_PAY_OUT_EXPIRED)) {
					lockStart = new Date();
					logger.debug(String.format("Thread ID %s exe schedule issue , successfully obtain locker ", 
							Thread.currentThread().getId()));
					try {
						String[] impls = lotteryTypeImpl.split(",");
						if(impls == null || impls.length == 0) {
							return;
						}
						for(String impl : impls) {
							LotteryTypeService lotteryTypeServ = LotteryTypeFactory
									.getInstance().createLotteryType(impl);
							if(lotteryTypeServ != null
									&& lotteryTypeServ.getLotteryType().equals(lottoType)) {
								lotteryTypeServ.payout((String)message);
								break;
							}
						}
					}finally {
						lockEnd = new Date();
						logger.debug(String.format("Thread ID %s release locker , consume %s ms", 
								Thread.currentThread().getId(),
								lockEnd.getTime() - lockStart.getTime()
								));
						cacheServ.releaseLock(keyLock);
					}
				}
			}
		});
		
	}

}
