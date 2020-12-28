package com.jll.entity;
// Generated 2018-8-14 16:52:58 by Hibernate Tools 5.2.10.Final

import java.math.BigDecimal;
import java.util.Date;

/**
 * PlayType generated by hbm2java
 */
public class UserTs implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userId;
	private Integer playTypeId;
	private String lotteryType;
	private String playTypeBrief;
	private Date createTime;
	private BigDecimal aTs;
	private BigDecimal bTs;
	private BigDecimal cTs;
	private BigDecimal dTs;
	private BigDecimal singleBetLimitAmount;
	private BigDecimal totalBetLimitAmount;
	
	public UserTs() {
	}

	public UserTs(String lotteryType, String classification, String ptName, String ptDesc, Integer state,
			Integer mulSinFlag, Integer isHidden, Integer seq, Date createTime) {
		this.lotteryType = lotteryType;
		this.createTime = createTime;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPlayTypeId() {
		return playTypeId;
	}

	public void setPlayTypeId(Integer playTypeId) {
		this.playTypeId = playTypeId;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	

	public String getPlayTypeBrief() {
		return playTypeBrief;
	}

	public void setPlayTypeBrief(String playTypeBrief) {
		this.playTypeBrief = playTypeBrief;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getaTs() {
		return aTs;
	}

	public void setaTs(BigDecimal aTs) {
		this.aTs = aTs;
	}

	public BigDecimal getbTs() {
		return bTs;
	}

	public void setbTs(BigDecimal bTs) {
		this.bTs = bTs;
	}

	public BigDecimal getcTs() {
		return cTs;
	}

	public void setcTs(BigDecimal cTs) {
		this.cTs = cTs;
	}

	public BigDecimal getdTs() {
		return dTs;
	}

	public void setdTs(BigDecimal dTs) {
		this.dTs = dTs;
	}

	public BigDecimal getSingleBetLimitAmount() {
		return singleBetLimitAmount;
	}

	public void setSingleBetLimitAmount(BigDecimal singleBetLimitAmount) {
		this.singleBetLimitAmount = singleBetLimitAmount;
	}

	public BigDecimal getTotalBetLimitAmount() {
		return totalBetLimitAmount;
	}

	public void setTotalBetLimitAmount(BigDecimal totalBetLimitAmount) {
		this.totalBetLimitAmount = totalBetLimitAmount;
	}
}
