package com.jll.sys.siteMsg;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.springframework.http.MediaType;

import com.ehome.test.ControllerJunitBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jll.common.constants.Message;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class SysSiteMsgControllerTest extends ControllerJunitBase {

	public SysSiteMsgControllerTest(String name) {
		super(name);
	}


	/**
	 * 为了测试查看站内信反馈
	 * 
	 * @throws Exception
	 */
	public void ItestSiteMsgDesc() throws Exception {
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		/*ByteArrayInputStream bis = null;

		ObjectNode node = mapper.createObjectNode();
		node.putPOJO("payerName", "test001");
		node.putPOJO("payCardNumber", "222222222222");
		node.putPOJO("payType", "2");
		node.putPOJO("payChannel", "1");
		node.putPOJO("amount", "10");
		
		
		System.out.println(mapper.writeValueAsString(node));
		bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));*/
		WebRequest request = new GetMethodWebRequest("http://localhost:8080/sys/site-msg/3/history-feedback");
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
	
	
	/**
	 * 为了测试查看站内信反馈
	 * 
	 * @throws Exception
	 */
	public void ItestSiteMsg_add() throws Exception {
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;

		ObjectNode node = mapper.createObjectNode();
		node.putPOJO("sendIds", 14);
		node.putPOJO("content", "asdasd");
		node.putPOJO("title", "asdasd");
		
		
		System.out.println(mapper.writeValueAsString(node));
		bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
		WebRequest request = new PostMethodWebRequest("http://localhost:8080/sys/site-msg/add",
				bis,
				MediaType.APPLICATION_JSON_VALUE);
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
	
	
	/**
	 * 
	 * 
	 * @throws Exception
	 */
	public void ItestSiteMsg_List_sent() throws Exception {
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;

		ObjectNode node = mapper.createObjectNode();
		node.putPOJO("startTime", "2019-01-03 00:00:00");
		node.putPOJO("endTime", "2019-01-03 23:59:59");
		node.putPOJO("pageIndex", 1);
		
		
		System.out.println(mapper.writeValueAsString(node));
		bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
		WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/site-msg/lists/0",
				bis,
				MediaType.APPLICATION_JSON_VALUE);
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

		//Thread.sleep(20000);
	}
	
	
	/**
	 * 
	 * 
	 * @throws Exception
	 */
	public void ItestSiteMsg_List_unread() throws Exception {
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;

		ObjectNode node = mapper.createObjectNode();
		node.putPOJO("startTime", "2019-1-1 00:00:00");		
		node.putPOJO("endTime", "2019-1-4 23:59:59");
		node.putPOJO("pageIndex", 1);
		node.putPOJO("isRead", 1);
		
		
		System.out.println(mapper.writeValueAsString(node));
		bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
		WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/site-msg/lists/1",
				bis,
				MediaType.APPLICATION_JSON_VALUE);
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

		//Thread.sleep(20000);
	}
	
	/**
	 * 
	 * 
	 * @throws Exception
	 */
	public void testSiteMsg_List_read() throws Exception {
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;

		ObjectNode node = mapper.createObjectNode();
		node.putPOJO("startTime", "2019-1-1 00:00:00");		
		node.putPOJO("endTime", "2019-1-4 23:59:59");
		node.putPOJO("pageIndex", 1);
		node.putPOJO("isRead", 0);
		
		
		System.out.println(mapper.writeValueAsString(node));
		bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
		WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/site-msg/lists/2",
				bis,
				MediaType.APPLICATION_JSON_VALUE);
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

		//Thread.sleep(20000);
	}
}