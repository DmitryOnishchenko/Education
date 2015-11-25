package com.donishchenko.springtest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain {
    public static void main(String[] args) {
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");

        Student student = ctx.getBean(Student.class);

        System.out.println("Name: " + student.getName());
        System.out.println("Age: " + student.getAge());
        System.out.println("Message: " + student.getMessage());

//        AnnotationConfigApplicationContext context =
//                new AnnotationConfigApplicationContext();
//        context.register(HelloWorldConfiguration.class);
//        context.refresh();
//
//        HelloWorld helloWorld = context.getBean(HelloWorld.class);
//        helloWorld.setMessage("Hello World!");
//        helloWorld.getMessage();
    }
}
