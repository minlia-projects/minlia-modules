package com.microsoft.crm.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.microsoft.crm.v1")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
