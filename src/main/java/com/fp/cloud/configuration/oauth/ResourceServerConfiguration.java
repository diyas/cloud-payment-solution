package com.fp.cloud.configuration.oauth;


import com.fp.cloud.handler.AuthExceptionEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

@Configuration
public class ResourceServerConfiguration {

    private static final String RESOURCE_ID = "rest-api";

    @Autowired
    private DataSource redisConnectionFactory;

    @Configuration
    @Order(10)
    protected class NonOauthResources extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/auth/login").permitAll()
                    .antMatchers("/**").permitAll()
                    .and().anonymous();
        }
    }

    @Configuration
    @EnableResourceServer
    protected class ResourceServerConfig extends ResourceServerConfigurerAdapter {

        @Autowired
        Oauth2Properties properties;


        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources
                    .tokenServices(tokenServices())
                    .resourceId(RESOURCE_ID)
                    .authenticationEntryPoint(new AuthExceptionEntryPoint());
        }

        @Bean
        public TokenStore tokenStore() {
            return new JdbcTokenStore(redisConnectionFactory);
        }

        @Bean
        public DefaultTokenServices tokenServices(){
            DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
            defaultTokenServices.setTokenStore(tokenStore());
            return defaultTokenServices;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.anonymous().disable()
                    .authorizeRequests()
                    .antMatchers("/private").access("hasRole('CLIENT')")
                    .antMatchers("/private").access("hasRole('USER')")
                    .antMatchers("/api/**").access("hasRole('ADMIN')")
                    .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
        }
    }
}
