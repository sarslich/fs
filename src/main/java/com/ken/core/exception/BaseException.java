package com.ken.core.exception;

import com.ken.core.exception.source.ExceptionSource;

/**   
 * @Author： junzhou52@hotmail.com   
 
 * @Date： 2015-7-21 下午1:56:03 
 
 * @Description: 异常基类
*/ 
@SuppressWarnings("serial")
public abstract class BaseException extends RuntimeException {
	private Long key;
	
	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(Long key, String message) {
		super(message);
		this.key = key;		
	}
	
	public BaseException(Long key, Throwable cause) {
		super(cause);
		this.key = key;		
	}
	
	public BaseException(String message, Throwable cause) {
		super(message,cause);
	}
	
	public abstract ExceptionSource getExceptionSource();
	
	public Long getKey(){
		return this.key;
	}
}
