package com.ken.core.security.entity;

import com.ken.core.entity.Entity;




/**   
 * @Author�� junzhou52@hotmail.com   
 
 * @Date�� 2015-6-16 ����2:16:57 
 
 * @Description: Ȩ��/��ɫ����
*/ 
public class Authorities extends Entity<String>{ 
	private static final long serialVersionUID = -4807921828956337103L;
	
	private String authority;
	private String userId;
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
