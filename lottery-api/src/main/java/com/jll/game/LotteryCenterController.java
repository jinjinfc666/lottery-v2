package com.jll.game;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Constants.SysCodeTypes;
import com.jll.common.constants.Constants.UserType;
import com.jll.common.constants.Message;
import com.jll.common.utils.MathUtil;
//import com.jll.common.utils.StringUtils;
import com.jll.dao.PageBean;
import com.jll.entity.Issue;
import com.jll.entity.OrderInfo;
import com.jll.entity.PlayType;
import com.jll.entity.PlayTypeNum;
import com.jll.entity.SysCode;
import com.jll.entity.UserAccount;
import com.jll.entity.UserInfo;
import com.jll.entity.display.BitColumn;
import com.jll.game.order.OrderService;
import com.jll.game.playtype.PlayTypeFacade;
import com.jll.game.playtype.PlayTypeNumService;
import com.jll.game.playtype.PlayTypeService;
import com.jll.game.playtypefacade.PlayTypeFactory;
import com.jll.report.MReportService;
import com.jll.sysSettings.syscode.SysCodeService;
import com.jll.user.UserInfoExtService;
import com.jll.user.UserInfoService;
import com.jll.user.wallet.WalletService;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;

@Api2Doc(id = "lotteryCenter", name = "lottery center")
@ApiComment(seeClass = UserInfo.class)
@RestController
@RequestMapping({ "/lotteries" })
public class LotteryCenterController {

	private Logger logger = Logger.getLogger(LotteryCenterController.class);

	@Resource
	IssueService issueServ;

	@Resource
	SysCodeService sysCodeServ;

	@Resource
	LotteryCenterService lotCenServ;

	@Resource
	CacheRedisService cacheServ;

	@Resource
	PlayTypeService playTypeServ;

	@Resource
	OrderService orderServ;

	@Resource
	WalletService walletServ;

	@Resource
	UserInfoService userServ;

	@Resource
	UserInfoExtService userExtServ;

	@Resource
	MReportService reportServ;

	@Resource
	PlayTypeNumService playTypeNumServ;
	
