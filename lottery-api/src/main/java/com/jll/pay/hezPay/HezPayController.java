package com.jll.pay.hezPay;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jll.common.constants.Constants.PayType;
import com.jll.entity.display.HezPayNotices;
import com.jll.pay.order.DepositOrderService;


@RestController
@RequestMapping({"/hezPay"})
public class HezPayController
{
	private Logger logger = Logger.getLogger(HezPayController.class);
  
	@Resource
	HezPayService hezPayService;
  
	@Resource
	DepositOrderService depositOrderService;
	
	@PostConstruct
	public void init() {
	  
	}
  
  /**
   * payment platform will call this method to inform that the deposit record already in database.
   * next step , this method have to change the status of deposit order, and change the balance of user
 * @param userName
 * @return        {"success": true}
 */
  @RequestMapping(value={"/notices/{noticeType}"}, method={RequestMethod.GET}, produces={MediaType.TEXT_PLAIN_VALUE})
  public String scanPayNotices(@PathVariable("noticeType") int noticeType ,
		  HezPayNotices notices,
		  HttpServletRequest request){
	  	
	    if(!hezPayService.isNoticesValid(notices)) {
	    	logger.debug(String.format("invalid Notices", ""));
	    	return "success";
	    }
	  
	    logger.debug(String.format("valid Notices", ""));
	    
	    boolean isNotified = depositOrderService.isOrderNotified(notices.getMerchantOrderNo(),PayType.HEZ_PAY);
	    if(isNotified) {
	    	logger.debug(String.format("Reapated Notices", ""));
	    	return "success";
	    }
	    
	    logger.debug(String.format("Have not Notices", ""));
	    
	    depositOrderService.processReceiveDepositOrder(notices.getMerchantOrderNo(),"");
	    return "success";
  }
  
  /**
   * payment platform will call this method to inform that the deposit record already in database.
   * next step , this method have to change the status of deposit order, and change the balance of user
 * @param userName
 * @return        {"success": true}
 */
//  @RequestMapping(value={"/onlineBankPayNotices/{noticeType}"}, method={RequestMethod.POST}, consumes={"application/json"}, produces={"application/json"})
//  public Map<String, Object> onlineBankPayNotices(@PathVariable("noticeType") int noticeType ,
//		  @RequestBody TlCloudNotices notices,
//		  HttpServletRequest request)
//  {
//	  logger.info(noticeType+"--------------------"+notices+"---------------------"+"caifuwangyin-------------------");
//    Map<String, Object> ret = new HashMap<>();
//    
//    String ip = request.getRemoteAddr();
//    
//    int orderIdInt = -1;
//    
//    if(notices == null 
//    		|| notices.getOrder_id() == null 
//    		|| notices.getOrder_id().length() == 0) {    	
//    	
//    	ret.put(KEY_RESPONSE_STATUS, false);
//    	return ret;
//    }
//    
//    try {
//		orderIdInt = Integer.parseInt(notices.getOrder_id());
//	}catch(NumberFormatException ex) {
//		ret.put(KEY_RESPONSE_STATUS, false);
//    	return ret;
//	}
//    
//    boolean isAuthorized = caiPayService.isAuthorized(ip);
//    
//    logger.info("The reuqest IP is ::: " + (ip == null?"null":ip) +"  isAuthroized ::: " + isAuthorized);
//    
//    if(!isAuthorized) {
//    	ret.put(KEY_RESPONSE_STATUS, false);
//    	return ret;
//    }
//    
//    boolean isExisting = caiPayService.isOrderExisting(orderIdInt);
//    
//    logger.info("The order ::: " + orderIdInt +"  isExisting :::" + isExisting);
//    
//    if(!isExisting) {
//    	ret.put(KEY_RESPONSE_STATUS, false);
//    	return ret;
//    }
//    caiPayService.receiveDepositOrder(orderIdInt);
//        
//    ret.put(KEY_RESPONSE_STATUS, true);
//    return ret;
//  }
}
