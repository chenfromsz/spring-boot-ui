package com.test.webui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.test")
public class WebuiApp {
    public static void main(String[] args) {
        SpringApplication.run(WebuiApp.class, args);
    }
}
