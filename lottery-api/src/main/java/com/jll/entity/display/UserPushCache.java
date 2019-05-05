package com.jll.entity.display;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jll.entity.UserPushConfig;

public class UserPushCache implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1914141843975063676L;

	List<UserPushConfig> userPushConfig = null;
	
	Map<String, Object> lotteryPushStatus = new HashMap<>();
	
	/*int currPushTimes;
	
	String pushNum;
	
	Double betAmount;*/

	public Map<String, Object> getLotteryPushStatus() {
		return lotteryPushStatus;
	}

	public void setLotteryPushStatus(Map<String, Object> lotteryPushStatus) {
		this.lotteryPushStatus = lotteryPushStatus;
	}

	public List<UserPushConfig> getUserPushConfig() {
		return userPushConfig;
	}

	public void setUserPushConfig(List<UserPushConfig> userPushConfig) {
		this.userPushConfig = userPushConfig;
	}
	
	
	
	
}
