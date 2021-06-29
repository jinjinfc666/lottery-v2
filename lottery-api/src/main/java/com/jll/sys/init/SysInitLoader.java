package com.jll.sys.init;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Constants.LottoType;
import com.jll.common.threadpool.ThreadPoolManager;
import com.jll.common.utils.StringUtils;
import com.jll.common.utils.Utils;
import com.jll.entity.IpBlackList;
import com.jll.entity.Issue;
import com.jll.entity.PayChannel;
import com.jll.entity.PayType;
import com.jll.entity.PlayType;
import com.jll.entity.PlayTypeNum;
import com.jll.entity.SysCode;
import com.jll.game.LotteryCenterServiceImpl;
import com.jll.game.LotteryTypeFactory;
import com.jll.game.LotteryTypeService;
import com.jll.game.mesqueue.MessageDelegateListener;
import com.jll.game.playtype.PlayTypeNumDao;
import com.jll.game.playtype.PlayTypeNumService;
import com.jll.game.playtype.PlayTypeService;
import com.jll.pay.PaymentService;
import com.jll.sys.blacklist.IpBlackListService;
import com.jll.sys.deposit.PayChannelService;
import com.jll.sys.deposit.PayTypeService;
import com.jll.sysSettings.syscode.SysCodeService;

public class SysInitLoader implements MessageDelegateListener{
	
	private Logger logger = Logger.getLogger(LotteryCenterServiceImpl.class);
	
	@Resource
	CacheRedisService cacheServ;
	
	@Resource
	SysCodeService sysCodeServ;
	
	@Resource
	PlayTypeService playTypeServ;
	
	@Resource
	PaymentService paymentService;
	
	@Resource
	IpBlackListService ipBlackListService;
	
	@Resource
	PayTypeService payTypeService;
	
	@Resource
	PayChannelService payChannelService;
	
	@Resource
	PlayTypeNumService playTypeNumServ;
	
	@Resource
	PlayTypeNumDao playTypeNumDao;
	
	private final static Map<String, String> keyMap = new HashMap<>();
	static{
		keyMap.put("213","279");
		keyMap.put("214","280");
		keyMap.put("215","281");
		keyMap.put("216","282");
		keyMap.put("217","283");
		keyMap.put("218","284");
		keyMap.put("219","285");
		keyMap.put("220","286");
		keyMap.put("221","287");
		keyMap.put("222","288");
		keyMap.put("223","289");
		keyMap.put("224","290");
		keyMap.put("225","291");
		keyMap.put("226","292");
		keyMap.put("227","293");
		keyMap.put("228","294");
		keyMap.put("229","295");
		keyMap.put("230","296");
		keyMap.put("231","297");
		keyMap.put("232","298");
		keyMap.put("233","299");
		keyMap.put("234","300");
		keyMap.put("235","301");
		keyMap.put("236","302");
		keyMap.put("237","303");
		keyMap.put("238","304");
		keyMap.put("239","305");
		keyMap.put("240","306");
		keyMap.put("241","307");
		keyMap.put("242","308");
		keyMap.put("243","309");
		keyMap.put("244","310");
		keyMap.put("245","311");
		keyMap.put("246","312");
		keyMap.put("247","313");
		keyMap.put("248","314");
		keyMap.put("249","315");
		keyMap.put("250","316");
		keyMap.put("251","317");
		keyMap.put("252","318");
		keyMap.put("253","319");
		keyMap.put("254","320");
		keyMap.put("255","321");
		keyMap.put("256","322");
		keyMap.put("257","323");
		keyMap.put("258","324");
		keyMap.put("259","325");
		keyMap.put("260","326");
		keyMap.put("261","327");
		keyMap.put("262","328");
		keyMap.put("263","329");
		keyMap.put("264","330");
		keyMap.put("265","331");
		keyMap.put("266","332");
		keyMap.put("267","333");
		keyMap.put("268","334");
		keyMap.put("269","335");
		keyMap.put("270","336");
		keyMap.put("271","337");
		keyMap.put("272","338");
		keyMap.put("273","339");
		keyMap.put("274","340");
		keyMap.put("275","341");
		keyMap.put("276","342");
		keyMap.put("277","343");
		keyMap.put("278","344");
	}
	
