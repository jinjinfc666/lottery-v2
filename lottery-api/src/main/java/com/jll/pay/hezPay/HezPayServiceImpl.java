package com.jll.pay.hezPay;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Message;
import com.jll.common.http.HttpRemoteStub;
import com.jll.common.utils.BigDecimalUtil;
import com.jll.common.utils.MD5Signature;
import com.jll.dao.SupserDao;
import com.jll.entity.DepositApplication;
import com.jll.entity.PayChannel;
import com.jll.entity.display.HezPayNotices;
import com.jll.pay.PaymentDao;
import com.jll.pay.order.DepositOrderDao;



@Configuration
@PropertySource("classpath:hez.properties")
@Service
@Transactional
public class HezPayServiceImpl implements HezPayService
{
	private Logger logger = Logger.getLogger(HezPayServiceImpl.class);
	/*private final String FAILED_CODE = "1";
	
	private final String SUCCESS_CODE = "0";*/
	
	//private final String FAILED_CODE_ONLINE_PAY = "1";
	
	private final String SUCCESS_CODE = "success";
	
	 @Resource
	 CacheRedisService cacheRedisService;
	
	@Resource
	HezPayDao tlCloudDao;
	
	@Resource
	PaymentDao payDao;
	
	@Resource
	SupserDao supserDao;
	
	@Resource
	DepositOrderDao depositOrderDao;
	
		  
	@Value("${hez.api.server}")
	private String apiServer;
	  
	@Value("${hez.api.scanQRPay}")
	private String apiScanQRPay;
	  
	@Value("${hez.api.onLineBankPay}")
	private String apiOnLineBankPay;
	  	
	@Value("${hez.api.scanQRPay.asynNotifyUrl}")
	private String scanPayAsynNOtifyURL;
	
	@Value("${hez.api.scanQRPay.synNotifyUrl}")
	private String scanPaySynNOtifyURL;	
	
	@Value("${hez.api.scanQRPay.paymodel}")
	private String scanARPayPayType;	
	
	@Value("${api.onLineBankPay.asynNotifyUrl}")
	private String onlineBankPayAsynNotifyUrl;
	
	@Value("${api.onLineBankPay.synNotifyUrl}")
	private String onlineBankPaysynNotifyUrl;
	
	
	@Value("${hez.merchant1.merId}")
	private String merchant1MerId;
	
	@Value("${hez.merchant1.key}")
	private String merchant1Key;
	
	
	private List<Merchant> merchants/* = new ArrayList<>()*/;
	
	@PostConstruct
	public void init() {
		merchants = new ArrayList<>();
		
		Merchant merchant = new Merchant();
		merchant.setKey(merchant1Key);
		merchant.setMerId(merchant1MerId);
		merchants.add(merchant);
	}
	
	
	
