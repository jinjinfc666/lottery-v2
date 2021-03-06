package com.jll.common.utils.sequence;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.entity.GenSequence;

@Repository
public class GenSequenceDaoImpl extends DefaultGenericDaoImpl<GenSequence> implements GenSequenceDao
{
	private Logger logger = Logger.getLogger(GenSequenceDaoImpl.class);

	@Override
	public GenSequence querySeqVal() {
		String sql = "from GenSequence where seqName='seq_num'";
		List<Object> params = new ArrayList<>();
		
		List<GenSequence> seqs = this.query(sql, params, GenSequence.class);
		if(seqs == null || seqs.size() == 0) {
			return null;
		}
		
		return seqs.get(0);
	}

	@Override
	public void saveSeq(GenSequence seq) {
		this.saveOrUpdate(seq);
	}

	@Override
	public GenSequence queryPK10SeqVal() {
		String sql = "from GenSequence where seqName='seq_num_bjpk10'";
		List<Object> params = new ArrayList<>();
		
		List<GenSequence> seqs = this.query(sql, params, GenSequence.class);
		if(seqs == null || seqs.size() == 0) {
			return null;
		}
		
		return seqs.get(0);
	}

	@Override
	public GenSequence queryTC3DSeqVal() {
		String sql = "from GenSequence where seqName='seq_num_tc3d'";
		List<Object> params = new ArrayList<>();
		
		List<GenSequence> seqs = this.query(sql, params, GenSequence.class);
		if(seqs == null || seqs.size() == 0) {
			return null;
		}
		
		return seqs.get(0);
	}

	@Override
	public GenSequence queryFC3DSeqVal() {
		String sql = "from GenSequence where seqName='seq_num_fc3d'";
		List<Object> params = new ArrayList<>();
		
		List<GenSequence> seqs = this.query(sql, params, GenSequence.class);
		if(seqs == null || seqs.size() == 0) {
			return null;
		}
		
		return seqs.get(0);
	}
	
}
