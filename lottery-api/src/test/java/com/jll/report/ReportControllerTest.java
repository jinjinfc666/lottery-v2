package com.jll.report;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;

import com.ehome.test.ControllerJunitBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jll.common.constants.Message;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpException;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class ReportControllerTest extends ControllerJunitBase {

	public ReportControllerTest(String name) {
		super(name);
	}


	/**
	 * 为了测试如何获取秒秒彩的上个期次的开奖结果 1，投注 2，查询开奖结果
	 * 
	 * @throws Exception
	 */
	public void testPayOrderToSystem() throws Exception {
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;

		ObjectNode node = mapper.createObjectNode();
		node.putPOJO("payerName", "test001");
		node.putPOJO("payCardNumber", "222222222222");
		node.putPOJO("payType", "2");
		node.putPOJO("payChannel", "1");
		node.putPOJO("amount", "10");
		
		
		System.out.println(mapper.writeValueAsString(node));
		bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
		WebRequest request = new GetMethodWebRequest("http://localhost:8080/report/loyTstRecord?lotteryType=cqssc&isZh=0&state=0&userName=zhaowei&startTime=2017-11-1&endTime=2019-0-2&pageIndex=1");
		WebConversation wc = new WebConversation();

		token = queryToken(userName, pwd, clientId);
		request.setHeaderField("Authorization", "bearer " + token);

		WebResponse response = wc.sendRequest(request);

		int status = response.getResponseCode();

		Assert.assertEquals(HttpServletResponse.SC_OK, status);
		String result = response.getText();

		Map<String, Object> retItems = null;

		retItems = mapper.readValue(result, HashMap.class);

		Assert.assertNotNull(retItems);

		Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));

		Thread.sleep(20000);
	}
	

	public void ItestMReportNextTeam_non_agent() throws Exception {
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;

		ObjectNode node = mapper.createObjectNode();
		node.putPOJO("payerName", "test001");
		node.putPOJO("payCardNumber", "222222222222");
		node.putPOJO("payType", "2");
		node.putPOJO("payChannel", "1");
		node.putPOJO("amount", "10");
		
		
		System.out.println(mapper.writeValueAsString(node));
		bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
		
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/report/MReportNextTeam?userName=zhaowei&startTime=2018-11-02&endTime=2018-11-02&pageIndex=1");
			WebConversation wc = new WebConversation();
			
			token = queryToken(userName, pwd, clientId);
			request.setHeaderField("Authorization", "bearer " + token);
			
			WebResponse response = wc.sendRequest(request);
			
			int status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);
			
			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
			
			Thread.sleep(20000);
			
		}catch(HttpException ex) {
			Assert.assertEquals(403, ex.getResponseCode());
		}
	}
	
	public void ItestMReportNextTeam_agent() throws Exception {
		String userName = "agent001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;

		ObjectNode node = mapper.createObjectNode();
		node.putPOJO("payerName", "test001");
		node.putPOJO("payCardNumber", "222222222222");
		node.putPOJO("payType", "2");
		node.putPOJO("payChannel", "1");
		node.putPOJO("amount", "10");
		
		
		System.out.println(mapper.writeValueAsString(node));
		bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
		WebRequest request = new GetMethodWebRequest("http://localhost:8080/report/MReportNextTeam?userName=zhaowei&startTime=2018-11-02&endTime=2018-11-02&pageIndex=1");
		WebConversation wc = new WebConversation();

		token = queryToken(userName, pwd, clientId);
		request.setHeaderField("Authorization", "bearer " + token);

		WebResponse response = wc.sendRequest(request);

		int status = response.getResponseCode();

		Assert.assertEquals(HttpServletResponse.SC_OK, status);
		String result = response.getText();

		Map<String, Object> retItems = null;

		retItems = mapper.readValue(result, HashMap.class);

		Assert.assertNotNull(retItems);

		//Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));

		//Thread.sleep(20000);
	}
	
	
	public void ItestAgentTransfer_non_agent() throws Exception {
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;

		ObjectNode node = mapper.createObjectNode();
		node.putPOJO("payerName", "test001");
		node.putPOJO("payCardNumber", "222222222222");
		node.putPOJO("payType", "2");
		node.putPOJO("payChannel", "1");
		node.putPOJO("amount", "10");
		
		
		System.out.println(mapper.writeValueAsString(node));
		bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
		
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/report/agent/transfer?agentId=2&startTime=2018-12-26 00:00:00&endTime=2018-12-26 23:59:59");
			WebConversation wc = new WebConversation();
			
			token = queryToken(userName, pwd, clientId);
			request.setHeaderField("Authorization", "bearer " + token);
			
			WebResponse response = wc.sendRequest(request);
			
			int status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);
			
			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
			
			Thread.sleep(20000);
			
		}catch(HttpException ex) {
			Assert.assertEquals(403, ex.getResponseCode());
		}
	}
	
	public void ItestAgentTransfer_agent() throws Exception {
		String userName = "agent001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;

		ObjectNode node = mapper.createObjectNode();
		node.putPOJO("payerName", "test001");
		node.putPOJO("payCardNumber", "222222222222");
		node.putPOJO("payType", "2");
		node.putPOJO("payChannel", "1");
		node.putPOJO("amount", "10");
		
		
		System.out.println(mapper.writeValueAsString(node));
		bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
		
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/report/agent/transfer?agentId=13&startTime=2018-12-26 00:00:00&endTime=2018-12-26 23:59:59");
			WebConversation wc = new WebConversation();
			
			token = queryToken(userName, pwd, clientId);
			request.setHeaderField("Authorization", "bearer " + token);
			
			WebResponse response = wc.sendRequest(request);
			
			int status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);
			
			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
			
			Thread.sleep(20000);
			
		}catch(HttpException ex) {
			Assert.assertEquals(403, ex.getResponseCode());
		}
	}
	
	
	
	public void ItestMReport() throws Exception {
		String userName = "agent001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/report/MReport");
			WebConversation wc = new WebConversation();
			
			request.setParameter("userName", "test001");
			request.setParameter("startTime", "2017-12-1");
			request.setParameter("endTime", "2019-1-3");
			request.setParameter("pageIndex", "1");
			
			token = queryToken(userName, pwd, clientId);
			request.setHeaderField("Authorization", "bearer " + token);
			
			WebResponse response = wc.sendRequest(request);
			
			int status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);
			
			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
			
			
		}catch(HttpException ex) {
			Assert.assertEquals(403, ex.getResponseCode());
		}
	}


	
	public void ItestQueryUserAccountDetails() throws Exception {
		String userName = "agent001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/report/userFlowDetail");
			WebConversation wc = new WebConversation();
			
			request.setParameter("startTime", "2017-12-1 00:00:00");
			request.setParameter("endTime", "2019-1-3 23:59:59");
			request.setParameter("pageIndex", "1");
			
			token = queryToken(userName, pwd, clientId); 
			request.setHeaderField("Authorization", "bearer " + token);
			
			WebResponse response = wc.sendRequest(request);
			
			int status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);
			
			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
			
			
		}catch(HttpException ex) {
			Assert.assertEquals(403, ex.getResponseCode());
		}
	}
	
	//terminalType=0&userName=test001&startTime=&endTime=&pageIndex=1&zhTrasactionNum=&issueNum=&orderId=397
	public void ItestQueryLoyTst() throws Exception {
		String userName = "agent001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/report/loyTstRecord");
			WebConversation wc = new WebConversation();
			
			request.setParameter("orderId", "2");
			request.setParameter("pageIndex", "1");
			
			
			request.setParameter("terminalType", "0");
			request.setParameter("userName", "test001");
			request.setParameter("startTime", "");
			request.setParameter("endTime", "");
			request.setParameter("zhTrasactionNum", "");
			request.setParameter("issueNum", "");
			
			
			token = queryToken(userName, pwd, clientId); 
			request.setHeaderField("Authorization", "bearer " + token);
			
			WebResponse response = wc.sendRequest(request);
			
			int status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);
			
			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
			
			
		}catch(HttpException ex) {
			Assert.assertEquals(403, ex.getResponseCode());
		}
	}
	
	
	public void testQueryUserAccountDetails() throws Exception {
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/report/userFlowDetail");
			WebConversation wc = new WebConversation();
			
			//request.setParameter("orderId", "2");
			request.setParameter("pageIndex", "1");
			
			
			request.setParameter("userName", "test001");
			request.setParameter("startTime", "");
			request.setParameter("endTime", "");
			request.setParameter("orderId", "");
			
			
			token = queryToken(userName, pwd, clientId); 
			request.setHeaderField("Authorization", "bearer " + token);
			
			WebResponse response = wc.sendRequest(request);
			
			int status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);
			
			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
			
			
		}catch(HttpException ex) {
			Assert.assertEquals(403, ex.getResponseCode());
		}
	}
}