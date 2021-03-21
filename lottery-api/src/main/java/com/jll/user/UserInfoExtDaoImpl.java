package com.jll.user;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.type.DateType;
import org.springframework.stereotype.Repository;

import com.jll.common.constants.Constants;
import com.jll.common.constants.Constants.UserType;
import com.jll.common.utils.DateUtil;
import com.jll.common.utils.StringUtils;
import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.dao.PageBean;
import com.jll.entity.OrderInfoExt;
import com.jll.entity.UserInfo;
import com.jll.entity.UserInfoExt;
import com.jll.entity.display.CreditMarket;

@Repository
public class UserInfoExtDaoImpl extends DefaultGenericDaoImpl<UserInfoExt> implements UserInfoExtDao
{
	private Logger logger = Logger.getLogger(UserInfoExtDaoImpl.class);

	

	@Override
	public void saveUserExt(UserInfo user) {
		Date today = new Date();
		UserInfoExt ext = null;
		String sql = "from UserInfoExt where userId=? ";
		List<Object> params = new ArrayList<>();
		params.add(user.getId());
		List<UserInfoExt> exts = query(sql, params, UserInfoExt.class);
		Map<String, UserInfoExt> extsInMap = exts.stream().collect(Collectors.toMap(UserInfoExt::getExtFieldName, v->v,(oldVal, newVal)->newVal));

		if(!StringUtils.isEmpty(user.getCreditMarketIds())) {
			ext = extsInMap.get("creditMarket");
			if(ext == null){
				ext = new UserInfoExt();
			}
			
			ext.setUserId(user.getId());
			ext.setExtFieldName("creditMarket");
			ext.setExtFieldVal(user.getCreditMarketIds());
			ext.setCreateTime(today);
			
			this.saveOrUpdate(ext);
			
			ext = new UserInfoExt();
			ext.setUserId(user.getId());
			ext.setExtFieldName("usedCreditAmount");
			ext.setExtFieldVal("0.00");
			ext.setCreateTime(today);
			this.saveOrUpdate(ext);
			
			ext = extsInMap.get("currCreditMarket");
			if(ext == null){
				ext = new UserInfoExt();
			}
			ext.setUserId(user.getId());
			ext.setExtFieldName("currCreditMarket");
			ext.setExtFieldVal(String.valueOf(user.getCreditMarketIds().charAt(0)));
			ext.setCreateTime(today);
			this.saveOrUpdate(ext);
		}
		
		if(user.getCurrentMarket() != null) {
			CreditMarket currentMarket = user.getCurrentMarket();
			
			ext = extsInMap.get("currCreditMarket");
			if(ext == null){
				ext = new UserInfoExt();
			}
			ext.setUserId(user.getId());
			ext.setExtFieldName("currCreditMarket");
			ext.setExtFieldVal(String.valueOf(currentMarket.getMarketId()));
			ext.setCreateTime(today);
			
			this.saveOrUpdate(ext);
		}
		
		if(user.getXyAmount() != null) {
			ext = extsInMap.get("xyAmount");
			if(ext == null){
				ext = new UserInfoExt();
			}
			ext.setUserId(user.getId());
			ext.setExtFieldName("xyAmount");
			ext.setExtFieldVal(String.valueOf(user.getXyAmount()));
			ext.setCreateTime(today);
				
			this.saveOrUpdate(ext);
		}
		
		if(user.getXyPayoutRate() != null) {
			ext = extsInMap.get("xyPayoutRate");
			if(ext == null){
				ext = new UserInfoExt();
			}
			ext.setUserId(user.getId());
			ext.setExtFieldName("xyPayoutRate");
			ext.setExtFieldVal(String.valueOf(user.getXyPayoutRate()));
			ext.setCreateTime(today);
				
			this.saveOrUpdate(ext);
		}
		
		if(user.getZcAmount() != null){
			ext = extsInMap.get("zcAmount");
			if(ext == null){
				ext = new UserInfoExt();
			}
			ext.setUserId(user.getId());
			ext.setExtFieldName("zcAmount");
			ext.setExtFieldVal(String.valueOf(user.getZcAmount().divide(new BigDecimal(100))));
			ext.setCreateTime(today);
				
			this.saveOrUpdate(ext);
		}
		if(user.getUsedCreditAmount() != null){
			ext = extsInMap.get("usedCreditAmount");
			if(ext == null){
				ext = new UserInfoExt();
			}
			ext.setUserId(user.getId());
			ext.setExtFieldName("usedCreditAmount");
			ext.setExtFieldVal(String.valueOf(user.getUsedCreditAmount()));
			ext.setCreateTime(today);
				
			this.saveOrUpdate(ext);
		}
	}



	@Override
	public String queryFiledByName(Integer userId, String fieldName) {
		String sql = "from UserInfoExt where userId=? and extFieldName=?";
		List<Object> params = new ArrayList<>();
		params.add(userId);
		params.add(fieldName);
		
		List<UserInfoExt> exts = this.query(sql, params, UserInfoExt.class);
		if(exts != null && exts.size() > 0) {
			return exts.get(0).getExtFieldVal();
		}else {
			return null;
		}
	}

 
	
  
}