	public void init() {
		init5DigitsOne2Ten();
		initSysCode();
		initPlayType();
		initIpBlackList();
		initPayType();
		initPayChannel();
//		init5DigitsOne2Ten();
	}





	private void initSysCode() {
		initLotteryType();
		initLotteryAttributes();
		initSysCodePlayType();
		initAccOpeType();
		initPaymentPlatform();
		initLuckyDraw();
		initSignInDay();
		initSysCodePayType();
		initSysRuntimeArgument();
		initBankCodeList();
		initWithdrawalCfg();
		initDemoUserCfg();
		initPanKou();
		initPlayTypeNum();
		
//		initPlayTYpeNumData();
	}
	
	


	private void initPlayTYpeNumData() {
		playTypeNumServ.initPlayTYpeNumData();
	}





	private void initPlayTypeNum() {
		String codeTypeName = Constants.KEY_PLAY_TYPE_NUM;
		Map<String, Map<String, Map<String, PlayTypeNum>>> lotteryTypePlayTypeNums = cacheServ.queryPlayTypeNum(codeTypeName);
		
		/*Map<String, Map<String, PlayTypeNum>> lotteryTypePlayTypeNumsTc3 = lotteryTypePlayTypeNums.get("tc3");
		List<PlayTypeNum> fc3dPlayTypeNums = new ArrayList<>();
		lotteryTypePlayTypeNumsTc3.entrySet().stream().forEach(entry->{
			String classification = entry.getKey();
			lotteryTypePlayTypeNumsTc3.get(classification).entrySet().stream().forEach(entry1->{
				String key = entry1.getKey();
				PlayTypeNum playTypeNum = entry1.getValue();
				PlayTypeNum fc3dPlayTypeNum = new PlayTypeNum();
				
				BeanUtils.copyProperties(playTypeNum, fc3dPlayTypeNum);
				logger.info(String.format("current playType id %s, playTypeId %s", playTypeNum.getId(), playTypeNum.getPlayTypeId()));
				fc3dPlayTypeNum.setPlayTypeId(Long.parseLong(keyMap.get(String.valueOf(playTypeNum.getPlayTypeId()))));
				fc3dPlayTypeNum.setId(null);
				fc3dPlayTypeNums.add(fc3dPlayTypeNum);
				
				playTypeNumDao.updatePlayTypeNum(playTypeNum);
			});
		});
		
		fc3dPlayTypeNums.stream().forEach(item->{
			playTypeNumDao.updatePlayTypeNum(item);
		});*/
		
		if(MapUtils.isEmpty(lotteryTypePlayTypeNums)) {
			Map<String, Map<String, Map<String, PlayTypeNum>>> lotteryTypePlayTypeNums_ = new HashMap<String, Map<String, Map<String, PlayTypeNum>>>();
			List<PlayType> tc3PlayTypes = playTypeServ.queryPlayType(LottoType.TC3.name());
			List<PlayType> fc3dPlayTypes = playTypeServ.queryPlayType(LottoType.FC3D.name());
			tc3PlayTypes.addAll(fc3dPlayTypes);
			
			tc3PlayTypes.forEach(playType->{
				List<PlayTypeNum> playTypeNumInList = playTypeNumServ.queryPlayTypeNum(new Long(playType.getId()));
				Map<String, PlayTypeNum> playTypeNumInMap = playTypeNumInList.stream().collect(Collectors.toMap(PlayTypeNum::getBetNum,Function.identity()));
				Map<String, PlayTypeNum> playTypeNumInTreeMap = new TreeMap<>();
				playTypeNumInTreeMap.putAll(playTypeNumInMap);
				Iterator<String> keys = playTypeNumInTreeMap.keySet().iterator();
				while(keys.hasNext()){
					System.out.println(keys.next());
				}
				Map<String, Map<String, PlayTypeNum>> playTypePlayTypeNums = Optional.ofNullable(lotteryTypePlayTypeNums_.get(playType.getLotteryType())).orElse(new HashMap<String, Map<String, PlayTypeNum>>());
				if(playTypePlayTypeNums.size() == 0)
					lotteryTypePlayTypeNums_.put(playType.getLotteryType(), playTypePlayTypeNums);
					
				playTypePlayTypeNums.put(playType.getClassification(), playTypeNumInMap);
			});
			
			cacheServ.setPlayTypeNum(codeTypeName, lotteryTypePlayTypeNums_);
		}
	}





