package com.blogapi.service.impl;

import com.blogapi.entity.User;
import com.blogapi.service.JwtService;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String generateAccessToken(User user) {

        return null;
    }

    @Override
    public String generateRefreshToken(User user) {

        return null;
    }
}