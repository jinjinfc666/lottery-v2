package com.jll.dao;

import java.math.BigInteger;
import java.util.Date;
//import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.TemporalType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.jll.game.LotteryCenterServiceImpl;

public class DefaultGenericDaoImpl<T> extends HibernateDaoSupport implements GenericDaoIf<T> {

	private Logger logger = Logger.getLogger(LotteryCenterServiceImpl.class);
	
	@Autowired
	@DependsOn("sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory)
	{
	    super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void saveOrUpdate(T entity) {
		logger.debug(String.format("Try to save the entity...", ""));
		Session session = null;
		boolean needClosed = false;
		try {
			session = getSessionFactory().getCurrentSession();
		}catch(HibernateException ex) {
			session = getSessionFactory().openSession();
			needClosed = true;
		}
		
		session.saveOrUpdate(entity);
		
		if(needClosed && session!= null && session.isOpen()) {
			session.close();
		}
	}

	@Override
	public void delete(T entity) {
		getSessionFactory().getCurrentSession().delete(entity);
	}

	@Override
	public List<T> query(String HQL, List<Object> params, Class<T> clazz) {
		String sql = HQL;
		Session session = null;
		boolean needClose = false;
		Query<T> query = null;
		List<T> ret = null;
		
		try {
			session = getSessionFactory().getCurrentSession();
		}catch(HibernateException ex) {
			session = getSessionFactory().openSession();
			needClose = true;
		}
		
		query = session.createQuery(sql, clazz);
		
		if(params != null) {
			int indx = 0;
			for(Object para : params) {
				query.setParameter(indx, para);
				
				indx++;
			}
		}
		
		ret = query.list();
		if(needClose && session.isOpen()) {
			session.close();
		}
		
		return ret;
		
	}

	public <K> List<K> queryObjectArray(String HQL, List<Object> params, Class<K> clazz) {
		String sql = HQL;
		Session session = null;
		boolean needClose = false;
		Query<K> query = null;
		List<K> ret = null;
		
		try {
			session = getSessionFactory().getCurrentSession();
		}catch(HibernateException ex) {
			session = getSessionFactory().openSession();
			needClose = true;
		}
		
		query = session.createQuery(sql, clazz);
		
		if(params != null) {
			int indx = 0;
			for(Object para : params) {
				query.setParameter(indx, para);
				
				indx++;
			}
		}
		
		ret = query.list();
		if(needClose && session.isOpen()) {
			session.close();
		}
		
		return ret;
		
	}
	
	@Override
	public long queryCount(String HQL, List<Object> params) {
		String sql = HQL;
		Session session = null;
		boolean needClose = false;
		Query<Long>  query = null;
		Long ret = null;
		
		try {
			session = getSessionFactory().getCurrentSession();
		}catch(HibernateException ex) {
			session = getSessionFactory().openSession();
			needClose = true;
		}
		
	    query = session.createQuery(sql, Long.class);

	    if(params != null) {
	    	int indx = 0;
	    	for(Object para : params) {
	    		query.setParameter(indx, para);
	    		
	    		indx++;
	    	}
	    }
	    
	    ret = query.getSingleResult();
	    if(needClose && session.isOpen()) {
			session.close();
		}
	    
	    return ret;
	}

	@Override
	public boolean add(List<T> entities) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageBean<T> queryByPagination(PageBean<T> page, String HQL, List<Object> params, Class<T> clazz) {
		//PageBean<T> ret = new PageBean<>();
		List<T> content = null;
		int entityNameStartInd = 0;
		String sql = HQL;
		StringBuffer sqlCount = new StringBuffer("select count(*) ");
		Long totalPages =  null;
		Long totalNum = null;
		Integer pageIndex = page.getPageIndex();
		Integer pageSize = page.getPageSize();
		
		if(pageIndex == null || pageIndex.intValue() < 1) {
			pageIndex = 1;
		}
		
		if(pageSize == null || pageSize <= 0) {
			pageSize = 20;
		}
		
		Integer startPosition = (pageIndex - 1) * pageSize;
	    Query<T> query = getSessionFactory().getCurrentSession().createQuery(sql, clazz);

	    entityNameStartInd = HQL.indexOf("from");
	    if(entityNameStartInd < 0) {
	    	entityNameStartInd = HQL.indexOf("FROM");
	    }
	    
	    sqlCount.append(HQL.substring(entityNameStartInd));
	    totalNum =  queryCount(sqlCount.toString(), params);
	    
	    if(totalNum % pageSize == 0) {
	    	totalPages = totalNum / pageSize;
	    }else {
	    	totalPages = totalNum / pageSize + 1; 
	    }
	    
	    if(pageIndex.intValue() > totalPages.intValue()) {
			return page;
		}
	    
	    if(params != null) {
	    	int indx = 0;
	    	for(Object para : params) {
	    		query.setParameter(indx, para);
	    		
	    		indx++;
	    	}
	    }
	    
	    query.setFirstResult(startPosition);
	    query.setMaxResults(pageSize);
	    content = query.list();
	    
	    page.setContent(content);
	    page.setPageSize(pageSize);
	    page.setTotalPages(totalPages);
	    page.setPageIndex(pageIndex);
	    page.setTotalNumber(totalNum);
		return page;
	}
	
	@Override
	public PageBean queryByPagination(PageBean page, String HQL, Map<String,Object> params) {
		PageBean ret = new PageBean();
		List<?> content = null;
		int entityNameStartInd = 0;
		String sql = HQL;
		StringBuffer sqlCount = new StringBuffer("select count(*) ");
		Long totalPages =  null;
		Long totalNumber=null;
		Integer pageIndex = page.getPageIndex();
		Integer pageSize = page.getPageSize();
		Query  query = null;
		Integer startPosition = null;
		
		if(pageIndex == null || pageIndex.intValue() < 1) {
			pageIndex = 1;
		}
		
		if(pageSize == null || pageSize <= 0) {
			pageSize = 20;
		}
		
		startPosition = (pageIndex -1) * pageSize;
		
		
		query= getSessionFactory().getCurrentSession().createQuery(sql);
	    
		entityNameStartInd = HQL.indexOf("from");
	    if(entityNameStartInd < 0) {
	    	entityNameStartInd = HQL.indexOf("FROM");
	    }
	    
	    sqlCount.append(HQL.substring(entityNameStartInd));
	    
	    totalNumber =  queryCount(sqlCount.toString(), params);
	    
	    if(totalNumber % pageSize == 0) {
	    	totalPages = totalNumber / pageSize;
	    }else {
	    	totalPages = totalNumber / pageSize + 1; 
	    }
	    if(params != null) {
	    	Set<String> keySet = params.keySet();  
            for (String string : keySet) {  
                Object obj = params.get(string);  
            	if(obj instanceof Date){  
                	query.setParameter(string, (Date)obj,TemporalType.DATE); //query.setParameter(string, (Date)obj,DateType.INSTANCE);   此方法为setDate的替代方法 
                }else if(obj instanceof Object[]){  
                    query.setParameterList(string, (Object[])obj);  
                }else{  
                    query.setParameter(string, obj);  
                }  
            }
	    }
	    

		//Integer startPosition = pageIndex==1 ? 0 : pageIndex*pageSize-pageSize;
	    //Integer startPosition = pageIndex*pageSize;
	    
	    
	    query.setFirstResult(startPosition);
	    query.setMaxResults(pageSize);
	    content = query.list();
	    
	    ret.setContent(content);
	    ret.setPageIndex(pageIndex);
	    ret.setPageSize(pageSize);
	    ret.setTotalPages(totalPages);
	    ret.setTotalNumber(totalNumber);
	    
		return ret;
	}
	@Override
	public long queryCount(String HQL, Map<String,Object> params) {
		String sql = HQL;
		
	    Query<Long> query = getSessionFactory().getCurrentSession().createQuery(sql, Long.class);

	    if(params != null) {
	    	Set<String> keySet = params.keySet();  
            for (String string : keySet) {  
                Object obj = params.get(string);  
            	if(obj instanceof Date){  
                	//query.setParameter(string, (Date)obj,DateType.INSTANCE); 
            		query.setParameter(string, (Date)obj, TemporalType.DATE);
                }else if(obj instanceof Object[]){  
                    query.setParameterList(string, (Object[])obj);  
                }else{  
                    query.setParameter(string, obj);  
                }  
            }
	    }
	    //List<?> list=query.list();
	    return  query.getSingleResult();
	}
	
	//通过sql查询
	@SuppressWarnings("rawtypes")
	@Override
	public PageBean queryBySqlPagination(PageBean page, String SQL, Map<String,Object> params) {
		PageBean ret = new PageBean();
		List<?> content = null;
		int entityNameStartInd = 0;
		String sql = SQL;
		StringBuffer sqlCount = new StringBuffer("select count(*) ");
		Long totalPages =  null;
		Long totalNumber=null;
		Integer pageIndex = page.getPageIndex();
		Integer pageSize = page.getPageSize();
	    Query query = null;
	    Integer startPosition = null;
		
		if(pageIndex == null || pageIndex.intValue() < 1) {
			pageIndex = 1;
		}
		
		if(pageSize == null || pageSize <= 0) {
			pageSize = 20;
		}
		
		startPosition = (pageIndex -1) * pageSize;
		
		query = getSessionFactory().getCurrentSession().createNativeQuery(sql);
			    
	    entityNameStartInd = sql.indexOf("from");
	    if(entityNameStartInd < 0) {
	    	entityNameStartInd = sql.indexOf("FROM");
	    }
	    
	    if(entityNameStartInd > 0) {
	    	sqlCount.append(sql.substring(entityNameStartInd));	
	    }
	    
	    
	    totalNumber =  querySqlCount(sqlCount.toString(), params);
	    
	    if(totalNumber % pageSize == 0) {
	    	totalPages = totalNumber / pageSize;
	    }else {
	    	totalPages = totalNumber / pageSize + 1; 
	    }
	    
	    if(params != null) {
	    	Set<String> keySet = params.keySet();  
            for (String string : keySet) {  
                Object obj = params.get(string);  
            	if(obj instanceof Date){  
                	query.setParameter(string, (Date)obj,TemporalType.DATE); //query.setParameter(string, (Date)obj,DateType.INSTANCE);   此方法为setDate的替代方法 
                }else if(obj instanceof Object[]){  
                    query.setParameterList(string, (Object[])obj);  
                }else{  
                    query.setParameter(string, obj);  
                }  
            }
	    }
	    
	    query.setFirstResult(startPosition);
	    query.setMaxResults(pageSize);
	    content = query.list();
	    
	    ret.setContent(content);
	    ret.setPageIndex(pageIndex);
	    ret.setPageSize(pageSize);
	    ret.setTotalPages(totalPages);
	    ret.setTotalNumber(totalNumber);
	    
		return ret;
	}
	
	@Override
	public PageBean<T> queryBySqlClazzPagination(PageBean<T> page, String SQL, Map<String,Object> params,Class<T> clazz) {
		PageBean<T> ret = new PageBean<>();
		List<T> content = null;
		String sql = SQL;
		Long totalPages =  null;
		Long totalNumber=null;
		Integer pageIndex = page.getPageIndex();
		Integer pageSize = page.getPageSize();
	    Query<T> query = getSessionFactory().getCurrentSession().createNativeQuery(sql,clazz);
	    
	    
	    totalNumber =  querySqlCount(SQL, params);
	    
	    if(totalNumber % pageSize == 0) {
	    	totalPages = totalNumber / pageSize;
	    }else {
	    	totalPages = totalNumber / pageSize + 1; 
	    }
	    if(params != null) {
	    	Set<String> keySet = params.keySet();  
            for (String string : keySet) {  
                Object obj = params.get(string);  
            	if(obj instanceof Date){  
                	query.setParameter(string, (Date)obj, TemporalType.DATE); //query.setParameter(string, (Date)obj,DateType.INSTANCE);   此方法为setDate的替代方法 
                }else if(obj instanceof Object[]){  
                    query.setParameterList(string, (Object[])obj);  
                }else{  
                    query.setParameter(string, obj);  
                }  
            }
	    }
	    

		Integer startPosition = pageIndex==1 ? 0 : pageIndex*pageSize-pageSize;
	    
	    
	    query.setFirstResult(startPosition);
	    query.setMaxResults(pageSize);
	    content = query.list();
	    
	    ret.setContent(content);
	    ret.setPageIndex(pageIndex);
	    ret.setPageSize(pageSize);
	    ret.setTotalPages(totalPages);
	    ret.setTotalNumber(totalNumber);
	    
		return ret;
	}
	@Override
	public long querySqlCount(String HQL, Map<String,Object> params) {
		String sql = HQL;
		long ret = 0L;
		
	    Query query = getSessionFactory().getCurrentSession().createNativeQuery(sql);

	    if(params != null) {
	    	Set<String> keySet = params.keySet();  
            for (String string : keySet) {  
                Object obj = params.get(string);  
            	if(obj instanceof Date){  
                	query.setParameter(string, (Date)obj, TemporalType.DATE); //query.setParameter(string, (Date)obj,DateType.INSTANCE);   此方法为setDate的替代方法 
                }else if(obj instanceof Object[]){  
                    query.setParameterList(string, (Object[])obj);  
                }else{  
                    query.setParameter(string, obj);  
                }  
            }
	    }
	    
	    Object result = query.getSingleResult();
	    ret = result == null?0L:((BigInteger)result).longValue();
	    return ret;
	}
	
	//时分秒的时间查询
	@Override
	public PageBean queryByTimePagination(PageBean page, String HQL, Map<String,Object> params) {
		PageBean ret = new PageBean();
		List<?> content = null;
		String sql = HQL;
		Long totalPages =  null;
		Long totalNumber=null;
		Integer pageIndex = page.getPageIndex();
		Integer pageSize = page.getPageSize();
		Query  query=null;
		query= getSessionFactory().getCurrentSession().createQuery(sql);
	    
	    totalNumber =  queryTimeCount(HQL, params);
	    
	    if(totalNumber % pageSize == 0) {
	    	totalPages = totalNumber / pageSize;
	    }else {
	    	totalPages = totalNumber / pageSize + 1; 
	    }
	    if(params != null) {
	    	Set<String> keySet = params.keySet();  
            for (String string : keySet) {  
                Object obj = params.get(string);  
            	if(obj instanceof Date){  
                	query.setParameter(string, (Date)obj,TimestampType.INSTANCE); //query.setParameter(string, (Date)obj,DateType.INSTANCE);   此方法为setDate的替代方法 
                }else if(obj instanceof Object[]){  
                    query.setParameterList(string, (Object[])obj);  
                }else{  
                    query.setParameter(string, obj);  
                }  
            }
	    }
	    

		Integer startPosition = pageIndex==1 ? 0 : pageIndex*pageSize-pageSize;
	    
	    
	    query.setFirstResult(startPosition);
	    query.setMaxResults(pageSize);
	    content = query.list();
	    
	    ret.setContent(content);
	    ret.setPageIndex(pageIndex);
	    ret.setPageSize(pageSize);
	    ret.setTotalPages(totalPages);
	    ret.setTotalNumber(totalNumber);
	    
		return ret;
	}
	@Override
	public long queryTimeCount(String HQL, Map<String,Object> params) {
		String sql = HQL;
		
	    Query query = getSessionFactory().getCurrentSession().createQuery(sql);

	    if(params != null) {
	    	Set<String> keySet = params.keySet();  
            for (String string : keySet) {  
                Object obj = params.get(string);  
            	if(obj instanceof Date){  
                	query.setParameter(string, (Date)obj,TimestampType.INSTANCE); //query.setParameter(string, (Date)obj,DateType.INSTANCE);   此方法为setDate的替代方法 
                }else if(obj instanceof Object[]){  
                    query.setParameterList(string, (Object[])obj);  
                }else{  
                    query.setParameter(string, obj);  
                }  
            }
	    }
	    List<?> list=query.list();
	    return list.size();
	}

	@Override
	public List<Object[]> queryNativeSQL(String sql, List<Object> params) {
		//String sql = HQL;
		Session session = null;
		boolean needClose = false;
		Query<Object[]> query = null;
		List<Object[]> ret = null;
		
		try {
			session = getSessionFactory().getCurrentSession();
		}catch(HibernateException ex) {
			session = getSessionFactory().openSession();
			needClose = true;
		}
		
		query = session.createNativeQuery(sql);
		
		if(params != null) {
			int indx = 1;
			for(Object para : params) {
				query.setParameter(indx, para);
				
				indx++;
			}
		}
		
		ret = query.list();
		if(needClose && session.isOpen()) {
			session.close();
		}
		
		return ret;		
	}
	
	
}