	//加载签到活动
	private void initSignInDay() {
		String codeTypeName = Constants.SysCodeTypes.SIGN_IN_DAY.getCode();
		Map<String, SysCode> signInDay = cacheServ.getSysCode(codeTypeName);
		List<SysCode> sysCodes = null;
		
		if(signInDay == null || signInDay.size() == 0) {
			sysCodes = sysCodeServ.queryAllSmallType(codeTypeName);
			List<SysCode> sysCodeTypes = sysCodeServ.queryByCodeNameBigType(codeTypeName);
			
			if(sysCodes == null || sysCodes.size() == 0
					|| sysCodeTypes == null
					|| sysCodeTypes.size() == 0) {
				return ;
			}
			
			sysCodes.add(sysCodeTypes.get(0));
			
			cacheServ.setSysCode(codeTypeName, sysCodes);
		}
	}
	
	//加载提款设置
	private void initWithdrawalCfg() {
		String codeTypeName = Constants.SysCodeTypes.WITHDRAWAL_CFG.getCode();
		Map<String, SysCode> cacheCodes = cacheServ.getSysCode(codeTypeName);
		List<SysCode> sysCodes = null;
		
		if(cacheCodes == null || cacheCodes.size() == 0) {
			sysCodes = sysCodeServ.queryAllSmallType(codeTypeName);
			List<SysCode> sysCodeTypes = sysCodeServ.queryByCodeNameBigType(codeTypeName);
			
			if(sysCodes == null || sysCodes.size() == 0
					|| sysCodeTypes == null
					|| sysCodeTypes.size() == 0) {
				return ;
			}
			
			sysCodes.add(sysCodeTypes.get(0));
			
			cacheServ.setSysCode(codeTypeName, sysCodes);
		}
	}
	
	//加载试玩用户生成设置
	private void initDemoUserCfg() {
		String codeTypeName = Constants.SysCodeTypes.DEMO_USER_CFG.getCode();
		Map<String, SysCode> cacheCodes = cacheServ.getSysCode(codeTypeName);
		List<SysCode> sysCodes = null;
		
		if(cacheCodes == null || cacheCodes.size() == 0) {
			sysCodes = sysCodeServ.queryAllSmallType(codeTypeName);
			List<SysCode> sysCodeTypes = sysCodeServ.queryByCodeNameBigType(codeTypeName);
			
			if(sysCodes == null || sysCodes.size() == 0
					|| sysCodeTypes == null
					|| sysCodeTypes.size() == 0) {
				return ;
			}
			
			sysCodes.add(sysCodeTypes.get(0));
			
			cacheServ.setSysCode(codeTypeName, sysCodes);
		}
	}
	
