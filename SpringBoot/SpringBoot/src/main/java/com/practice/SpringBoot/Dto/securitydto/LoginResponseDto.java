package com.practice.SpringBoot.Dto.securitydto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponseDto {
    private Long id;
    private String accesstoken;
    private String refreshtoken;
}
