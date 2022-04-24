package com.minlia.module.realname;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.minlia.*")
public class MinliaRealNameApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinliaRealNameApplication.class, args);
	}

}
