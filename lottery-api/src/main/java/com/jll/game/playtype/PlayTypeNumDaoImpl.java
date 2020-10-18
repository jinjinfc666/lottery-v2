package com.jll.game.playtype;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.entity.PlayType;
import com.jll.entity.PlayTypeNum;

@Repository
public class PlayTypeNumDaoImpl extends DefaultGenericDaoImpl<PlayTypeNum> implements PlayTypeNumDao{

	@Override
	public List<PlayTypeNum> queryPlayTypeNum(Long playTypeId) {
		String sql = "from PlayTypeNum where playTypeId=?";
		List<Object> params = new ArrayList<>();
		params.add(playTypeId);
		
		return this.query(sql, params, PlayTypeNum.class);
	}
}
