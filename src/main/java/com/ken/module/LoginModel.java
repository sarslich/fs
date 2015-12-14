package com.ken.module;

import java.io.Serializable;


/**   
 * @Author： junzhou52@hotmail.com   
 
 * @Date： 2015-7-8 上午11:03:34 
 
 * @Description: 用户名/密码登录model。
*/ 
public class LoginModel implements Serializable {
	private static final long serialVersionUID = 1347446685662622342L;
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
