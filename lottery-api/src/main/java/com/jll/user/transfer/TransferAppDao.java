package com.jll.user.transfer;

import com.jll.entity.TransferApplication;

public interface TransferAppDao
{
		
	/**
	 * persist the transfer application
	 * @param TransferApplication
	 */
	void saveTransferApplication(TransferApplication transApp);
	
}
