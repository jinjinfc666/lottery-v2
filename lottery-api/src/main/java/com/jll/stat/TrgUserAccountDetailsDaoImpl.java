package com.jll.stat;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.dao.PageBean;
import com.jll.entity.TrgUserAccountDetails;

@Repository
public class TrgUserAccountDetailsDaoImpl 
				extends DefaultGenericDaoImpl<TrgUserAccountDetails> 
					implements TrgUserAccountDetailsDao
{
	private Logger logger = Logger.getLogger(TrgUserAccountDetailsDaoImpl.class);

	@Override
	public void saveAccDetails(TrgUserAccountDetails userDetails) {
		this.saveOrUpdate(userDetails);
	}

	@Override
	public PageBean<TrgUserAccountDetails> queryTrgUserAccDetailsByFlag(PageBean<TrgUserAccountDetails> page) {
		String sql = "from TrgUserAccountDetails t where t.flag=? order by createTime";
		
		return queryByPagination(page, 
				sql, 
				page.getParams(), 
				TrgUserAccountDetails.class);
	}
}