	//加载流水类型
	private void initAccOpeType() {
		String codeTypeName = Constants.SysCodeTypes.FLOW_TYPES.getCode();
		Map<String, SysCode> accOpeType = cacheServ.getSysCode(codeTypeName);
		List<SysCode> sysCodes = null;
		
		if(accOpeType == null || accOpeType.size() == 0) {
			sysCodes = sysCodeServ.queryAllSmallType(codeTypeName);
			List<SysCode> sysCodeTypes = sysCodeServ.queryByCodeNameBigType(codeTypeName);
			
			if(sysCodes == null || sysCodes.size() == 0
					|| sysCodeTypes == null
					|| sysCodeTypes.size() == 0) {
				return ;
			}
			
			sysCodes.add(sysCodeTypes.get(0));
			
			cacheServ.setSysCode(codeTypeName, sysCodes);
		}
	}
	//加载支付平台
	private void initPaymentPlatform() {
		String codeTypeName = Constants.SysCodeTypes.PAYMENT_PLATFORM.getCode();
		Map<String, SysCode> paymentPlatform = cacheServ.getSysCode(codeTypeName);
		List<SysCode> sysCodes = null;
		
		if(paymentPlatform == null || paymentPlatform.size() == 0) {
			sysCodes = sysCodeServ.queryAllSmallType(codeTypeName);
			List<SysCode> sysCodeTypes = sysCodeServ.queryByCodeNameBigType(codeTypeName);
			
			if(sysCodes == null || sysCodes.size() == 0
					|| sysCodeTypes == null
					|| sysCodeTypes.size() == 0) {
				return ;
			}
			
			sysCodes.add(sysCodeTypes.get(0));
			
			cacheServ.setSysCode(codeTypeName, sysCodes);
		}
	}
	//加载幸运抽奖
	private void initLuckyDraw() {
		String codeTypeName = Constants.SysCodeTypes.LUCKY_DRAW.getCode();
		Map<String, SysCode> luckyDraw = cacheServ.getSysCode(codeTypeName);
		List<SysCode> sysCodes = null;
		
		if(luckyDraw == null || luckyDraw.size() == 0) {
			sysCodes = sysCodeServ.queryAllSmallType(codeTypeName);
			List<SysCode> sysCodeTypes = sysCodeServ.queryByCodeNameBigType(codeTypeName);
			
			if(sysCodes == null || sysCodes.size() == 0
					|| sysCodeTypes == null
					|| sysCodeTypes.size() == 0) {
				return ;
			}
			
			sysCodes.add(sysCodeTypes.get(0));
			
			cacheServ.setSysCode(codeTypeName, sysCodes);
		}
	}

	//加载彩种类型
	private void initLotteryType() {
		String codeTypeName = Constants.SysCodeTypes.LOTTERY_TYPES.getCode();
		Map<String, SysCode> lottoTypes = cacheServ.getSysCode(codeTypeName);
		List<SysCode> sysCodes = null;
		
		if(lottoTypes == null || lottoTypes.size() == 0) {
			sysCodes = sysCodeServ.queryAllSmallType(codeTypeName);
			List<SysCode> sysCodeTypes = sysCodeServ.queryByCodeNameBigType(codeTypeName);
			
			if(sysCodes == null || sysCodes.size() == 0
					|| sysCodeTypes == null
					|| sysCodeTypes.size() == 0) {
				return ;
			}
			
			sysCodes.add(sysCodeTypes.get(0));
			
			cacheServ.setSysCode(codeTypeName, sysCodes);
		}
	}
	//加载充值方式类型   这是SysCode表里的Pay_Type(充值方式)的缓存
	private void initSysCodePayType() {
		String codeTypeName = Constants.SysCodeTypes.PAY_TYPE_CLASS.getCode();
		Map<String, SysCode> sysCodePayType = cacheServ.getSysCode(codeTypeName);
		List<SysCode> sysCodes = null;
		
		if(sysCodePayType == null || sysCodePayType.size() == 0) {
			sysCodes = sysCodeServ.queryAllSmallType(codeTypeName);
			List<SysCode> sysCodeTypes = sysCodeServ.queryByCodeNameBigType(codeTypeName);
			
			if(sysCodes == null || sysCodes.size() == 0
					|| sysCodeTypes == null
					|| sysCodeTypes.size() == 0) {
				return ;
			}
			
			sysCodes.add(sysCodeTypes.get(0));
			
			cacheServ.setSysCode(codeTypeName, sysCodes);
		}
	}
	//加载玩法类型
	private void initSysCodePlayType() {
		String codeTypeName = Constants.SysCodePlayType.CT_PLAY_TYPE_CLASSICFICATION.getCode();
		Map<String, SysCode> playTypes = cacheServ.getSysCode(codeTypeName);
		List<SysCode> sysCodes = null;
		
		if(playTypes == null || playTypes.size() == 0) {
			sysCodes = sysCodeServ.queryAllSmallType(codeTypeName);
			List<SysCode> sysCodeTypes = sysCodeServ.queryByCodeNameBigType(codeTypeName);
			
			if(sysCodes == null || sysCodes.size() == 0
					|| sysCodeTypes == null
					|| sysCodeTypes.size() == 0) {
				return ;
			}
			
			sysCodes.add(sysCodeTypes.get(0));
			
			cacheServ.setSysCode(codeTypeName, sysCodes);
		}
	}
	
