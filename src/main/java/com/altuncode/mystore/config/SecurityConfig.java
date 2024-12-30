package com.altuncode.mystore.config;

import com.altuncode.mystore.services.admin.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Value;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Value("${app.security.rememberme.key}")
    private String rememberMeKey;


    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(@Qualifier("PersonDetailsService") PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Bean("PasswordEncoderBean")
    public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/admin/**")
                .csrf(withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().hasRole("ADMIN")
                )
                .formLogin(withDefaults())
                .logout(withDefaults())
                .rememberMe(remember -> remember
                        .key(rememberMeKey)
                        .tokenValiditySeconds(360 * 24 * 60 * 60)
                        .userDetailsService(personDetailsService)
                        .useSecureCookie(true)
                        .alwaysRemember(true)
                );

        return http.build();
    }

    
}
