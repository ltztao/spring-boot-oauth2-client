package com.example.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthAccessDeniedHandler deniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("http://localhost:8081/auth/logout")
                .and()
                //配置没有权限的自定义处理类
                .exceptionHandling()
                .accessDeniedHandler(deniedHandler);

        http.csrf().disable();

        // security 不开启 登录验证
        http.httpBasic().disable();
    }
}