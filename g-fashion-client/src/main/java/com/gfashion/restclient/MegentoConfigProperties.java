package com.gfashion.restclient;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "magento")
@Data
public class MegentoConfigProperties {
    private Map<String, String> url;
    private String username;
    private String password;
}
