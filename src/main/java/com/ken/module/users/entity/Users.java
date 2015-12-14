package com.ken.module.users.entity;

import com.ken.core.security.entity.SecurityUser;

/**   
 * @Author： junzhou52@hotmail.com   
 
 * @Date： 2015-7-8 上午10:10:07 
 
 * @Description: User domain module entity
*/ 
public class Users extends SecurityUser {
	private static final long serialVersionUID = -5264539705623870398L;
	private String realName;
	private String deptId;
	private String deptName;
	private Long vversion;
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Long getVversion() {
		return vversion;
	}
	public void setVversion(Long vversion) {
		this.vversion = vversion;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
}
