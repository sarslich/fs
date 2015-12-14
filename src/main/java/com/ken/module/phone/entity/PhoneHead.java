package com.ken.module.phone.entity;

public class PhoneHead {
	private  String phoneSeg;// '号段',
	private  String province;// '省份编码',
	private  String provinceName;// '省份名称',
	private	 String operator;// '所属运营商（0：无；1：电信；2：移动；3：联通）',
	private  String modifyBy;
	private  String modifyTime;
	public String getPhoneSeg() {
		return phoneSeg;
	}
	public void setPhoneSeg(String phoneSeg) {
		this.phoneSeg = phoneSeg;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
}
