package com.bwgproject.parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

// TODO check if all annotations are necessary
@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    public static void main(String... args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        BwgService bwgService = context.getBean(BwgService.class);
        bwgService.run();
    }

}
