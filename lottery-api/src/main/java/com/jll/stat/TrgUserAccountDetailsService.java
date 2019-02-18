package com.jll.stat;

import java.util.List;

import com.jll.dao.PageBean;
import com.jll.entity.TrgUserAccountDetails;

public interface TrgUserAccountDetailsService
{	
	void updateTrgUserAccDetails(TrgUserAccountDetails trgAccDetails);

	PageBean<TrgUserAccountDetails> queryTrgUserAccDetailsByFlag(PageBean<TrgUserAccountDetails> page);
	
	
}
