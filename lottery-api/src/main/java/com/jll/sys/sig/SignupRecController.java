package com.jll.sys.sig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Message;
import com.jll.common.utils.DateUtil;
import com.jll.common.utils.MathUtil;
import com.jll.entity.SignupRec;
import com.jll.entity.SysCode;
import com.jll.entity.UserAccount;
import com.jll.entity.UserAccountDetails;
import com.jll.entity.UserInfo;
import com.jll.user.UserInfoService;
import com.jll.user.account.UserAccountService;
import com.jll.user.details.UserAccountDetailsService;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;

@Api2Doc(id = "signup", name = "签到")
@ApiComment(seeClass = SignupRecController.class)
@RestController
@RequestMapping({ "/signup" })
public class SignupRecController {
	private Logger logger = Logger.getLogger(SignupRecController.class);

	@Resource
	SignupRecService signupRecService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	UserAccountService userAccountService;
	
	@Resource
	CacheRedisService cacheServ;
	
	@Resource
	UserAccountDetailsService accDetailsServ;
	
	//通过用户名(false)或时间去查询(true)
	@RequestMapping(value={"/sgnupRecSave"}, method={RequestMethod.POST}, produces={"application/json"})
	public Map<String, Object> sgnupRecSave() {
		Map<String, Object> ret = new HashMap<>();
		String opeType = Constants.AccOperationType.DAILY_SIGN_IN.getCode();
		String sysCodeType = Constants.SysCodeTypes.FLOW_TYPES.getCode();
		Map<String, SysCode> accOpetioans = cacheServ.getSysCode(sysCodeType);
		
		try {
			UserInfo user=userInfoService.getCurLoginInfo();
			if(null == user){
				ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
				ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_USER_NO_VALID_USER.getCode());
				ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_USER_NO_VALID_USER.getErrorMes());
				return ret;
			}
			Integer count=signupRecService.getCount(user.getId());
			if(count>0) {
				ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
				ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_USER_SIGNED_IN.getCode());
				ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_USER_SIGNED_IN.getErrorMes());
				return ret;
			}
			SignupRec signupRec=new SignupRec();
			signupRec.setUserId(user.getId());
			signupRec.setCreateTime(new Date());
			signupRec.setRewardPoints(Constants.SignupRecClass.SIGN_IN_INTEGRATION.getCode());
			signupRecService.saveSignupRec(signupRec);
			
			UserAccount userAccount=userAccountService.getUserAccount(user.getId());
			UserAccountDetails accountDetail = new UserAccountDetails();
			accountDetail.setAmount(new Float(Constants.SignupRecClass.SIGN_IN_INTEGRATION.getCode()));
			accountDetail.setCreateTime(new Date());
			accountDetail.setDataItemType(Constants.DataItemType.REWARD_POINTS.getCode());
			SysCode rewardPointCode = accOpetioans.get(opeType);
			accountDetail.setOperationType(rewardPointCode.getCodeName());
			accountDetail.setDataItemType(Constants.DataItemType.REWARD_POINTS.getCode());
			//accountDetail.setOrderId(orderId);
			accountDetail.setPostAmount(MathUtil.add(userAccount.getRewardPoints(), 
					Constants.SignupRecClass.SIGN_IN_INTEGRATION.getCode(),
					Double.class));
			accountDetail.setPreAmount(userAccount.getRewardPoints().doubleValue());
			//accountDetail.setRemark(remark);
			accountDetail.setUserId(user.getId());
			accountDetail.setWalletId(Constants.WalletType.RED_PACKET_WALLET.getCode());
			accDetailsServ.saveAccDetails(accountDetail);
			
			Long rewardPoints=userAccount.getRewardPoints();
			userAccount.setRewardPoints(rewardPoints+Constants.SignupRecClass.SIGN_IN_INTEGRATION.getCode());
			userAccountService.saveUserAccount(userAccount);
			
			ret.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
			return ret;
		}catch(Exception e){
			ret.clear();
			ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_OTHERS.getCode());
			ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_OTHERS.getErrorMes());
			return ret;
		}
	}
	
	/*
	 * 
	 */
	@RequestMapping(value={"/sgnupRecRecord"}, method={RequestMethod.GET}, produces={"application/json"})
	public Map<String, Object> sgnupRecRecord(@RequestParam(name = "userName", required = false) String userName,
			@RequestParam(name = "startTime", required = true) String startTime,
			@RequestParam(name = "endTime", required = true) String endTime,
			@RequestParam(name = "pageIndex", required = true) Integer pageIndex,
			@RequestParam(name = "pageSize", required = false) Integer pageSize,
			@RequestParam(name = "sourceFlag", required = false) Integer sourceFlag,
			  HttpServletRequest request) {
		Map<String, Object> ret = new HashMap<>();
		Integer pageSize_ = pageSize;
		Integer userId = null;
		if(!DateUtil.isValidDate(startTime)||!DateUtil.isValidDate(endTime)) {
			ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode());
			ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getErrorMes());
	    	return ret;
		}
		try {
			UserInfo user=null;
			if(sourceFlag == null || sourceFlag.intValue() == 0) {
				user=userInfoService.getCurLoginInfo();
				if(null == user){
					ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
					ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_USER_NO_VALID_USER.getCode());
					ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_USER_NO_VALID_USER.getErrorMes());
					return ret;
				}
				
				userId=user.getId();
			}else {
				user=userInfoService.getUserByUserName(userName);
				/*if(null == user){
					ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
					ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_USER_NO_VALID_USER.getCode());
					ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_USER_NO_VALID_USER.getErrorMes());
					return ret;
				}*/
				
				if(user != null) {
					userId=user.getId();					
				}
			}
			
			if(pageSize_ == null || pageSize_.intValue() == 0) {
				pageSize_ = Constants.Pagination.SUM_NUMBER.getCode();				
			}
			
			ret=signupRecService.queryRecord(userId, startTime, endTime,pageIndex, pageSize_);
			return ret;
		}catch(Exception e){
			ret.clear();
			ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_OTHERS.getCode());
			ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_OTHERS.getErrorMes());
			return ret;
		}
	}	
	@RequestMapping(value={"/sgnupRecNowMonthRecord"}, method={RequestMethod.GET}, produces={"application/json"})
	public Map<String, Object> sgnupRecNowMonthRecord() {
		Map<String, Object> ret = new HashMap<>();
		List<Integer> datas = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("dd");
		
		try {
			UserInfo user=userInfoService.getCurLoginInfo();
			if(null == user){
				ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
				ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_USER_NO_VALID_USER.getCode());
				ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_USER_NO_VALID_USER.getErrorMes());
				return ret;
			}
			Integer userId=user.getId();
			List<SignupRec> list=signupRecService.queryNowMonthRecord(userId);
			if(list != null && list.size() > 0) {
				for(SignupRec rec : list) {
					datas.add(Integer.parseInt(format.format(rec.getCreateTime())));
				}				
			}
			ret.put(Message.KEY_DATA, datas);
			ret.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
			return ret;
		}catch(Exception e){
			ret.clear();
			ret.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			ret.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_OTHERS.getCode());
			ret.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_OTHERS.getErrorMes());
			return ret;
		}
	}	
}
