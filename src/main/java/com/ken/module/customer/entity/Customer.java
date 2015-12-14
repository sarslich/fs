package com.ken.module.customer.entity;

import java.sql.Date;

import com.ken.core.entity.Entity;
import com.ken.core.repository.ibatis.IBatisDao;

public class Customer extends  Entity<String>{

	private String appKey;
	private String customerName;
	private String securityKey;
	private String linkMan;
	private String contactNumber;
	private String contactEmail;
	private Double amount; //充值余额
	private Double creditAmount;//授信金额
	private Double warnAmount; //预警额度
	private Double gnTeleDiscount;//国内电信产品折扣
	private Double gnCmccDiscount;//国内移动产品折扣
	private Double gnUnicomDiscount;//国内联通产品折扣
	private Double snTeleDiscount;//省内电信产品折扣
	private Double snCmccDiscount;//省内移动产品折扣
	private Double snUnicomDiscount;//省内联通产品折扣
	private String status;
	private String delFlag;
	private Date   createDate;
	private Date   updateDate;
	
 
	
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSecurityKey() {
		return securityKey;
	}
	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}
	public Double getWarnAmount() {
		return warnAmount;
	}
	public void setWarnAmount(Double warnAmount) {
		this.warnAmount = warnAmount;
	}
	public Double getGnTeleDiscount() {
		return gnTeleDiscount;
	}
	public void setGnTeleDiscount(Double gnTeleDiscount) {
		this.gnTeleDiscount = gnTeleDiscount;
	}
	public Double getGnCmccDiscount() {
		return gnCmccDiscount;
	}
	public void setGnCmccDiscount(Double gnCmccDiscount) {
		this.gnCmccDiscount = gnCmccDiscount;
	}
	public Double getGnUnicomDiscount() {
		return gnUnicomDiscount;
	}
	public void setGnUnicomDiscount(Double gnUnicomDiscount) {
		this.gnUnicomDiscount = gnUnicomDiscount;
	}
	public Double getSnTeleDiscount() {
		return snTeleDiscount;
	}
	public void setSnTeleDiscount(Double snTeleDiscount) {
		this.snTeleDiscount = snTeleDiscount;
	}
	public Double getSnCmccDiscount() {
		return snCmccDiscount;
	}
	public void setSnCmccDiscount(Double snCmccDiscount) {
		this.snCmccDiscount = snCmccDiscount;
	}
	public Double getSnUnicomDiscount() {
		return snUnicomDiscount;
	}
	public void setSnUnicomDiscount(Double snUnicomDiscount) {
		this.snUnicomDiscount = snUnicomDiscount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
	
}
