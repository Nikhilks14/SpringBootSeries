package com.practice.SpringBoot.Dto.securitydto;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
