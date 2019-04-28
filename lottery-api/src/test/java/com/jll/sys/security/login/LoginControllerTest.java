package com.jll.sys.security.login;

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
import com.meterware.httpunit.HttpException;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.PutMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


public class LoginControllerTest extends ControllerJunitBase{
		
	public LoginControllerTest(String name) {
		super(name);
	}
	
	public void testRefreshToken() throws Exception{
		String userName = "test001";
		//String userName = "admin";
		String pwd = "test001";
		//String pwd = "111111";
		String clientId = "lottery-client";
		//String clientId = "lottery-admin";
		String token = queryRefreshToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("grant_type", "refresh_token");
			node.putPOJO("client_id", clientId);
			node.putPOJO("client_secret", "secret_1");
			node.putPOJO("refresh_token", token);
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/oauth/token");
			//WebRequest request = new PostMethodWebRequest("http://110.92.64.70/lottery-api/oauth/token");
			request.setParameter("grant_type", "refresh_token");
			request.setParameter("client_id", clientId);
			request.setParameter("client_secret", "secret_1");
			request.setParameter("refresh_token", token);
			WebConversation wc = new WebConversation();
			
			//request.setHeaderField("Authorization", "Bearer " + token);
			
			WebResponse response = wc.sendRequest(request);
			
			int  status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);

			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(bis != null) {
				bis.close();
			}
		}
	}
}