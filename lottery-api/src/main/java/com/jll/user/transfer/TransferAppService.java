package com.jll.user.transfer;

import java.util.Map;

import com.jll.dao.PageBean;
import com.jll.entity.TransferApplication;

public interface TransferAppService
{
	void saveTransferApplication(TransferApplication transferApp);
	
	PageBean queryTransfer(Map<String, Object> params);
}
