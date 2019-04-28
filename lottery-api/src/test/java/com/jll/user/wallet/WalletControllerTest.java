package com.jll.user.wallet;

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


public class WalletControllerTest extends ControllerJunitBase{
		
	public WalletControllerTest(String name) {
		super(name);
	}
	
	
	public void testQueryByUserIdUserAccount() throws Exception{
		String userName = "agent001";
		String pwd = "test001";
		String clientId = "lottery-client";
		Integer msgId = 7;
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/wallet/queryByUserIdUserAccount");
			
			
			WebConversation wc = new WebConversation();
			request.setParameter("userId", "89");
			request.setHeaderField("Authorization", "Bearer " + token);
			
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
			/*if(bis != null) {
				bis.close();
			}*/
		}
	}
}