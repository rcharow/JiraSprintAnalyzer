package com.analyzer;

import com.analyzer.web.JiraController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.ws.rs.ApplicationPath;

@SpringBootApplication
@ApplicationPath("/api")
public class SprintAnalyzer extends ResourceConfig {

    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/index.html", "/js/views/home.html").permitAll()
                    .and()
                    .httpBasic()
                    .disable();

        }
    }

    public SprintAnalyzer(){
        register(JiraController.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SprintAnalyzer.class, args);
    }
}
