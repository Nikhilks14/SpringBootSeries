package com.practice.SpringBoot.Services;

import com.practice.SpringBoot.Dto.securitydto.LoginDto;
import com.practice.SpringBoot.Dto.securitydto.LoginResponseDto;
import com.practice.SpringBoot.Dto.securitydto.TokenPairDto;
import com.practice.SpringBoot.entity.Session;
import com.practice.SpringBoot.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;

    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String accesstoken = jwtService.generateAcessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        sessionService.generateNewSession(user, refreshToken);

        log.info("Access token and refresh token generated for user: {}", user.getId());
        return new LoginResponseDto(user.getId(), accesstoken, refreshToken);
    }

    @Transactional
    public TokenPairDto refreshToken(String refreshToken) {

        // 1. Verify JWT signature + expiry FIRST before trusting anything inside it
        if(!jwtService.isTokenValid(refreshToken)){
            throw new AuthenticationServiceException("Invalid or Expired refresh token");
        }

        // 2. Now safe to extract claims
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userService.getUserById(userId);

        // 3. Check session exists in DB
        sessionService.validateSession(refreshToken);

        // 4. Generate BOTH tokens correctly
        String newAccessToken = jwtService.generateAcessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        sessionService.rotateSession(refreshToken, newRefreshToken);
        log.info("Access token and refresh token generated for user: {}", user.getId());

        return new TokenPairDto(newAccessToken , newRefreshToken);
    }

    public String extractRefreshTokenFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            throw new AuthenticationServiceException("No cookies found");
        }
        return Arrays.stream(cookies)
                .filter( c -> "refreshToken".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow( ()-> new AuthenticationServiceException("No refresh token found"));
    }
}
