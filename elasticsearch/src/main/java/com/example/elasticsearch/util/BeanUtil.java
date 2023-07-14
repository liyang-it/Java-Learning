package com.example.elasticsearch.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <h2>获取 bean工具类</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月11日 15:48
 */
@Component
public class BeanUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	
	public static <T> T getBean(Class<T> beanClass) {
		return applicationContext.getBean(beanClass);
	}
	
	public static <T> T getBean(String beanName, Class<T> beanClass) {
		return applicationContext.getBean(beanName, beanClass);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}
}
