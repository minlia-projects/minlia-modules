package com.minlia.module.captcha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
@MapperScan(basePackages={"com.minlia.module.*.mapper"})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(com.minlia.module.captcha.Application.class, args);
  }

}
