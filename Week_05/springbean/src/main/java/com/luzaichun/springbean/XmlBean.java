package com.luzaichun.springbean;

import com.luzaichun.springbean.entity.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author:luzaichun
 * @Date:2021/2/20
 * @Time:22:25
 **/
public class XmlBean {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:application-context.xml");
        Student student = (Student)applicationContext.getBean("student");
        System.out.println(student.toString());
    }
}
