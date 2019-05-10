package com.zhu.config.parse;

import com.zhu.config.Bean;
import com.zhu.config.Property;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.zhu.config.parse
 * 2019/3/16
 */
public class ConfigManager {
    /**
     * getConfig
     * @param path XML 配置文件位置
     * @return Map<String,Bean>
     * KEY——>String  XML 中配置的 Bean 的名字
     * VALUE——>Bean  容器中生成的 Bean 对象
     */
    public static Map<String, Bean> getConfig(String path){
        final String rule = "//bean";
        Map<String,Bean> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        InputStream inputStream = ConfigManager.class.getResourceAsStream(path);
        Document document = null;
        try {
            document = reader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw  new RuntimeException("Please check your config file path！");
        }
        List<Element> list = document.selectNodes(rule);
        if (list!=null){
            for (Element node : list) {
                Bean bean = new Bean();
                bean.setName(node.attributeValue("name"));
                bean.setClassName(node.attributeValue("class"));
                if (node.attributeValue("scope") != null){
                    bean.setScope(node.attributeValue("scope"));
                }
                List<Element>children = node.elements("property");
                if (children!=null){
                    for (Element child : children) {
                        Property property = new Property();
                        property.setName(child.attributeValue("name"));
                        property.setValue(child.attributeValue("value"));
                        property.setRef(child.attributeValue("ref"));
                        bean.getPropertyList().add(property);
                    }
                }
                map.put(node.attributeValue("name"),bean);
            }
        }
        return map;
    }
}
