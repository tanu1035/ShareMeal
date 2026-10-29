package com.sharemeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ShareMealApplication {

    public static void main(String[] args) {
        // CommandLineRunners (the console menu) run inside run(); exit cleanly when they finish
        ConfigurableApplicationContext context = SpringApplication.run(ShareMealApplication.class, args);
        System.exit(SpringApplication.exit(context));
    }
}
