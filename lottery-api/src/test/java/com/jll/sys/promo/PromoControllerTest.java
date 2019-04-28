package com.jll.sys.promo;

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

public class PromoControllerTest extends ControllerJunitBase {

	public PromoControllerTest(String name) {
		super(name);
	}

	
	
	/**
	 * 查询活动
	 * 
	 * @throws Exception
	 */
	public void ItestList() throws Exception {
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;

		ObjectNode node = mapper.createObjectNode();
		node.putPOJO("pageIndex", 1);
		node.putPOJO("pageSize", 20);
		node.putPOJO("totalPages", 1);
		
		
		System.out.println(mapper.writeValueAsString(node));
		bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
		WebRequest request = new PostMethodWebRequest("http://localhost:8080/promo/lists",
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
	 * 查询活动
	 * 
	 * @throws Exception
	 */
	public void testQualificationScreening() throws Exception {
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = null;
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;

		ObjectNode node = mapper.createObjectNode();
		node.putPOJO("id", 1);
		
		
		System.out.println(mapper.writeValueAsString(node));
		bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
		WebRequest request = new PostMethodWebRequest("http://localhost:8080/promo/qualification-Screening",
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
}