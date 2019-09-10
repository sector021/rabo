package com.rabo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
public class RaboStatementProcessor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(RaboStatementProcessor.class, args);

	}

}
