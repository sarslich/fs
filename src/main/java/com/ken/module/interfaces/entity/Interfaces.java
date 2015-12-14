package com.ken.module.interfaces.entity;

import java.sql.Timestamp;

import com.ken.core.entity.Entity;

public class Interfaces extends Entity<String> {
	private String interfaceName;
	private String description;
	private String parameter;
	private String busModel;//'所属的业务模块ID'
	private String isUsable;//'是否启用（0:启用，1:禁用）',
	private String modifyBy;//'操作人',
	private Timestamp modifyTime;// '操作时间',
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getBusModel() {
		return busModel;
	}
	public void setBusModel(String busModel) {
		this.busModel = busModel;
	}
	public String getIsUsable() {
		return isUsable;
	}
	public void setIsUsable(String isUsable) {
		this.isUsable = isUsable;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
		
}
