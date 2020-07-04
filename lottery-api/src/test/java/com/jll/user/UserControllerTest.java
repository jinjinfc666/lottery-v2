package com.jll.user;

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


public class UserControllerTest extends ControllerJunitBase{
		
	public UserControllerTest(String name) {
		super(name);
	}	
	
	public void testSelfRegUser() throws Exception{
		/*String userName = "agent001";
		String pwd = "agent001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);*/
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("userName", "nihao111");
			node.putPOJO("loginPwd", "111111");
			node.putPOJO("fundPwd", "111111");
			node.putPOJO("userType", "0");
			node.putPOJO("superior", "13");
			
			
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/self/players",
					bis,
					MediaType.APPLICATION_JSON_VALUE);
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
	
	public void ItestRegUser() throws Exception{
		String userName = "agent001";
		String pwd = "agent001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("userName", "test001");
			node.putPOJO("loginPwd", "111111");
			node.putPOJO("platRebate", "5.00");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/players",
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
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	
	public void ItestModifyPlayer() throws Exception{
		String userName = "agent001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("realName", "测试");
			node.putPOJO("userName", "test001");
			node.putPOJO("userId", "");
			node.putPOJO("email", "");
			node.putPOJO("phoneNum", "");
			node.putPOJO("qq", "");
			node.putPOJO("wechat", "");
			node.putPOJO("platRebate", 6.2);
			node.putPOJO("userType", 1);
			//{"realName":"代理","userName":"agent003","userId":"","email":"","phoneNum":"","qq":"","wechat":"","platRebate":6.2,"userType":1}
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PutMethodWebRequest("http://localhost:8080/users/players",
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
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	
	public void ItestRegAgent() throws Exception{
		String userName = "admin";
		String pwd = "admin";
		String clientId = "lottery-admin";
		String token = queryToken(userName, pwd, clientId);
		
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("userName", "agent001");
			node.putPOJO("loginPwd", "111111");
			node.putPOJO("platRebate", "10.00");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/agents",
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
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	public void ItestRegSysUser() throws Exception{
		String userName = "admin";
		String pwd = "admin";
		String clientId = "lottery-admin";
		String token = queryToken(userName, pwd, clientId);
		
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("userName", "sys_user_001");
			node.putPOJO("loginPwd", "sys_user_001");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/sys-users",
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
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	public void ItestApplySMS() throws Exception{
		/*String userName = "admin";
		String pwd = "admin";
		String token = queryToken(userName, pwd);*/
		
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("userName", "test001");
			node.putPOJO("loginPwd", "test001");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/users/test001/attrs/login-pwd/reset/sms");
			WebConversation wc = new WebConversation();
			
			/*request.setHeaderField("Authorization", "Bearer " + token);*/
			
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
	
	public void ItestResetPwdSMS() throws Exception{
		/*String userName = "admin";
		String pwd = "admin";
		String token = queryToken(userName, pwd);*/
		
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("sms", "612416");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PutMethodWebRequest("http://localhost:8080/users/test001/attrs/login-pwd/reset/sms",
					bis,
					MediaType.APPLICATION_JSON_VALUE);
			
			WebConversation wc = new WebConversation();
			
			/*request.setHeaderField("Authorization", "Bearer " + token);*/
			
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
	
	
	public void ItestApplyEmail() throws Exception{
		/*String userName = "admin";
		String pwd = "admin";
		String token = queryToken(userName, pwd);*/
		
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("userName", "sys_user_001");
			node.putPOJO("loginPwd", "sys_user_001");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/users/test001/attrs/login-pwd/pre-reset/email");
			WebConversation wc = new WebConversation();
			
			/*request.setHeaderField("Authorization", "Bearer " + token);*/
			
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
	
	public void ItestResetPwdEmail() throws Exception{
		/*String userName = "admin";
		String pwd = "admin";
		String token = queryToken(userName, pwd);*/
		
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("sms", "612416");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PutMethodWebRequest("http://localhost:8080/users/test001/attrs/login-pwd/reset/sms",
					bis,
					MediaType.APPLICATION_JSON_VALUE);
			
			WebConversation wc = new WebConversation();
			
			/*request.setHeaderField("Authorization", "Bearer " + token);*/
			
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
	
	
	public void ItestQueryAllUsers() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("userName", "中国");
			node.putPOJO("startTime", "2018");
			node.putPOJO("endTime", "2018");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/users/queryAllUserInfo?userName=中国&startTime=2018&endTime=2018");
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
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	
	public void ItestQDAgentNextAgent_non_agent() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			/*ObjectNode node = mapper.createObjectNode();
			node.putPOJO("userName", "中国");
			node.putPOJO("startTime", "2018");
			node.putPOJO("endTime", "2018");*/
			
			//bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/users/QDAgentNextAgent?id=2&startTime=2018-01-01&endTime=2018-12-01&pageIndex=1");
			WebConversation wc = new WebConversation();
			
			request.setHeaderField("Authorization", "Bearer " + token);
			
			WebResponse response = wc.sendRequest(request);
			
			int  status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_FORBIDDEN, status);
			/*String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);

			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));*/
		} catch (Exception e) {
			HttpException ex = (HttpException)e;
			Assert.assertEquals(403, ex.getResponseCode());
			/*e.printStackTrace();
			throw e;*/
		}finally {
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	
	public void ItestQDAgentNextAgent_agent() throws Exception{
		String userName = "agent001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			/*ObjectNode node = mapper.createObjectNode();
			node.putPOJO("userName", "中国");
			node.putPOJO("startTime", "2018");
			node.putPOJO("endTime", "2018");*/
			
			//bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/users/QDAgentNextAgent?id=13&startTime=2019-1-5 00:00:00&endTime=2019-1-5 23:59:59&pageIndex=1");
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
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	
	public void ItestCreateUser_non_agent() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("userName", "test001");
			node.putPOJO("loginPwd", "111111");
			node.putPOJO("platRebate", "5.00");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/players",
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
		} catch (HttpException e) {
			Assert.assertEquals(403, e.getResponseCode());
		}finally {
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	
	public void ItestCreateUser_agent() throws Exception{
		String userName = "agent001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("userName", "test001");
			node.putPOJO("loginPwd", "111111");
			node.putPOJO("platRebate", "5.00");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/players",
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

			//Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
		} catch (HttpException e) {
			Assert.assertEquals(403, e.getResponseCode());
		}finally {
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	public void ItestQuerySiteMsgRec_player_noUserName() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/users/site-msg-rec");
			/*request.setParameter("userName", "test001");
			request.setParameter("pageIndex", Integer.toString(0));
			request.setParameter("pageSize", Integer.toString(2));*/
			
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
		} catch (HttpException e) {
			Assert.assertEquals(403, e.getResponseCode());
		}finally {
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	public void ItestQuerySiteMsgRec_player_withUserName() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/users/site-msg-rec");
			request.setParameter("userName", "admin");
			request.setParameter("pageIndex", Integer.toString(0));
			request.setParameter("pageSize", Integer.toString(2));
			
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
		} catch (HttpException e) {
			Assert.assertEquals(403, e.getResponseCode());
		}finally {
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	public void ItestQuerySiteMsgRec_agent() throws Exception{
		String userName = "agent001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/users/site-msg-rec");
			//request.setParameter("userName", "admin");
			request.setParameter("pageIndex", Integer.toString(0));
			request.setParameter("pageSize", Integer.toString(2));
			
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
		} catch (HttpException e) {
			Assert.assertEquals(403, e.getResponseCode());
		}finally {
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	public void ItestQuerySiteMsgRec_sysAdmin_admin() throws Exception{
		String userName = "admin";
		String pwd = "test001";
		String clientId = "lottery-admin";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/users/site-msg-rec");
			//request.setParameter("userName", "admin");
			request.setParameter("pageIndex", Integer.toString(0));
			request.setParameter("pageSize", Integer.toString(2));
			
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
		} catch (HttpException e) {
			Assert.assertEquals(403, e.getResponseCode());
		}finally {
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	
	public void ItestUpdateUserInfo_user() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("id", 22);
			node.putPOJO("userName", "test001");
			node.putPOJO("realName", "测试");
			node.putPOJO("loginPwd", "111111");
			node.putPOJO("platRebate", "5.00");
			node.putPOJO("phoneNum", "13130570751");
			node.putPOJO("email", "54924867@qq.com");
			node.putPOJO("qq", "54924867");
			node.putPOJO("wechat", "seayf1111");
			
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PutMethodWebRequest("http://localhost:8080/users/updateUserInfo",
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
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	
	
	public void ItestUpdateUserInfo_Fund_pwd() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("oldPwd", "111111");
			node.putPOJO("newPwd", "test001");
			node.putPOJO("confirmPwd", "test001");
			
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/attrs/fund-pwd",
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
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	
	public void ItestRegDemoUser() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		/*String token = queryToken(userName, pwd, clientId);*/
		ObjectMapper mapper = new ObjectMapper();
		//ByteArrayInputStream bis = null;
		try {
			/*ObjectNode node = mapper.createObjectNode();
			node.putPOJO("oldPwd", "111111");
			node.putPOJO("newPwd", "test001");
			node.putPOJO("confirmPwd", "test001");
			
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));*/
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/users/demo-players");
			
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
			/*if(bis != null) {
				bis.close();
			}*/
		}
	}
	
	
	public void ItestApplyVerifyPhone() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		//ByteArrayInputStream bis = null;
		try {
			/*ObjectNode node = mapper.createObjectNode();
			node.putPOJO("oldPwd", "111111");
			node.putPOJO("newPwd", "test001");
			node.putPOJO("confirmPwd", "test001");
			
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));*/
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/users/" +userName+ "/verify/phone-apply");
			
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
	
	
	public void ItestQueryQDAgentNextAgent() throws Exception{
		String userName = "agent001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/users/QDAgentNextAgent");
			request.setParameter("id", "13");
			//request.setParameter("userName", "test001");
			request.setParameter("startTime", "2019-1-6 00:00:00");
			request.setParameter("endTime", "2019-1-6 23:59:59");
			request.setParameter("pageIndex", "1");
			
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
	
	public void ItestShowSiteMessageFeedback() throws Exception{
		String userName = "agent001";
		String pwd = "test001";
		String clientId = "lottery-client";
		Integer msgId = 7;
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/users/site-msg/"+String.valueOf(msgId)+"/history-feedback");
			
			
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
	
	
	/**
	 * 在此期间没有有效充值,也没有投注,但是有账户余额
	 * 操作红包钱包 , 红包钱包账户余额 500
	 * @throws Exception
	 */
	public void ItestUserWithdrawApply_no_deposit_no_betting() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		//Integer msgId = 7;
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("walletId", 16);
			node.putPOJO("bankId", "1");
			node.putPOJO("password", pwd);
			node.putPOJO("amount", "105");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/withdraw/apply",
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
	
	/**
	 * 投注金额/充值金额 = 0.7
	 * 确保用户的充值金额为0,投注金额10元, 
	 * 提款申请可以成功
	 * @throws Exception
	 */
	public void ItestUserWithdrawApply_no_deposit_betting() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		//Integer msgId = 7;
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("walletId", 16);
			node.putPOJO("bankId", "1");
			node.putPOJO("password", pwd);
			node.putPOJO("amount", "105");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/withdraw/apply",
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
	
	/**
	 * 投注金额/充值金额 = 0.7
	 * 确保用户的充值金额为100,投注金额10元, 
	 * 由于 实际投注金额/本次充值金额 = 0.1 不满足提款要求
	 * @throws Exception
	 */
	public void ItestUserWithdrawApply_deposit_betting_less() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		//Integer msgId = 7;
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("walletId", 16);
			node.putPOJO("bankId", "1");
			node.putPOJO("password", pwd);
			node.putPOJO("amount", "105");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/withdraw/apply",
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

			Assert.assertEquals(Message.status.FAILED.getCode(), 
					retItems.get(Message.KEY_STATUS));
			
			Assert.assertEquals(Message.Error.ERROR_USER_CONSUMPTION_LESS_THAN_LIMITION.getCode(), 
					retItems.get(Message.KEY_ERROR_CODE));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			/*if(bis != null) {
				bis.close();
			}*/
		}
	}
	
	
	/**
	 * 投注金额/充值金额 = 0.7
	 * 确保用户的充值金额为100,投注金额80元, 
	 * 由于 实际投注金额/本次充值金额 = 0.8 满足提款要求
	 * @throws Exception
	 */
	public void ItestUserWithdrawApply_deposit_betting() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		//Integer msgId = 7;
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("walletId", 15);
			node.putPOJO("bankId", "1");
			node.putPOJO("password", pwd);
			node.putPOJO("amount", "105");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/withdraw/apply",
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
	
	/**
	 * 红包钱包提款
	 * 
	 * 
	 * @throws Exception
	 */
	public void ItestUserWithdrawApply_red_packet() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		//Integer msgId = 7;
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("walletId", 16);
			node.putPOJO("bankId", "1");
			node.putPOJO("password", pwd);
			node.putPOJO("amount", "105");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/withdraw/apply",
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
	
	
	
	/**
	 * 红包钱包提款
	 * 
	 * 
	 * @throws Exception
	 */
	public void ItestUserWithdrawApply_red_packet_connect_to_server() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		//Integer msgId = 7;
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		
		try {
			ObjectNode node = mapper.createObjectNode();
			node.putPOJO("walletId", 16);
			node.putPOJO("bankId", "1");
			node.putPOJO("password", pwd);
			node.putPOJO("amount", "105");
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/withdraw/apply",
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
	/**
	 * 红包钱包提款
	 * 
	 * 
	 * @throws Exception
	 */
	public void ItestAddBank() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
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
			WebRequest request = new PostMethodWebRequest("http://localhost:8080/users/userAddBank",
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