package com.ken.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**   
 * @Author： junzhou52@hotmail.com   
 
 * @Date： 2015-6-3 下午4:39:02 
 
 * @Description: Spring工具类。
*/ 
@Component
public class SpringUtils implements ApplicationContextAware {
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) {
		SpringUtils.context = context;
	}

	/**
	 * 从Spring容器中获取指定名称的Bean。
	 * 
	 * @param beanName
	 *            bean名称
	 * @return 返回指定名称的Bean。
	 */
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

}
