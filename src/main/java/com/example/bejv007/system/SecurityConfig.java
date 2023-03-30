package com.example.bejv007.system;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private static final String[] AUTH_ALLOWLIST = {
            // -- Swagger UI v3
            "/v3/api-docs/**",
            "v3/api-docs/**",
            "/swagger-ui/**",
            "swagger-ui/**",
            "/swagger-ui.html",
            "swagger-ui.html",
            // Actuators
            "/actuator/**",
            "/health/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .httpBasic().and()
                .authorizeHttpRequests(httpRequests ->
                        httpRequests
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers(AUTH_ALLOWLIST).permitAll()
                                .requestMatchers("/users").permitAll()
                                .anyRequest().authenticated()
                )
                .build();
//                .authorizeHttpRequests().anyRequest().permitAll()
//                .and().build(); //Todas Requests permitidas
                //.authorizeHttpRequests().requestMatchers("/users").permitAll().and().build(); Só /users no caso
                //.anyRequest().authenticated()

    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(new AntPathRequestMatcher("swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("v3/api-docs/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                        .anyRequest().authenticated())
                .httpBasic();
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManagerBean();
    }


}
