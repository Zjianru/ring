package com.zhu.main;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * 2019/5/9
 * com.zhu.main
 */
public interface BeanFactory {

	/**
	 * 根据对象名称获得相应对象
	 * @param beanName 对象名称
	 * @return 对象
	 */
	Object getBean(String beanName);
}