	@RequestMapping(value = "/{lottery-type}/pre-bet", method = {
			RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> PreBet(@PathVariable(name = "lottery-type", required = true) String lotteryType,
			@RequestBody Map<String, Object> params) {
		boolean isTimesValid = false;
		boolean isMonUnitValid = false;
		boolean isPlayTypeValid = false;
		Map<String, Object> resp = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		String betNum = (String) params.get("betNum");
		Integer times = (Integer) params.get("times");
		Float monUnit = null;
		try {
			monUnit = Float.parseFloat((String) params.get("monUnit"));
		} catch (Exception e) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getErrorMes());
			return resp;
		}
		Integer playTypeId = (Integer) params.get("playType");
		String retCode = null;

		params.put("lottoType", lotteryType);
		params.put("monUnit", monUnit);

		if (StringUtils.isBlank(betNum)) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_GAME_INVALID_BET_NUM.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_GAME_INVALID_BET_NUM.getErrorMes());
			return resp;
		}

		// 验证号码-----------------------------------------------Start---------------------------------------------------------
		String playTypeName = null;
		PlayType playType = null;
		PlayTypeFacade playTypeFacade = null;
		boolean isBetNumValid = false;
		OrderInfo order = new OrderInfo();
		order.setBetNum(betNum);
		if (playTypeId == null) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getErrorMes());
			return resp;
		}
		playType = playTypeServ.queryById(playTypeId);
		if (playType == null) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getErrorMes());
			return resp;
		}

		if (playType.getPtName().equals("fs")) {
			playTypeName = playType.getClassification() + "/fs";
		} else if (playType.getPtName().equals("ds")) {
			playTypeName = playType.getClassification() + "/ds";
		} else {
			playTypeName = playType.getClassification() + "/" + playType.getPtName();
		}
		playTypeFacade = PlayTypeFactory.getInstance().getPlayTypeFacade(playTypeName);
		isBetNumValid = playTypeFacade.validBetNum(order);
		if (!isBetNumValid) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_GAME_INVALID_BET_NUM.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_GAME_INVALID_BET_NUM.getErrorMes());
			return resp;
		}
		// 验证号码-----------------------------------------------End---------------------------------------------------------
		isTimesValid = cacheServ.isTimesValid(lotteryType, times);

		if (!isTimesValid) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_SYSTEM_INVALID_BETTING_TIMES.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_SYSTEM_INVALID_BETTING_TIMES.getErrorMes());
			return resp;
		}

		isMonUnitValid = cacheServ.isMonUnitValid(lotteryType, monUnit);
		if (!isMonUnitValid) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_SYSTEM_INVALID_BETTING_MONEY_UNIT.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_SYSTEM_INVALID_BETTING_MONEY_UNIT.getErrorMes());
			return resp;
		}

		isPlayTypeValid = cacheServ.isPlayTypeValid(lotteryType, playTypeId);
		if (!isPlayTypeValid) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_GAME_INVALID_PLAY_TYPE.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_GAME_INVALID_PLAY_TYPE.getErrorMes());
			return resp;
		}

		retCode = lotCenServ.PreBet(params, data);
		if (StringUtils.isBlank(retCode) || !retCode.equals(Integer.toString(Message.status.SUCCESS.getCode()))) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_GAME_FAILED_PROCESS_BETTING_NUM.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_GAME_FAILED_PROCESS_BETTING_NUM.getErrorMes());
			return resp;
		}

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}

	@RequestMapping(value = "/{lottery-type}/bet/zh/{zhFlag}/wallet/{walletId}", method = {
			RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> bet(@PathVariable(name = "lottery-type", required = true) String lotteryType,
			@PathVariable(name = "zhFlag", required = true) int zhFlag,
			@PathVariable(name = "walletId", required = true) int walletId, @RequestBody List<OrderInfo> orders) {
		Map<String, Object> resp = new HashMap<String, Object>();
		boolean isLotteryTypeExisting = false;
		String retCode = null;

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		UserInfo user = userServ.getUserByUserName(userName);

		int bettingBlockTimes = 3000;
		int bettingBlockCounter = 0;
		String keyLock = Constants.KEY_LOCK_BETTING;

		if (orders == null || orders.size() == 0) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getErrorMes());
			return resp;
		}

		keyLock = keyLock.replace("{userId}", String.valueOf(user.getId()));
		keyLock = keyLock.replace("{issue}", String.valueOf(orders.get(0).getIssueId()));

		while (bettingBlockCounter < bettingBlockTimes) {
			logger.debug(
					String.format("Thread Id %s    loker  %s   entering", Thread.currentThread().getId(), keyLock));

			if (cacheServ.lock(keyLock, keyLock, Constants.LOCK_BETTING_EXPIRED)) {
				try {
					logger.debug(String.format("Thread Id %s    loker   %s  enter", Thread.currentThread().getId(),
							keyLock));

					isLotteryTypeExisting = cacheServ.isCodeExisting(SysCodeTypes.LOTTERY_TYPES, lotteryType);
					if (!isLotteryTypeExisting) {
						resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
						resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_GAME_LOTTERY_TYPE_INVALID.getCode());
						resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_GAME_LOTTERY_TYPE_INVALID.getErrorMes());
						return resp;
					}

					Constants.ZhState zh = Constants.ZhState.getByCode(zhFlag);
					if (zh == null) {
						resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
						resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_GAME_INVALID_ZH_FLAG.getCode());
						resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_GAME_INVALID_ZH_FLAG.getErrorMes());
						return resp;
					}

					if (orders == null || orders.size() == 0) {
						resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
						resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_GAME_NO_ORDER.getCode());
						resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_GAME_NO_ORDER.getErrorMes());
						return resp;
					}

					if (!isRateValid(orders, user)) {
						resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
						resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_GAME_ORDER_ERROR_RATE.getCode());
						resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_GAME_ORDER_ERROR_RATE.getErrorMes());
						return resp;
					}

					retCode = orderServ.saveOrders(orders, walletId, zhFlag, lotteryType);
					if (!String.valueOf(Message.status.SUCCESS.getCode()).equals(retCode)) {
						resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
						resp.put(Message.KEY_ERROR_CODE, Message.Error.getErrorByCode(retCode).getCode());
						resp.put(Message.KEY_ERROR_MES, Message.Error.getErrorByCode(retCode).getErrorMes());
						return resp;
					}

					
					if (user.getUserType().intValue() == UserType.ENTRUST_PLAYER.getCode()) {
//						String payoutRate = userExtServ.queryFiledByName(user.getId(), "xyPayoutRate");
						String xyAmount = userExtServ.queryFiledByName(user.getId(), "xyAmount");
						UserAccount wallet = walletServ.queryById(walletId);
						user.setXyAmount(Double.parseDouble(xyAmount));
						user.setUsedCreditAmount(
								new BigDecimal(xyAmount).subtract(new BigDecimal(wallet.getBalance())));
						userExtServ.saveUserInfoExt(user);
//						user.setXyPayoutRate(Double.parseDouble(payoutRate));
						/*
						 * boolean isExtendMaxmium = validMaxPayout(lotteryType, orders, user); if
						 * (!isExtendMaxmium) { resp.put(Message.KEY_STATUS,
						 * Message.status.FAILED.getCode()); resp.put(Message.KEY_ERROR_CODE,
						 * Message.Error.ERROR_GAME_PAYOUT_UP_LIMIT.getCode());
						 * resp.put(Message.KEY_ERROR_MES,
						 * Message.Error.ERROR_GAME_PAYOUT_UP_LIMIT.getErrorMes()); return resp; }
						 */
					}

					resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());

					logger.debug(
							String.format("Thread Id %s    loker   %s  exit", Thread.currentThread().getId(), keyLock));
				} catch (Exception ex) {
					ex.printStackTrace();
					resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
					resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_OTHERS.getCode());
					resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_OTHERS.getErrorMes());
					return resp;
				} finally {
					// cacheServ.releaseLock(keyLock);
					break;
				}
			}

			bettingBlockCounter++;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resp;
	}

	private boolean isRateValid(List<OrderInfo> orders, UserInfo userInfo) {
		return playTypeNumServ.isRateValid(orders, userInfo);
	}
	
	private boolean validMaxPayout(String lotteryType, List<OrderInfo> orders, UserInfo user) {
		// String payoutRate = userExtServ.queryFiledByName(user.getId(),
		// "xyPayoutRate");
		Integer pageSize = Constants.Pagination.SUM_NUMBER.getCode();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String todayStr = formater.format(today);
		String startTime = todayStr + " 00:00:00";
		String endTime = todayStr + " 23:59:59";
		PageBean list = null;
		Double totalPayoutAmount = null;
		Double maxPayoutRate = user.getXyPayoutRate();
		Double maxPayoutAmount = MathUtil.multiply(user.getXyAmount(), maxPayoutRate, Double.class);

		Map<String, Object> ret = new HashMap<>();
		ret.put("pageSize", pageSize);
		ret.put("pageIndex", 1);
		ret.put("userName", user.getUserName());
		ret.put("startTime", startTime);
		ret.put("endTime", endTime);
		ret.put("userType", user.getUserType());
		try {
			list = reportServ.queryAll(ret);

		} catch (Exception e) {

		}

		if (list == null || list.getContent() == null || list.getContent().size() == 0) {
			return true;
		}

		Object[] profitRec = (Object[]) list.getContent().get(0);

		if (profitRec[8] == null) {
			return true;
		}

		totalPayoutAmount = ((BigDecimal) (profitRec[8])).doubleValue();

		int counter = 0;

		for (OrderInfo order : orders) {
			if (order.getIsPrize() == null) {
				continue;
			}

			if (order.getIsPrize() == 1) {

				counter++;

				Map<String, Object> params = new HashMap<>();
				params.put("betNum", order.getBetNum());
				params.put("times", order.getTimes());
				params.put("monUnit", String.valueOf(order.getPattern().doubleValue()));
				params.put("playType", order.getPlayType());

				Map<String, Object> preBetRec = PreBet(lotteryType, params);
				Double winAmount = new Double(
						(Float) ((HashMap<String, Object>) preBetRec.get("data")).get("maxWinAmount"));
				totalPayoutAmount = MathUtil.add(totalPayoutAmount, winAmount, Double.class);
			}
		}

		System.out.print(String.format("counter : %s", counter));

		if (totalPayoutAmount >= maxPayoutAmount) {
			return false;
		}

		return true;
	}

	@RequestMapping(value = "/{lottery-type}/betting-issue", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryBettingIssue(
			@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<String, Object>();

		Map<String, Object> data = new HashMap<>();
		List<PlayType> playTypes = null;
		List<PlayType> playTypess = new ArrayList<PlayType>();
		boolean isExisting = false;
		// boolean hasMoreIssue = false;
		Issue currentIssue = null;
		Issue lastIssue = null;
		SysCode lotteryTypeObj = null;
		Date nowTime = new Date();
		Long downCounter = null;

		isExisting = cacheServ.isCodeExisting(SysCodeTypes.LOTTERY_TYPES, lotteryType);
		if (!isExisting) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_GAME_LOTTERY_TYPE_INVALID.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_GAME_LOTTERY_TYPE_INVALID.getErrorMes());
			return resp;
		}

		lotteryTypeObj = cacheServ.getSysCode(SysCodeTypes.LOTTERY_TYPES.getCode(), lotteryType);

		playTypes = cacheServ.getPlayType(lotteryTypeObj);

		if (playTypes != null && playTypes.size() > 0) {
			Integer stateas = null;
			for (int i = 0; i < playTypes.size(); i++) {
				PlayType playType = playTypes.get(i);
				stateas = playType.getState();
				if ((int) stateas == 1) {
					playTypess.add(playType);
				}
			}
		}

		if (playTypes == null || playTypes.size() == 0) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_GAME_MISSTING_PLAY_TYPE.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_GAME_MISSTING_PLAY_TYPE.getErrorMes());
			return resp;
		}

		currentIssue = lotCenServ.queryBettingIssue(lotteryType);
		lastIssue = lotCenServ.queryLastIssue(lotteryType);

		if (!lotteryType.equals(Constants.LottoType.MMC.getCode())) {
			if (currentIssue == null) {
				/*
				 * currentIssue = new Issue(); Issue nextIssue =
				 * lotCenServ.queryNextIssue(lastIssue); if(nextIssue != null) { downCounter =
				 * nextIssue.getStartTime().getTime() - nowTime.getTime(); downCounter =
				 * downCounter/1000; currentIssue.setDownCounter(downCounter); }else {
				 * currentIssue.setDownCounter(-1L); }
				 */
			} else {
				String codeTypeName = Constants.KEY_LOTTO_ATTRI_PREFIX + lotteryType;
				String codeValName = Constants.LotteryAttributes.BETTING_END_TIME.getCode();
				SysCode lottoAttri = cacheServ.getSysCode(codeTypeName, codeValName);
				downCounter = currentIssue.getEndTime().getTime() - nowTime.getTime();
				downCounter = downCounter / 1000 - Long.valueOf(lottoAttri.getCodeVal());

				if (downCounter < 0) {
					// Issue nextIssue = lotCenServ.queryNextIssue(lastIssue);
					// if(nextIssue != null) {
					// downCounter = nextIssue.getStartTime().getTime() -
					// nowTime.getTime();
					// downCounter = downCounter/1000;
					// currentIssue.setDownCounter(downCounter);
					// }else {
					// currentIssue.setDownCounter(-1L);
					// }
					currentIssue.setDownCounter(0L);
				} else {
					currentIssue.setDownCounter(downCounter);
				}
			}
			data.put("currIssue", currentIssue);
		} else {
			data.put("currIssue", null);
		}
		// 官网------start----------
		String officialWebSite = Constants.LotteryAttributes.OFFICIAL_WEB_SITE.getCode();
		String lotteryConfig = Constants.KEY_LOTTO_ATTRI_PREFIX + lotteryType;
		SysCode sysCode = cacheServ.getSysCode(lotteryConfig, officialWebSite);
		String officialWebSiteValue = null;
		if (sysCode != null) {
			officialWebSiteValue = sysCode.getCodeVal();
		}
		// 官网------END----------
		// 官网------单笔最大中奖金额----------
		String maxPrizeAmount = Constants.LotteryAttributes.MAX_PRIZE_AMOUNT.getCode();
		SysCode sysCodeMax = cacheServ.getSysCode(lotteryConfig, maxPrizeAmount);
		String maxPrizeAmountValue = null;
		if (sysCodeMax != null) {
			maxPrizeAmountValue = sysCodeMax.getCodeVal();
		}
		data.put("maxPrizeAmount", maxPrizeAmountValue);
		// 官网------单笔最大中奖金额----------
		data.put("officialWebSite", officialWebSiteValue);
		data.put("lastIssue", lastIssue);
		data.put("playType", playTypess);
		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}

	@RequestMapping(value = "/{lottery-type}/times", method = {
			RequestMethod.GET }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryTimes(@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<String, Object>();
		boolean isExisting = false;

		isExisting = cacheServ.isCodeExisting(SysCodeTypes.LOTTERY_TYPES, lotteryType);
		if (!isExisting) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_GAME_LOTTERY_TYPE_INVALID.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_GAME_LOTTERY_TYPE_INVALID.getErrorMes());
			return resp;
		}

		SysCode sysCode = cacheServ.getBetTimes(lotteryType);

		if (sysCode == null || StringUtils.isBlank(sysCode.getCodeVal())) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_SYSTEM_INVALID_BETTING_TIMES.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_SYSTEM_INVALID_BETTING_TIMES.getErrorMes());
			return resp;
		}

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put("times", sysCode.getCodeVal());
		return resp;
	}

	@RequestMapping(value = "/{lottery-type}/patterns", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryPattern(@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<String, Object>();
		boolean isExisting = false;

		isExisting = cacheServ.isCodeExisting(SysCodeTypes.LOTTERY_TYPES, lotteryType);
		if (!isExisting) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_GAME_LOTTERY_TYPE_INVALID.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_GAME_LOTTERY_TYPE_INVALID.getErrorMes());
			return resp;
		}

		SysCode sysCode = cacheServ.getMoneyUnit(lotteryType);

		if (sysCode == null || StringUtils.isBlank(sysCode.getCodeVal())) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_SYSTEM_INVALID_BETTING_MONEY_UNIT.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_SYSTEM_INVALID_BETTING_MONEY_UNIT.getErrorMes());
			return resp;
		}

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put("patterns", sysCode.getCodeVal());
		return resp;
	}

	@RequestMapping(value = "/{lottery-type}/play-type/{play-type}/prize-rates", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryPrizeRate(@PathVariable(name = "lottery-type", required = true) String lotteryType,
			@PathVariable(name = "play-type", required = true) Integer playTypeId, HttpServletRequest request) {
		Map<String, Object> resp = new HashMap<String, Object>();
		String userName = null;
		boolean isExisting = false;
		UserInfo user = null;
		Float prizePattern = null;
		// Integer playTypeIdInt = null;
		String playTypeName = null;
		PlayTypeFacade playTypeFacade = null;
		// Map<String, Object> params = new HashMap<>();

		isExisting = cacheServ.isCodeExisting(SysCodeTypes.LOTTERY_TYPES, lotteryType);
		if (!isExisting) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_GAME_LOTTERY_TYPE_INVALID.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_GAME_LOTTERY_TYPE_INVALID.getErrorMes());
			return resp;
		}

		userName = SecurityContextHolder.getContext().getAuthentication().getName();
		user = userServ.getUserByUserName(userName);
		prizePattern = userServ.calPrizePattern(user, lotteryType);

		PlayType playType = playTypeServ.queryById(playTypeId);
		if (playType == null) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getErrorMes());
			return resp;
		}

		if (playType.getPtName().equals("fs")) {
			playTypeName = playType.getClassification() + "/fs";
		} else if (playType.getPtName().equals("ds")) {
			playTypeName = playType.getClassification() + "/ds";
		} else {
			playTypeName = playType.getClassification() + "/" + playType.getPtName();
		}
		playTypeFacade = PlayTypeFactory.getInstance().getPlayTypeFacade(playTypeName);

		Map<String, Object> retPreProcess = playTypeFacade.querySingleBettingPrizeRange(prizePattern);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, retPreProcess);
		return resp;
	}

	/**
	 * @param currT    当前奖金模式 1700-2000
	 * @param currR    当前用户平台点数
	 * @param superior 上级用户ID
	 * @return
	 */
	@RequestMapping(value = "/prize-templates", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryPrizeTemplate(@RequestParam(name = "currT", required = false) Integer currT,
			@RequestParam(name = "currR", required = false) Float currR,
			@RequestParam(name = "superior", required = false) Integer superior) {
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		UserInfo user = null;
		Float maxT = null;
		Float maxR = null;
		Integer minT = null;
		Float minR = new Float(0.0);
		String keyRunTimeArg = Constants.SysCodeTypes.SYS_RUNTIME_ARGUMENT.getCode();
		String keyPrizeRange = Constants.SysRuntimeArgument.LOTTO_PRIZE_RATE.getCode();
		SysCode prizeRange = cacheServ.getSysCode(keyRunTimeArg, keyPrizeRange);
		String[] prizeRanges = prizeRange.getCodeVal().split(",");

		if (superior != null) {
			user = userServ.getUserById(superior);
			if (user == null) {
				resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
				resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_USER_NO_VALID_USER.getCode());
				resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_USER_NO_VALID_USER.getErrorMes());
				return resp;
			}

		} else {
			user = userServ.getCurLoginInfo();
		}

		boolean isAgency = UserType.isAgency(UserType.getStateByCode(user.getUserType()));
		if (!isAgency && user.getUserType().intValue() != Constants.UserType.GENERAL_AGENCY.getCode()) {
			resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_USER_INVALID_USER_TYPE.getCode());
			resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_USER_INVALID_USER_TYPE.getErrorMes());
			return resp;
		}

		maxT = userServ.calPrizePattern(user, null);
		minT = Integer.valueOf(prizeRanges[0]);
		maxR = user.getPlatRebate().floatValue();

		if (currR != null) {
			if (currR.floatValue() > maxR) {
				resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
				resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode());
				resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getErrorMes());
				return resp;
			}
		}

		if (currT != null) {
			currT = currT / 2;
			if (currT.floatValue() > maxT) {
				resp.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
				resp.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode());
				resp.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getErrorMes());
				return resp;
			}
		}

		if (currT != null) {
			currR = userServ.calPlatformRebate(currT).floatValue();
		} else if (currR != null) {
			UserInfo user_ = new UserInfo();
			user_.setPlatRebate(new BigDecimal(currR));
			Float currT_ = userServ.calPrizePattern(user_, null);
			currT = currT_.intValue();
		} else {
			currR = minR;
			currT = minT;
		}

		data.put(Constants.KEY_PRIZE_TEMPLATE_MIN_TEMPLATE, minT * 2);
		data.put(Constants.KEY_PRIZE_TEMPLATE_MAX_TEMPLATE, maxT.intValue() * 2);
		data.put(Constants.KEY_PRIZE_TEMPLATE_MIN_REBATE, minR);
		data.put(Constants.KEY_PRIZE_TEMPLATE_MAX_REBATE, maxR);
		data.put(Constants.KEY_PRIZE_TEMPLATE_CURR_TEMPLATE, currT * 2);
		data.put(Constants.KEY_PRIZE_TEMPLATE_CURR_REBATE, currR);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}

	// 未结算的注单 (只给30期)前端只传彩种，默认查询state为0的数据显示给前端
	@RequestMapping(value = "/{lottery-type}/unsettled-bet", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryUnsettledBet(
			@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> map = new HashMap();
		String userName = null;
		userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if (StringUtils.isBlank(userName)) {
			map.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			map.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_USER_NO_VALID_USER.getCode());
			map.put(Message.KEY_ERROR_MES, Message.Error.ERROR_USER_NO_VALID_USER.getErrorMes());
		} else {
			map = issueServ.queryUnsettlement(lotteryType, userName);
		}
		return map;

	}

	// 近期注单 前端只传过来彩种，后台默认只查询30条记录给前端
	@RequestMapping(value = "/{lottery-type}/recent-bet", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryRecentBet(
			@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> map = new HashMap();
		String userName = null;
		userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if (StringUtils.isBlank(userName)) {
			map.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			map.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_USER_NO_VALID_USER.getCode());
			map.put(Message.KEY_ERROR_MES, Message.Error.ERROR_USER_NO_VALID_USER.getErrorMes());
		} else {
			map = issueServ.queryNear(lotteryType, userName);
		}
		return map;
	}

	// query main pan shi
	@RequestMapping(value = "/{lottery-type}/play-type/main-ps", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryMainPs(@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		List<List<PlayTypeNum>> mainPs = playTypeServ.queryMainPs(lotteryType);
		List<List<PlayTypeNum>> mainPsHs = playTypeServ.queryMainPsHs(lotteryType);
		List<BitColumn> mainPsDwd = playTypeServ.queryMainPsDwd(lotteryType);
		data.put("mainPs", mainPs);
		data.put("hs", mainPsHs);
		data.put("dwd", mainPsDwd);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}

	// query main pan shi
	@RequestMapping(value = "/{lottery-type}/play-type/yzps", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryYzps(@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		List<List<PlayTypeNum>> mainPs = playTypeServ.queryMainPs(lotteryType);
		List<List<PlayTypeNum>> bwsz = playTypeServ.queryBwsz(lotteryType);
		List<List<PlayTypeNum>> swsz = playTypeServ.querySwsz(lotteryType);
		List<List<PlayTypeNum>> gwsz = playTypeServ.queryGwsz(lotteryType);
		data.put("mainPs", mainPs);
		data.put("bwsz", bwsz);
		data.put("swsz", swsz);
		data.put("gwsz", gwsz);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}

	// query main pan shi
	@RequestMapping(value = "/{lottery-type}/play-type/ezdw/{numType}", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryEzps(@PathVariable(name = "lottery-type", required = true) String lotteryType,
			@PathVariable(name = "numType", required = true) String numType) {
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		List<List<PlayTypeNum>> ezdw = playTypeServ.queryEzdw(lotteryType, numType);
		data.put("ezdw", ezdw);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}
	
	@RequestMapping(value = "/{lottery-type}/play-type/ezhs/{numType}", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryEzhs(@PathVariable(name = "lottery-type", required = true) String lotteryType,
			@PathVariable(name = "numType", required = true) String numType) {
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		List<List<PlayTypeNum>> ezhs = playTypeServ.queryEzhs(lotteryType, numType);
		data.put("ezhs", ezhs);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}
	
	@RequestMapping(value = "/{lottery-type}/play-type/szdw", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> querySzdw(@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		List<List<PlayTypeNum>> szdw = playTypeServ.querySzdw(lotteryType);
		data.put("szdw", szdw);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}
	
	@RequestMapping(value = "/{lottery-type}/play-type/ezzh", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryEzzh(@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		List<List<PlayTypeNum>> ezzh = playTypeServ.queryEzzh(lotteryType);
		data.put("ezzh", ezzh);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}
	
	@RequestMapping(value = "/{lottery-type}/play-type/szhs", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> querySzhs(@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		List<List<PlayTypeNum>> szhs = playTypeServ.querySzhs(lotteryType);
		data.put("szhs", szhs);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}
	
	@RequestMapping(value = "/{lottery-type}/play-type/szzh", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> querySzzh(@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		List<List<PlayTypeNum>> szzh = playTypeServ.querySzzh(lotteryType);
		data.put("szzh", szzh);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}
	
	@RequestMapping(value = "/{lottery-type}/play-type/zs", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryZs(@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		List<BitColumn> zs = playTypeServ.queryZx3(lotteryType);
		data.put("zs", zs);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}
	
	@RequestMapping(value = "/{lottery-type}/play-type/zl", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryZl(@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		List<BitColumn> zl = playTypeServ.queryZx6(lotteryType);
		data.put("zl", zl);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}
	
	@RequestMapping(value = "/{lottery-type}/play-type/yztg", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryYztg(@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		List<BitColumn> dwd = playTypeServ.queryMainPsDwd(lotteryType);
		data.put("dwd", dwd);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}
	
	@RequestMapping(value = "/{lottery-type}/play-type/kd", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryKd(@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		List<BitColumn> kd = playTypeServ.queryKd(lotteryType);
		data.put("kd", kd);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}
	
	@RequestMapping(value = "/{lottery-type}/play-type/fs", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryFs(@PathVariable(name = "lottery-type", required = true) String lotteryType) {
		Map<String, Object> resp = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		List<BitColumn> fs = playTypeServ.queryFs(lotteryType);
		data.put("fs", fs);

		resp.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		resp.put(Message.KEY_DATA, data);
		return resp;
	}
}
