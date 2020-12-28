package com.jll.entity;
// Generated 2018-8-14 16:52:58 by Hibernate Tools 5.2.10.Final

import java.math.BigDecimal;
import java.util.Date;

/**
 * MemberPlReport generated by hbm2java
 */
public class TeamPlReport implements java.io.Serializable {

	private Integer id;
	private Date createTime;
	private Integer userId;
	private String userName;
	private BigDecimal deposit;    
	private BigDecimal withdrawal;
	private BigDecimal transfer;    
	private BigDecimal transferOut;
	private BigDecimal deduction;
	private BigDecimal consumption;
	private BigDecimal cancelAmount;
	private BigDecimal returnPrize;
	private BigDecimal sysBonus;
	private BigDecimal rebate;
	private Integer rechargeMember;
	private Integer newMembers;
	private BigDecimal profit;
	private Integer userType;

	//退水
	private BigDecimal tsAmount;
	
	//占成
	private BigDecimal zcAmount;
	
	//已用信用额度
	private BigDecimal usedCreditLimit;
	
	//剩余信用额度
	private BigDecimal remainCreditLimit;
	
	//结算金额
	private BigDecimal settlementAmount;
	
	//结算标识 0 未结算  1 已结算
	private Integer settlementFlag;
	
	//彩种
	private String lotteryType;
	
	//玩法
	private String playType;
	
	public TeamPlReport() {
	}

	public TeamPlReport(Date createTime) {
		this.createTime = createTime;
	}

	public TeamPlReport(Date createTime,Integer userId, String userName, BigDecimal deposit,
			BigDecimal withdrawal,BigDecimal transfer,BigDecimal transferOut, BigDecimal deduction, BigDecimal consumption, BigDecimal cancelAmount,
			BigDecimal returnPrize, BigDecimal rebate, Integer rechargeMember,
			Integer newMembers, BigDecimal profit, Integer userType) {
		this.createTime = createTime;
		this.userId = userId;
		this.userName = userName;
		this.deposit = deposit;
		this.withdrawal = withdrawal;
		this.transfer = transfer;
		this.transferOut = transferOut;
		this.deduction = deduction;
		this.consumption = consumption;
		this.cancelAmount = cancelAmount;
		this.returnPrize = returnPrize;
		this.rebate = rebate;
		this.rechargeMember = rechargeMember;
		this.newMembers = newMembers;
		this.profit = profit;
		this.userType = userType;
	}
	
	
	public BigDecimal getTransfer() {
		return transfer;
	}

	public void setTransfer(BigDecimal transfer) {
		this.transfer = transfer;
	}

	public BigDecimal getTransferOut() {
		return transferOut;
	}

	public void setTransferOut(BigDecimal transferOut) {
		this.transferOut = transferOut;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getDeposit() {
		return this.deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}


	public BigDecimal getWithdrawal() {
		return this.withdrawal;
	}

	public void setWithdrawal(BigDecimal withdrawal) {
		this.withdrawal = withdrawal;
	}

	public BigDecimal getDeduction() {
		return this.deduction;
	}

	public void setDeduction(BigDecimal deduction) {
		this.deduction = deduction;
	}

	public BigDecimal getConsumption() {
		return this.consumption;
	}

	public void setConsumption(BigDecimal consumption) {
		this.consumption = consumption;
	}

	public BigDecimal getCancelAmount() {
		return this.cancelAmount;
	}

	public void setCancelAmount(BigDecimal cancelAmount) {
		this.cancelAmount = cancelAmount;
	}

	public BigDecimal getReturnPrize() {
		return this.returnPrize;
	}

	public void setReturnPrize(BigDecimal returnPrize) {
		this.returnPrize = returnPrize;
	}

	public BigDecimal getRebate() {
		return this.rebate;
	}

	public void setRebate(BigDecimal rebate) {
		this.rebate = rebate;
	}

	public Integer getRechargeMember() {
		return this.rechargeMember;
	}

	public void setRechargeMember(Integer rechargeMember) {
		this.rechargeMember = rechargeMember;
	}

	public Integer getNewMembers() {
		return this.newMembers;
	}

	public void setNewMembers(Integer newMembers) {
		this.newMembers = newMembers;
	}

	public BigDecimal getProfit() {
		return this.profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public BigDecimal getSysBonus() {
		return sysBonus;
	}

	public void setSysBonus(BigDecimal sysBonus) {
		this.sysBonus = sysBonus;
	}

	public BigDecimal getTsAmount() {
		return tsAmount;
	}

	public void setTsAmount(BigDecimal tsAmount) {
		this.tsAmount = tsAmount;
	}

	public BigDecimal getZcAmount() {
		return zcAmount;
	}

	public void setZcAmount(BigDecimal zcAmount) {
		this.zcAmount = zcAmount;
	}

	public BigDecimal getUsedCreditLimit() {
		return usedCreditLimit;
	}

	public void setUsedCreditLimit(BigDecimal usedCreditLimit) {
		this.usedCreditLimit = usedCreditLimit;
	}

	public BigDecimal getRemainCreditLimit() {
		return remainCreditLimit;
	}

	public void setRemainCreditLimit(BigDecimal remainCreditLimit) {
		this.remainCreditLimit = remainCreditLimit;
	}

	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public Integer getSettlementFlag() {
		return settlementFlag;
	}

	public void setSettlementFlag(Integer settlementFlag) {
		this.settlementFlag = settlementFlag;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

	
}
