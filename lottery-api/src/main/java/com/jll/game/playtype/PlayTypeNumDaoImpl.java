package com.jll.game.playtype;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jll.common.constants.Constants.CreditMarketEnum;
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

	@Override
	public void changeUserCurrMarket(String userId, String currMarket) {
		CreditMarketEnum creditMarketEnum = CreditMarketEnum.getByCode(Integer.parseInt(currMarket));
		StringBuffer buffer = new StringBuffer();
		switch(creditMarketEnum){
		case MARKET_A:{
			buffer.append("update PlayTypeNum set currentOdds = a_odds");
			break;
		}
		case MARKET_B:{
			buffer.append("update PlayTypeNum set currentOdds = b_odds");
			break;
		}
		case MARKET_C:{
			buffer.append("update PlayTypeNum set currentOdds = c_odds");
			break;
		}
		default:
			
		}
		
		Session session=getSessionFactory().getCurrentSession();
		Query query = null;
		try{
			query = session.createQuery(buffer.toString());
			query.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		/*Query query = session.createQuery(buffer.toString());*/
		/*query.executeUpdate();*/
	}
}
