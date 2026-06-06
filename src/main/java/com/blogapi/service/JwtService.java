package com.blogapi.service;

import com.blogapi.entity.User;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);
}
