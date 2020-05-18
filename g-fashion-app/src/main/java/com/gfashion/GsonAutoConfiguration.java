package com.gfashion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;

@Configuration
@Slf4j
public class GsonAutoConfiguration {

    @Bean
    public Gson restTemplate() {
        return new Gson();
    }

}
