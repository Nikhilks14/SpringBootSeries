package com.practice.SpringBoot.controller;

import com.practice.SpringBoot.Dto.securitydto.*;
import com.practice.SpringBoot.Services.AuthService;
import com.practice.SpringBoot.Services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {
        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginIn(@RequestBody LoginDto loginDto,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        LoginResponseDto loginResponseDto = authService.login(loginDto);

        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshtoken() );
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv)); // Only when https request (only if the deployEnv is production)
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDto);
    }

//    @PostMapping("/refresh")
//    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request) {
//        String refreshToken = Arrays.stream(request.getCookies())
//                .filter(cookie -> "refreshToken".equals(cookie.getName()))
//                .findFirst()
//                .map(Cookie::getValue)
//                .orElseThrow(()-> new AuthenticationServiceException("Refresh token not found inside the Cookies"));
//
//        LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);
//        return ResponseEntity.ok(loginResponseDto);
//    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String,String >> refresh(HttpServletRequest request,
                                                       HttpServletResponse response) {

        String oldRefreshToken = authService.extractRefreshTokenFromCookie(request);
       TokenPairDto tokens = authService.refreshToken(oldRefreshToken);

       Cookie cookie = new Cookie("refreshToken", tokens.getRefreshToken());
       cookie.setHttpOnly(true);
       cookie.setSecure("production".equals(deployEnv));
       cookie.setMaxAge(1000 * 60 * 60 * 60);
//       cookie.setMaxAge(1000 * 20 );
       response.addCookie(cookie);

       return ResponseEntity.ok(Map.of("accessToken",  tokens.getAccessToken() ));


    }
}
