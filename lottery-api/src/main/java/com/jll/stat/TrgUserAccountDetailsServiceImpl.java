package com.jll.stat;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.dao.PageBean;
import com.jll.entity.TrgUserAccountDetails;

@Service
@Transactional
public class TrgUserAccountDetailsServiceImpl implements TrgUserAccountDetailsService
{
	private Logger logger = Logger.getLogger(TrgUserAccountDetailsServiceImpl.class);

	@Resource
	TrgUserAccountDetailsDao trgUserAccDetailsDao;
	
	@Override
	public void updateTrgUserAccDetails(TrgUserAccountDetails trgAccDetails) {
		trgUserAccDetailsDao.saveAccDetails(trgAccDetails);
	}

	@Override
	public PageBean<TrgUserAccountDetails> queryTrgUserAccDetailsByFlag(PageBean<TrgUserAccountDetails> page) {
		return trgUserAccDetailsDao.queryTrgUserAccDetailsByFlag(page);
	}

	
}
