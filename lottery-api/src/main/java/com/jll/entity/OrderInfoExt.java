package com.jll.entity;
// Generated 2018-8-14 16:52:58 by Hibernate Tools 5.2.10.Final

import java.util.Date;

/**
 * SysCode generated by hbm2java
 */
public class OrderInfoExt implements java.io.Serializable {

	private Integer id;
	private Integer orderId;
	private String extFieldName;
	private String extFieldVal;
	private Date createTime;

	public OrderInfoExt() {
	}

	public OrderInfoExt(Integer id, Integer orderId,  String extFieldName, String extFieldVal, Date createTime) {
		this.id = id;
		this.orderId = orderId;
		this.extFieldName = extFieldName;
		this.extFieldVal = extFieldVal;
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getExtFieldName() {
		return extFieldName;
	}

	public void setExtFieldName(String extFieldName) {
		this.extFieldName = extFieldName;
	}

	public String getExtFieldVal() {
		return extFieldVal;
	}

	public void setExtFieldVal(String extFieldVal) {
		this.extFieldVal = extFieldVal;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	
}
