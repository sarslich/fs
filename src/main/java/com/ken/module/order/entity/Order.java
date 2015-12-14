package com.ken.module.order.entity;

import java.sql.Timestamp;

import com.ken.core.entity.Entity;

public class Order extends Entity<String>   {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2463755125238929089L;
	private String   phoneNo;
	private String   productCode;
	private Timestamp   orderTime;
	private Timestamp   openTime;
	private String   batchId;//'批量订购ID(弱关联vas_batch_order表)',
	private String   customerId;
	private String   province;
	private String   channel;//'渠道',
	private String   status;// '状态:0受理中 1订购中 7订购成功 8订购失败',
	private String   message;
	private String   operator;// '所属运营商（0：无；1：电信；2：移动；3：联通）',
	private String   isPay;
	private String   orderFrom;//'开通渠道 0 web 1 接口',
	private String   callbackUrl;// '回调地址',
	private Double  orderPrice;// '订购价格',
	private Double   sellPrice;// '销售价格',
	private String   prodId; //'平台产品表ID',
	private Integer  productSize;// '产品规格',
	private Double   customerDiscount;//'给客户的折扣',
	private String   prodName;
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Timestamp getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}
	public Timestamp getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Timestamp openTime) {
		this.openTime = openTime;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getIsPay() {
		return isPay;
	}
	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}
	public String getOrderFrom() {
		return orderFrom;
	}
	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public Double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}
	public Double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public Integer getProductSize() {
		return productSize;
	}
	public void setProductSize(Integer productSize) {
		this.productSize = productSize;
	}
	public Double getCustomerDiscount() {
		return customerDiscount;
	}
	public void setCustomerDiscount(Double customerDiscount) {
		this.customerDiscount = customerDiscount;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	
}
