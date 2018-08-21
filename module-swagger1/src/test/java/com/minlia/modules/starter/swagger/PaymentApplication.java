//package com.minlia.modules.starter.swagger;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@SpringBootApplication
//@EnableDevSwagger
//public class PaymentApplication {
//
//  public static void main(String[] args) {
//    SpringApplication.run(PaymentApplication.class, args);
//  }
//
//  @RestController
//  @RequestMapping("/rest")
//  public static class Controller {
//
//    @GetMapping("/{name}")
//    public String sayHello(@PathVariable String name) {
//      return "Hello " + name;
//    }
//  }
//
//}
