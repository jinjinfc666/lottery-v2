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
import org.xml.sax.SAXException;

import com.ehome.test.ControllerJunitBase;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jll.common.constants.Message;
import com.jll.common.threadpool.ThreadPoolManager;
import com.jll.common.utils.MathUtil;
import com.jll.common.utils.StringUtils;
import com.jll.entity.Issue;
import com.jll.entity.PlayType;
import com.jll.entity.UserInfo;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


public class XyftBettingTest extends ControllerJunitBase{
	
	String server = "http://localhost/lottery-api";
	
	String[] dwdBettingNumbers = {"{betNum},,,,,,,,,",",{betNum},,,,,,,,",",,{betNum},,,,,,,",",,,{betNum},,,,,,",",,,,{betNum},,,,,",",,,,,{betNum},,,,",",,,,,,{betNum},,,",",,,,,,,{betNum},,",",,,,,,,,{betNum},",",,,,,,,,,{betNum}"};
	
	String[][] testUsers = {{"sm_test01", "25317"},{"sm_test02", "25319"},{"sm_test03", "25321"},{"sm_test04", "25323"},{"sm_test05", "25325"},
							{"sm_test06", "25327"},{"sm_test07", "25329"},{"sm_test08", "25331"},{"sm_test09", "25333"},{"sm_test10", "25335"},
							{"sm_test11", "25337"},{"sm_test12", "25339"},{"sm_test13", "25341"},{"sm_test14", "25343"},{"sm_test15", "25345"},
							{"sm_test16", "25347"},{"sm_test17", "25349"},{"sm_test18", "25351"},{"sm_test19", "25353"},{"sm_test20", "25355"},
							{"sm_test21", "25357"},{"sm_test22", "25359"},{"sm_test23", "25361"},{"sm_test24", "25363"},{"sm_test25", "25365"},
							{"sm_test26", "25367"},{"sm_test27", "25369"},{"sm_test28", "25371"},{"sm_test29", "25373"},{"sm_test30", "25375"},
							{"sm_test31", "25377"},{"sm_test32", "25379"},{"sm_test33", "25381"},{"sm_test34", "25383"},{"sm_test35", "25385"},
							{"sm_test36", "25387"},{"sm_test37", "25389"},{"sm_test38", "25391"},{"sm_test39", "25393"},{"sm_test40", "25395"},
							{"sm_test41", "25445"},{"sm_test42", "25447"},{"sm_test43", "25449"},{"sm_test44", "25451"},{"sm_test45", "25453"},
							{"sm_test46", "25455"},{"sm_test47", "25457"},{"sm_test48", "25459"},{"sm_test49", "25461"},{"sm_test50", "25463"},
							{"sm_test51", "25465"},{"sm_test52", "25467"},{"sm_test53", "25469"},{"sm_test54", "25471"},{"sm_test55", "25473"},
							{"sm_test56", "25475"},{"sm_test57", "25477"},{"sm_test58", "25479"},{"sm_test59", "25481"},{"sm_test60", "25483"},
							{"sm_test61", "25485"},{"sm_test62", "25487"},{"sm_test63", "25489"},{"sm_test64", "25491"},{"sm_test65", "25493"},
							{"sm_test66", "25495"},{"sm_test67", "25497"},{"sm_test68", "25499"},{"sm_test69", "25501"},{"sm_test70", "25503"},
							{"sm_test71", "25505"},{"sm_test72", "25507"},{"sm_test73", "25509"},{"sm_test74", "25511"},{"sm_test75", "25513"},
							{"sm_test76", "25515"},{"sm_test77", "25517"},{"sm_test78", "25519"},{"sm_test79", "25668"},{"sm_test80", "25669"},
							{"sm_test81", "25670"},{"sm_test82", "25671"},{"sm_test83", "25672"},{"sm_test84", "25673"},{"sm_test85", "25674"},
							{"sm_test86", "25675"},{"sm_test87", "25676"},{"sm_test88", "25677"},{"sm_test89", "25678"},{"sm_test90", "25679"},
							{"sm_test91", "25680"},{"sm_test92", "25681"},{"sm_test93", "25682"},{"sm_test94", "25683"},{"sm_test95", "25684"},
							{"sm_test96", "25685"},{"sm_test97", "25686"},{"sm_test98", "25687"},{"sm_test99", "25688"},{"sm_test100", "25689"},
							{"sm_test101", "25690"},{"sm_test102", "25691"},{"sm_test103", "25692"},{"sm_test104", "25693"},{"sm_test105", "25694"},
							{"sm_test106", "25695"},{"sm_test107", "25696"},{"sm_test108", "25697"},{"sm_test109", "25698"},{"sm_test110", "25699"},
							{"sm_test111", "25700"},{"sm_test112", "25701"},{"sm_test113", "25702"},{"sm_test114", "25703"},{"sm_test115", "25704"},
							{"sm_test116", "25705"},{"sm_test117", "25706"},{"sm_test118", "25707"},{"sm_test119", "25708"},{"sm_test120", "25709"},
							{"sm_test121", "25710"},{"sm_test122", "25711"},{"sm_test123", "25712"},{"sm_test124", "25713"},{"sm_test125", "25714"},
							{"sm_test126", "25715"},{"sm_test127", "25716"},{"sm_test128", "25717"},{"sm_test129", "25718"},{"sm_test130", "25719"},
							{"sm_test131", "25720"},{"sm_test132", "25721"},{"sm_test133", "25722"},{"sm_test134", "25723"},{"sm_test135", "25724"},
							{"sm_test136", "25725"},{"sm_test137", "25726"},{"sm_test138", "25727"},{"sm_test139", "25728"},{"sm_test140", "25729"},
							{"sm_test141", "25730"},{"sm_test142", "25731"},{"sm_test143", "25732"}
							};
	
	
	String[][] testUsers3_7 = {{"sm_test200", "26024"}, {"sm_test201", "26025"}, {"sm_test202", "26026"}, {"sm_test203", "26027"}, {"sm_test204", "26028"},
			{"sm_test205", "26029"}, {"sm_test206", "26030"}, {"sm_test207", "26031"}, {"sm_test208", "26032"}, {"sm_test209", "26033"},
			{"sm_test210", "26034"}, {"sm_test211", "26035"}, {"sm_test212", "26036"}, {"sm_test213", "26037"}, {"sm_test214", "26038"},
			{"sm_test215", "26039"}, {"sm_test216", "26040"}, {"sm_test217", "26041"}, {"sm_test218", "26042"}, {"sm_test219", "26043"},
			{"sm_test220", "26044"}, {"sm_test221", "26045"}, {"sm_test222", "26046"}, {"sm_test223", "26047"}, {"sm_test224", "26048"},
			{"sm_test225", "26049"}, {"sm_test226", "26050"}, {"sm_test227", "26051"}, {"sm_test228", "26052"}, {"sm_test229", "26053"},
			{"sm_test230", "26054"}, {"sm_test231", "26055"}, {"sm_test232", "26056"}, {"sm_test233", "26057"}, {"sm_test234", "26058"},
			{"sm_test235", "26059"}, {"sm_test236", "26060"}, {"sm_test237", "26061"}, {"sm_test238", "26062"}, {"sm_test239", "26063"},
			{"sm_test240", "26064"}, {"sm_test241", "26065"}, {"sm_test242", "26066"}, {"sm_test243", "26067"}, {"sm_test244", "26068"},
			{"sm_test245", "26069"}, {"sm_test246", "26070"}, {"sm_test247", "26071"}, {"sm_test248", "26072"}, {"sm_test249", "26073"},
			{"sm_test250", "26074"}, {"sm_test251", "26075"}, {"sm_test252", "26076"}, {"sm_test253", "26077"}, {"sm_test254", "26078"},
			{"sm_test255", "26079"}, {"sm_test256", "26080"}, {"sm_test257", "26081"}, {"sm_test258", "26082"}, {"sm_test259", "26083"},
			{"sm_test260", "26084"}, {"sm_test261", "26085"}, {"sm_test262", "26086"}, {"sm_test263", "26087"}, {"sm_test264", "26088"},
			{"sm_test265", "26089"}, {"sm_test266", "26090"}, {"sm_test267", "26091"}, {"sm_test268", "26092"}, {"sm_test269", "26093"},
			{"sm_test270", "26094"}, {"sm_test271", "26095"}, {"sm_test272", "26096"}, {"sm_test273", "26097"}, {"sm_test274", "26098"},
			{"sm_test275", "26099"}, {"sm_test276", "26100"}, {"sm_test277", "26101"}, {"sm_test278", "26102"}, {"sm_test279", "26103"},
			{"sm_test280", "26104"}, {"sm_test281", "26105"}, {"sm_test282", "26106"}, {"sm_test283", "26107"}, {"sm_test284", "26108"},
			{"sm_test285", "26109"}, {"sm_test286", "26110"}, {"sm_test287", "26111"}, {"sm_test288", "26112"}, {"sm_test289", "26113"},
			{"sm_test290", "26114"}, {"sm_test291", "26115"}, {"sm_test292", "26116"}, {"sm_test293", "26117"}, {"sm_test294", "26118"},
			{"sm_test295", "26119"}, {"sm_test296", "26120"}, {"sm_test297", "26121"}, {"sm_test298", "26122"}, {"sm_test299", "26123"}};
	
