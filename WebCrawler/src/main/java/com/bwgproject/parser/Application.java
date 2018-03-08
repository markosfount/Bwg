package com.bwgproject.parser;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Application {

    public static void main(String... args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        BwgService bwgService = context.getBean(BwgService.class);
        bwgService.run();
    }

}
