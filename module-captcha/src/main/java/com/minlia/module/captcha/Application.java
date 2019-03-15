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

  // 这个方法还是比较实用的，就是在@ResponseBody转换json的时候不打印null的内容
//  @Bean
//  public ObjectMapper jsonMapper() {
//    ObjectMapper objectMapper = new ObjectMapper();
//    // null 不输出
//    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//    return objectMapper;
//  }

}
