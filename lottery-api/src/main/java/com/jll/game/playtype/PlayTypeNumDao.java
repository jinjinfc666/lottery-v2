package com.jll.game.playtype;

import java.util.List;

import com.jll.entity.PlayTypeNum;

public interface PlayTypeNumDao {

	List<PlayTypeNum> queryPlayTypeNum(Long playTypeId);

}
