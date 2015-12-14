package com.ken.core.web.action;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @Author： junzhou52@hotmail.com
 * 
 * @Date： 2015-7-8 上午11:10:58
 * 
 * @Description: 
 *               常规Action基类，实现获取Servlet的request/response/context对象的接口，使其子类Action可以直接使用这些对象
 *               。
 */
public abstract class GenericAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware, ServletContextAware {

	private static final long serialVersionUID = -2319619152887065659L;

	@Autowired
	protected MessageSource messageSource;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ServletContext context;

	/**
	 * 获取配置文字信息。
	 * 
	 * @param code
	 *            配置文字编码
	 * @param vars
	 *            变量
	 * @return 返回配置文字信息。
	 */
	public String getMessage(String code, Locale locale, Object... vars) {
		return messageSource.getMessage(code, vars, locale);
	}
	
	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
