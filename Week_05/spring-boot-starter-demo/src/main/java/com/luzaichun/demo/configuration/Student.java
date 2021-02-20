package com.luzaichun.demo.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author:luzaichun
 * @Date:2021/2/20
 * @Time:22:53
 **/
@ConfigurationProperties("school.student")
public class Student {

    private Integer id = 1;
    private String name = "xiaohong";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
