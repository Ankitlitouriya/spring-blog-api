package com.blogapi.controller;

import com.blogapi.dto.LoginRequestDto;
import com.blogapi.dto.LoginResponseDto;
import com.blogapi.dto.RegisterRequestDto;
import com.blogapi.dto.RegisterResponseDto;
import com.blogapi.service.AuthService;
import com.blogapi.service.impl.AuthServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

}
