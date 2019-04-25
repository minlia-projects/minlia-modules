package com.minlia.modules.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication/*(scanBasePackages = {"com.minlia"})*/
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(com.minlia.modules.rbac.Application.class, args);
    }

}
