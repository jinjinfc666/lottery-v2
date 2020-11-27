package com.jll.entity;
// Generated 2018-8-14 16:52:58 by Hibernate Tools 5.2.10.Final

import java.math.BigDecimal;
import java.util.Date;

/**
 * PayTypeNum generated by hbm2java
 */
public class PlayTypeNum implements java.io.Serializable {
	   
	private Long id;
	private Long playTypeId;
	private String betNum;
	private BigDecimal aOdds;
	private BigDecimal bOdds;
	private BigDecimal cOdds;
	private BigDecimal dOdds;
	private BigDecimal currentOdds;
	private Date createTime;
	private Date updateTime;
	private String displayName;
	private String betNumDesc;
	
	public PlayTypeNum() {
	}

	public PlayTypeNum(Long playTypeId, String betNum, BigDecimal aOdds, BigDecimal bOdds, BigDecimal cOdds, BigDecimal dOdds,
			Date createTime, Date updateTime) {
		this.playTypeId = playTypeId;
		this.betNum = betNum;
		this.aOdds = aOdds;
		this.bOdds = bOdds;
		this.cOdds = cOdds;
		this.dOdds = dOdds;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPlayTypeId() {
		return playTypeId;
	}

	public void setPlayTypeId(Long playTypeId) {
		this.playTypeId = playTypeId;
	}

	public String getBetNum() {
		return betNum;
	}

	public void setBetNum(String betNum) {
		this.betNum = betNum;
	}

	public BigDecimal getaOdds() {
		return aOdds;
	}

	public void setaOdds(BigDecimal aOdds) {
		this.aOdds = aOdds;
	}

	public BigDecimal getbOdds() {
		return bOdds;
	}

	public void setbOdds(BigDecimal bOdds) {
		this.bOdds = bOdds;
	}

	public BigDecimal getcOdds() {
		return cOdds;
	}

	public void setcOdds(BigDecimal cOdds) {
		this.cOdds = cOdds;
	}

	public BigDecimal getdOdds() {
		return dOdds;
	}

	public void setdOdds(BigDecimal dOdds) {
		this.dOdds = dOdds;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getCurrentOdds() {
		return currentOdds;
	}

	public void setCurrentOdds(BigDecimal currentOdds) {
		this.currentOdds = currentOdds;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getBetNumDesc() {
		return betNumDesc;
	}

	public void setBetNumDesc(String betNumDesc) {
		this.betNumDesc = betNumDesc;
	}

	
}