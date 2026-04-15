package com.practice.SpringBoot.handler;

import com.practice.SpringBoot.Services.JwtService;
import com.practice.SpringBoot.Services.UserService;
import com.practice.SpringBoot.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${deploy.env}")
    private String deployEnv;

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();

//        log.info(oAuth2User.getAttribute("email").toString());
//        log.info(oAuth2User.getAttribute("email").toString());


        // If the user login for first time

        String email = oAuth2User.getAttribute("email");
        User user = userService.getUserByEmail(email);

        if (user == null) {
            User newUser = User.builder()
                    .name(oAuth2User.getName())
                    .email(email)
                    .build();
            user = userService.save(newUser);
        }

        String accesstoken = jwtService.generateAcessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);


        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv)); // Only when https request (only if the deployEnv is production)
        response.addCookie(cookie);

        String frontEndUrl = "http://localhos:8080/home.html?token"+accesstoken;

        // redirect StRATEGY
        getRedirectStrategy().sendRedirect(request, response, frontEndUrl);

        // or redirect strategy
//        response.sendRedirect(frontEndUrl);
    }
}
