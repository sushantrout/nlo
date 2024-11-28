package com.nlo;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .additionalInterceptors(addHeaderInterceptor())
                .build();
    }

    private ClientHttpRequestInterceptor addHeaderInterceptor() {
        return (request, body, execution) -> {
            request.getHeaders().add("api_key", "4Jcx!Xp1KQp1jr3x4dg9");
            return execution.execute(request, body);
        };
    }
}

