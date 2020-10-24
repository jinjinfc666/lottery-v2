package com.jll.game.playtype;

import java.util.List;
import java.util.Map;

import com.jll.entity.PlayType;
import com.jll.entity.PlayTypeNum;
import com.jll.entity.display.BitColumn;

public interface PlayTypeService
{
	
	/**
	 * query the play type 
	 * @param lotteryType
	 * @return
	 */
	List<PlayType> queryPlayType(String lotteryType);
	
	Map<String,Object> addPlayType(PlayType playType);
	
	Map<String,Object> updateState(Integer id,Integer state);
	
	Map<String,Object> updateIsHidden(Integer id, Integer isHidden);
	
	Map<String,Object> updateMulSinFlag(Integer id, Integer mulSinFlag);
	
	Map<String,Object> updatePlayType(PlayType playType);
	
	List<PlayType> queryByLotteryType(String lotteryType);
	
	PlayType queryById(Integer id);
	
	//添加时的验证
	boolean isNoPlayType(PlayType playType);
	//查询id是否存在
	boolean isNoPlayType(Integer id);
	//修改排序
	Map<String,Object> updatePlayTypeSeq(String lotteryType,String allId);

	List<List<PlayTypeNum>> queryMainPs(String lotteryType);

	List<List<PlayTypeNum>> queryMainPsHs(String lotteryType);

	List<BitColumn> queryMainPsDwd(String lotteryType);

	List<List<PlayTypeNum>> queryBwsz(String lotteryType);

	List<List<PlayTypeNum>> querySwsz(String lotteryType);
	
	List<List<PlayTypeNum>> queryGwsz(String lotteryType);

	List<List<PlayTypeNum>> queryEzdw(String lotteryType, String numType);

	List<List<PlayTypeNum>> querySzdw(String lotteryType);

	List<List<PlayTypeNum>> queryEzzh(String lotteryType);

	List<List<PlayTypeNum>> queryEzhs(String lotteryType, String numType);

	List<List<PlayTypeNum>> querySzhs(String lotteryType);

	List<List<PlayTypeNum>> querySzzh(String lotteryType);

	List<BitColumn> queryZx3(String lotteryType);
}
