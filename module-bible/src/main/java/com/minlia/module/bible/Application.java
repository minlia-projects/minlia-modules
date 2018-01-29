package com.minlia.module.bible;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

//  是在@ResponseBody转换json的时候不打印null的内容
//  @JsonInclude(JsonInclude.Include.NON_NULL)
//  @Bean
//  public ObjectMapper jsonMapper() {
//    ObjectMapper objectMapper = new ObjectMapper();
//    // null 不输出
//    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//    return objectMapper;
//  }

}
