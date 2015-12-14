package com.ken.core.web.security.exception;

import org.springframework.security.core.AuthenticationException;

import com.ken.core.exception.source.ExceptionSource;



/**   
 * @Author： junzhou52@hotmail.com   
 
 * @Date： 2015-7-21 下午1:58:44 
 
 * @Description: 验证码异常
*/ 
public class CaptchaException extends AuthenticationException{
 
	private static final long serialVersionUID = -5511683000043175259L;

	public CaptchaException(String message) {
		super(message);
	}
	
	public CaptchaException(String message, Throwable cause) {
		super(message,cause);
	}

	public ExceptionSource getExceptionSource(){
		return ExceptionSource.BIZ;		
	}
}
