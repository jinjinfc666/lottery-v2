package com.jll.user.transfer;

import java.util.Map;

import com.jll.dao.PageBean;
import com.jll.entity.TransferApplication;

public interface TransferAppDao
{
		
	/**
	 * persist the transfer application
	 * @param TransferApplication
	 */
	void saveTransferApplication(TransferApplication transApp);

	PageBean queryTransfer(Map<String, Object> params);
	
}
