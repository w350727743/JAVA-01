package com.luzaichun.springbean;

import com.luzaichun.springbean.entity.School;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:luzaichun
 * @Date:2021/2/20
 * @Time:22:39
 **/
@Configuration
public class ConfigBean {


    @Bean
    public School school() {
        School school = new School();
        school.setSchoolName("xingxingxiaoxue");
        return school;
    }
}
