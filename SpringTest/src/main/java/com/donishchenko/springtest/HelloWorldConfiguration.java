package com.donishchenko.springtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfiguration {

    @Bean
    public HelloWorld helloWorld() {
        return new HelloWorld();
    }
}
