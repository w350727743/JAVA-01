package com.luzaichun.springbean;

import com.luzaichun.springbean.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author:luzaichun
 * @Date:2021/2/20
 * @Time:22:30
 **/
@Component
public class AnnotationBean{
    @Autowired
    private Student student;

    @PostConstruct
    public void init(){
        System.out.println(student.toString());
    }
}
