package com.jll.stat;

import com.jll.dao.PageBean;
import com.jll.entity.TrgUserAccountDetails;

public interface TrgUserAccountDetailsDao
{

	void saveAccDetails(TrgUserAccountDetails userDetails);

	PageBean<TrgUserAccountDetails> queryTrgUserAccDetailsByFlag(PageBean<TrgUserAccountDetails> page);
}
