package com.ken.core.web.security.auth.support;

import java.io.Serializable;



/**   
 * @Author： junzhou52@hotmail.com   
 
 * @Date： 2015-7-21 下午2:45:32 
 
 * @Description: 验证次数计数器。
*/ 

public class AuthCounter implements Serializable {
 
	private static final long serialVersionUID = -2578035396498220153L;
	private Integer allowRetries = 1;
	private Integer retries = 0;

	/**
	 * 增加验证次数计数。
	 */
	public void add() {
		retries++;
	}

	/**
	 * 判断是否已经超过允许的重试次数。
	 * 
	 * @return 如果已超过允许的重试次数返回true，否则返回false。
	 */
	public Boolean isOver() {
		return retries >= allowRetries;
	}

	/**
	 * 验证次数清零。
	 */
	public void clean() {
		retries = 0;
	}

	public void setAllowRetries(Integer allowRetries) {
		this.allowRetries = allowRetries;
	}	
	
}
