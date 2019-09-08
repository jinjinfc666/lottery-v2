package com.jll.user.transfer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jll.common.annotation.LogsInfo;
import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Constants.BankCardState;
import com.jll.common.constants.Constants.EmailValidState;
import com.jll.common.constants.Constants.PhoneValidState;
import com.jll.common.constants.Constants.SysCodeTypes;
import com.jll.common.constants.Constants.UserType;
import com.jll.common.constants.Constants.WithdrawConif;
import com.jll.common.constants.Message;
import com.jll.common.utils.DateUtil;
import com.jll.common.utils.StringUtils;
import com.jll.common.utils.Utils;
import com.jll.dao.PageBean;
import com.jll.entity.SiteMessFeedback;
import com.jll.entity.SiteMessage;
import com.jll.entity.SysCode;
import com.jll.entity.UserAccount;
import com.jll.entity.UserAccountDetails;
import com.jll.entity.UserBankCard;
import com.jll.entity.UserInfo;
import com.jll.entity.WithdrawApplication;
import com.jll.sys.siteMsg.SysSiteMsgService;
import com.jll.tp.EmailService;
import com.jll.tp.SMSService;
import com.jll.user.bank.UserBankCardService;
import com.jll.user.wallet.WalletService;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;

@Api2Doc(id = "userInfo", name = "user Info")
@ApiComment(seeClass = UserInfo.class)
@RestController
@RequestMapping({ "/transfers" })
@Configuration
@PropertySource("classpath:sys-setting.properties")
public class TransferAppController {
	
	private Logger logger = Logger.getLogger(TransferAppController.class);
	
	@Resource
	TransferAppService transferServ;
	
	@Resource
	UserBankCardService userBankCardService;
		
	@Resource
	HttpServletRequest request;
	
	@Resource
	WalletService walletServ;
	
	@RequestMapping(method={RequestMethod.GET}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> queryTransfers(@RequestParam(name = "orderNum", required = false) String orderNum,
			  @RequestParam(name = "sourceUser", required = false) String sourceUser,
			  @RequestParam(name = "dstUser", required = false) String dstUser,
			  @RequestParam(name = "state", required = false) Integer state,
			  @RequestParam(name = "startTime", required = false) String startTime,
			  @RequestParam(name = "endTime", required = false) String endTime,
			  @RequestParam(name = "pageIndex", required = true) Integer pageIndex,
			  HttpServletRequest request) {
		Map<String, Object> ret = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		PageBean data = null;
		
		if(!StringUtils.isBlank(startTime)||!StringUtils.isBlank(endTime)) {
			if(!DateUtil.isValidDate(startTime)||!DateUtil.isValidDate(endTime)) {
				ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
				ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode());
				ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getErrorMes());
		    	return ret;
			}
		}
		Integer pageSize=Constants.Pagination.SUM_NUMBER.getCode();
		ret.put("pageSize", pageSize);
		ret.put("pageIndex", pageIndex);
		ret.put("sourceUser", sourceUser);
		ret.put("state", state);
		ret.put("dstUser", dstUser);
		ret.put("startTime", startTime);
		ret.put("endTime", endTime);
		ret.put("orderNum", orderNum);
		try {
			data = transferServ.queryTransfer(ret);
			
			map.put(Message.KEY_DATA, data);
			map.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
			return map;
		}catch(Exception e){
			ret.clear();
			ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_OTHERS.getCode());
			ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_OTHERS.getErrorMes());
			return ret;
		}
	}
}
