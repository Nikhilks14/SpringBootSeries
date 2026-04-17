package com.practice.SpringBoot.config;

import com.practice.SpringBoot.Filter.JwtAuthFilter;
import com.practice.SpringBoot.entity.enums.Role;
import com.practice.SpringBoot.handler.Oauth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static com.practice.SpringBoot.entity.enums.Role.ADMIN;
import static com.practice.SpringBoot.entity.enums.Role.CREATOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter  jwtAuthFilter;
    private final Oauth2SuccessHandler  oauth2SuccessHandler;

    private static final String[] publicRoutes ={
            "/auth/**", "/error", "/home.html",
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    };


    @Bean
    DefaultSecurityFilterChain securityWebFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
//                .formLogin(form -> form.permitAll()) // permit all
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(publicRoutes).permitAll()
                                .requestMatchers(HttpMethod.GET, "/posts").permitAll()
                                .requestMatchers(HttpMethod.GET, "/posts/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyRole(ADMIN.name(), CREATOR.name())
                                .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // create(always create) and use the session (use condition based)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Config -> oauth2Config
                        .failureUrl("/login?error=true")
                        .successHandler(oauth2SuccessHandler));

//                .formLogin(Customizer.withDefaults()) ; // Opens Default login page
//                .formLogin(formLoginConfig -> formLoginConfig.loginPage("/login.html")) // custom form login page , html page inside static

        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // for a now we create and use userDetails service user

//    @Bean
//    UserDetailsService myInMemeoryUserDetailsService() {
//        UserDetails user1 = User
//                .withUsername("user1")
//                .password(passwordEncoder().encode("pass"))
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User
//                .withUsername("user2")
//                .password(passwordEncoder().encode("pass"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }


}