	//加载ip限制
	private void initIpBlackList() {
		String codeTypeName = Constants.IpBlackList.IP_BLACK_LIST.getCode();
		Map<Integer, IpBlackList> ipBlackListMaps = cacheServ.getIpBlackList(codeTypeName);
		List<IpBlackList> ipBlackLists = null;
		
		if(ipBlackListMaps == null || ipBlackListMaps.size() == 0) {
			ipBlackLists = ipBlackListService.query();
			
			if(ipBlackLists == null || ipBlackLists.size() == 0) {
				return ;
			}
			cacheServ.setIpBlackList(codeTypeName, ipBlackLists);
		}
	}
	//加载彩种的属性
	private void initLotteryAttributes() {
		List<String> lotteryAttributesList=Constants.SysCodeTypes.getList();
		if(lotteryAttributesList!=null&&lotteryAttributesList.size()>0) {
			for(int a=0;a<lotteryAttributesList.size();a++) {
				String codeTypeName = lotteryAttributesList.get(a);
				Map<String, SysCode> lottoTypes = cacheServ.getSysCode(codeTypeName);
				List<SysCode> sysCodes = null;
				
				if(lottoTypes == null || lottoTypes.size() == 0) {
					sysCodes = sysCodeServ.queryAllSmallType(codeTypeName);
					List<SysCode> sysCodeTypes = sysCodeServ.queryByCodeNameBigType(codeTypeName);
					
					if(sysCodes == null || sysCodes.size() == 0
							|| sysCodeTypes == null
							|| sysCodeTypes.size() == 0) {
						continue ;
					}
					
					sysCodes.add(sysCodeTypes.get(0));
					
					cacheServ.setSysCode(codeTypeName, sysCodes);
				}
			}
		}
	}
	//加载玩法
	private void initPlayType() {
		String lotteryTypeName = Constants.SysCodeTypes.LOTTERY_TYPES.getCode();
		Map<String, SysCode> lotteryTypes = cacheServ.getSysCode(lotteryTypeName);
		if(lotteryTypes == null || lotteryTypes.size() == 0) {
			return ;
		}
		
		Iterator<String> keys = lotteryTypes.keySet().iterator(); 
		while(keys.hasNext()) {
			String key = keys.next();
			SysCode lotteryType = lotteryTypes.get(key);
			if(lotteryType.getIsCodeType() == Constants.SysCodeTypesFlag.code_type.getCode()) {
				continue;
			}
			
			List<PlayType> playTypes = cacheServ.getPlayType(lotteryType);
			
			logger.debug((playTypes == null || playTypes.size() == 0)?
					"no play type cache for lottery type:" + lotteryType.getCodeName()
					:lotteryType.getCodeName() + " cached play types :" + playTypes.size());
			
			if(playTypes == null || playTypes.size() == 0) {
				playTypes = playTypeServ.queryPlayType(String.valueOf(lotteryType.getCodeName()));
				if(playTypes == null || playTypes.size() == 0) {
					continue;
				}
				
				logger.debug(String.format("Loading %s play types into cache!", playTypes.size()));
				
				cacheServ.setPlayType(lotteryType.getCodeName(), playTypes);
			}
		}
	}

