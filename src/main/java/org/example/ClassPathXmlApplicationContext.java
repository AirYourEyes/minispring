package org.example;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.sql.rowset.spi.XmlReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassPathXmlApplicationContext {
    // 存放 Bean 定义信息
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    // 存放 Bean 实例
    private Map<String, Object> singletons = new HashMap<>();

    public ClassPathXmlApplicationContext(String fileName) {
        readXml(fileName);
        instanceBeans();
    }

    private void readXml(String fileName) {
        SAXReader saxReader = new SAXReader();
        try {
            URL xmlResource = this.getClass().getClassLoader().getResource(fileName);
            Document document = saxReader.read(xmlResource);
            Element rootElement = document.getRootElement();
            // 对配置文件中的每一个 <bean> 进行处理
            for (Element element : rootElement.elements()) {
                String id = element.attributeValue("id");
                String className = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition();
                beanDefinition.setId(id);
                beanDefinition.setClassName(className);
                beanDefinitions.add(beanDefinition);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void instanceBeans() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            String id = beanDefinition.getId();
            String className = beanDefinition.getClassName();
            try {
                Object bean = Class.forName(className).newInstance();
                singletons.put(id, bean);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Object getBean(String id) {
        return singletons.get(id);
    }

}
