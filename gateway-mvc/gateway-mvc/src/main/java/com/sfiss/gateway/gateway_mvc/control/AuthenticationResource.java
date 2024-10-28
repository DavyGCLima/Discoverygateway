package com.sfiss.gateway.gateway_mvc.control;

import com.sfiss.gateway.gateway_mvc.DTO.Login;
import com.sfiss.gateway.gateway_mvc.DTO.TokenDetailsDTO;
import com.sfiss.gateway.gateway_mvc.service.AuthService;
import com.sfiss.gateway.gateway_mvc.service.TokenDetailsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationResource {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";

    private final AuthService authService;

    private final TokenDetailsService tokenDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDetailsDTO> authorize(HttpServletResponse response, @Valid @RequestBody Login login) {
        //01 - Receive the token from AuthService
        String token = authService.login(login);

        //02 - Set the token as a response using JwtAuthResponse Dto class
//        TokenDetailsDTO authResponseDto = new TokenDetailsDTO();
//        authResponseDto.setToken(token);

        TokenDetailsDTO authResponseDto = tokenDetailsService.getTokenDetails(token);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION_HEADER, BEARER + token);
        setTokenResponse(token, response);

        //03 - Return the response to the user
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    private void setTokenResponse(String token, HttpServletResponse response) {
        response.setHeader(AUTHORIZATION_HEADER, BEARER + token);
        Cookie cookie = new Cookie("jwt-token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }


}
