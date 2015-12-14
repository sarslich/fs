package com.ken.core.web.action;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.struts2.convention.annotation.Action;

import com.ken.core.web.captcha.CaptchaImageConfig;
import com.ken.core.web.captcha.CaptchaImageGenerator;
import com.ken.core.web.constant.Constant;


public class CaptchaAction extends GenericAction {
	
	private static final long serialVersionUID = -8400979200093082055L;

	@Resource
	protected CaptchaImageConfig captchaImageConfig;
	@Resource
	protected CaptchaImageGenerator captchaImageGenerator;
	
	private String captcha;
	
	/**
	 * 
	 * 输出验证码图片。
	 * 
	 * @return 不返回页面，直接输出。
	 * @throws Exception
	 *             图片输出失败时抛出异常。
	 */
	@Action(value = "captcha-code-image")
	public String captchaCode() throws Exception {
		ImageIO.write(generateImage(), "JPEG",
				response.getOutputStream());
		return NONE;
	}

	/**
	 * 生成随机验证码字符串。
	 * 
	 * @return 返回随机验证码字符串。
	 */
	private String generateCode() {
		char[] chars = captchaImageConfig.getChars().toCharArray();
		StringBuffer challengeString = new StringBuffer();
		for (int i = 0; i < captchaImageConfig.getLength(); i++) {
			double randomValue = Math.random();
			int randomIndex = (int) Math
					.round(randomValue * (chars.length - 1));
			char characterToShow = chars[randomIndex];
			challengeString.append(characterToShow);
		}
		return challengeString.toString();
	}
	
	/**
	 * 生成验证码图片。
	 * 
	 * @return 返回验证码图片。
	 */
	public BufferedImage generateImage() {
		captcha = generateCode();
		this.request.getSession().setAttribute(Constant.SESSION_GENERATED_CAPTCHA_KEY, captcha);
		return captchaImageGenerator.generateImage(captcha);
	}
	
	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}
