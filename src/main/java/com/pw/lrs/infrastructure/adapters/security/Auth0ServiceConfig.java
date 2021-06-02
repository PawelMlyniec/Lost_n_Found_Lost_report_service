package com.pw.lrs.infrastructure.adapters.security;

import com.pw.lrs.infrastructure.adapters.auth0.Auth0Api;
import com.pw.lrs.infrastructure.adapters.retrofit.RetrofitClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="spring.auth0.api")
@RequiredArgsConstructor
public class Auth0ServiceConfig {

    private final RetrofitClient retrofitClient;
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    @Bean
    Auth0Api auth0Api() {
        return retrofitClient.getRetrofitClient(url)
            .create(Auth0Api.class);
    }
}
