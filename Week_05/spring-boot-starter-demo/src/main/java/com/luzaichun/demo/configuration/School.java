package com.luzaichun.demo.configuration;


public class School{
    Klass class1;
    Student student;

    public Klass getClass1() {
        return class1;
    }

    public void setClass1(Klass class1) {
        this.class1 = class1;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "School{" +
                "class1=" + class1 +
                ", student=" + student +
                '}';
    }
}
