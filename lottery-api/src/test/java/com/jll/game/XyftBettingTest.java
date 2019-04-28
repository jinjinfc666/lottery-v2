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
import com.jll.common.threadpool.ThreadPoolManager;
import com.jll.common.utils.StringUtils;
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
import com.terran4j.commons.util.Maths;


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
	
	
	double[] moneyArray = {25, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
	
	int[] betTimesArray = {3, 5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110};
	
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
		//int maxTimes = 10000;
		//int counter = 0;
		String lottoType = "xyft";
		int userIndx = 0;
		Map<String, UserInfoPerformance> users = new HashMap<>();
		Map<String, Issue> issueNum = new HashMap<>();
		
		for(int i = 0; i < moneyArray.length; i++) {
			for(int ii = 0; ii< betTimesArray.length; ii++) {
				
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
				users.put(userName, user);
				
				userIndx++;
			}
		}
		
		while(true) {
			if(hasNewIssue(users, issueNum, lottoType)) {
				Issue currIssue = issueNum.get("issueNumber");
				Issue lastIssue = issueNum.get("lastIsssue");
				/*Issue issue = null;
				Iterator<String> issueNumKeys = issueNum.keySet().iterator();
				while(issueNumKeys.hasNext()) {
					String key = issueNumKeys.next();
					issue = issueNum.get(key);
					break;
				}*/
				
				betting(users, currIssue, lastIssue, lottoType);
			}
			
			Thread.sleep(1000);
		}
		
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