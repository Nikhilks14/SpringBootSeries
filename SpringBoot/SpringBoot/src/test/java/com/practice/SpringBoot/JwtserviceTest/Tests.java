package com.practice.SpringBoot.JwtserviceTest;

import com.practice.SpringBoot.Services.JwtService;
import com.practice.SpringBoot.entity.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Tests {

    @Autowired
    private JwtService jwtService;

    @Test
    void contextLoads() {
        User user = new User(4L,"Nikhil","nikhil@gmail.com", "1234");

        String token = jwtService.generateAcessToken(user);

        System.out.println(token);

        Long id = jwtService.getUserIdFromToken(token);
        System.out.println(id);
    }



}
