package com.jll.common.utils.sequence;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.entity.GenSequence;

@Configuration
@PropertySource("classpath:sys-setting.properties")
@Service
@Transactional
public class GenSequenceServiceImpl implements GenSequenceService
{
	private Logger logger = Logger.getLogger(GenSequenceServiceImpl.class);

	@Resource
	GenSequenceDao genSeqDao;

	@Override
	public GenSequence querySeqVal() {
		return genSeqDao.querySeqVal();
	}

	@Override
	public void saveSeq(GenSequence seq) {
		genSeqDao.saveSeq(seq);
	}

	@Override
	public GenSequence queryPK10SeqVal() {
		return genSeqDao.queryPK10SeqVal();
	}
	
	@Override
	public GenSequence queryTC3DSeqVal() {
		return genSeqDao.queryTC3DSeqVal();
	}

	@Override
	public GenSequence queryFC3DSeqVal() {
		return genSeqDao.queryFC3DSeqVal();
	}
		
}
