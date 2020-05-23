package com.minlia.modules.rebecca;

import com.minlia.modules.rebecca.annotation.EnablePermissionCollector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnablePermissionCollector
@SpringBootApplication/*(scanBasePackages = {"com.minlia"})*/
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(com.minlia.modules.rebecca.Application.class, args);
    }

}
