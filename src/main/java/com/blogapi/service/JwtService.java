package com.blogapi.service;

import com.blogapi.entity.User;
import java.util.Date;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    String extractEmail(String token);

    Date extractExpiration(String token);

    boolean isTokenValid(String token, User user);


}
