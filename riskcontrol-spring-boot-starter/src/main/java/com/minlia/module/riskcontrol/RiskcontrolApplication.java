package com.minlia.module.riskcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.minlia.*"})
public class RiskcontrolApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiskcontrolApplication.class, args);
	}

}