package com.minlia.module.encryptbody;

import com.minlia.module.encryptbody.annotation.EnableEncryptBody;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEncryptBody
@SpringBootApplication
public class EncryptbodyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncryptbodyApplication.class, args);
    }

}