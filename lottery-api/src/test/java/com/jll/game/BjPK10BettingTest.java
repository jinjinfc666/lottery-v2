package com.jll.game;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.springframework.http.MediaType;

import com.ehome.test.ControllerJunitBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Message;
import com.jll.entity.Issue;
import com.jll.entity.PlayType;
import com.jll.game.playtype.PlayTypeFacade;
import com.jll.game.playtypefacade.PlayTypeFactory;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.PutMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


public class BjPK10BettingTest extends ControllerJunitBase{
		
	public BjPK10BettingTest(String name) {
		super(name);
	}
	
	public void ItestBetting_qcssc_qszx() throws Exception{
		int maxTimes = 6000;
		int counter = 0;
		String lottoType = "bjpk10";
		long maxWaittingTime = 30000;
		
		try {
			Thread.sleep(60*1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		
		while(counter < maxTimes) {
			Thread.sleep(maxWaittingTime);
			
			
			Map<String, Object> ret = queryCurrIssue(token, lottoType);
			while((ret == null || ret.size() == 0) 
					&& counter <= 200) {
				counter++;
				
				ret = queryCurrIssue(token, lottoType);
			}
			
			if(ret == null || ret.size() == 0) {
				Assert.fail("Can't obtain the current issue!!!!");
			}
			
			Issue currIssue = (Issue)ret.get("currIssue");
			if(currIssue.getDownCounter() <= 0) {
				return;
			}
			
			final Integer currIssueId = currIssue.getId();
			Thread exe = new Thread(new Runnable(){
				@Override
				public void run() {
					
					
					
					
					
					ObjectMapper mapper = new ObjectMapper();
					ByteArrayInputStream bis = null;
					try {
						
						
						ArrayNode array = mapper.createArrayNode();
						
						ObjectNode node = array.addObject();
						node.putPOJO("issueId", currIssueId);
						node.putPOJO("playType", 1);
						node.putPOJO("betNum", "1,1,1");
						node.putPOJO("times", "1");
						node.putPOJO("pattern", "1");
						node.putPOJO("isZh", "0");
						node.putPOJO("terminalType", "0");
						
						bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
						WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/" +lottoType+ "/bet/zh/1/wallet/15",
								bis,
								MediaType.APPLICATION_JSON_VALUE);
						WebConversation wc = new WebConversation();
						
						request.setHeaderField("Authorization", "bearer " + token);
						
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
						//throw e;
					}finally {
						if(bis != null) {
							try {
								bis.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				}
			});
			
			exe.start();
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_qcssc_zszx() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(3*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 3);
							node.putPOJO("betNum", "1,1,1");
							//node.putPOJO("betTotal", "1");
							//node.putPOJO("betAmount", "5.00");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							//node.putPOJO("prizeRate", "1750");
							//node.putPOJO("state", "5.00");
							//node.putPOJO("delayPayoutFlag", "5.00");
							node.putPOJO("isZh", "0");
							//node.putPOJO("isZhBlock", "5.00");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_qcssc_hszx() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(3*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 5);
							node.putPOJO("betNum", "1,1,1");
							//node.putPOJO("betTotal", "1");
							//node.putPOJO("betAmount", "5.00");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							//node.putPOJO("prizeRate", "1750");
							//node.putPOJO("state", "5.00");
							//node.putPOJO("delayPayoutFlag", "5.00");
							node.putPOJO("isZh", "0");
							//node.putPOJO("isZhBlock", "5.00");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_qcssc_qszuxzs() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(3*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 7);
							node.putPOJO("betNum", "1234");
							//node.putPOJO("betTotal", "1");
							//node.putPOJO("betAmount", "5.00");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							//node.putPOJO("prizeRate", "1750");
							//node.putPOJO("state", "5.00");
							//node.putPOJO("delayPayoutFlag", "5.00");
							node.putPOJO("isZh", "0");
							//node.putPOJO("isZhBlock", "5.00");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_qcssc_qszuxzl() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(3*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 8);
							node.putPOJO("betNum", "1234");
							//node.putPOJO("betTotal", "1");
							//node.putPOJO("betAmount", "5.00");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							//node.putPOJO("prizeRate", "1750");
							//node.putPOJO("state", "5.00");
							//node.putPOJO("delayPayoutFlag", "5.00");
							node.putPOJO("isZh", "0");
							//node.putPOJO("isZhBlock", "5.00");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_qcssc_qszuxMix() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 9);
							node.putPOJO("betNum", "123;122");
							//node.putPOJO("betTotal", "1");
							//node.putPOJO("betAmount", "5.00");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							//node.putPOJO("prizeRate", "1750");
							//node.putPOJO("state", "5.00");
							//node.putPOJO("delayPayoutFlag", "5.00");
							node.putPOJO("isZh", "0");
							//node.putPOJO("isZhBlock", "5.00");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_qcssc_zszuxZs() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 10);
							node.putPOJO("betNum", "123");
							//node.putPOJO("betTotal", "1");
							//node.putPOJO("betAmount", "5.00");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							//node.putPOJO("prizeRate", "1750");
							//node.putPOJO("state", "5.00");
							//node.putPOJO("delayPayoutFlag", "5.00");
							node.putPOJO("isZh", "0");
							//node.putPOJO("isZhBlock", "5.00");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_qcssc_zszuxZl() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 11);
							node.putPOJO("betNum", "123");
							//node.putPOJO("betTotal", "1");
							//node.putPOJO("betAmount", "5.00");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							//node.putPOJO("prizeRate", "1750");
							//node.putPOJO("state", "5.00");
							//node.putPOJO("delayPayoutFlag", "5.00");
							node.putPOJO("isZh", "0");
							//node.putPOJO("isZhBlock", "5.00");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_qcssc_zszuxMix() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 12);
							node.putPOJO("betNum", "123");
							//node.putPOJO("betTotal", "1");
							//node.putPOJO("betAmount", "5.00");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							//node.putPOJO("prizeRate", "1750");
							//node.putPOJO("state", "5.00");
							//node.putPOJO("delayPayoutFlag", "5.00");
							node.putPOJO("isZh", "0");
							//node.putPOJO("isZhBlock", "5.00");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_qcssc_hszuxZs() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 13);
							node.putPOJO("betNum", "123");
							//node.putPOJO("betTotal", "1");
							//node.putPOJO("betAmount", "5.00");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							//node.putPOJO("prizeRate", "1750");
							//node.putPOJO("state", "5.00");
							//node.putPOJO("delayPayoutFlag", "5.00");
							node.putPOJO("isZh", "0");
							//node.putPOJO("isZhBlock", "5.00");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_qcssc_hszuxZl() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 14);
							node.putPOJO("betNum", "123");
							//node.putPOJO("betTotal", "1");
							//node.putPOJO("betAmount", "5.00");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							//node.putPOJO("prizeRate", "1750");
							//node.putPOJO("state", "5.00");
							//node.putPOJO("delayPayoutFlag", "5.00");
							node.putPOJO("isZh", "0");
							//node.putPOJO("isZhBlock", "5.00");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_qcssc_hszuxMix() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 15);
							node.putPOJO("betNum", "123");
							//node.putPOJO("betTotal", "1");
							//node.putPOJO("betAmount", "5.00");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							//node.putPOJO("prizeRate", "1750");
							//node.putPOJO("state", "5.00");
							//node.putPOJO("delayPayoutFlag", "5.00");
							node.putPOJO("isZh", "0");
							//node.putPOJO("isZhBlock", "5.00");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_qcssc_wxq2() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 16);
							node.putPOJO("betNum", "1,2");
							//node.putPOJO("betTotal", "1");
							//node.putPOJO("betAmount", "5.00");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							//node.putPOJO("prizeRate", "1750");
							//node.putPOJO("state", "5.00");
							//node.putPOJO("delayPayoutFlag", "5.00");
							node.putPOJO("isZh", "0");
							//node.putPOJO("isZhBlock", "5.00");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	
	public void ItestBetting_qcssc_wxq2Zx() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 18);
							node.putPOJO("betNum", "12345");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_qcssc_wxh2() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							if(currIssue == null) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 31);
							node.putPOJO("betNum", "0,0");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_qcssc_wxh2Zx() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 33);
							node.putPOJO("betNum", "12345");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_qcssc_bdw_qs() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 19);
							node.putPOJO("betNum", "12345");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_qcssc_bdw_zs() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 20);
							node.putPOJO("betNum", "12345");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_qcssc_bdw_hs() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 21);
							node.putPOJO("betNum", "12345");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_qcssc_dwd() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 22);
							node.putPOJO("betNum", "12345,,,,");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_guanDong11In5_qszx() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 34);
							node.putPOJO("betNum", "0102030405,06,07");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_guanDong11In5_qszux() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 36);
							node.putPOJO("betNum", "0102030405");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_guanDong11In5_wxq2() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 38);
							node.putPOJO("betNum", "01,02");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_guanDong11In5_wxq2Zx() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 40);
							node.putPOJO("betNum", "0102");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_guanDong11In5_bdw() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 42);
							node.putPOJO("betNum", "0102");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_guanDong11In5_dwd() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 43);
							node.putPOJO("betNum", "0102,01,02");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_guanDong11In5_qwDs() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 44);
							node.putPOJO("betNum", "543210");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	
	public void ItestBetting_guanDong11In5_qwZw() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 45);
							node.putPOJO("betNum", "345");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	
	public void ItestBetting_guanDong11In5_Rx1() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 46);
							node.putPOJO("betNum", "030405");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_guanDong11In5_Rx2() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 47);
							node.putPOJO("betNum", "0304");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_guanDong11In5_Rx3() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 48);
							node.putPOJO("betNum", "030405");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_guanDong11In5_Rx4() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 49);
							node.putPOJO("betNum", "03040506");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_guanDong11In5_Rx5() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 50);
							node.putPOJO("betNum", "0304050607");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_guanDong11In5_Rx6() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 51);
							node.putPOJO("betNum", "030405060708");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_guanDong11In5_Rx7() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 52);
							node.putPOJO("betNum", "02030405060708");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_guanDong11In5_Rx8() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 53);
							node.putPOJO("betNum", "0203040506070809");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_pk10_qy() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 63);
							node.putPOJO("betNum", "0203");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_pk10_qe() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 64);
							node.putPOJO("betNum", "0203,0405");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_pk10_qs() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 65);
							node.putPOJO("betNum", "0203,0405,0608");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	
	public void ItestBetting_pk10_Lh_1v10() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 68);
							node.putPOJO("betNum", "01");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_pk10_Lh_2v9() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 69);
							node.putPOJO("betNum", "01");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_pk10_Lh_3v8() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 70);
							node.putPOJO("betNum", "01");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_pk10_Lh_4v7() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 71);
							node.putPOJO("betNum", "01");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_pk10_Lh_5v6() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 72);
							node.putPOJO("betNum", "01");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_pk10_Dx_1() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 73);
							node.putPOJO("betNum", "01");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_pk10_Dx_2() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 74);
							node.putPOJO("betNum", "01");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_pk10_Dx_3() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 75);
							node.putPOJO("betNum", "01");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_pk10_Ds_1() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 76);
							node.putPOJO("betNum", "01");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_pk10_Ds_2() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 77);
							node.putPOJO("betNum", "01");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_pk10_Ds_3() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 78);
							node.putPOJO("betNum", "01");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	public void ItestBetting_pk10_dwd() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 66);
							node.putPOJO("betNum", "0203,0405,0608");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	
	public void ItestPreBetting() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(1000);
			
			if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
														
							ObjectNode node = mapper.createObjectNode();
							node.putPOJO("playType", 1);
							node.putPOJO("betNum", "0,0,0");
							node.putPOJO("times", 1);
							node.putPOJO("monUnit", 0.1);
							
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/bjpk10/pre-bet",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			}
			
			counter++;
			
		}
	}
	
	
	
	public void ItestBetting_5fc_zs() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "bjpk10";
		
		while(counter < maxTimes) {
			Thread.sleep(10000);
			
			//if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 79);
							node.putPOJO("betNum", "023,45,68");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/5fc/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			//}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_sfc_zs() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "sfc";
		while(counter < maxTimes) {
			Thread.sleep(10000);
			
			//if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 109);
							node.putPOJO("betNum", "023,45,68");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/" +lottoType+ "/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			//}
			
			counter++;
			
		}
	}
	
	
	
	public void ItestBetting_ffc_zs() throws Exception{
		int maxTimes = 10000;
		int counter = 0;
		String lottoType = "ffc";
		while(counter < maxTimes) {
			Thread.sleep(10000);
			
			//if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							Map<String, Object> ret = queryCurrIssue(token, lottoType);
							int counter = 0;
							while((ret == null || ret.size() == 0) 
									&& counter <= 200) {
								counter++;
								
								ret = queryCurrIssue(token, lottoType);
							}
							
							if(ret == null || ret.size() == 0) {
								Assert.fail("Can't obtain the current issue!!!!");
							}
							
							Issue currIssue = (Issue)ret.get("currIssue");
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 139);
							node.putPOJO("betNum", "023,45,68");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/" +lottoType+ "/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			//}
			
			counter++;
			
		}
	}
	
	
	public void ItestBetting_mmc_zs() throws Exception{
		int maxTimes = 5;
		int counter = 0;
		String lottoType = "mmc";
		while(counter < maxTimes) {
			
			Thread.sleep(120000);
			//if(counter == 0) {
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						
						/*try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
						String userName = "test001";
						String pwd = "test001";
						String clientId = "lottery-client";
						String token = queryToken(userName, pwd, clientId);
						ObjectMapper mapper = new ObjectMapper();
						ByteArrayInputStream bis = null;
						try {
							/*Issue currIssue = queryCurrIssue(token, lottoType);
							int counter = 0;
							while(currIssue == null && counter <= 200) {
								counter++;
								
								currIssue = queryCurrIssue(token, lottoType);
							}
							
							if(currIssue == null) {
								Assert.fail("Can't obtain the current issue!!!!");
							}*/
							
							ArrayNode array = mapper.createArrayNode();
							
							ObjectNode node = array.addObject();
							//node.putPOJO("issueId", currIssue.getId());
							node.putPOJO("playType", 169);
							node.putPOJO("betNum", "023,45,68");
							node.putPOJO("times", "1");
							node.putPOJO("pattern", "1");
							node.putPOJO("isZh", "0");
							node.putPOJO("terminalType", "0");
							
							bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
							WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/" +lottoType+ "/bet/zh/1/wallet/15",
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
							//throw e;
						}finally {
							if(bis != null) {
								try {
									bis.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
				});
				
				exe.start();
			//}
			
			counter++;
			
		}
		
		
		Thread.sleep(10000000);
	}
	
	
	public Map<String, Object> queryCurrIssue(String token, String lottoType) throws Exception{
		Map<String, Object> ret = new HashMap<String, Object>();
		List<PlayType> playTypes = new ArrayList<>();
		Issue currIssue = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/" + lottoType + "/betting-issue");
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
			
			Map data = (Map)retItems.get("data");
			
			Map currIssueMap = (Map)data.get("currIssue");
			
			List<Map<String, Object>> playTypesMap = (List<Map<String, Object>>)data.get("playType");
			if(currIssueMap != null && currIssueMap.size() > 0) {
				//Long downCounter = (Long)((LinkedHashMap)currIssueMap).get(0);
				Integer downCounter = (Integer)currIssueMap.get("downCounter");
				Date endTime = new Date((Long)currIssueMap.get("endTime"));
				Integer id = (Integer)currIssueMap.get("id");
				String lotteryType = (String)currIssueMap.get("lotteryType");
				Date startTime = new Date((Long)currIssueMap.get("startTime"));
				Integer state = (Integer)currIssueMap.get("state");
				String issueNum = (String)currIssueMap.get("issueNum");
				currIssue = new Issue();
				
				currIssue.setDownCounter(new Long(downCounter.intValue()));
				currIssue.setEndTime(endTime);
				currIssue.setId(id);
				currIssue.setIssueNum(issueNum);
				currIssue.setLotteryType(lotteryType);
				currIssue.setStartTime(startTime);
				currIssue.setState(state);
			}
			
			
			for(Map<String, Object> temp : playTypesMap) {
				//id=1, lotteryType=bjpk10, classification=qszx|前三直选, ptName=fs, ptDesc=复式, state=1, mulSinFlag=1, isHidden=1, seq=1, createTime=1533895746000
				Integer id = (Integer)temp.get("id");
				String lotteryType = (String)temp.get("lotteryType");
				String classification = (String)temp.get("classification");
				String ptName = (String)temp.get("ptName");
				String ptDesc = (String)temp.get("ptDesc");
				Integer state = (Integer)temp.get("state");
				Integer mulSinFlag = (Integer)temp.get("mulSinFlag");
				Integer isHidden = (Integer)temp.get("isHidden");
				Integer seq = (Integer)temp.get("seq");
				PlayType playType = new PlayType();
				playType.setClassification(classification);
				playType.setId(id);
				playType.setIsHidden(isHidden);
				playType.setLotteryType(lotteryType);
				playType.setMulSinFlag(mulSinFlag);
				playType.setPtDesc(ptDesc);
				playType.setSeq(seq);
				playType.setState(state);
				playType.setPtName(ptName);
				playTypes.add(playType);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		ret.put("currIssue", currIssue);
		ret.put("playTypes", playTypes);
		return ret;
	}
	
	
	
	public void ItestBetting_bjpk10() throws Exception{
		int maxTimes = 600000;
		int counter = 0;
		String lottoType = "bjpk10";
		long maxWaittingTime = 17000;
		
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		
		//String token ;
		//String winningNum = null;
		StringBuffer winningNumBuffer = new StringBuffer();
		Map<String, Integer> currIndx = new HashMap<>();
		Map<String, PlayTypeFacade> betNumbers = new HashMap<>();
		Map<String, String> tokens = new HashMap<>();
		
		try {
			Thread.sleep(60*1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		while(counter < maxTimes) {
			Thread.sleep(maxWaittingTime);
			String token = queryToken(userName, pwd, clientId);
			tokens.put("token", token);
			
			Map<String, Object> ret = queryCurrIssue(token, lottoType);
			int queryIssueCounter = 0;
			while((ret == null || ret.size() == 0
					|| ret.get("currIssue") == null) 
					&& queryIssueCounter <= 1000) {
				queryIssueCounter++;
				
				ret = queryCurrIssue(token, lottoType);
				Thread.sleep(500);
			}
			
			if(ret == null || ret.size() == 0) {
				Assert.fail("Can't obtain the current issue!!!!");
			}
			
			Issue currIssue = (Issue)ret.get("currIssue");
			Assert.assertNotNull(currIssue);
			List<PlayType> playTypes = (List<PlayType>)ret.get("playTypes");
			if(currIssue.getDownCounter() <= 0) {
				//Thread.sleep(60000);
				Thread exe = new Thread(new Runnable(){
					@Override
					public void run() {
						String userName = "admin";
						String pwd = "test001";
						String clientId = "lottery-admin";
						String adminToken = queryToken(userName, pwd, clientId);
						Random random = new Random();
						int playTypeIndx = random.nextInt(betNumbers.size());
						Iterator<String> keys = betNumbers.keySet().iterator();
						int indx = 0;
						PlayTypeFacade playTypeFacade = null;
						String betNum = null;
						while(keys.hasNext()) {
							betNum = keys.next();
							if(indx == playTypeIndx) {
								playTypeFacade = betNumbers.get(betNum);
								break;
							}
							
							indx++;
						}
						//winningNum = winningNumBuffer.toString();
						if(winningNumBuffer.length() == 0) {
							List<Map<String, String>> maps = playTypeFacade.parseBetNumber(betNum);
							if(maps != null && maps.size() > 0) {
								Map<String, String> row = maps.get(0);
								String winningNum = row.get(Constants.KEY_FACADE_BET_NUM_SAMPLE);
								for(int i = 0; i< winningNum.length();) {
									String bit = winningNum.substring(i, i + 2);
									if(!",".equals(bit)) {
										winningNumBuffer.append(bit).append(",");
									}
									
									 i += 2;
								}
								winningNumBuffer.delete(winningNumBuffer.length() - 1, winningNumBuffer.length());
							}							
						}
						try {
							System.out.println(String.format("current issue status   %s", currIssue.getState()));
							Thread.sleep(60000);
							manualDrawResult(lottoType,
									currIssue.getIssueNum(),
									winningNumBuffer.toString(),
									adminToken);
							
							Thread.sleep(120000);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				exe.start();
				
				winningNumBuffer.delete(0, winningNumBuffer.length());
				playTypes.clear();
				currIndx.remove("currIndx");
				continue;
			}
			
			final Integer currIssueId = currIssue.getId();
			Thread exe = new Thread(new Runnable(){
				@Override
				public void run() {
					ObjectMapper mapper = new ObjectMapper();
					ByteArrayInputStream bis = null;
					String playTypeName = null;
					String betNum = null;
					try {
						ArrayNode array = mapper.createArrayNode();
						Integer indx = currIndx.get("currIndx");
						if(indx == null) {
							indx = 0;
							currIndx.put("currIndx", indx);
						}else {
							indx++;
							if(indx > playTypes.size() - 1) {
								indx = playTypes.size() -1;
							}
							currIndx.put("currIndx", indx);
						}
						
						PlayType playType = playTypes.get(indx);
						
						System.out.println(String.format("current Indx  %s,   size   %s", 
								currIndx,
								playTypes.size()));
						
						if(playType.getPtName().equals("fs") || playType.getPtName().equals("ds")) {
							playTypeName = playType.getClassification() + "/fs-ds";
						}else {
							playTypeName = playType.getClassification() + "/" + playType.getPtName();
						}
						PlayTypeFacade playTypeFacade = PlayTypeFactory.getInstance().getPlayTypeFacade(playTypeName);
						
						if(playTypeFacade == null) {
							return ;
						}
						
						betNum = playTypeFacade.obtainSampleBetNumber();
						
						betNumbers.put(betNum, playTypeFacade);
												
						ObjectNode node = array.addObject();
						node.putPOJO("issueId", currIssueId);
						node.putPOJO("playType", playType.getId());
						node.putPOJO("betNum", betNum.toString());
						node.putPOJO("times", "1");
						node.putPOJO("pattern", "1");
						node.putPOJO("isZh", "0");
						node.putPOJO("terminalType", "0");
						
						System.out.println(mapper.writeValueAsString(node));
						bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
						WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/" +lottoType+ "/bet/zh/1/wallet/15",
								bis,
								MediaType.APPLICATION_JSON_VALUE);
						WebConversation wc = new WebConversation();
						
						String token = tokens.get("token");
						request.setHeaderField("Authorization", "bearer " + token);
						
						WebResponse response = wc.sendRequest(request);
						
						int  status = response.getResponseCode();
						
						Assert.assertEquals(HttpServletResponse.SC_OK, status);
						String result = response.getText();
						
						Map<String, Object> retItems = null;
						
						retItems = mapper.readValue(result, HashMap.class);
						
						Assert.assertNotNull(retItems);
						
						Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
						
						
						Thread.sleep(10000);
						
						
					} catch (Exception e) {
						e.printStackTrace();
						//throw e;
					}finally {
						if(bis != null) {
							try {
								bis.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}						
					}
					
				}
			});
			
			exe.start();
			
			counter++;
			
		}
	}
	
	
	
	
	
	public void manualDrawResult(String lottoType, 
			String issueNum, 
			String winningNum,
			String token) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			ObjectNode node = mapper.createObjectNode();
			
			System.out.println(String.format("lottoType    %s ,   issueNum   %s    winningNum   %s", 
					lottoType,
					issueNum,
					winningNum));
			node.putPOJO("winningNum", winningNum);
			
			bis = new ByteArrayInputStream(mapper.writeValueAsBytes(node));
			WebRequest request = new PutMethodWebRequest("http://localhost:8080/sys/oper/" + lottoType + "/issue/" + issueNum + "/manual-draw-result",
					bis,
					MediaType.APPLICATION_JSON_VALUE);
			WebConversation wc = new WebConversation();
			
			request.setHeaderField("Authorization", "bearer " + token);
			
			WebResponse response = wc.sendRequest(request);
			
			int  status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);
			
			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
			
			Thread.sleep(10000);
		}catch(Exception ex) {
			throw ex;
		}finally {
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	public void logout(String token) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		try {			
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/security/logout");
			WebConversation wc = new WebConversation();
			
			request.setHeaderField("Authorization", "bearer " + token);
			
			WebResponse response = wc.sendRequest(request);
			
			int  status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);
			
			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
			
			Thread.sleep(10000);
		}catch(Exception ex) {
			throw ex;
		}
	}
}