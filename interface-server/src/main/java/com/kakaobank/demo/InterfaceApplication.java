package com.kakaobank.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class InterfaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InterfaceApplication.class, args);
    }
}
