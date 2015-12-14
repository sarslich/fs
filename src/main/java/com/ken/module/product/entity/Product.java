package com.ken.module.product.entity;

import java.sql.Date;

import com.ken.core.entity.Entity;

public class Product extends Entity<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 978134012019353686L;
	private  String  prodName;// '产品名称',
	private  String  prodCode;// '产品编码',
	private  String  prodDesc;// '产品描述',
	private  String  operator;//'运营商类型:0无;1:电信;2:移动;3:联通',
	private  Double  price;// '价格',
	private  String  status;//'状态 0 有效 1 无效',
	private  Date    createTime;//'创建日期',
	private  String  createName;// '创建人',
	private  Date    updateTime;// '修改日期',
	private  String  updateName;//'修改人',
	private  Integer  productSize;
	private  String  channelId;
	private  String  province;
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getProdDesc() {
		return prodDesc;
	}
	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public Integer getProductSize() {
		return productSize;
	}
	public void setProductSize(Integer productSize) {
		this.productSize = productSize;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}

}
