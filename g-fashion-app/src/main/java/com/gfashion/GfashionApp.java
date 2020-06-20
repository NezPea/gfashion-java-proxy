package com.gfashion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GfashionApp {

    public static void main(String[] args) {
        SpringApplication.run(GfashionApp.class, args);
    }

}
