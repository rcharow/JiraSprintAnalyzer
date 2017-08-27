package com.analyzer;

import com.analyzer.web.AnalysisController;
import com.analyzer.web.IndexController;
import com.analyzer.web.JiraController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.ws.rs.ApplicationPath;

@EnableWebSecurity
@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication
@ApplicationPath("/api")
public class SprintAnalyzer extends ResourceConfig {

//    @RequestMapping("/user")
//    public String user(Principal user) {
//        log.trace("Trying to return user: " + user.getName());
//        return user.getName();
//    }

    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    private static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
        public SecurityConfiguration(){}

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            ///ORIGINAL SETUP FOR NO AUTH
//            http
//                .authorizeRequests()
//                .antMatchers("/**")
//                .authenticated()
////                .antMatchers("/index.html").permitAll()
//                .and()
//                .httpBasic();

            //TRYING TO GET AUTH TO WORK
            http
                    .httpBasic()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated().and()
                    .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        }
    }

    public SprintAnalyzer(){
        register(IndexController.class);
        register(JiraController.class);
        register(AnalysisController.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SprintAnalyzer.class, args);
    }
}
