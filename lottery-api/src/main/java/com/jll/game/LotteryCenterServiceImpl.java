package com.jll.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Message;
import com.jll.common.utils.StringUtils;
import com.jll.entity.Issue;
import com.jll.entity.PlayType;
import com.jll.entity.SysCode;
import com.jll.entity.UserInfo;
import com.jll.game.playtype.PlayTypeFacade;
import com.jll.game.playtype.PlayTypeService;
import com.jll.game.playtypefacade.PlayTypeFactory;
import com.jll.user.UserInfoService;

@Configuration
@PropertySource("classpath:sys-setting.properties")
@Service
@Transactional
public class LotteryCenterServiceImpl implements LotteryCenterService
{
	private Logger logger = Logger.getLogger(LotteryCenterServiceImpl.class);

	@Value("${sys_lottery_type_impl}")
	private String lotteryTypeImpl;
	
	@Resource
	IssueService issueServ;
	
	@Resource
	CacheRedisService cacheServ;
	
	@Resource
	PlayTypeService playTypeServ;
	
	@Resource
	UserInfoService userServ;
	
	@Override
	public void makeAPlan() {
		String keyLock = Constants.KEY_LOCK_MAKING_PLAN;
		Date lockStart = null;
		Date lockEnd = null;
		
		logger.debug(String.format("Thread ID %s try to make plan  , waitting for locker ", 
				Thread.currentThread().getId()));
		//加互斥锁，防止多进程同步执行
		if(cacheServ.lock(keyLock, keyLock, Constants.LOCK_MAKING_PLAN_EXPIRED)) {
			lockStart = new Date();
			
			logger.debug(String.format("Thread ID %s try to make plan, successfully obtain locker ", 
					Thread.currentThread().getId()));
			try {
				if(StringUtils.isBlank(lotteryTypeImpl)) {
					return ;
				}
				
				String[] impls = lotteryTypeImpl.split(",");
				if(impls == null || impls.length == 0) {
					return;
				}
				
				for(String impl : impls) {
					LotteryTypeService lotteryTypeServ = LotteryTypeFactory.getInstance().createLotteryType(impl);
					if(lotteryTypeServ == null 
							|| lotteryTypeServ.getLotteryType()
							.equals(Constants.LottoType.MMC.getCode())) {
						continue;
					}
					
					String lotteryType = lotteryTypeServ.getLotteryType();
					boolean isPlanExisting = isPlanExisting(lotteryType);
					
					logger.debug(String.format("Thread ID %s ,plans of %s is exisitng? %s", 
							Thread.currentThread().getId(),
							lotteryType,
							isPlanExisting
							));
					
					if(isPlanExisting) {
						continue;
					}
					
					List<Issue> issues = lotteryTypeServ.makeAPlan();
					List<Issue> loadedIssues = new ArrayList<>();
					
					if(issues != null && issues.size() > 0) {
						for(Issue issue : issues) {
							Issue loadedIssue = issueServ.getIssueByIssueNum(issue.getLotteryType(), issue.getIssueNum());
							if(loadedIssue != null) {
								loadedIssues.add(loadedIssue);
							}
						}
						String cacheKey = lotteryType;
						cacheServ.setPlan(cacheKey, loadedIssues);
					}
				}
			}finally {
				lockEnd = new Date();
				logger.debug(String.format("Thread ID %s release locker , consume %s ms", 
						Thread.currentThread().getId(),
						lockEnd.getTime() - lockStart.getTime()));
				cacheServ.releaseLock(keyLock);
			}
		}else {
			logger.debug(String.format("Thread ID %s try to make plan, but failed to obtain locker ", Thread.currentThread().getId()));
		}
		
	}

	private boolean isPlanExisting(String lotteryType) {
		return cacheServ.isPlanExisting(lotteryType);
	}

	@Override
	public boolean hasMoreIssue(String lotteryType) {
		if(lotteryType.equals(Constants.LottoType.MMC.getCode())) {
			return true;
		}
		String cacheKey = lotteryType;
		List<Issue> issues = cacheServ.getPlan(cacheKey);
		Issue currIssue = null;
		BulletinBoard bulletinBoard = cacheServ.getBulletinBoard(lotteryType);
		
		if(bulletinBoard == null 
				|| issues == null
				|| issues.size() == 0) {
			return false;
		}
		
		currIssue = bulletinBoard.getCurrIssue();
				
		for(Issue issueTemp : issues) {
			if(currIssue != null) {
				if(currIssue.getStartTime().getTime() > issueTemp.getStartTime().getTime()) {
					continue;
				}
			}
			if(issueTemp.getState() == Constants.IssueState.INIT.getCode()) {
				/*if(currIssue != null
						&& currIssue.getEndTime().getTime() != issueTemp.getStartTime().getTime()) {
					return false;
				}*/
				
				return true;
			}
		}
		
		return false;
	}


