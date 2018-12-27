package com.jll.entity;
// Generated 2018-8-14 16:52:58 by Hibernate Tools 5.2.10.Final

import java.util.Date;

/**
 * Issue generated by hbm2java
 */
public class Issue implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -162220270110557773L;
	
	private Integer id;
	private String lotteryType;
	private String issueNum;
	private String retNum;
	private Integer state;
	private Date startTime;
	private Date endTime;
	private Long downCounter;

	public Issue() {
	}

	public Issue(String lotteryType, String issueNum, String retNum, Integer state, Date startTime, Date endTime, Long downCounter) {
		this.lotteryType = lotteryType;
		this.issueNum = issueNum;
		this.retNum = retNum;
		this.state = state;
		this.startTime = startTime;
		this.endTime = endTime;
		this.downCounter = downCounter;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLotteryType() {
		return this.lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getIssueNum() {
		return this.issueNum;
	}

	public void setIssueNum(String issueNum) {
		this.issueNum = issueNum;
	}

	public String getRetNum() {
		return this.retNum;
	}

	public void setRetNum(String retNum) {
		this.retNum = retNum;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Long getDownCounter() {
		return downCounter;
	}

	public void setDownCounter(Long downCounter) {
		this.downCounter = downCounter;
	}
}
