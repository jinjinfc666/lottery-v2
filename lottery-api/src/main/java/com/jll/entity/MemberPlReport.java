package com.jll.entity;
// Generated 2018-8-14 16:52:58 by Hibernate Tools 5.2.10.Final

import java.math.BigDecimal;
import java.util.Date;

/**
 * MemberPlReport generated by hbm2java
 */
public class MemberPlReport implements java.io.Serializable {

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
	private BigDecimal rebate;
	private BigDecimal profit;
	private Integer userType;

	public MemberPlReport() {
	}

	public MemberPlReport(Date createTime) {
		this.createTime = createTime;
	}

	public MemberPlReport(Date createTime,Integer userId, String userName, BigDecimal deposit,
			BigDecimal withdrawal,BigDecimal transfer,BigDecimal transferOut, BigDecimal deduction, BigDecimal consumption, BigDecimal cancelAmount,
			BigDecimal returnPrize, BigDecimal rebate, BigDecimal profit, Integer userType) {
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

}
