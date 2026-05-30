package com.alfred_core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig { //CAN BE CONSIDERED AS A SINGLE USER

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}