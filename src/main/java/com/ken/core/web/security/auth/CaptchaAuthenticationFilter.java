package com.ken.core.web.security.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.ken.core.web.constant.Constant;
import com.ken.core.web.security.auth.support.AuthCounter;
import com.ken.core.web.security.exception.CaptchaException;


public class CaptchaAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter {
	public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "j_captcha";
	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "j_password";

	public static final String SESSION_GENERATED_CAPTCHA_KEY = Constant.SESSION_GENERATED_CAPTCHA_KEY;
	public static final String SESSION_AUTHCOUNTER_KEY = Constant.SESSION_AUTHCOUNTER_KEY;
	public static final String SESSION_LOGIN_MSG_KEY = Constant.SESSION_LOGIN_MSG_KEY;
	
	private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;
	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
	private boolean postOnly = true; 
	
	private AuthCounter authCounter;

	public CaptchaAuthenticationFilter() {
		super("/j_spring_security_check");
	}

	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {

//		if (postOnly && !request.getMethod().equals("POST")) {
//			throw new AuthenticationServiceException(
//					"Authentication method not supported: "
//							+ request.getMethod());
//		}

		authCounter = obtainAuthCounter(request);
		
		if (authCounter != null && authCounter.isOver()) {
			String genCode = this.obtainGeneratedCaptcha(request);
			String inputCode = this.obtainCaptcha(request);
			if (genCode == null){
				String msg = this.messages
						.getMessage("LoginAuthentication.captchaInvalid");
				setMessage(request,msg);
				throw new CaptchaException("LoginAuthentication captchaInvalid");

			}
			if (!genCode.equalsIgnoreCase(inputCode)) {
				String msg = this.messages
						.getMessage("LoginAuthentication.captchaNotEquals");
				setMessage(request,msg);
				throw new CaptchaException( "LoginAuthentication captchaNotEquals" );
			}
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		try {
			return this.getAuthenticationManager().authenticate(authRequest);
		} catch (AuthenticationException ae) {
			if (authCounter == null)
				authCounter = new AuthCounter();
			authCounter.add();
			setupAuthCounter(request,authCounter);
			throw ae;
		}
	}

	protected String obtainCaptcha(HttpServletRequest request) {
		return request.getParameter(this.captchaParameter);
	}

	protected String obtainGeneratedCaptcha(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(
				SESSION_GENERATED_CAPTCHA_KEY);
	}

	protected AuthCounter obtainAuthCounter(HttpServletRequest request) {
		return (AuthCounter) request.getSession().getAttribute(
				SESSION_AUTHCOUNTER_KEY);
	}

	protected void setupAuthCounter(HttpServletRequest request,
			AuthCounter authCounter) {
		request.getSession().setAttribute(SESSION_AUTHCOUNTER_KEY, authCounter);
	}

	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParameter);
	}

	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

	protected void setDetails(HttpServletRequest request,
			UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource
				.buildDetails(request));
	}

	protected void setMessage(HttpServletRequest request,
			String msg) {
		request.getSession().setAttribute(SESSION_LOGIN_MSG_KEY, msg);
	}
	
	public String getCaptchaParameter() {
		return captchaParameter;
	}

	public void setCaptchaParameter(String captchaParameter) {
		this.captchaParameter = captchaParameter;
	}

	public String getUsernameParameter() {
		return usernameParameter;
	}

	public void setUsernameParameter(String usernameParameter) {
		this.usernameParameter = usernameParameter;
	}

	public String getPasswordParameter() {
		return passwordParameter;
	}

	public void setPasswordParameter(String passwordParameter) {
		this.passwordParameter = passwordParameter;
	}

	public boolean isPostOnly() {
		return postOnly;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public AuthCounter getAuthCounter() {
		return authCounter;
	}

	public void setAuthCounter(AuthCounter authCounter) {
		this.authCounter = authCounter;
	}

}
