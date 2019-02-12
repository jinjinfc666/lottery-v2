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
@RequestMapping({ "/users" })
@Configuration
@PropertySource("classpath:sys-setting.properties")
public class TransferAppController {
	
	private Logger logger = Logger.getLogger(TransferAppController.class);
	
	@Value("${sys_reset_pwd_default_pwd}")
	String defaultPwd;
	
	@Resource
	TransferAppService userInfoService;
	
	@Resource
	UserBankCardService userBankCardService;
	
	@Resource
	SMSService smsServ;
	
	@Resource
	EmailService emailServ;
	
	@Resource
	HttpServletRequest request;
	
	@Resource
	CacheRedisService cacheRedisService;
	
	@Resource
	SysSiteMsgService sysSiteMsgService;
	
	@Value("${sys_captcha_code_expired_time}")
	private int captchaCodeExpiredTime;
	
	@Resource
	CacheRedisService cacheServ;
	
	@Resource
	WalletService walletServ;
	
	
}
