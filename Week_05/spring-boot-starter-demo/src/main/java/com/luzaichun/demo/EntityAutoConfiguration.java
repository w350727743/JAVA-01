package com.luzaichun.demo;

import com.luzaichun.demo.configuration.School;
import com.luzaichun.demo.configuration.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:luzaichun
 * @Date:2021/2/20
 * @Time:23:18
 **/
@Configuration
@EnableConfigurationProperties(Student.class)
public class EntityAutoConfiguration  {

    @Autowired
    private Student student;

    @Bean
    @ConditionalOnMissingBean
    public School school(){
        School school = new School();
        school.setStudent(student);
        return school;
    }
}
