package com.zhu.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.zhu.config
 * 2019/3/16
 *
 * XML 配置文件中表示依赖类的 Bean 标签
 * name —— 生成的依赖元素的命名
 * className —— 生成的依赖元素的路径
 * propertyList —— 对应多个 property 标签的列表
 */
public class Bean {

    private String name;
    private String className;
    private String scope = "singleton";
    private List<Property> propertyList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