	@Override
	public Issue queryBettingIssue(String lotteryType) {
		BulletinBoard bulletinBoard = cacheServ.getBulletinBoard(lotteryType);
		
		if(bulletinBoard == null) {
			return null;
		}
		
		return bulletinBoard.getCurrIssue();
	}

	@Override
	public Issue queryLastIssue(String lotteryType) {
		BulletinBoard bulletinBoard = cacheServ.getBulletinBoard(lotteryType);
		
		if(bulletinBoard == null) {
			return null;
		}
		
		return bulletinBoard.getLastIssue();
	}
	
	
	/**
	 * 定时遍历期次，修改状态
	 */
	@Override
	public void exeScheduleIssue() {
		String keyLock = Constants.KEY_LOCK_SCHEDULE_ISSUE;
		Date startTime = null;
		Date endTime = null;
		Date lockStart = null;
		Date lockEnd = null;
		
		logger.debug(String.format("Thread ID %s try to exe schedule issue , waitting for locker ", 
				Thread.currentThread().getId()));
		//加互斥锁，防止多进程同步执行
		if(cacheServ.lock(keyLock, keyLock, Constants.LOCK_SCHEDULE_ISSUE_EXPIRED)) {
			lockStart = new Date();
			logger.debug(String.format("Thread ID %s exe schedule issue , successfully obtain locker ", 
					Thread.currentThread().getId()));
			
			try {
				String[] impls = lotteryTypeImpl.split(",");
				if(impls == null || impls.length == 0) {
					return;
				}
				
				for(String impl : impls) {
					LotteryTypeService lotteryTypeServ = LotteryTypeFactory.getInstance().createLotteryType(impl);
					if(lotteryTypeServ == null 
							|| lotteryTypeServ.getLotteryType()
							.equals(Constants.LottoType.MMC.getCode())) {
						continue;
					}
					startTime = new Date();
					Map<String, SysCode> lottoTypes = cacheServ.getSysCode(Constants.SysCodeTypes.LOTTERY_TYPES.getCode());
					endTime = new Date();
					logger.debug(String.format("Consumed %s ms to obtain lotoTypes  %s", 
							endTime.getTime() - startTime.getTime(),
							lotteryTypeServ.getLotteryType()));					
										
					SysCode sysCode = lottoTypes.get(lotteryTypeServ.getLotteryType());
					
					dealBulletinBoard(sysCode);
				}
			}finally {
				lockEnd = new Date();
				logger.debug(String.format("Thread ID %s release locker , consume %s ms", 
						Thread.currentThread().getId(),
						lockEnd.getTime() - lockStart.getTime()
						));
				cacheServ.releaseLock(keyLock);
			}
		}else {
			logger.debug(String.format("Thread ID %s try  exe schedule issue , but failed to obtain locker ", Thread.currentThread().getId()));
		}
	}

	private void dealBulletinBoard(SysCode lottoType) {
		Issue currIssue = null;
		boolean isChanged = false;
		Map<String, Object> ret = null;
		Date startTime;
		Date endTime;
		List<Issue> plans = null;
		
		startTime = new Date();
		BulletinBoard bulletinBoard = initBulletinBoard(lottoType);
		endTime = new Date();
		
		logger.debug(String.format("Consumed %s ms to initBulletinBoard", 
				endTime.getTime() - startTime.getTime()));
		
		startTime = new Date();
		plans = cacheServ.getPlan(lottoType.getCodeName());
		endTime = new Date();
		
		logger.debug(String.format("Consumed %s ms to getPlan, totally %s", 
				endTime.getTime() - startTime.getTime(),
				plans == null?0 : plans.size()
				));
		
		startTime = new Date();
		boolean isMoved = hasMoved(lottoType, bulletinBoard, plans);
		endTime = new Date();
		
		logger.debug(String.format("Consumed %s ms to hasMoved", 
				endTime.getTime() - startTime.getTime()));
		
		if(isMoved) {
			return ;
		}
		
		startTime = new Date();
		ret = changeIssueState(lottoType.getCodeName(), bulletinBoard, plans);
		endTime = new Date();
		
		logger.debug(String.format("Consumed %s ms to changeIssueState", 
				endTime.getTime() - startTime.getTime()));
		
		if(ret == null) {
			return ;
		}
		currIssue = (Issue)ret.get(Constants.KEY_CURR_ISSUE);
		isChanged = (Boolean)ret.get(Constants.KEY_IS_CHANGED);
		if(currIssue != null && 
				currIssue.getState() == Constants.IssueState.END_ISSUE.getCode()
				&& isChanged) {
			String currIssueNum = currIssue.getIssueNum();
			
			final String message = currIssue.getLotteryType()+ "|" + currIssueNum;
			
			cacheServ.publishMessage(Constants.TOPIC_WINNING_NUMBER, 
					message);
		}
	}

