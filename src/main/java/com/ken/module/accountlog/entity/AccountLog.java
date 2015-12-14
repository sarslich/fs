package com.ken.module.accountlog.entity;

import java.sql.Timestamp;

import com.ken.core.entity.Entity;

public class AccountLog extends Entity<String> {
	private  String		customerId;// '客户ID',
	private  Double		amount;//'扣除或退款金额（客户充值金额）',  
	private  String		orderId;// '订单ID',
	private  Timestamp	createTime;
	private  String		type;//'类型：0-扣款，1-退款',
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
