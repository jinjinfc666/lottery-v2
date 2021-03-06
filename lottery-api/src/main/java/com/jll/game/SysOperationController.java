package com.jll.game;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jll.common.annotation.LogsInfo;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Message;
import com.jll.common.utils.StringUtils;
import com.jll.entity.Issue;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;

@Api2Doc(id = "SysOperation", name = "Sys Operation")
@ApiComment(seeClass = Issue.class)
@RestController
@RequestMapping({"/sys/oper"})

public class SysOperationController{
	
	  private Logger logger = Logger.getLogger(SysOperationController.class);
	  
	  @Resource
	  IssueService issueService;
  
	  @ApiComment("updte issue open number")
	  @RequestMapping(value={"/issue/{issueNum}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
	  public Map<String, Object> updateIssueOpenNum(@PathVariable("issueNum") String issueNum,
			  @RequestBody Map<String, String> params){
		  return issueService.updateIssueOpenNum(issueNum,params);
	  }
	  
	  /**
	   * 
	   * @param issueNum
	   * @param params {lottoType}
	   * @return
	   */
	  
	  @ApiComment("issue payout")
	  @RequestMapping(value={"/issue/{issueNum}/payout"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
	  @LogsInfo(logType=StringUtils.OPE_LOG_ISSUE_MANUAL_PAYOUT)
	  public Map<String, Object> betOrderPayout(@PathVariable("issueNum") String issueNum,
			 // @PathVariable("lottoType") String lottoType,
			  @RequestBody Map<String, String> params){
		  return issueService.processBetOrderPayout("", issueNum,params);
	  }
	  
	  /**
	   * 
	   * @param issueNum
	   * @param params {lottoType}
	   * @return
	   */
	  @ApiComment("cancel current issue all  payout")
	  @RequestMapping(value={"/issue/{issueNum}/cancel-payout"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
	  @LogsInfo(logType=StringUtils.OPE_LOG_REVOKE_PAYOUT)
	  public Map<String, Object> calcelIssuePayout(@PathVariable("issueNum") String issueNum,
			  //@PathVariable("lottoType") String lottoType,
			  @RequestBody Map<String, String> params){
		  return issueService.processCalcelIssuePayout("",issueNum,params);
	  }
	  
	  /**
	   * 
	   * @param issueNum
	   * @param params {lottoType}
	   * @return
	   */
	  @ApiComment("issue re payout")
	  @RequestMapping(value={"/issue/{issueNum}/re-payout"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
	  @LogsInfo(logType=StringUtils.OPE_LOG_RE_PAYOUT)
	  public Map<String, Object> betOrderRePayout(@PathVariable("issueNum") String issueNum,
			  @RequestBody Map<String, String> params){
		  return issueService.processBetOrderRePayout("",issueNum,params);
	  }
	  
	  
	  
	  
	  /**
	   * 根据期次  撤单（取消）
	   * @param issueNum
	   * @param params {lottoType}
	   * @return
	   */
	  @ApiComment("issue disbale")
	  @RequestMapping(value={"/issue/{issueNum}/disbale"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
	  @LogsInfo(logType=StringUtils.OPE_LOG_CANCEL_ISSUE)
	  public Map<String, Object> issueDisbale(@PathVariable("issueNum") String issueNum,
			  //@PathVariable("lottoType") String lottoType,
			  @RequestBody Map<String, String> params){
		  return issueService.updateIssueDisbale("",issueNum,params);
	  }
	  
	  
	  
	 
	  /**
	   * 
	   * @param issueNum
	   * @param params {lottoType}
	   * @return
	   */
	  //标记订单为延迟派奖
	  @ApiComment("issue delay payout")
	  @RequestMapping(value={"/issue/{issueNum}/delay-payout"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
	  public Map<String, Object> issueDelayePayout(@PathVariable("issueNum") String issueNum,
			  @RequestBody Map<String, String> params){
		  return issueService.processIssueDelayePayout(issueNum,params);
	  }
	  //针对单笔订单取消延迟派奖
	  @RequestMapping(value={"/issue/order/{id}/delay-payout"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json"})
	  public Map<String, Object> updateOrderDelayPayoutFlag(@PathVariable("id") Integer id){
		  return issueService.updateOrderDelayPayoutFlag(id);
	  }
	  
	  
	  @ApiComment("order manual payout")
	  @RequestMapping(value={"/order/{orderNum}/manual-payout"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
	  @LogsInfo(logType=StringUtils.OPE_LOG_ORDER_MANUAL_PAYOUT)
	  public Map<String, Object> manualPayoutOrder(@PathVariable("orderNum") String orderNum){
		  return issueService.manualPayoutOrder(orderNum);
	  }
	  
	  
	  //单笔订单撤单
	  @ApiComment("order cancel")
	  @RequestMapping(value={"/order/{orderNum}/cancel"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json"})
	  @LogsInfo(logType=StringUtils.OPE_LOG_CANCEL_ORDER)
	  public Map<String, Object> orderCancel(@PathVariable("orderNum") String orderNum){
		  return issueService.processOrderCancel(orderNum);
	  }
	  
	  @ApiComment("manual draw result")
	  @RequestMapping(value={"/{lottery-type}/issue/{issueNum}/manual-draw-result"}, method={RequestMethod.PUT}, produces={"application/json"})
	  @LogsInfo(logType=StringUtils.OPE_LOG_SPEC_WINNING_NUM)
	  public Map<String, Object> manualDrawResult(@PathVariable(name="issueNum", required=true) String issueNum,
			  @PathVariable("lottery-type") String lottoType,
			  @RequestBody Map<String, String> params){
		  
		  Map<String, Object> response = new HashMap<>();
		  String winningNum = params.get("winningNum");
		  String ret = null;
		  Issue issue = null;
		  boolean isManualPrieModel = false;
		  
		  if(StringUtils.isBlank(winningNum)) {
			  response.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			  response.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode());
			  response.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getErrorMes());
			  
			  return response;
		  }
		  
		  issue = issueService.getIssueByIssueNum(lottoType, issueNum);
		  if(issue == null) {
			  response.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			  response.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode());
			  response.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getErrorMes());
			  
			  return response;
		  }
		  
		  
		  if(issue.getState() != Constants.IssueState.END_ISSUE.getCode()) {
			  response.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			  response.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_ISSUE_INVALID_STATUS.getCode());
			  response.put(Message.KEY_ERROR_MES, Message.Error.ERROR_ISSUE_INVALID_STATUS.getErrorMes());
			  
			  return response;
		  }
		  
		  isManualPrieModel = issueService.isManualPrieModel(lottoType,issueNum);
		  if(!isManualPrieModel) {
			  response.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			  response.put(Message.KEY_ERROR_CODE, 
					  Message.Error.ERROR_ISSUE_NOT_ALLOWED_MANUAL_DRAW_RESULT.getCode());
			  response.put(Message.KEY_ERROR_MES, 
					  Message.Error.ERROR_ISSUE_NOT_ALLOWED_MANUAL_DRAW_RESULT.getErrorMes());
			  
			  return response;
		  }
		  
		  ret = issueService.manualDrawResult(lottoType,issueNum,winningNum);
		  if(!Integer.toString(Message.status.SUCCESS.getCode()).equals(ret)) {
			  response.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			  response.put(Message.KEY_STATUS, Message.Error.getErrorByCode(ret).getCode());
			  response.put(Message.KEY_ERROR_MES, Message.Error.getErrorByCode(ret).getErrorMes());
			  
			  return response;
		  }
		  response.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		  
		  return response;
	  }
	  
}
