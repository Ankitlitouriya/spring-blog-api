package com.blogapi.service;

import com.blogapi.dto.RegisterRequestDto;
import com.blogapi.dto.RegisterResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public interface AuthService {

    RegisterResponseDto register(RegisterRequestDto requestDto);
}
