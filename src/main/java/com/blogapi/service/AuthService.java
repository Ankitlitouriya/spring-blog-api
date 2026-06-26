package com.blogapi.service;

import com.blogapi.dto.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public interface AuthService {

    RegisterResponseDto register(RegisterRequestDto requestDto);

    LoginResponseDto login(LoginRequestDto request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
