package com.donishchenko.contactmanager;

import com.donishchenko.contactmanager.config.HibernateConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(new Object[] {Application.class, HibernateConfig.class}, args);
    }

}
