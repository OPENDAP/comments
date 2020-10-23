package org.opendap.rest.controller;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .regexMatcher("^\\/login$|^\\/feedback\\/(form|database)$")
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                //.and().oauth2Login()
        ;
    }

    //@Override
    protected void Mconfigure(HttpSecurity http) throws Exception {
        http
                .regexMatcher("^\\/.*$")
                .authorizeRequests()
                .antMatchers("/", "/logout", "/index", "/css/**",  "/images/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")
                .and().oauth2Login()


        ;
    }
}
