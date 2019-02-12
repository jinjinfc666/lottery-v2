package com.jll.user.transfer;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.entity.TransferApplication;
import com.jll.entity.UserInfo;

@Repository
public class TransferAppDaoImpl extends DefaultGenericDaoImpl<TransferApplication> implements TransferAppDao
{
	private Logger logger = Logger.getLogger(TransferAppDaoImpl.class);

	@Override
	public void saveTransferApplication(TransferApplication transApp) {
		this.saveOrUpdate(transApp);
	}

 
	
}
