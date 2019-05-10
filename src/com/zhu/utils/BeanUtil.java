package com.zhu.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * 2019/5/9
 * com.zhu.utils
 */
public class BeanUtil {
	/**
	 *
	 * @param beanObj 对象
	 * @param name 对象对应的属性名称
	 * @return Method
	 */
	public static Method getWriteMethod(Object beanObj,String name){
		Method method = null;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(beanObj.getClass());
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			if (descriptors != null){
				for (PropertyDescriptor propertyDescriptor : descriptors){
					if (name.equals(propertyDescriptor.getName())){
						method = propertyDescriptor.getWriteMethod();
					}
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		if (method == null){
			throw new RuntimeException("please check class Set() method in "+name);
		}
		return method;
	}
}
