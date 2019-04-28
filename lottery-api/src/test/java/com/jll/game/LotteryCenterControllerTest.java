package com.jll.game;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.springframework.http.MediaType;

import com.ehome.test.ControllerJunitBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jll.common.constants.Message;
import com.jll.entity.Issue;
import com.jll.entity.PlayType;
import com.jll.game.playtype.PlayTypeFacade;
import com.jll.game.playtypefacade.PlayTypeFactory;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class LotteryCenterControllerTest extends ControllerJunitBase {

	public LotteryCenterControllerTest(String name) {
		super(name);
	}

	public void ItestQueryPrizeRate_cqssc() throws Exception {
		String lottoType = "cqssc";
		long maxWaittingTime = 17000;

		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";

		String playTypeName = null;
		Integer playTypeId = null;

		Map<String, Integer> currIndx = new HashMap<>();
		Map<String, String> tokens = new HashMap<>();

		try {
			Thread.sleep(60 * 1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		Thread.sleep(maxWaittingTime);
		String token = queryToken(userName, pwd, clientId);
		tokens.put("token", token);

		Map<String, Object> ret = queryCurrIssue(token, lottoType);
		int queryIssueCounter = 0;
		while ((ret == null || ret.size() == 0 || ret.get("currIssue") == null) && queryIssueCounter <= 1000) {
			queryIssueCounter++;

			ret = queryCurrIssue(token, lottoType);
			Thread.sleep(500);
		}

		if (ret == null || ret.size() == 0) {
			Assert.fail("Can't obtain the current issue!!!!");
		}

		Issue currIssue = (Issue) ret.get("currIssue");
		Assert.assertNotNull(currIssue);
		List<PlayType> playTypes = (List<PlayType>) ret.get("playTypes");

		if (playTypes != null && playTypes.size() > 0) {
			for (PlayType playType : playTypes) {
				System.out.println(String.format("current Indx  %s,   size   %s", currIndx, playTypes.size()));

				if (playType.getPtName().equals("fs")) {
					playTypeName = playType.getClassification() + "/fs";
				} else if (playType.getPtName().equals("ds")) {
					playTypeName = playType.getClassification() + "/ds";
				} else {
					playTypeName = playType.getClassification() + "/" + playType.getPtName();
				}

				PlayTypeFacade playTypeFacade = PlayTypeFactory.getInstance().getPlayTypeFacade(playTypeName);

				if (playTypeFacade == null) {
					return;
				}

				playTypeId = playType.getId();

				WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/" + lottoType
						+ "/play-type/" + String.valueOf(playTypeId) + "/prize-rates");
				WebConversation wc = new WebConversation();

				token = tokens.get("token");
				request.setHeaderField("Authorization", "bearer " + token);

				WebResponse response = wc.sendRequest(request);

				int status = response.getResponseCode();

				Assert.assertEquals(HttpServletResponse.SC_OK, status);
				String result = response.getText();

				System.out.println(String.format("PlayType %s,   single betting prize   %s", playTypeName, result));

			}
		}
	}

	public void ItestQueryPrizeRate_gd11x5() throws Exception {
		String lottoType = "gd11x5";
		long maxWaittingTime = 17000;

		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";

		String playTypeName = null;
		Integer playTypeId = null;

		Map<String, Integer> currIndx = new HashMap<>();
		Map<String, String> tokens = new HashMap<>();

		try {
			Thread.sleep(60 * 1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		Thread.sleep(maxWaittingTime);
		String token = queryToken(userName, pwd, clientId);
		tokens.put("token", token);

		Map<String, Object> ret = queryCurrIssue(token, lottoType);
		int queryIssueCounter = 0;
		while ((ret == null || ret.size() == 0 || ret.get("currIssue") == null) && queryIssueCounter <= 1000) {
			queryIssueCounter++;

			ret = queryCurrIssue(token, lottoType);
			Thread.sleep(500);
		}

		if (ret == null || ret.size() == 0) {
			Assert.fail("Can't obtain the current issue!!!!");
		}

		Issue currIssue = (Issue) ret.get("currIssue");
		Assert.assertNotNull(currIssue);
		List<PlayType> playTypes = (List<PlayType>) ret.get("playTypes");

		if (playTypes != null && playTypes.size() > 0) {
			for (PlayType playType : playTypes) {
				System.out.println(String.format("current Indx  %s,   size   %s", currIndx, playTypes.size()));

				if (playType.getPtName().equals("fs")) {
					playTypeName = playType.getClassification() + "/fs";
				} else if (playType.getPtName().equals("ds")) {
					playTypeName = playType.getClassification() + "/ds";
				} else {
					playTypeName = playType.getClassification() + "/" + playType.getPtName();
				}

				PlayTypeFacade playTypeFacade = PlayTypeFactory.getInstance().getPlayTypeFacade(playTypeName);

				if (playTypeFacade == null) {
					return;
				}

				playTypeId = playType.getId();

				WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/" + lottoType
						+ "/play-type/" + String.valueOf(playTypeId) + "/prize-rates");
				WebConversation wc = new WebConversation();

				token = tokens.get("token");
				request.setHeaderField("Authorization", "bearer " + token);

				WebResponse response = wc.sendRequest(request);

				int status = response.getResponseCode();

				Assert.assertEquals(HttpServletResponse.SC_OK, status);
				String result = response.getText();

				System.out.println(String.format("PlayType %s,   single betting prize   %s", playTypeName, result));

			}
		}
	}
	
	
	
	public void ItestQueryPrizeRate_pk10() throws Exception {
		String lottoType = "bjpk10";
		long maxWaittingTime = 17000;

		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";

		String playTypeName = null;
		Integer playTypeId = null;

		Map<String, Integer> currIndx = new HashMap<>();
		Map<String, String> tokens = new HashMap<>();

		try {
			Thread.sleep(60 * 1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		Thread.sleep(maxWaittingTime);
		String token = queryToken(userName, pwd, clientId);
		tokens.put("token", token);

		Map<String, Object> ret = queryCurrIssue(token, lottoType);
		int queryIssueCounter = 0;
		while ((ret == null || ret.size() == 0 || ret.get("currIssue") == null) && queryIssueCounter <= 1000) {
			queryIssueCounter++;

			ret = queryCurrIssue(token, lottoType);
			Thread.sleep(500);
		}

		if (ret == null || ret.size() == 0) {
			Assert.fail("Can't obtain the current issue!!!!");
		}

		Issue currIssue = (Issue) ret.get("currIssue");
		Assert.assertNotNull(currIssue);
		List<PlayType> playTypes = (List<PlayType>) ret.get("playTypes");

		if (playTypes != null && playTypes.size() > 0) {
			for (PlayType playType : playTypes) {
				System.out.println(String.format("current Indx  %s,   size   %s", currIndx, playTypes.size()));

				if (playType.getPtName().equals("fs")) {
					playTypeName = playType.getClassification() + "/fs";
				} else if (playType.getPtName().equals("ds")) {
					playTypeName = playType.getClassification() + "/ds";
				} else {
					playTypeName = playType.getClassification() + "/" + playType.getPtName();
				}

				PlayTypeFacade playTypeFacade = PlayTypeFactory.getInstance().getPlayTypeFacade(playTypeName);

				if (playTypeFacade == null) {
					return;
				}

				playTypeId = playType.getId();

				WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/" + lottoType
						+ "/play-type/" + String.valueOf(playTypeId) + "/prize-rates");
				WebConversation wc = new WebConversation();

				token = tokens.get("token");
				request.setHeaderField("Authorization", "bearer " + token);

				WebResponse response = wc.sendRequest(request);

				int status = response.getResponseCode();

				Assert.assertEquals(HttpServletResponse.SC_OK, status);
				String result = response.getText();

				System.out.println(String.format("PlayType %s,   single betting prize   %s", playTypeName, result));

			}
		}
	}
	
	
	public void ItestQueryPrizeRate_cqssc_demoPlayer() throws Exception {
		String lottoType = "cqssc";
		long maxWaittingTime = 17000;

		Map demoPlayer = createDemoPlayer();
		String userName = (String)demoPlayer.get("userName");
		String pwd = (String)demoPlayer.get("default_password");
		
		String clientId = "lottery-client";

		String playTypeName = null;
		Integer playTypeId = null;

		Map<String, Integer> currIndx = new HashMap<>();
		Map<String, String> tokens = new HashMap<>();

		try {
			Thread.sleep(60 * 1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		Thread.sleep(maxWaittingTime);
		String token = queryToken(userName, pwd, clientId);
		tokens.put("token", token);

		Map<String, Object> ret = queryCurrIssue(token, lottoType);
		int queryIssueCounter = 0;
		while ((ret == null || ret.size() == 0 || ret.get("currIssue") == null) && queryIssueCounter <= 1000) {
			queryIssueCounter++;

			ret = queryCurrIssue(token, lottoType);
			Thread.sleep(500);
		}

		if (ret == null || ret.size() == 0) {
			Assert.fail("Can't obtain the current issue!!!!");
		}

		Issue currIssue = (Issue) ret.get("currIssue");
		Assert.assertNotNull(currIssue);
		List<PlayType> playTypes = (List<PlayType>) ret.get("playTypes");

		if (playTypes != null && playTypes.size() > 0) {
			for (PlayType playType : playTypes) {
				System.out.println(String.format("current Indx  %s,   size   %s", currIndx, playTypes.size()));

				if (playType.getPtName().equals("fs")) {
					playTypeName = playType.getClassification() + "/fs";
				} else if (playType.getPtName().equals("ds")) {
					playTypeName = playType.getClassification() + "/ds";
				} else {
					playTypeName = playType.getClassification() + "/" + playType.getPtName();
				}

				PlayTypeFacade playTypeFacade = PlayTypeFactory.getInstance().getPlayTypeFacade(playTypeName);

				if (playTypeFacade == null) {
					return;
				}

				playTypeId = playType.getId();

				WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/" + lottoType
						+ "/play-type/" + String.valueOf(playTypeId) + "/prize-rates");
				WebConversation wc = new WebConversation();

				token = tokens.get("token");
				request.setHeaderField("Authorization", "bearer " + token);

				WebResponse response = wc.sendRequest(request);

				int status = response.getResponseCode();

				Assert.assertEquals(HttpServletResponse.SC_OK, status);
				String result = response.getText();

				System.out.println(String.format("PlayType %s,   single betting prize   %s", playTypeName, result));

			}
		}
	}
	
	
	/**
	 * 非代理用户 初始化 奖金模式以及平台点数 
	 * @throws Exception
	 */
	public void ItestQueryPrizeTemplate_nonAgent_init() throws Exception{
		String userName = "test001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/prize-templates");
			
			WebConversation wc = new WebConversation();
						
			request.setHeaderField("Authorization", "Bearer " + token);
			
			WebResponse response = wc.sendRequest(request);
			
			int  status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);

			Assert.assertEquals(Message.status.FAILED.getCode(), retItems.get(Message.KEY_STATUS));
			Assert.assertEquals(Message.Error.ERROR_USER_INVALID_USER_TYPE.getCode(), retItems.get(Message.KEY_ERROR_CODE));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			
		}
	}
	
	/**
	 * 代理用户 初始化 奖金模式以及平台点数 
	 * @throws Exception
	 */
	public void ItestQueryPrizeTemplate_Agent_init() throws Exception{
		//agent001是一个代理用户 平台点数是 13.8 
		String userName = "agent001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/prize-templates");
			
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
			
			retItems = (Map<String, Object>)retItems.get("data");
			
			Assert.assertEquals(0.0, retItems.get("currR"));
			Assert.assertEquals(1700, retItems.get("currT"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			
		}
	}
	
	/**
	 * 后台用户 初始化 奖金模式以及平台点数
	 * 不指定代理用户 
	 * @throws Exception
	 */
	public void ItestQueryPrizeTemplate_admin_init_no_user() throws Exception{
		String userName = "admin";
		String pwd = "test001";
		String clientId = "lottery-admin";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/prize-templates");
			
			WebConversation wc = new WebConversation();
						
			request.setHeaderField("Authorization", "Bearer " + token);
			
			WebResponse response = wc.sendRequest(request);
			
			int  status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);

			Assert.assertEquals(Message.status.FAILED.getCode(), retItems.get(Message.KEY_STATUS));
			
			Assert.assertEquals(Message.Error.ERROR_USER_INVALID_USER_TYPE.getCode(), retItems.get(Message.KEY_ERROR_CODE)); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			
		}
	}
	
	
	/**
	 * 后台用户 初始化 奖金模式以及平台点数
	 * 指定玩家用户 
	 * @throws Exception
	 */
	public void ItestQueryPrizeTemplate_admin_init_player() throws Exception{
		String userName = "admin";
		String pwd = "test001";
		String clientId = "lottery-admin";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/prize-templates");
			
			WebConversation wc = new WebConversation();
						
			request.setHeaderField("Authorization", "Bearer " + token);
			
			//14 是玩家用户ID 
			request.setParameter("superior", "13");
			WebResponse response = wc.sendRequest(request);
			
			int  status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);

			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
			
			retItems = (Map<String, Object>)retItems.get("data");
			
			Assert.assertEquals(0.0, retItems.get("currR"));
			Assert.assertEquals(1700, retItems.get("currT"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			
		}
	}
	
	
	
	
	
	/**
	 * 代理用户通过当前的奖金模式查询平台点数 
	 * @throws Exception
	 */
	public void ItestQueryPrizeTemplate_agent_currT() throws Exception{
		String userName = "agent001";
		String pwd = "test001";
		String clientId = "lottery-client";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/prize-templates");
			
			WebConversation wc = new WebConversation();
						
			request.setHeaderField("Authorization", "Bearer " + token);
			
			//1800  是玩家的当前奖金模式 
			request.setParameter("currT", "1800");
			
			WebResponse response = wc.sendRequest(request);
			
			int  status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);

			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
			
			retItems = (Map)retItems.get("data");
			Assert.assertEquals(4.6, retItems.get("currR"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			
		}
	}
	
	/**
	 * 代理用户通过当前的平台点数查询奖金模式 
	 * @throws Exception
	 */
	public void testQueryPrizeTemplate_agent_currR() throws Exception{
		/*String userName = "agent001";
		String pwd = "test001";*/
		
		String userName = "general_agency";
		String pwd = "111111";
		String clientId = "lottery-client"; 
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/prize-templates");
			
			WebConversation wc = new WebConversation();
						
			request.setHeaderField("Authorization", "Bearer " + token);
			
			//5  是玩家的当前平台点数 
			request.setParameter("currR", "14.8");
			
			WebResponse response = wc.sendRequest(request);
			
			int  status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);

			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
			
			retItems = (Map<String, Object>)retItems.get("data");
			Assert.assertEquals(1808, retItems.get("currT"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			
		}
	}
	
	
	/**
	 * 代理用户通过当前的奖金模式查询平台点数 
	 * @throws Exception
	 */
	public void ItestQueryPrizeTemplate_admin_currT() throws Exception{
		String userName = "admin";
		String pwd = "test001";
		String clientId = "lottery-admin";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/prize-templates");
			
			WebConversation wc = new WebConversation();
						
			request.setHeaderField("Authorization", "Bearer " + token);
		
			//1800  是玩家的当前奖金模式 
			request.setParameter("currT", "1800");
			request.setParameter("superior", "13");
			
			
			WebResponse response = wc.sendRequest(request);
			
			int  status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);

			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
			
			retItems = (Map)retItems.get("data");
			
			Assert.assertEquals(4.6, retItems.get("currR"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			
		}
	}
	
	/**
	 * 代理用户通过当前的平台点数查询奖金模式 
	 * @throws Exception
	 */
	public void ItestQueryPrizeTemplate_admin_currR() throws Exception{
		String userName = "admin";
		String pwd = "test001";
		String clientId = "lottery-admin";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/prize-templates");
			
			WebConversation wc = new WebConversation();
						
			request.setHeaderField("Authorization", "Bearer " + token);
			
			//5  是玩家的当前平台点数 
			request.setParameter("currR", "5");
			request.setParameter("superior", "13");
			
			WebResponse response = wc.sendRequest(request);
			
			int  status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);

			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));
			
			retItems = (Map)retItems.get("data");
			
			Assert.assertEquals(1808, retItems.get("currT"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			
		}
	}
	
	/**
	 * 代理用户通过当前的平台点数查询奖金模式 
	 * @throws Exception
	 */
	public void ItestQueryPrizeTemplate_admin_currR_bigger() throws Exception{
		String userName = "admin";
		String pwd = "test001";
		String clientId = "lottery-admin";
		String token = queryToken(userName, pwd, clientId);
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			WebRequest request = new GetMethodWebRequest("http://localhost:8080/lotteries/prize-templates");
			
			WebConversation wc = new WebConversation();
						
			request.setHeaderField("Authorization", "Bearer " + token);
			
			//5  是玩家的当前平台点数 
			request.setParameter("currR", "14");
			request.setParameter("superior", "13");
			
			WebResponse response = wc.sendRequest(request);
			
			int  status = response.getResponseCode();
			
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();
			
			Map<String, Object> retItems = null;
			
			retItems = mapper.readValue(result, HashMap.class);
			
			Assert.assertNotNull(retItems);

			Assert.assertEquals(Message.status.FAILED.getCode(), retItems.get(Message.KEY_STATUS));
			
			Assert.assertEquals(Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode(), retItems.get(Message.KEY_ERROR_CODE));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			
		}
	}
	
	
	
	
	
	public Map<String, Object> queryCurrIssue(String token, String lottoType) throws Exception {
		Map<String, Object> ret = new HashMap<String, Object>();
		List<PlayType> playTypes = new ArrayList<>();
		Issue currIssue = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			WebRequest request = new GetMethodWebRequest(
					"http://localhost:8080/lotteries/" + lottoType + "/betting-issue");
			WebConversation wc = new WebConversation();

			request.setHeaderField("Authorization", "Bearer " + token);

			WebResponse response = wc.sendRequest(request);

			int status = response.getResponseCode();

			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();

			Map<String, Object> retItems = null;

			retItems = mapper.readValue(result, HashMap.class);

			Assert.assertNotNull(retItems);

			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));

			Map data = (Map) retItems.get("data");

			Map currIssueMap = (Map) data.get("currIssue");

			List<Map<String, Object>> playTypesMap = (List<Map<String, Object>>) data.get("playType");
			if (currIssueMap != null && currIssueMap.size() > 0) {
				// Long downCounter = (Long)((LinkedHashMap)currIssueMap).get(0);
				Integer downCounter = (Integer) currIssueMap.get("downCounter");
				Date endTime = new Date((Long) currIssueMap.get("endTime"));
				Integer id = (Integer) currIssueMap.get("id");
				String lotteryType = (String) currIssueMap.get("lotteryType");
				Date startTime = new Date((Long) currIssueMap.get("startTime"));
				Integer state = (Integer) currIssueMap.get("state");
				String issueNum = (String) currIssueMap.get("issueNum");
				currIssue = new Issue();

				currIssue.setDownCounter(new Long(downCounter.intValue()));
				currIssue.setEndTime(endTime);
				currIssue.setId(id);
				currIssue.setIssueNum(issueNum);
				currIssue.setLotteryType(lotteryType);
				currIssue.setStartTime(startTime);
				currIssue.setState(state);
			}

			for (Map<String, Object> temp : playTypesMap) {
				// id=1, lotteryType=cqssc, classification=qszx|前三直选, ptName=fs, ptDesc=复式,
				// state=1, mulSinFlag=1, isHidden=1, seq=1, createTime=1533895746000
				Integer id = (Integer) temp.get("id");
				String lotteryType = (String) temp.get("lotteryType");
				String classification = (String) temp.get("classification");
				String ptName = (String) temp.get("ptName");
				String ptDesc = (String) temp.get("ptDesc");
				Integer state = (Integer) temp.get("state");
				Integer mulSinFlag = (Integer) temp.get("mulSinFlag");
				Integer isHidden = (Integer) temp.get("isHidden");
				Integer seq = (Integer) temp.get("seq");
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

	
	public Map<String, Object> createDemoPlayer() throws Exception {
		Map data = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			WebRequest request = new GetMethodWebRequest(
					"http://localhost:8080/users/demo-players");
			WebConversation wc = new WebConversation();

			WebResponse response = wc.sendRequest(request);

			int status = response.getResponseCode();

			Assert.assertEquals(HttpServletResponse.SC_OK, status);
			String result = response.getText();

			Map<String, Object> retItems = null;

			retItems = mapper.readValue(result, HashMap.class);

			Assert.assertNotNull(retItems);

			Assert.assertEquals(Message.status.SUCCESS.getCode(), retItems.get(Message.KEY_STATUS));

			data = (Map) retItems.get("data");

			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return data;
	}
}