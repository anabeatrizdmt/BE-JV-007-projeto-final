package com.example.bejv007.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .httpBasic().and()
                .authorizeHttpRequests().anyRequest().permitAll()
                .and().build(); //Todas Requests permitidas
                //.authorizeHttpRequests().requestMatchers("/users").permitAll().and().build(); Só /users no caso
                //.anyRequest().authenticated()

    }

}
