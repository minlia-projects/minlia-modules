package com.minlia.modules.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.minlia"})
@MapperScan(basePackages = {"com.minlia.module.*.mapper","com.minlia.modules.*.mapper"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(com.minlia.modules.rbac.Application.class, args);
    }

}
