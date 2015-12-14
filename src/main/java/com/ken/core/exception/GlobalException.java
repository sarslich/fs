package com.ken.core.exception;

import com.ken.core.exception.source.ExceptionSource;

/**   
 * @Author： junzhou52@hotmail.com   
 
 * @Date： 2015-5-24 上午11:32:58 
 
 * @Description: 全局类异常，通常用在公共组件的异常捕获
 */

public class GlobalException extends BaseException {

	private static final long serialVersionUID = 3941311250101082495L;

	public GlobalException(String message) {
		super(message);
	}
	
	public GlobalException(Long key, String message) {
		super(key,message); 
	}
	
	public GlobalException(Long key, Throwable cause) {
		super(key,cause); 
	}
	
	public GlobalException(String message, Throwable cause) {
		super(message,cause);
	}

	public ExceptionSource getExceptionSource(){
		return ExceptionSource.GLOBAL;
	}
	
}
