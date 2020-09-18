package com.minlia.module.rebecca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnablePermissionCollector
@SpringBootApplication/*(scanBasePackages = {"com.minlia"})*/
public class RebeccaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RebeccaApplication.class, args);
    }

}