	private boolean isResponseSuccess(Map<String, Object> response) {
		if(response.size() == 0) {
			logger.debug("Can't read response from the cai-pay server!!!");
			return false;
		}
		
		int status = (int)response.get("status");
		if(status != HttpStatus.SC_OK) {
			return false;
		}else {
			String body = (String)response.get("responseBody");
			
			logger.debug("the response is ::: " + (body == null?"":body));
			
			if(body != null && body.length() > 0) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					JsonNode node = mapper.readTree(body.getBytes("UTF-8"));
					if(node == null) {
						logger.debug("can't read the response!!!");
						return false;
					}
					
					node = node.get("result");
					if(node == null) {
						logger.debug("retCode is null");
						return false;
					}
					
					String code = node.asText();
					if(code == null 
							|| code.length() == 0) {
						logger.debug("retCode value is null");
						return false;
					}
					
					logger.debug("retCode value is :::" + code);
					if(code.equals(SUCCESS_CODE)) {
						return true;
					}
					
					return false;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
	}
	
	private boolean isOnlineBankResponseSuccess(Map<String, Object> response) {
		int status = (int)response.get("status");
		if(status != HttpStatus.SC_OK) {
			return false;
		}else {
			String body = (String)response.get("responseBody");
			if(body != null && body.length() > 0) {
				if(body.contains("pay_form")) {
					return true;
				}else {
					return false;
				}
			}
		}
		return false;
	}
	
	private Map<String, Object> produceParamsOfScanQRPay(Map<String, Object> params){
		Merchant merchant = queryCurrMerchant((String)params.get("rechargeType"));
		
		if(merchant == null) {
			return null;
		}
		
		DecimalFormat numFormat = new DecimalFormat("##0");
		DepositApplication depositOrder = (DepositApplication)params.get("depositOrder");
		SortedMap<String, Object> pushParams = new TreeMap<>();
		pushParams.put("appId", merchant.getMerId());
		pushParams.put("payType", scanARPayPayType);
		pushParams.put("amount", numFormat.format(((Float)params.get("amount"))));
		pushParams.put("merChantOrderNo", depositOrder.getOrderNum());
		pushParams.put("successUrl", params.get("synNotifyURL"));
		pushParams.put("notifyUrl", params.get("asynNotifyURL"));
		//pushParams.put("sign", signType);
		
		pushParams = encryptData1(pushParams, merchant1Key);		
				
		depositOrder.setPlatAccount(merchant.getMerId());
		supserDao.update(depositOrder);
		//pushParams.put("signData", sign);
		return pushParams;
	}

	private Map<String, Object> produceParamsOfOnlinePay(Map<String, Object> params){
		Merchant merchant = queryCurrMerchant((String)params.get("rechargeType"));
		
		if(merchant == null) {
			return null;
		}
		
		DecimalFormat numFormat = new DecimalFormat("##0");
		DepositApplication depositOrder = (DepositApplication)params.get("depositOrder");
		SortedMap<String, Object> pushParams = new TreeMap<>();
		pushParams.put("appId", merchant.getMerId());
		pushParams.put("payType", scanARPayPayType);
		pushParams.put("amount", numFormat.format(((Float)params.get("amount"))*100));
		pushParams.put("merChantOrderNo", depositOrder.getOrderNum());
		pushParams.put("successUrl", scanPaySynNOtifyURL);
		pushParams.put("notifyUrl", scanPayAsynNOtifyURL);
		//pushParams.put("sign", signType);
		
		pushParams = encryptData1(pushParams, merchant1Key);
		
		
		depositOrder.setPlatAccount(merchant.getMerId());
		supserDao.update(depositOrder);
		//pushParams.put("signData", sign);
		return pushParams;
	}
	
	
	@Override
	public String processScanPay(Map<String, Object> params) {
		
		Date createTime = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		URI url = null;
		boolean isSuccess = true;
		Map<String, Object> pushParams = new TreeMap<>();
		Map<String, String> reqHeaders = new HashMap<>();
		
		params.put("createTime", createTime);
		reqHeaders.put("Content-Type", "application/x-www-form-urlencoded");
		params.put("createTime", format.format(createTime));
		String reqHost = (String)params.get("reqHost");
		String reqContext = (String)params.get("reqContext");
		params.put("asynNotifyURL", scanPayAsynNOtifyURL.replace("{host}", reqHost).replace("{context}", reqContext));
		params.put("synNotifyURL", scanPaySynNOtifyURL.replace("{host}", reqHost).replace("{context}", reqContext));
		pushParams = produceParamsOfScanQRPay(params);
		if(pushParams == null || pushParams.size() == 0) {
			return Message.Error.ERROR_PAYMENT_CAIPAY_FAILED_SIGNATURE_PARAMS.getCode();
		}
		
		logger.debug(String.format("apiServer  %s     apiScanQRPay  %s", apiServer, apiScanQRPay));
		logger.debug(String.format("scanPayAsynNOtifyURL  %s     scanPaySynNOtifyURL  %s", scanPayAsynNOtifyURL.replace("{host}", reqHost).replace("{context}", reqContext), scanPaySynNOtifyURL.replace("{host}", reqHost).replace("{context}", reqContext)));
		 try {
		 	url = new URI(apiServer + apiScanQRPay);
		 } catch (URISyntaxException e) {
		 	return Message.Error.ERROR_PAYMENT_CAIPAY_FAILED_CANCEL_ORDER.getCode();
		 }
		 //TODO 由於api 接口暫時調不通，所以暫時注销掉
		 Map<String, Object> response = HttpRemoteStub.synPost(url, reqHeaders, pushParams);
		 isSuccess = isResponseSuccess(response);
		 logger.debug("If request successful::::" + isSuccess);
		 //isSuccess = true;
		// depositOrder.setOrderNumber(String.valueOf(depositOrder.getRecordID()));
		 if(isSuccess) {
			// depositOrderDao.updateDepositOrder(depositOrder);
			 String qrcode = null;
			 String qrCodeKey = "payUrl";
			 //TODO 暂时注销
			 String jsonStr = (String)response.get("responseBody");
			 qrcode = readJSON(qrCodeKey, jsonStr);
			 //qrcode = "http://www.baidu.com";
			 params.put("qrcode", qrcode);
		 	return String.valueOf(Message.status.SUCCESS.getCode());
		 }else {
			 //failed to push 
			 //depositOrder.setStatus(Constants.DepositOrderState.FAILED_PUSH.getCode());
			 //depositOrderDao.updateDepositOrder(depositOrder);
		 	return Message.Error.ERROR_PAYMENT_TLCLOUD_FAILED_PUSH_ORDER.getCode();
		 }
	}


	private String readJSON(String qrCodeKey, String jsonStr) {
		ObjectMapper mapper = new ObjectMapper();
		String qrcode = null;
		 try {
			 JsonNode node = mapper.readTree(jsonStr);
			 qrcode = node.findValue(qrCodeKey).asText();
			 //
		} catch (JsonProcessingException e) {
		} catch (IOException e) {                    			
		}
		return qrcode;
	}


	@Override
	public String processOnlineBankPay(Map<String, Object> params) {
		Date createTime = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		URI url = null;
		boolean isSuccess = true;
		Map<String, Object> pushParams = new TreeMap<>();
		Map<String, String> reqHeaders = new HashMap<>();
		
		params.put("createTime", createTime);
		DepositApplication depositOrder = (DepositApplication)params.get("depositOrder");
		reqHeaders.put("Content-Type", "application/x-www-form-urlencoded");
		params.put("createTime", format.format(createTime));
		String reqHost = (String)params.get("reqHost");
		String reqContext = (String)params.get("reqContext");
		params.put("asynNotifyURL", onlineBankPayAsynNotifyUrl.replace("{host}", reqHost).replace("{context}", reqContext));
		params.put("synNotifyURL", onlineBankPaysynNotifyUrl.replace("{host}", reqHost).replace("{context}", reqContext));
		
		pushParams = produceParamsOfOnlinePay(params);
		if(pushParams == null || pushParams.size() == 0) {
			return Message.Error.ERROR_PAYMENT_CAIPAY_FAILED_SIGNATURE_PARAMS.getCode();
		}
		 try {
		 	url = new URI(apiServer + apiOnLineBankPay);
		 } catch (URISyntaxException e) {
		 	return Message.Error.ERROR_PAYMENT_CAIPAY_FAILED_CANCEL_ORDER.getCode();
		 }

		 //TODO 接口调不通，暂时屏蔽
		 /*Map<String, Object> response = HttpRemoteStub.synPost(url, reqHeaders, pushParams);
		    
		 isSuccess = isOnlineBankResponseSuccess(response);
		 
		 String redirect = (String)response.get("responseBody");*/
		 String redirect = "http://www.baidu.com";
		 params.put("redirect", redirect);
		 
		 if(isSuccess) {
			// depositOrderDao.updateDepositOrder(depositOrder);
		 	return String.valueOf(Message.status.SUCCESS.getCode());
		 }else {
			 //failed to push 
			 //depositOrder.setStatus(Constants.DepositOrderState.FAILED_PUSH.getCode());
			// depositOrderDao.updateDepositOrder(depositOrder);
		 	return Message.Error.ERROR_PAYMENT_TLCLOUD_FAILED_PUSH_ORDER.getCode();
		 }
	}


	@Override
	public String receiveNoties(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isValid(Map<String, Object> params) {
		String payMode = (String)params.get("rechargeType");
		if("00023".equals(payMode)) {
			return verifyQuickPayment(params);
		}
		return true;
	}
	
	private boolean verifyQuickPayment(Map<String, Object> params) {
		String bankCardNo = (String)params.get("bankCardNo");
		String userName = (String)params.get("userName");
		String phone = (String)params.get("phone");
		String idNo = (String)params.get("idNo");
		String expireDate = (String)params.get("expireDate");
		String CVn2 = (String)params.get("CVn2");
		if(bankCardNo == null || bankCardNo.length() == 0
				|| userName == null || userName.length() == 0
				|| phone == null || phone.length() == 0
				|| CVn2 == null || CVn2.length() == 0
				|| idNo == null || idNo.length() == 0
				|| expireDate == null || expireDate.length() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isNoticesValid(HezPayNotices notices) {
		String sign = notices.getSign();
		SortedMap<String, Object> pushParams = new TreeMap<>();
		pushParams.put("appId",notices.getAppId());
		pushParams.put("merchantOrderNo", notices.getMerchantOrderNo());
		pushParams.put("amount", notices.getAmount());
		pushParams.put("orderId", notices.getOrderId());
		pushParams.put("result", notices.getResult());
		
		//解密
		 boolean decriptResult = decriptData1(sign, merchant1Key, pushParams);
		 if(decriptResult){
			 return true;
		 }
		
		return false;
	}
	
	
	private Merchant queryCurrMerchant(String payMode) {
		return merchants.get(0);
	}
	
	class Merchant{
		private String merId;
		
		private String key;
		
		private List<String> payModes;
		
		private String receivableType;

		public String getMerId() {
			return merId;
		}

		public void setMerId(String merId) {
			this.merId = merId;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public List<String> getPayModes() {
			return payModes;
		}

		public void setPayModes(List<String> payModes) {
			this.payModes = payModes;
		}

		public String getReceivableType() {
			return receivableType;
		}

		public void setReceivableType(String receivableType) {
			this.receivableType = receivableType;
		}
		
		
	}
	
	
	
	/**
     * 参数加密方法
     *
     * @param param
     * @return
     */
    public SortedMap<String, Object> encryptData1(SortedMap<String, Object> param, String signKey) {

        StringBuilder stringBuilder = new StringBuilder(makeGetParam(param, false));
        stringBuilder.append(signKey);
        
        logger.debug(String.format("encrtypt data  %s", stringBuilder.toString()));
        String  sign = this.md5(stringBuilder.toString());

        param.put("sign", sign);

        return param;
    }
	
    
    public String makeGetParam(Map<String, Object> data, boolean urlEncode) {
        StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(data)) {
            Iterator<String> iterator = data.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String val = (String)data.get(key);
                sb.append(key);
                sb.append("=");
                if(urlEncode){
                    sb.append(URLEncoder.encode(val));
                }else{
                    sb.append(val);
                }
                if (iterator.hasNext()) {
                    sb.append("&");
                }
            }
        }
        return sb.toString();
    }

    
	
	/**
     * 解密参数方法
	
     */
    private boolean decriptData1(String sign, String secretKey, Map<String, Object> param) {

        StringBuilder stringBuilder = new StringBuilder(makeGetParam(param, false));
        stringBuilder.append(secretKey);
       
        String paramSign = this.md5(stringBuilder.toString());
        logger.debug(String.format("paramSign  %s    sign  %s", paramSign, sign));
        if (!paramSign.equals(sign)) {
            logger.info("参数匹配异常");
            return false;
        }
        return true;
    }
	
	
	
	public static String md5(String str) {
        String re_md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes(StandardCharsets.UTF_8));
            byte b[] = md.digest();

            int i;

            StringBuilder buf = new StringBuilder();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

}
