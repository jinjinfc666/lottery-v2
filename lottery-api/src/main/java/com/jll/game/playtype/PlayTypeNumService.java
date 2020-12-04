package com.jll.game.playtype;

import java.util.List;

import com.jll.entity.OrderInfo;
import com.jll.entity.PlayTypeNum;
import com.jll.entity.UserInfo;

public interface PlayTypeNumService {

	List<PlayTypeNum> queryPlayTypeNum(Long playTypeId);

	boolean isRateValid(List<OrderInfo> orders, UserInfo userInfo);

	void updateUserCurrMarket(String userId, String currMarket);

	void initPlayTYpeNumData();
}
