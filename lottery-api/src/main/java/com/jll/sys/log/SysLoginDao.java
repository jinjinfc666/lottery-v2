package com.jll.sys.log;

import java.util.List;

import com.jll.dao.PageBean;
import com.jll.entity.IpBlackList;
import com.jll.entity.SysLogin;

public interface SysLoginDao
{
	//saveOrUpdate
	public void saveUpdate(SysLogin sysLogin);
	//查询用户登录日志
	public PageBean queryLoginlog(Integer type,Integer userId,String startTime,String endTime,Integer pageIndex,Integer pageSize);
	//查询不存在用户登录日志
	public PageBean queryLoginlog(String ip,String startTime,String endTime,Integer pageIndex,Integer pageSize);
	//通过ip查询用户失败登录此时
	public List<SysLogin> queryFailLoginCount(String ip);
	//删除
	public void deleteSysLogin(SysLogin sysLogin);
	//通过id查找
	public List<SysLogin> queryById(Integer id);
}