	//加载充值方式  这是pay_type表的缓存
	private void initPayType() {
		String payTypeName=Constants.PayTypeName.PAY_TYPE.getCode();
		List<PayType> payTypeLists = cacheServ.getPayType(payTypeName);
		
		if(payTypeLists == null || payTypeLists.size() == 0) {
			payTypeLists = payTypeService.queryAllPayType();
			if(payTypeLists == null || payTypeLists.size() == 0) {
				return ;
			}
			cacheServ.setPayType(payTypeName, payTypeLists);
		}
	}
	//加载充值渠道
	private void initPayChannel() {
		String payChannelName=Constants.PayChannel.PAY_CHANNEL.getCode();
		Map<Integer, PayChannel> payChannelMaps = cacheServ.getPayChannel(payChannelName);
		List<PayChannel> payChannelLists = null;
		
		if(payChannelMaps == null || payChannelMaps.size() == 0) {
			payChannelLists = payChannelService.queryAll();
			
			if(payChannelLists == null || payChannelLists.size() == 0) {
				return ;
			}
			cacheServ.setPayChannel(payChannelName, payChannelLists);
		}
	}
	//系统参数
	private void initSysRuntimeArgument() {
		String keySysRuntimeArg = Constants.SysCodeTypes.SYS_RUNTIME_ARGUMENT.getCode();
		Map<String, SysCode> sysRuntimeArgs = cacheServ.getSysRuntimeArg(keySysRuntimeArg);
		List<SysCode> sysRuntimeArgList = sysCodeServ.queryAllSmallType(keySysRuntimeArg);
		
		if(sysRuntimeArgList == null || sysRuntimeArgList.size() == 0) {
			return ;
		}
		
		if(sysRuntimeArgs == null 
				|| sysRuntimeArgs.size() != sysRuntimeArgList.size()) {
			
			cacheServ.setSysCode(keySysRuntimeArg, sysRuntimeArgList);
		}
	}
	//加载彩种类型
	private void initBankCodeList() {
		String codeTypeName = Constants.SysCodeTypes.BANK_CODE_LIST.getCode();
		Map<String, SysCode> lottoTypes = cacheServ.getSysCode(codeTypeName);
		List<SysCode> sysCodes = null;
		
		if(lottoTypes == null || lottoTypes.size() == 0) {
			sysCodes = sysCodeServ.queryAllSmallType(codeTypeName);
			List<SysCode> sysCodeTypes = sysCodeServ.queryByCodeNameBigType(codeTypeName);
			
			if(sysCodes == null || sysCodes.size() == 0
					|| sysCodeTypes == null
					|| sysCodeTypes.size() == 0) {
				return ;
			}
			
			sysCodes.add(sysCodeTypes.get(0));
			
			cacheServ.setSysCode(codeTypeName, sysCodes);
		}	
	}
	
	
	private void initPanKou() {
		String codeTypeName = Constants.SysCodeTypes.SM_PANKOU.getCode();
		Map<String, SysCode> lottoTypes = cacheServ.getSysCode(codeTypeName);
		List<SysCode> sysCodes = null;
		
		if(lottoTypes == null || lottoTypes.size() == 0) {
			sysCodes = sysCodeServ.queryAllSmallType(codeTypeName);
			List<SysCode> sysCodeTypes = sysCodeServ.queryByCodeNameBigType(codeTypeName);
			
			if(sysCodes == null || sysCodes.size() == 0
					|| sysCodeTypes == null
					|| sysCodeTypes.size() == 0) {
				return ;
			}
			
			sysCodes.add(sysCodeTypes.get(0));
			
			cacheServ.setSysCode(codeTypeName, sysCodes);
		}
	}
	
	private void init5DigitsOne2Ten() {
		List<String> rows = cacheServ.get5DigitsOne2TenNumbers();
		if(rows != null && rows.size() > 0) {
			return ;
		}
		
		Thread produceNumThread = new Thread(new Runnable() {

			@Override
			public void run() {
				List<String> rows = Utils.produce5Digits1to10Number();
				
				cacheServ.set5DigitsOne2TenNumbers(rows);
			}
			
		});
		
		produceNumThread.start();
		
		
		
	}
	
	
	@Override
	public void handleMessage(Serializable message) {
		String msg = (String)message;
		if(Constants.KEY_INIT_SYS_CODE_PLAY_TYPE_NUM.equals(msg)){
			ThreadPoolManager.getInstance().exeThread(new Runnable() {
				@Override
				public void run() {
					cacheServ.deletePlayTypeNum();
					initPlayTypeNum();
				}
			});
		}
	}
}
