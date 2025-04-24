package com.avalara.avalara.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@ConditionalOnClass({AuthenticationManager.class,
        EnableWebSecurity.class, AuthenticationEntryPoint.class})
@ConditionalOnWebApplication
public class JwtPropagationAutoConfiguration {
    @Configuration
    @ConditionalOnClass(RestTemplate.class)
    protected static class JwtRestPropagationConfiguration {
        @Bean
        public JwtPropagrationInterceptor jwtPropagrationInterceptor() {
            return new JwtPropagrationInterceptor();
        }
    }
        // Configuration for RestTemplate

        @Configuration
        @ConditionalOnClass(RestTemplate.class)
        @AutoConfigureAfter(JwtRestPropagationConfiguration.class)
        protected static class JwtPropagationInterceptorConfiguration {

            @Autowired(required = false)
            private Collection<RestTemplate> restTemplates;

            @Autowired
            private JwtPropagrationInterceptor jwtPropagrationInterceptor;

            @PostConstruct
            public void init() {
                if(this.restTemplates != null) {
                    this.restTemplates.forEach(restTemplate -> {
                        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(restTemplate.getInterceptors());
                        interceptors.add(this.jwtPropagrationInterceptor);
                        restTemplate.setInterceptors(interceptors);
                    });
                }
            }
        }
}
