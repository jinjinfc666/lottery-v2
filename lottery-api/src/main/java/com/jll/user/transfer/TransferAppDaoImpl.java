package com.jll.user.transfer;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.jll.common.utils.DateUtil;
import com.jll.common.utils.StringUtils;
import com.jll.dao.DefaultGenericDaoImpl;
import com.jll.dao.PageBean;
import com.jll.entity.TransferApplication;

@Repository
public class TransferAppDaoImpl extends DefaultGenericDaoImpl<TransferApplication> implements TransferAppDao
{
	private Logger logger = Logger.getLogger(TransferAppDaoImpl.class);

	@Override
	public void saveTransferApplication(TransferApplication transApp) {
		this.saveOrUpdate(transApp);
	}

	@Override
	public PageBean queryTransfer(Map<String, Object> params) {
		Integer pageSize = (Integer)params.get("pageSize");
		Integer pageIndex = (Integer)params.get("pageIndex");
		String sourceUser = (String)params.get("sourceUser");
		String dstUser = (String)params.get("dstUser");
		Integer state = (Integer)params.get("state");
		String startTime = (String)params.get("startTime");
		String endTime = (String)params.get("endTime");
		String orderNum = (String)params.get("orderNum");
		
		Map<String, Object> paramsArray = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		PageBean<TransferApplication> page= new PageBean<>();
		
		
		sql.append("from TransferApplication t where 1=1 ");
		
		if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)) {
			sql.append(" and createTime >=:startTime and createTime <=:endTime");
			Date beginDate = DateUtil.fmtYmdHisToDate(startTime);
		    Date endDate = DateUtil.fmtYmdHisToDate(endTime);
		    
		    paramsArray.put("startTime", beginDate);
		    paramsArray.put("endTime", endDate);
		}
		
		if(state != null) {
			sql.append(" and state=:state");
			paramsArray.put("state", state);
		}
		
		if(!StringUtils.isBlank(sourceUser)) {
			sql.append(" and sourceUserName=:sourceUser");
			paramsArray.put("sourceUser", sourceUser);
		}
		 
		if(!StringUtils.isBlank(dstUser)) {
			sql.append(" and dstUserName=:dstUser");
			paramsArray.put("dstUser", dstUser);
		}
		
		if(!StringUtils.isBlank(orderNum)) {
			sql.append(" and orderNum=:orderNum");
			paramsArray.put("orderNum", orderNum);
		}
		
		sql.append(" order by createTime desc ");
		
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		
		PageBean pageBean = queryByPagination(page, sql.toString(), paramsArray); 
		return pageBean;
	}
}
