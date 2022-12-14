package com.pw.lrs.infrastructure.adapters.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    String jwkSetUri;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        setupAuth0(http);
        permitManagementEndpoints(http);
        permitSwaggerUi(http);
        permitPublicEndpoints(http);
        secureAnyOtherEndpoint(http);
        setupCors(http);
        disableCsrf(http);
    }

    private void setupAuth0(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

    private void permitManagementEndpoints(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .requestMatchers(forPort(8081))
            .permitAll();
    }

    private void permitSwaggerUi(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/swagger-ui.html", "/v3/**", "/swagger-ui/**")
            .permitAll();
    }

    private void permitPublicEndpoints(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/lostReports", "/lostReports/searches")
                .permitAll()
            .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/lostReports", "/lostReports/**")
                .permitAll()
            .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/foundReports", "/foundReports/searches", "/foundReports/matching")
                .permitAll()
            .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/foundReports", "/foundReports/**")
                .permitAll();
        // @formatter:on
    }

    private void secureAnyOtherEndpoint(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest()
            .authenticated();
    }

    private void setupCors(HttpSecurity http) throws Exception {
        http.cors();
    }

    private void disableCsrf(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    private RequestMatcher forPort(final int port) {
        return (HttpServletRequest request) -> port == request.getLocalPort();
    }
}
