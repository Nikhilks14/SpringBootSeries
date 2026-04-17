package com.practice.SpringBoot.Dto.securitydto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenPairDto {
    private final String accessToken;
    private final String refreshToken;
}