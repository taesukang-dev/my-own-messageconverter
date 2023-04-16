package com.example.messageconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class MessageconverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageconverterApplication.class, args);
	}

}
