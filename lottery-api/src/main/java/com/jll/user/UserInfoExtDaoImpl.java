package com.jll.user;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		if(CollectionUtils.isEmpty(exts)) {
			if(!CollectionUtils.isEmpty(user.getCreditMarkets())) {
				StringBuffer buffer = new StringBuffer();
				List<CreditMarket> creditMarkets = user.getCreditMarkets();
				creditMarkets.forEach(creditMarket->{
					buffer.append(creditMarket.getMarketId()).append(",");
				});
				buffer.deleteCharAt(buffer.length() - 1);
				ext = new UserInfoExt();
				ext.setUserId(user.getId());
				ext.setExtFieldName("creditMarket");
				ext.setExtFieldVal(buffer.toString());
				ext.setCreateTime(today);
				
				this.saveOrUpdate(ext);
			}
			
			if(user.getCurrentMarket() != null) {
				CreditMarket currentMarket = user.getCurrentMarket();
				
				ext = new UserInfoExt();
				ext.setUserId(user.getId());
				ext.setExtFieldName("currCreditMarket");
				ext.setExtFieldVal(String.valueOf(currentMarket.getMarketId()));
				ext.setCreateTime(today);
				
				this.saveOrUpdate(ext);
			}
			
			if(user.getXyAmount() != null) {
				ext = new UserInfoExt();
				ext.setUserId(user.getId());
				ext.setExtFieldName("xyAmount");
				ext.setExtFieldVal(String.valueOf(user.getXyAmount()));
				ext.setCreateTime(today);
					
				this.saveOrUpdate(ext);
			}
			
			if(user.getXyPayoutRate() != null) {
				ext = new UserInfoExt();
				ext.setUserId(user.getId());
				ext.setExtFieldName("xyPayoutRate");
				ext.setExtFieldVal(String.valueOf(user.getXyPayoutRate()));
				ext.setCreateTime(today);
					
				this.saveOrUpdate(ext);
			}
			
			if(user.getZcAmount() != null){
				ext = new UserInfoExt();
				ext.setUserId(user.getId());
				ext.setExtFieldName("zcAmount");
				ext.setExtFieldVal(String.valueOf(user.getZcAmount().divide(new BigDecimal(100))));
				ext.setCreateTime(today);
					
				this.saveOrUpdate(ext);
			}
			
			if(user.getTsAmount() != null){
				ext = new UserInfoExt();
				ext.setUserId(user.getId());
				ext.setExtFieldName("tsAmount");
				ext.setExtFieldVal(String.valueOf(user.getTsAmount().divide(new BigDecimal(100))));
				ext.setCreateTime(today);
					
				this.saveOrUpdate(ext);
			}
			
			if(user.getUsedCreditAmount() != null){
				ext = new UserInfoExt();
				ext.setUserId(user.getId());
				ext.setExtFieldName("usedCreditAmount");
				ext.setExtFieldVal(String.valueOf(user.getUsedCreditAmount()));
				ext.setCreateTime(today);
					
				this.saveOrUpdate(ext);
			}
		}else {
			for(UserInfoExt ext_ : exts) {
				if(ext_.getExtFieldName().equals("creditMarket") && user.getCreditMarkets() != null) {
					StringBuffer buffer = new StringBuffer();
					List<CreditMarket> creditMarkets = user.getCreditMarkets();
					creditMarkets.forEach(creditMarket->{
						buffer.append(creditMarket.getMarketId()).append(",");
					});
					buffer.deleteCharAt(buffer.length() - 1);
					
					ext_.setExtFieldVal(buffer.toString());
					this.saveOrUpdate(ext_);
					
				}else if(ext_.getExtFieldName().equals("currCreditMarket") && user.getCurrentMarket() != null) {
					ext_.setExtFieldVal(String.valueOf(user.getCurrentMarket().getMarketId()));
					this.saveOrUpdate(ext_);
				}else if(ext_.getExtFieldName().equals("xyAmount") && user.getXyAmount() != null) {
					ext_.setExtFieldVal(String.valueOf(user.getXyAmount()));
					this.saveOrUpdate(ext_);
				}else if(ext_.getExtFieldName().equals("xyPayoutRate") && user.getXyPayoutRate() != null) {
					ext_.setExtFieldVal(String.valueOf(user.getXyPayoutRate()));
					this.saveOrUpdate(ext_);
				}else if(ext_.getExtFieldName().equals("zcAmount") && user.getZcAmount() != null) {
					ext_.setExtFieldVal(String.valueOf(user.getZcAmount().multiply(new BigDecimal(0.01))));
					this.saveOrUpdate(ext_);
				}else if(ext_.getExtFieldName().equals("tsAmount") && user.getTsAmount() != null) {
					ext_.setExtFieldVal(String.valueOf(user.getTsAmount().multiply(new BigDecimal(0.01))));
					this.saveOrUpdate(ext_);
				}else if(ext_.getExtFieldName().equals("usedCreditAmount") && user.getUsedCreditAmount() != null) {
					ext_.setExtFieldVal(String.valueOf(user.getUsedCreditAmount()));
					this.saveOrUpdate(ext_);
				}
			}
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
