package com.practice.SpringBoot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    DefaultSecurityFilterChain securityWebFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
//                .formLogin(form -> form.permitAll()) // permit all
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html").permitAll()
                                .requestMatchers("/posts").permitAll()
                                .requestMatchers("/posts/**").hasAnyRole("ADMIN")
                                .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // create(always create) and use the session (use condition based)
                .formLogin(Customizer.withDefaults()) ; // Opens Default login page
//                .formLogin(formLoginConfig -> formLoginConfig.loginPage("/login.html")) // custom form login page , html page inside static

        return httpSecurity.build();
    }

    // for a now we create and use userDetails service user

    @Bean
    UserDetailsService myInMemeoryUserDetailsService() {
        UserDetails user1 = User
                .withUsername("user1")
                .password(passwordEncoder().encode("pass"))
                .roles("USER")
                .build();

        UserDetails user2 = User
                .withUsername("user2")
                .password(passwordEncoder().encode("pass"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
