package com.minlia.modules.starter.swagger;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@EnableAutoConfiguration
@SpringBootApplication
public class DemoApp {
 
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApp.class, args);
    }
     
}