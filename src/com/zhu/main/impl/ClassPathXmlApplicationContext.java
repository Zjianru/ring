package com.zhu.main.impl;

import com.zhu.config.Bean;
import com.zhu.config.Property;
import com.zhu.config.parse.ConfigManager;
import com.zhu.main.BeanFactory;
import com.zhu.utils.BeanUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * 2019/5/9
 * com.zhu.main.impl
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

	private Map<String, Bean> config;

	private Map<String, Object> context = new HashMap<>();

	/**
	 * Application Context Container constructor
	 * @param path config path
	 */
	public ClassPathXmlApplicationContext(String path) {
		config = ConfigManager.getConfig(path);
		if (config != null) {
			for (Map.Entry<String, Bean> en : config.entrySet()) {
				String beanName = en.getKey();
				Bean bean = en.getValue();
				Object existBean = context.get(beanName);
				if (existBean == null && "singleton".equals(bean.getScope())) {
					Object beanObj = createBean(bean);
					context.put(beanName, beanObj);
				}
			}
		}

	}

	/**
	 * 根据 BeanName 获取 Bean 实例
	 * @param beanName 对象名称
	 * @return Object Bean
	 */
	@Override
	public Object getBean(String beanName) {
		Object bean = context.get(beanName);
		if (bean == null){
			bean =  createBean(config.get(beanName));
		}
		return bean;
	}

	/**
	 * @param bean 待创建 Bean
	 * @return create finish Object
	 */
	private Object createBean(Bean bean) {

		String className = bean.getClassName();
		Class clazz = null;

		// class not found Error 路径错误
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR [ CLASS NOT FOUND ! please check bean class path in:" + className + " ]");
		}

		// 避免缺少无参构造方法
		Object beanObj = null;
		try {
			beanObj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR [ not found legal Constructor in :" + className + " ]");
		}
		// 开始注入属性
		if (bean.getPropertyList() != null) {
			for (Property property : bean.getPropertyList()) {
				// 普通属性——直接注入
				if (property.getValue() != null) {
					Map<String,String[]> paramMap = new HashMap<>();
					paramMap.put(property.getName(),new String[]{property.getValue()});
					try {
						BeanUtils.populate(beanObj,paramMap);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				if(property.getRef()!=null){
					Method setMethod = BeanUtil.getWriteMethod(beanObj,property.getName());
					Object existBean =  context.get(property.getRef());
					if(existBean == null ){
						existBean = createBean(config.get(property.getRef()));
						if("singleton".equals(config.get(property.getRef()).getScope())){
							context.put(property.getRef(), existBean);
						}
					}
					// 调用set方法注入即可
					try {
						setMethod.invoke(beanObj, existBean);
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException("客官!您的Bean的属性"+property.getName()+"没有对应的set方法,或方法参数不正确"+className);
					}
				}
			}
		}
		return beanObj;
	}
}