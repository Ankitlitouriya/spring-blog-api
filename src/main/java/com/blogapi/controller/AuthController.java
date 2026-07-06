package com.blogapi.controller;

import com.blogapi.dto.request.LoginRequestDto;
import com.blogapi.dto.request.LogoutRequest;
import com.blogapi.dto.request.RefreshTokenRequest;
import com.blogapi.dto.request.RegisterRequestDto;
import com.blogapi.dto.response.LoginResponseDto;
import com.blogapi.dto.response.LogoutResponse;
import com.blogapi.dto.response.RefreshTokenResponse;
import com.blogapi.dto.response.RegisterResponseDto;
import com.blogapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto>register(@RequestBody RegisterRequestDto registerDto){
         RegisterResponseDto response= authService.register(registerDto);
         return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto>login(@RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse>refreshToken(@RequestBody RefreshTokenRequest request){

        RefreshTokenResponse refreshTokenResponse = authService.refreshToken(request);

        return ResponseEntity.ok(refreshTokenResponse);
    }
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse>logout(@RequestBody LogoutRequest request){
        LogoutResponse response = authService.logout(request);
        return ResponseEntity.ok(response);
    }

}
