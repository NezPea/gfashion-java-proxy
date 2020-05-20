package com.gfashion.config;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 1. @author cq
 2. @title: TomcatConfig
 3. @projectName springBootDemo
 4. @description: tomcat特殊字符处理问题
 5. @date 2020/5/5 21:06
 */
@Configuration
public class TomcatConfig {
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            //允许的特殊字符
            connector.setProperty("relaxedQueryChars", "|{}[]");
        });
        return factory;
    }
}