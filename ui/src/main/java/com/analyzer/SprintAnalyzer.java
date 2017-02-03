package com.analyzer;

import com.analyzer.web.IndexController;
import com.analyzer.web.JiraController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.ws.rs.ApplicationPath;

//@EnableWebSecurity
@SpringBootApplication
@ApplicationPath("/api")
public class SprintAnalyzer extends ResourceConfig {

    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    private static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
        public SecurityConfiguration(){}

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                .antMatchers("/**")
                .authenticated()
//                .antMatchers("/index.html").permitAll()
                .and()
                .httpBasic();

        }
    }

    public SprintAnalyzer(){
        register(IndexController.class);
        register(JiraController.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SprintAnalyzer.class, args);
    }
}