	private BulletinBoard initBulletinBoard(SysCode lottoType) {
		Date startTime = null;
		Date endTime = null;
		
		
		startTime = new Date();
		BulletinBoard bulletinBoard = cacheServ.getBulletinBoard(lottoType.getCodeName());
		
		endTime = new Date();
		logger.debug(String.format("Consumed %s ms to obtain bulletinBoard", 
				endTime.getTime() - startTime.getTime()));
		
		if(bulletinBoard == null) {
			bulletinBoard = new BulletinBoard();
			
			startTime = new Date();
			cacheServ.setBulletinBoard(lottoType.getCodeName(), bulletinBoard);
			endTime = new Date();
			logger.debug(String.format("Consumed %s ms to set bulletinBoard", 
					endTime.getTime() - startTime.getTime()));
		}
		
		return bulletinBoard;
	}

	private Map<String, Object> changeIssueState(String lottoType, BulletinBoard bulletinBoard, List<Issue> issues) {
		Map<String, Object> ret = new HashMap<>();
		Issue currIssue = null;
		Date currTime = new Date();
		Date startTime = null;
		Date endTime = null;
		Date endBettingTime = null;
		String codeTypeName = "lottery_config_" + lottoType;
		String codeValName = Constants.LotteryAttributes.BETTING_END_TIME.getCode();
		SysCode lottoAttri = cacheServ.getSysCode(codeTypeName, codeValName);
		boolean hasChanged = false;
		int state = -1;
		
		if(lottoAttri == null) {
			return null;
		}
		
		currIssue = bulletinBoard.getCurrIssue();

		if(currIssue == null) {
			return null;
		}
		
		startTime = currIssue.getStartTime();
		endTime = currIssue.getEndTime();
		endBettingTime = DateUtils.addSeconds(endTime, 
				Integer.valueOf(lottoAttri.getCodeVal())*-1);
		endTime = DateUtils.addSeconds(endTime, -5);
		
		state = currIssue.getState();
		//TODO 
		logger.debug(String.format("lotto Type  %s   issue number %s   Issue Id %s", 
				lottoType, 
				currIssue.getIssueNum(), 
				currIssue.getId()));
		logger.debug(state+"-----------current state------------");
		logger.debug(endTime.getTime()+"------------------endTime--------------------");
		logger.debug(startTime.getTime()+"------------startTime---------------");
		
		logger.debug(endBettingTime.getTime()+"------------endBettingTime---------------");
		logger.debug(currTime.getTime()+"------------currTime---------------");
		
		if(state == Constants.IssueState.BETTING.getCode()
				&& endBettingTime.getTime() <= currTime.getTime()) {
			currIssue.setState(Constants.IssueState.END_BETTING.getCode());
			hasChanged = true;
		}else if(state == Constants.IssueState.END_BETTING.getCode()
				&& endTime.getTime() <= currTime.getTime()) {
			currIssue.setState(Constants.IssueState.END_ISSUE.getCode());
			hasChanged = true;
			
		}else if(state == Constants.IssueState.INIT.getCode()
				&& startTime.getTime() <= currTime.getTime()) {
			currIssue.setState(Constants.IssueState.BETTING.getCode());
			hasChanged = true;
		}
		
		state = currIssue.getState();
		//TODO 
		logger.debug(state + "-----------current state------------");
		
		if(hasChanged) {
			if(state == Constants.IssueState.END_ISSUE.getCode()) {
				logger.debug(String.format("set current issue to null",""));
				bulletinBoard.setCurrIssue(null);
				bulletinBoard.setLastIssue(currIssue);
			}else {
				bulletinBoard.setCurrIssue(currIssue);
			}
			/*state = currIssue.getState();
			logger.debug(state + "-----------current state------------");
			logger.debug(String.format("issue number %s   Issue Id %s", currIssue.getIssueNum(), currIssue.getId()));*/
			
			cacheServ.updatePlan(lottoType, issues, currIssue);
			
			cacheServ.setBulletinBoard(lottoType, bulletinBoard);
			logger.debug("-----------Save Issue------------");
			issueServ.saveIssue(currIssue);
		}

		ret.put(Constants.KEY_CURR_ISSUE, currIssue);
		ret.put(Constants.KEY_IS_CHANGED, hasChanged);
		return ret;
	}

