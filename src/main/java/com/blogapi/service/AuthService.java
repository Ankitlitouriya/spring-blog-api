package com.blogapi.service;

import com.blogapi.dto.request.LoginRequestDto;
import com.blogapi.dto.request.LogoutRequest;
import com.blogapi.dto.request.RefreshTokenRequest;
import com.blogapi.dto.request.RegisterRequestDto;
import com.blogapi.dto.response.LoginResponseDto;
import com.blogapi.dto.response.LogoutResponse;
import com.blogapi.dto.response.RefreshTokenResponse;
import com.blogapi.dto.response.RegisterResponseDto;


public interface AuthService {

    RegisterResponseDto register(RegisterRequestDto requestDto);

    LoginResponseDto login(LoginRequestDto request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    LogoutResponse logout(LogoutRequest request);
}
