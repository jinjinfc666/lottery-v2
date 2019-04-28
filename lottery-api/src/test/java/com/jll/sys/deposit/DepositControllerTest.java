package com.jll.sys.deposit;

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


public class DepositControllerTest extends ControllerJunitBase{
		
	public DepositControllerTest(String name) {
		super(name);
	}
	
	/**
	 * 红包钱包提款
	 * 
	 * 
	 * @throws Exception
	 */
	public void testAddBank() throws Exception{
		String userName = "admin";
		String pwd = "111111";
		String clientId = "lottery-admin";
		//Integer msgId = 7;
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("cardNum", "6230580000152299413 ");
			node.putPOJO("bankBranch", "平安银行");
			node.putPOJO("remark", pwd);
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/pay-types/uploadQRCode",
					bis,
					MediaType.APPLICATION_JSON_VALUE);
			
			
			WebConversation wc = new WebConversation();
			
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