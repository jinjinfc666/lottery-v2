package com.jll.entity;
// Generated 2018-8-14 16:52:58 by Hibernate Tools 5.2.10.Final

/**
 * UserInfo generated by hbm2java
 */
public class TbUser implements java.io.Serializable {
	private Integer id;
	private String userName;
	private Double usermoney;
	private Double icemoney;
	private Double creditmoney;
	private String regfrom;
	private String isdaili;
	private String islock;
	private String ip;
	private Double activitymoney;
	
	public TbUser() {
	}

	public TbUser(String userName,Double usermoney,Double icemoney,Double creditmoney,String regfrom,
			String isdaili,String islock,String ip,Double activitymoney) {
		this.userName = userName;
		this.usermoney = usermoney;
		this.icemoney = icemoney;
		this.creditmoney = creditmoney;
		this.regfrom = regfrom;
		this.isdaili = isdaili;
		this.islock = islock;
		this.ip = ip;
		this.activitymoney = activitymoney;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getUsermoney() {
		return usermoney;
	}

	public void setUsermoney(Double usermoney) {
		this.usermoney = usermoney;
	}

	public Double getIcemoney() {
		return icemoney;
	}

	public void setIcemoney(Double icemoney) {
		this.icemoney = icemoney;
	}

	public Double getCreditmoney() {
		return creditmoney;
	}

	public void setCreditmoney(Double creditmoney) {
		this.creditmoney = creditmoney;
	}

	public String getRegfrom() {
		return regfrom;
	}

	public void setRegfrom(String regfrom) {
		this.regfrom = regfrom;
	}

	public String getIsdaili() {
		return isdaili;
	}

	public void setIsdaili(String isdaili) {
		this.isdaili = isdaili;
	}

	public String getIslock() {
		return islock;
	}

	public void setIslock(String islock) {
		this.islock = islock;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Double getActivitymoney() {
		return activitymoney;
	}

	public void setActivitymoney(Double activitymoney) {
		this.activitymoney = activitymoney;
	}

	
	
}