	private Issue moveToNext(BulletinBoard bulletinBoard, String lotteryType, List<Issue> plans) {
		Issue nextIssue = null;
		Integer indx = 0;
		Date startTime = new Date();
		Date endTime = null;
		//List<Issue> plans = cacheServ.getPlan(lotteryType);
		endTime = new Date();
		logger.debug(String.format("Consumed %s ms to getPlan", 
					endTime.getTime() - startTime.getTime()));
		 
		Issue currIssue = bulletinBoard.getCurrIssue();
		if(plans == null || plans.size() == 0) {
			return null;
		}
		
		startTime = new Date();
		indx = getIndexOfNextIssue(currIssue, plans);
		endTime = new Date();
		logger.debug(String.format("Consumed %s ms to getIndexOfNextIssue", 
					endTime.getTime() - startTime.getTime()));
		
		if(indx == null) {
			return null;
		}
		
		startTime = new Date();
		nextIssue = plans.get(indx);
		nextIssue.setState(Constants.IssueState.BETTING.getCode());
		cacheServ.updatePlan(lotteryType, plans, nextIssue);
		endTime = new Date();
		logger.debug(String.format("Consumed %s ms to updatePlan", 
					endTime.getTime() - startTime.getTime()));
		
		/*logger.debug(String.format("next issue %s   , type %s", 
				nextIssue.getIssueNum(), 
				nextIssue.getLotteryType()));*/
		startTime = new Date();
		bulletinBoard.setCurrIssue(nextIssue);
		cacheServ.setBulletinBoard(lotteryType, bulletinBoard);
		endTime = new Date();
		logger.debug(String.format("Consumed %s ms to setBulletinBoard", 
					endTime.getTime() - startTime.getTime()));
		
		startTime = new Date();
		issueServ.saveIssue(nextIssue);
		endTime = new Date();
		logger.debug(String.format("Consumed %s ms to saveIssue", 
					endTime.getTime() - startTime.getTime()));
		
		return nextIssue;
	}

	private Integer getIndexOfNextIssue(Issue currIssue, List<Issue> plans) {
		int indx = 0;
		Date nowTime = new Date();
				
		for(Issue issueTemp : plans) {
			if(currIssue != null) {
				if(currIssue.getStartTime().getTime() > issueTemp.getStartTime().getTime()) {
					indx++;
					continue;
				}
			}
			if(issueTemp.getStartTime().getTime() <= nowTime.getTime()
					&& issueTemp.getEndTime().getTime() > nowTime.getTime()
					&& issueTemp.getState() == Constants.IssueState.INIT.getCode()) {
				return indx;
			}
			
			indx++;
		}
		
		return null;
	}
	
	/*private Integer getIndexOfIssue(Issue currIssue, List<Issue> plans) {
		int indx = 0;
		
		if(currIssue == null || plans == null || plans.size() == 0) {
			return null;
		}
				
		for(Issue issueTemp : plans) {
			if(issueTemp.getId().intValue() == currIssue.getId().intValue()) {
				return indx;
			}
			
			indx++;
		}
		
		return null;
	}*/

	private boolean hasMoved(SysCode lottoType, BulletinBoard bulletinBoard, List<Issue> plans) {
		Date startTime = null;
		Date endTime = null;
		
		startTime = new Date();
		Issue issue = moveToNext(bulletinBoard,  lottoType.getCodeName(), plans);
		endTime = new Date();
		logger.debug(String.format("Consumed %s ms to moveToNext", 
				endTime.getTime() - startTime.getTime()));
		if(issue == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public String PreBet(Map<String, Object> params, Map<String, Object> data) {
		String playTypeName = null;
		PlayTypeFacade playTypeFacade = null;
		String lotteryType = (String)params.get("lottoType");
		Integer playTypeId = (Integer)params.get("playType");
		PlayType playType = playTypeServ.queryById(playTypeId);
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		UserInfo user = userServ.getUserByUserName(userName);
		
		if(playType.getPtName().equals("fs")) {
			playTypeName = playType.getClassification() + "/fs";
		}else if(playType.getPtName().equals("ds")){
			playTypeName = playType.getClassification() + "/ds";
		}else {
			playTypeName = playType.getClassification() + "/" + playType.getPtName();
		}
		playTypeFacade = PlayTypeFactory.getInstance().getPlayTypeFacade(playTypeName);
		
		Map<String, Object> retData = playTypeFacade.preProcessNumber(params, user);
		data.putAll(retData);
		
		return Integer.toString(Message.status.SUCCESS.getCode());
	}

	@Override
	public Issue queryNextIssue(Issue lastIssue) {
		if(lastIssue == null) {
			return null;
		}
		
		String lottoType = lastIssue.getLotteryType();
		List<Issue> plans = null;
		
		
		plans = cacheServ.getPlan(lottoType);
		
		if(plans == null || plans.size() == 0) {
			return null;
		}
		
		for(int i = 0; i < plans.size(); i++) {
			Issue issue = plans.get(i);
			if(issue.getId().intValue() == lastIssue.getId().intValue()) {
				if(i + 1 < plans.size()) {
					return plans.get(i + 1);
				}
			}
		}
		return null;
	}

	
}
