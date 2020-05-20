package com.gfashion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GfashionApp {

    public static void main(String[] args) {
        SpringApplication.run(GfashionApp.class, args);
    }

}
