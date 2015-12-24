package com.donishchenko.testgame;

import com.donishchenko.testgame.engine.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
public class Application {

    @Autowired
    @Qualifier("engineV3")
    private GameEngine gameEngine;

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.donishchenko.testgame");
        Application app = context.getBean(Application.class);

        app.gameEngine.init();
        app.gameEngine.start();
    }

}
