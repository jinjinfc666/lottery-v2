package com.jll.game.playtype;

import java.util.List;

import com.jll.entity.OrderInfo;
import com.jll.entity.PlayTypeNum;

public interface PlayTypeNumService {

	List<PlayTypeNum> queryPlayTypeNum(Long playTypeId);

	boolean isRateValid(List<OrderInfo> orders);

	void updateUserCurrMarket(String userId, String currMarket);
}
