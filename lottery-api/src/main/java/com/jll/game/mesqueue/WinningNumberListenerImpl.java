package com.jll.game.mesqueue;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.common.threadpool.ThreadParam;
import com.jll.common.threadpool.ThreadPoolManager;
import com.jll.common.utils.StringUtils;
import com.jll.entity.Issue;
import com.jll.game.IssueService;
import com.jll.game.LotteryTypeFactory;
import com.jll.game.LotteryTypeService;

@Configuration
@PropertySource("classpath:sys-setting.properties")
public class WinningNumberListenerImpl implements MessageDelegateListener {

	private Logger logger = Logger.getLogger(WinningNumberListenerImpl.class);
	
	@Resource
	IssueService issueServ;
	
	@Resource
	CacheRedisService cacheServ;
	
	@Value("${sys_lottery_type_impl}")
	private String lotteryTypeImpl;
	
	@Override
	public void handleMessage(Serializable message) {
		logger.debug(String.format("Thread ID %s try to process winning number, receive message %s  , thread pool queue size %s, active thread count %s", 
				Thread.currentThread().getId(),
				(String)message,
				ThreadPoolManager.getInstance().getTotalTaskSize(),
				ThreadPoolManager.getInstance().getActiveThreads()
				));
		
		ThreadPoolManager.getInstance().exeThread(new Runnable() {
			
			@Override
			public void run() {
				String keyLock = Constants.KEY_LOCK_WINNING_NUMBER;
				Date lockStart = null;
				Date lockEnd = null;
				
				//加互斥锁，防止多进程同步执行
				try {
					String[] lottoTypeAndIssueNum = null;
					Issue issue = null;
					String lottoType = null;
					String issueNum = null;
					
					lottoTypeAndIssueNum = ((String)message).split("\\|");
					lottoType = lottoTypeAndIssueNum[0];
					issueNum = lottoTypeAndIssueNum[1];
					
					keyLock = keyLock.replace("{lottoType}", lottoType);
					keyLock = keyLock.replace("{issue}", issueNum);
					
					logger.debug(String.format("Thread ID %s try to process winning number, waitting for locker ", 
							Thread.currentThread().getId()));
					
					if(cacheServ.lock(keyLock, keyLock, Constants.LOCK_WINNING_NUMBER_EXPIRED)) {
						lockStart = new Date();
						logger.debug(String.format("Thread ID %s exe schedule issue , successfully obtain locker ", 
								Thread.currentThread().getId()));
						
						ThreadParam.set(lockStart);
						
						try {
							issue = issueServ.getIssueByIssueNum(lottoType, issueNum);
							
							//mmc期次保存到数据库会有延迟
							if(lottoType.equals(Constants.LottoType.MMC.getCode())) {
								/*try {
									Thread.sleep(10000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}*/
								int maxCount = 100;
								int counter = 0;
								while(counter < maxCount
										&& issue == null) {
									issue = issueServ.getIssueByIssueNum(lottoType, issueNum);
									
									counter++;									
									
									Thread.sleep(100);
								}
							}
							
							
							if(issue == null || !StringUtils.isBlank(issue.getRetNum())) {
								logger.debug(String.format("return with no issue or the issue has winning number", ""));
								return ;
							}
							
							if(StringUtils.isBlank(lotteryTypeImpl)) {
								logger.debug(String.format("return with no lottery Type Impl", ""));
								return ; 
							}
							
							String[] impls = lotteryTypeImpl.split(",");
							if(impls == null || impls.length == 0) {
								return;
							}
							
							for(String impl : impls) {
								LotteryTypeService lotteryTypeServ = LotteryTypeFactory
										.getInstance().createLotteryType(impl);
								
								if(lotteryTypeServ != null
										&& lotteryTypeServ.getLotteryType().equals(lottoType)) {
									lotteryTypeServ.queryWinningNum((String)message);
									
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
					
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
			}
			
		});
		
	}

}