	double[] moneyArray = {25, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
	
	//int[] betTimesArray = {3, 5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110};
	
	int[] betTimesArray = {3, 4, 5, 6, 7, 8, 9, 10, 20, 30, 40, 50};
	
	public XyftBettingTest(String name) {
		super(name);
	}
	
	public static void main(String[] args) {
		XyftBettingTest test = new XyftBettingTest("");
		try {
			test.testBetting_xyft_dwd();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testBetting_xyft_dwd() throws Exception{
		String lottoType = "xyft";
		String playType = "227";
		
		int userIndx = 0;
		Map<String, UserInfoPerformance[]> users = new HashMap<>();
		Map<String, UserInfoPerformance[]> users3_7 = new HashMap<>();
		Map<String, Issue> issueNum = new HashMap<>();
		Map<String, Issue> issueNum3_7 = new HashMap<>();
		
		for(int i = 0; i < moneyArray.length; i++) {
			for(int ii = 0; ii< betTimesArray.length; ii++) {
				UserInfoPerformance userPair[] = new UserInfoPerformance[2];
				if(userIndx >= testUsers.length) {
					continue;
				}
				
				String userName = testUsers[userIndx][0];
				String wallet = testUsers[userIndx][1];
				int betTimes = betTimesArray[ii];
				double betAmount = moneyArray[i];
				UserInfoPerformance user = new UserInfoPerformance();
				user.setUserName(userName);
				user.setBetAmount(betAmount);
				user.setMaxCounter(betTimes);
				user.setPassword("111111");
				user.setWallet(Integer.parseInt(wallet));
				
				userPair[0] = user;
				users.put(userName, userPair);
				
				userIndx++;
			}
		}
		
		
		for(int i = 0; i < testUsers3_7.length/2; i++) {
			UserInfoPerformance userPair[] = new UserInfoPerformance[2];
			String userName = testUsers3_7[i][0];
			String wallet = testUsers3_7[i][1];
			//int betTimes = betTimesArray[ii];
			//double betAmount = moneyArray[i];
			UserInfoPerformance user = new UserInfoPerformance();
			user.setUserName(userName);
			//user.setBetAmount(betAmount);
			user.setMaxCounter(5);
			user.setPassword("111111");
			user.setWallet(Integer.parseInt(wallet));
			
			UserInfoPerformance userPartner = new UserInfoPerformance();
			userPartner.setUserName(testUsers3_7[testUsers3_7.length/2 + i][0]);
			//user.setBetAmount(betAmount);
			//user.setMaxCounter(betTimes);
			userPartner.setPassword("111111");
			userPartner.setWallet(Integer.parseInt(testUsers3_7[testUsers3_7.length/2 + i][1]));
			
			userPair[0] = user;
			userPair[1] = userPartner;
			
			users3_7.put(userName, userPair);
			
			//break;
		}
		
		while(true) {
			//TODO 暂时注销
			/*if(hasNewIssue(users, issueNum, lottoType)) {
				Issue currIssue = issueNum.get("issueNumber");
				Issue lastIssue = issueNum.get("lastIsssue");
				
				betting(users, currIssue, lastIssue, lottoType);
			}*/
			
			if(hasNewIssue_3_7(users3_7, issueNum3_7, lottoType)) {
				Issue currIssue = issueNum3_7.get("issueNumber");
				Issue lastIssue = issueNum3_7.get("lastIsssue");
				
				betting_3_7(users3_7, currIssue, lastIssue, lottoType, playType);
			}
			
			Thread.sleep(1000);
		}
		
	}
	
	private boolean hasNewIssue_3_7(Map<String, UserInfoPerformance[]> users, Map<String, Issue> issueNum,
			String lottoType) {

		String pwd = "111111";
		String clientId = "lottery-client";
		String userName = "sm_user001";
		
		
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			Map<String, Object> ret = queryCurrIssue(token, lottoType);
			int counter_ = 0;
			while((ret == null || ret.size() == 0) 
					&& counter_ <= 200) {
				counter_++;
				
				ret = queryCurrIssue(token, lottoType);
			}
			
			if(ret == null || ret.size() == 0) {
				Assert.fail("Can't obtain the current issue!!!!");
			}
			
			Issue currIssue = (Issue)ret.get("currIssue");
			
			if(currIssue != null) {
				if(issueNum.get("issueNumber") != null) {
					if(currIssue.getId().intValue() != issueNum.get("issueNumber").getId().intValue()) {
						
						Long downCounter = currIssue.getDownCounter();
						
						if(downCounter.intValue() > 180) {
							return false;
						}
						
						
						issueNum.put("lastIsssue", issueNum.get("issueNumber"));
						issueNum.put("issueNumber", currIssue);
						return true;
					}else {
						return false;
					}
				}else {
					issueNum.put("issueNumber", currIssue);
					
					return true;
				}
			}else {
				return false;
			}
		}catch(Exception ex) {
			
		}finally {
			if(bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				if(!StringUtils.isBlank(token)) {
					System.out.println(String.format("user name %s ,   token   %s", userName,  token));
					logout(token);					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	
	}


	

	private void betting_3_7(Map<String, UserInfoPerformance[]> users, Issue currIssue, Issue lastIssue,
			String lottoType, String playType) throws Exception {
		
		Iterator<String> keys = users.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			UserInfoPerformance[] userPairs = users.get(key);
									
			
			boolean isDayChanged = isDayChanged(currIssue, lastIssue);
			if(!isDayChanged 
					&& userPairs[0].getCounter() >= userPairs[0].getMaxCounter()) {
				continue;
			}
			
			if(isDayChanged) {
				userPairs[0].setCounter(0);
			}
			
			int nextCounter = userPairs[0].getCounter() + 1;
			userPairs[0].setCounter(nextCounter);
			ThreadPoolManager.getInstance().exeThread(new Runnable() {
				@Override
				public void run() {
					String clientId = "lottery-client";
					String token = null;
					ObjectMapper mapper = new ObjectMapper();
					List<Integer> bettingNums = new ArrayList<>();
					List<Integer> bettingNumsPair = null;
					
					
					Double betingAmount = null;
					
					try {
						
						Double balance = queryBalance(userPairs[0]);
						if(balance <= 0 || balance >= 3900) {
							return ;
						}
						
						betingAmount = MathUtil.divide(balance, 3, 2);
						
						Double balancePair = queryBalance(userPairs[1]);
						
						if(betingAmount > MathUtil.divide(balancePair, 7, 2)) {
							betingAmount = MathUtil.divide(balancePair, 7, 2);
						}
						
						if(betingAmount <= 0) {
							return ;
						}
						
						betingAmount = new Double(betingAmount.intValue());
						
						System.out.println(String.format("%s balance is %s   , %s balance is %s", 
								userPairs[0].getUserName(), 
								String.valueOf(balance), 
								userPairs[1].getUserName(), 
								String.valueOf(balancePair)));
						ArrayNode array = mapper.createArrayNode();
						ArrayNode arrayPair = mapper.createArrayNode();
						
						
						int raceLane = new Random().nextInt(10);
						int betNum = new Random().nextInt(10) + 1;
						int betNum1 = new Random().nextInt(10) + 1;
						int betNum2 = new Random().nextInt(10) + 1;
						
						while(betNum1 == betNum) {
							Thread.sleep(1000);
							betNum1 = new Random().nextInt(10) + 1;
						}
						
						while(betNum2 == betNum
								|| betNum2 == betNum1) {
							Thread.sleep(1000);
							betNum2 = new Random().nextInt(10) + 1;
						}
						
						
						String betNumTemplate = dwdBettingNumbers[raceLane];											
						
						bettingNums.add(betNum);
						bettingNums.add(betNum1);
						bettingNums.add(betNum2);
						bettingNumsPair = producePairNum(bettingNums);
						
						
						produceBettingRec(currIssue, betingAmount, array, bettingNums,
								betNumTemplate, playType);
						
						
						produceBettingRec(currIssue, betingAmount, arrayPair, bettingNumsPair,
								betNumTemplate, playType);
						
												
						token = queryToken(userPairs[0].getUserName(), userPairs[0].getPassword(), clientId);
						sendBettingRequest(lottoType, userPairs[0], token, mapper, array);
						
						
						/////////////////////////////
						if(!StringUtils.isBlank(token)) {
							logout(token);					
						}
						token = queryToken(userPairs[1].getUserName(), userPairs[1].getPassword(), clientId);
						sendBettingRequest(lottoType, userPairs[1], token, mapper, arrayPair);
						
						if(!StringUtils.isBlank(token)) {
							logout(token);					
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						//throw e;
					}finally {
						try {
							if(!StringUtils.isBlank(token)) {
								//logout(token);					
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}

				private void sendBettingRequest(String lottoType, UserInfoPerformance userPairs,
						String token, ObjectMapper mapper, ArrayNode array) throws JsonProcessingException, IOException,
						SAXException, JsonParseException, JsonMappingException {
					ByteArrayInputStream bis = null;
					WebRequest request = null;
					WebConversation wc = null;
					String reqURL = server + "/lotteries/" +lottoType + "/bet/zh/1/wallet/" + userPairs.getWallet();
					
					//System.out.println(String.format("reqURL   %s", reqURL));
					
					try {
						bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
						
						request = new PostMethodWebRequest(reqURL,
								bis,
								MediaType.APPLICATION_JSON_VALUE);
						
						wc = new WebConversation();
						
						request.setHeaderField("Authorization", "Bearer " + token);
						
						WebResponse response = wc.sendRequest(request);
						
						int  status = response.getResponseCode();
						
						Assert.assertEquals(HttpServletResponse.SC_OK, status);
						String result = response.getText();
						
						Map<String, Object> retItems = null;
						
						retItems = mapper.readValue(result, HashMap.class);
						
						System.out.println(String.format("the reqURL is %s,\r\n  the response is %s", reqURL, result));
						
						Assert.assertNotNull(retItems);
						
						Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
						
					}catch(Exception ex) {
						
					}finally {
						if(bis != null) {
							bis.close();
						}
					}
				}

				private void produceBettingRec(Issue currIssue, Double betingAmount,
						ArrayNode array, List<Integer> bettingNums, String betNumTemplate, String playType) {
					
					for(Integer betNum : bettingNums) {
						String betNumStr = String.valueOf(betNum);
						if(betNumStr.length() == 1) {
							betNumStr = "0" + betNumStr;
						}
						//String betNumTemplate_ = betNumTemplate.replace("{betNum}", betNumStr);
												
						ObjectNode node1 = array.addObject();
						node1.putPOJO("issueId", currIssue.getId());
						node1.putPOJO("playType", playType);
						node1.putPOJO("times", betingAmount);
						node1.putPOJO("pattern", "1");
						node1.putPOJO("isZh", "0");
						node1.putPOJO("terminalType", "0");
						
						
						betNumStr = String.valueOf(betNum);
						if(betNumStr.length() == 1) {
							betNumStr = "0" + betNumStr;
						}
						String betNumTemplate1_ = betNumTemplate.replace("{betNum}", betNumStr);
						
						node1.putPOJO("betNum", betNumTemplate1_);						
					}
				}

				private List<Integer> producePairNum(List<Integer> bettingNums) {
					List<Integer> ret = new ArrayList<>();
					for(int i = 1; i < 11; i++) {
						boolean isIgnore = false;
						for(int ii = 0; ii < bettingNums.size(); ii++) {
							if(i == bettingNums.get(ii).intValue()) {
								isIgnore = true;
								break;
							}							
						}
						
						if(!isIgnore) {
							ret.add(i);
						}
					}
					return ret;
				}
			});
			
			
		}	
		
	}

	private Double queryBalance(UserInfoPerformance user) throws Exception {
		Double ret = null;
		ObjectMapper mapper = new ObjectMapper();
		UserInfo userInfo = null;
		String token = null;
		String clientId = "lottery-client";
		try {
			token = queryToken(user.getUserName(), user.getPassword(), clientId);
			userInfo = queryUserInfo(token);
			WebRequest request = new GetMethodWebRequest(server + "/wallet/queryByUserIdUserAccount?userId=" + userInfo.getId());
			
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
			
			List<Map<String, Object>> data = (List<Map<String, Object>>)retItems.get("data");
			Map<String, Object> userAcc = (Map<String, Object>)data.get(0);
			Integer accType = (Integer)userAcc.get("accType");
			if(accType.intValue() == 1) {
				ret = (Double)userAcc.get("balance");
			}else {
				 userAcc = (Map<String, Object>)data.get(1);
				 ret = (Double)userAcc.get("balance");
			}
			
			
		} catch (Exception e) {
			System.out.println(String.format("userName %s", userInfo.getUserName()));
			e.printStackTrace();
			throw e;
		}finally {
			if(!StringUtils.isBlank(token)) {
				logout(token);
			}
		}
		
		
		System.out.println(String.format("userName %s; balance %s", userInfo.getUserName(), ret));
		
		return ret;
	}

	private UserInfo queryUserInfo(String token) throws Exception {
		UserInfo userInfo = new UserInfo();
		ObjectMapper mapper = new ObjectMapper();
		//String token = null;
		String clientId = "lottery-client";
		
		try {
			WebRequest request = new GetMethodWebRequest(server + "/users/info");
			
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
			//{id=12680, realName=, userName=sm_test20, userId=, loginPwd=****, fundPwd=****, state=0, level=0, loginCount=0, failLoginCount=null, unlockTime=null, userType=5, superior=12641,0, rebate=0.2, platRebate=14.1, phoneNum=, qq=, wechat=, email=, isValidPhone=0, isValidEmail=0, regIp=112.206.193.179, createTime=1555502683000, creator=1}
			Integer userId = (Integer)data.get("id");
			String userName = (String)data.get("userName");
			userInfo.setId(userId);
			userInfo.setUserName(userName);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return userInfo;
	}

	boolean hasNewIssue(Map<String, UserInfoPerformance> users, Map<String, Issue> issueNum, String lottoType) {
		String pwd = null;
		String clientId = "lottery-client";
		Iterator<String> keys = users.keySet().iterator();
		String userName = null;
		while(keys.hasNext()) {
			userName = keys.next();
			pwd = users.get(userName).getPassword();
			break;
		}
		
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		ByteArrayInputStream bis = null;
		try {
			Map<String, Object> ret = queryCurrIssue(token, lottoType);
			int counter_ = 0;
			while((ret == null || ret.size() == 0) 
					&& counter_ <= 200) {
				counter_++;
				
				ret = queryCurrIssue(token, lottoType);
			}
			
			if(ret == null || ret.size() == 0) {
				Assert.fail("Can't obtain the current issue!!!!");
			}
			
			Issue currIssue = (Issue)ret.get("currIssue");
			
			if(currIssue != null) {
				if(issueNum.get("issueNumber") != null) {
					if(currIssue.getId().intValue() != issueNum.get("issueNumber").getId().intValue()) {
						issueNum.put("lastIsssue", issueNum.get("issueNumber"));
						issueNum.put("issueNumber", currIssue);
						return true;
					}else {
						return false;
					}
				}else {
					issueNum.put("issueNumber", currIssue);
					
					return true;
				}
			}else {
				return false;
			}
		}catch(Exception ex) {
			
		}finally {
			if(bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				if(!StringUtils.isBlank(token)) {
					logout(token);					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public void betting(Map<String, UserInfoPerformance> users, final Issue currIssue, final Issue lastIssue, String lottoType) throws Exception{
		
		
		Iterator<String> keys = users.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			UserInfoPerformance user = users.get(key);
			
			System.out.println(String.format("userName  %s,  betAmount  %s,  betTimes %s,  current counter %s", user.getUserName(),
					user.getBetAmount(), 
					user.getMaxCounter(),
					user.getCounter()));
			boolean isDayChanged = isDayChanged(currIssue, lastIssue);
			if(!isDayChanged 
					&& user.getCounter() >= user.getMaxCounter()) {
				continue;
			}
			
			if(isDayChanged) {
				user.setCounter(0);
			}
			
			int nextCounter = user.getCounter() + 1;
			user.setCounter(nextCounter);
			ThreadPoolManager.getInstance().exeThread(new Runnable() {
				@Override
				public void run() {
					String clientId = "lottery-client";
					String token = queryToken(user.getUserName(), user.getPassword(), clientId);
					ObjectMapper mapper = new ObjectMapper();
					ByteArrayInputStream bis = null;
					try {
						ArrayNode array = mapper.createArrayNode();
						
						ObjectNode node = array.addObject();
						node.putPOJO("issueId", currIssue.getId());
						node.putPOJO("playType", "227");
						node.putPOJO("times", new Double(user.getBetAmount()).intValue());
						node.putPOJO("pattern", "1");
						node.putPOJO("isZh", "0");
						node.putPOJO("terminalType", "0");
						
						int raceLane = new Random().nextInt(10);
						int betNum = new Random().nextInt(10) + 1;
						int betNum1 = new Random().nextInt(10) + 1;
						while(betNum1 == betNum) {
							Thread.sleep(1000);
							betNum1 = new Random().nextInt(10) + 1;
						}
						String betNumTemplate = dwdBettingNumbers[raceLane];
						String betNumStr = String.valueOf(betNum);
						if(betNumStr.length() == 1) {
							betNumStr = "0" + betNumStr;
						}
						String betNumTemplate_ = betNumTemplate.replace("{betNum}", betNumStr);
						
						node.putPOJO("betNum", betNumTemplate_);
						
						ObjectNode node1 = array.addObject();
						node1.putPOJO("issueId", currIssue.getId());
						node1.putPOJO("playType", "227");
						node1.putPOJO("times", new Double(user.getBetAmount()).intValue());
						node1.putPOJO("pattern", "1");
						node1.putPOJO("isZh", "0");
						node1.putPOJO("terminalType", "0");
						
						
						betNumStr = String.valueOf(betNum1);
						if(betNumStr.length() == 1) {
							betNumStr = "0" + betNumStr;
						}
						String betNumTemplate2_ = betNumTemplate.replace("{betNum}", betNumStr);
						
						node1.putPOJO("betNum", betNumTemplate2_);
						
						bis = new ByteArrayInputStream(mapper.writeValueAsBytes(array));
						/*WebRequest request = new PostMethodWebRequest("http://localhost:8080/lotteries/" +lottoType + "/bet/zh/1/wallet/" + user.getWallet(),
								bis,
								MediaType.APPLICATION_JSON_VALUE);*/
						
						/*WebRequest request = new PostMethodWebRequest("http://miss369.com/lottery-api/lotteries/xyft/bet/zh/1/wallet/15",
										bis,
										MediaType.APPLICATION_JSON_VALUE);*/
						
						WebRequest request = new PostMethodWebRequest(server + "/lotteries/" +lottoType + "/bet/zh/1/wallet/" + user.getWallet(),
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
						
						//System.out.println(String.format("userName  %s,  betAmount  %s,  betTimes %s, counter  %s", userName, betAmount, betTimes, counter));
						
						//counter++;
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
						
						try {
							if(!StringUtils.isBlank(token)) {
								logout(token);					
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					
					//}
					
				}
			});
			
			
		}
				//exe.start();
			//}
			
			
			
		//}
	}
	
		
	
	private boolean isDayChanged(Issue currIssue, Issue lastIssue) {
		if(lastIssue == null) {
			return false;
		}
		
		String lastIssueNum = lastIssue.getIssueNum();
		String currIssueNum = currIssue.getIssueNum();
		String lastIssueNumSlices = lastIssueNum.split("-")[0];
		String currIssueNumSlices = currIssueNum.split("-")[0];
		
		if(!lastIssueNumSlices.equals(currIssueNumSlices)) {
			return true;
		}
		return false;
	}

	public Map<String, Object> queryCurrIssue(String token, String lottoType) throws Exception{
		Map<String, Object> ret = new HashMap<String, Object>();
		List<PlayType> playTypes = new ArrayList<>();
		Issue currIssue = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			//WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/" + lottoType + "/betting-issue");
			//WebRequest request = new GetMethodWebRequest("http://miss369.com/lottery-api/lotteries/" + lottoType + "/betting-issue");
			WebRequest request = new GetMethodWebRequest(server + "/lotteries/" + lottoType + "/betting-issue");
			
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
	
	
	public void logout(String token) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		try {			
			WebRequest request = new GetMethodWebRequest(server + "/security/logout");
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
	
	
	class UserInfoPerformance {
		String userName;
		
		String password;
		
		int counter;
		
		int maxCounter ;
		
		double betAmount;

		int wallet ;
		
		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public int getCounter() {
			return counter;
		}

		public void setCounter(int counter) {
			this.counter = counter;
		}

		public int getMaxCounter() {
			return maxCounter;
		}

		public void setMaxCounter(int maxCounter) {
			this.maxCounter = maxCounter;
		}

		public double getBetAmount() {
			return betAmount;
		}

		public void setBetAmount(double betAmount) {
			this.betAmount = betAmount;
		}

		public int getWallet() {
			return wallet;
		}

		public void setWallet(int wallet) {
			this.wallet = wallet;
		}
	}
}