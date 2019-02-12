package com.jll.user.transfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Constants.AccOperationType;
import com.jll.common.constants.Constants.DepositOrderState;
import com.jll.common.constants.Constants.EmailValidState;
import com.jll.common.constants.Constants.MainWallet;
import com.jll.common.constants.Constants.OrderState;
import com.jll.common.constants.Constants.PhoneValidState;
import com.jll.common.constants.Constants.RedWallet;
import com.jll.common.constants.Constants.SiteMessageReadType;
import com.jll.common.constants.Constants.State;
import com.jll.common.constants.Constants.SysCodeState;
import com.jll.common.constants.Constants.SysCodeTypes;
import com.jll.common.constants.Constants.SysNotifyReceiverType;
import com.jll.common.constants.Constants.SysNotifyType;
import com.jll.common.constants.Constants.SysRuntimeArgument;
import com.jll.common.constants.Constants.TransferOrderState;
import com.jll.common.constants.Constants.UserLevel;
import com.jll.common.constants.Constants.UserState;
import com.jll.common.constants.Constants.UserType;
import com.jll.common.constants.Constants.WalletState;
import com.jll.common.constants.Constants.WalletType;
import com.jll.common.constants.Constants.WithdrawConif;
import com.jll.common.constants.Constants.WithdrawOrderState;
import com.jll.common.constants.Message;
import com.jll.common.utils.BigDecimalUtil;
import com.jll.common.utils.DateUtil;
import com.jll.common.utils.MathUtil;
import com.jll.common.utils.PageQuery;
import com.jll.common.utils.SecurityUtils;
import com.jll.common.utils.StringUtils;
import com.jll.common.utils.Utils;
import com.jll.common.utils.sequence.GenSequenceService;
import com.jll.dao.PageBean;
import com.jll.dao.PageQueryDao;
import com.jll.dao.SupserDao;
import com.jll.entity.DepositApplication;
import com.jll.entity.GenSequence;
import com.jll.entity.MemberPlReport;
import com.jll.entity.OrderInfo;
import com.jll.entity.SiteMessFeedback;
import com.jll.entity.SiteMessage;
import com.jll.entity.SysAuthority;
import com.jll.entity.SysCode;
import com.jll.entity.SysNotification;
import com.jll.entity.SysRole;
import com.jll.entity.TransferApplication;
import com.jll.entity.UserAccount;
import com.jll.entity.UserAccountDetails;
import com.jll.entity.UserBankCard;
import com.jll.entity.UserInfo;
import com.jll.entity.WithdrawApplication;
import com.jll.game.IssueService;
import com.jll.game.order.OrderDao;
import com.jll.game.order.OrderService;
import com.jll.report.WithdrawApplicationService;
import com.jll.sys.security.permission.SysRoleService;
import com.jll.sys.security.user.SysAuthorityDao;
import com.jll.sys.security.user.SysAuthorityService;
import com.jll.sysSettings.syscode.SysCodeService;
import com.jll.user.bank.UserBankCardService;
import com.jll.user.details.UserAccountDetailsService;
import com.jll.user.wallet.WalletService;

@Configuration
@PropertySource("classpath:sys-setting.properties")
@Service
@Transactional
public class TransferAppServiceImpl implements TransferAppService
{
	private Logger logger = Logger.getLogger(TransferAppServiceImpl.class);
	
	@Resource
	TransferAppDao userDao;
	
	@Resource
	SupserDao  supserDao; 

	@Resource
	WalletService walletServ;
	
	@Resource
	IssueService issueService;
	
	@Resource
	OrderService orderService;
	
	@Resource
	OrderDao orderDao;
	
	@Resource
	UserAccountDetailsService userAccountDetailsService;
	
	@Resource
	SysCodeService sysCodeService;
	
	@Resource
	WithdrawApplicationService withdrawService;
	
	@Resource
	CacheRedisService cacheRedisService;
	
	@Resource
	CacheRedisService cacheServ;
	
	@Resource
	UserBankCardService userBankCardService;
	
	@Resource
	SysAuthorityService sysAuthorityService;
	
	@Resource
	SysRoleService sysRoleService;
	
	@Resource
	SysAuthorityDao sysAuthorityDao;
	
	//@Resource
	//HttpServletRequest request;
 	
	@Value("${sys_reset_pwd_default_pwd}")
	String defaultPwd;
	
	@Resource
	GenSequenceService genSeqServ;

	@Resource
	TransferAppDao transferAppDao;
	
	@Override
	public void saveTransferApplication(TransferApplication transferApp) {
		transferAppDao.saveTransferApplication(transferApp);
	}
	
	
}
