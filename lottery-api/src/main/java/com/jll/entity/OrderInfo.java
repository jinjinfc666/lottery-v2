package com.jll.entity;
// Generated 2018-8-14 16:52:58 by Hibernate Tools 5.2.10.Final

import java.math.BigDecimal;
import java.util.Date;

/**
 * OrderInfo generated by hbm2java
 */
public class OrderInfo implements java.io.Serializable {

	private Integer id;
	private String orderNum;
	private Integer userId;
	private Integer issueId;
	private Integer walletId;
	private Integer playType;
	private String betNum;
	private Integer betTotal;
	private Float betAmount;
	private Float winBetTotal;
	private Float winAmount;
	private Integer times;
	private BigDecimal pattern;
	private BigDecimal prizeRate;
	private Integer state;
	private Integer delayPayoutFlag;
	private Integer isZh;
	private Integer isZhBlock;
	private String zhTrasactionNum;
	private Integer terminalType;
	private Date createTime;
	private Integer isPrize;

	public OrderInfo() {
	}

	public OrderInfo(String orderNum, Integer userId, Integer issueId, Integer walletId, Integer playType, String betNum,
			Integer betTotal, Float betAmount, Float winBetTotal,Float winAmount,Integer times, BigDecimal pattern, BigDecimal prizeRate, Integer state,
			Integer delayPayoutFlag, Integer isZh, Integer isZhBlock, String zhTrasactionNum, Integer terminalType, Date createTime, Integer isPrize) {
		this.orderNum = orderNum;
		this.userId = userId;
		this.issueId = issueId;
		this.walletId = walletId;
		this.playType = playType;
		this.betNum = betNum;
		this.betTotal = betTotal;
		this.betAmount = betAmount;
		this.winBetTotal = winBetTotal;
		this.winAmount = winAmount;
		this.times = times;
		this.pattern = pattern;
		this.prizeRate = prizeRate;
		this.state = state;
		this.delayPayoutFlag = delayPayoutFlag;
		this.isZh = isZh;
		this.isZhBlock = isZhBlock;
		this.zhTrasactionNum = zhTrasactionNum;
		this.terminalType = terminalType;
		this.createTime = createTime;
		this.isPrize = isPrize;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIssueId() {
		return this.issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	
	public Integer getWalletId() {
		return walletId;
	}

	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}

	public Integer getPlayType() {
		return this.playType;
	}

	public void setPlayType(Integer playType) {
		this.playType = playType;
	}

	public String getBetNum() {
		return this.betNum;
	}

	public void setBetNum(String betNum) {
		this.betNum = betNum;
	}

	public Integer getBetTotal() {
		return this.betTotal;
	}

	public void setBetTotal(Integer betTotal) {
		this.betTotal = betTotal;
	}

	public Float getBetAmount() {
		return this.betAmount;
	}

	public void setBetAmount(Float betAmount) {
		this.betAmount = betAmount;
	}
	
	public Float getWinBetTotal() {
		return winBetTotal;
	}

	public void setWinBetTotal(Float winBetTotal) {
		this.winBetTotal = winBetTotal;
	}

	public Float getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(Float winAmount) {
		this.winAmount = winAmount;
	}

	public Integer getTimes() {
		return this.times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public BigDecimal getPattern() {
		return this.pattern;
	}

	public void setPattern(BigDecimal pattern) {
		this.pattern = pattern;
	}

	public BigDecimal getPrizeRate() {
		return this.prizeRate;
	}

	public void setPrizeRate(BigDecimal prizeRate) {
		this.prizeRate = prizeRate;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getDelayPayoutFlag() {
		return this.delayPayoutFlag;
	}

	public void setDelayPayoutFlag(Integer delayPayoutFlag) {
		this.delayPayoutFlag = delayPayoutFlag;
	}

	public Integer getIsZh() {
		return this.isZh;
	}

	public void setIsZh(Integer isZh) {
		this.isZh = isZh;
	}

	public Integer getIsZhBlock() {
		return this.isZhBlock;
	}

	public void setIsZhBlock(Integer isZhBlock) {
		this.isZhBlock = isZhBlock;
	}

	
	public String getZhTrasactionNum() {
		return zhTrasactionNum;
	}

	public void setZhTrasactionNum(String zhTrasactionNum) {
		this.zhTrasactionNum = zhTrasactionNum;
	}

	public Integer getTerminalType() {
		return this.terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsPrize() {
		return isPrize;
	}

	public void setIsPrize(Integer isPrize) {
		this.isPrize = isPrize;
	}
	
	

}
