package org.example.test;

import org.example.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Service service = (Service) applicationContext.getBean("service");
        service.sayHello();
    }
}
