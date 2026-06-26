package com.blogapi.service.impl;

import com.blogapi.dto.*;
import com.blogapi.entity.RefreshToken;
import com.blogapi.entity.Role;
import com.blogapi.entity.User;

import com.blogapi.repository.RefreshTokenRepository;
import com.blogapi.repository.RoleRepository;
import com.blogapi.repository.UserRepository;
import com.blogapi.service.AuthService;
import com.blogapi.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;




    public RegisterResponseDto register(RegisterRequestDto requestDto){

        RegisterResponseDto response = new RegisterResponseDto();
        if (userRepository.existsByEmail(requestDto.getEmail())){
            response.setMessage("Email Id already exist");
            return response;
        }
       Role role =  roleRepository.findByName("ROLE_USER")
               .orElseThrow(()->new RuntimeException("User role not found"));
        User user = new User();
        user.setName(requestDto.getName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRoles(Set.of(role));
          userRepository.save(user);
         response.setMessage("User register successfully");
         return response;
    }
    public LoginResponseDto login(LoginRequestDto loginRequest){
        LoginResponseDto loginResponseDto = new LoginResponseDto();

         User user = userRepository.findByEmail(loginRequest.getEmail())
                 .orElseThrow(()->new RuntimeException("invalid user and password"));

          if (!passwordEncoder.matches(
                   loginRequest.getPassword(),
                   user.getPassword()
           )){
              throw new RuntimeException("invalid password");
          }

          String accessToken = jwtService.generateAccessToken(user);
          String refreshToken = jwtService.generateRefreshToken(user);

          loginResponseDto.setAccessToken(accessToken);
          loginResponseDto.setRefreshToken(refreshToken);

        RefreshToken refreshToken1 = new RefreshToken();
        refreshToken1.setToken(refreshToken);
        refreshToken1.setUser(user);
        refreshToken1.setExpiryDate(LocalDateTime.now().plusDays(7));

          refreshTokenRepository.save(refreshToken1);
          return loginResponseDto;
    }

    public  RefreshTokenResponse refreshToken(RefreshTokenRequest request){
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshtoken())
                .orElseThrow(()->new RuntimeException("Invalid Token"));
        User user = refreshToken.getUser();
        //Validation token
        if (!jwtService.isTokenValid(refreshToken.getToken(),user)){
            throw new RuntimeException("Invalid token");
        }
        //Generate new tokens
        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        //delet old token
        refreshTokenRepository.delete(refreshToken);
        //creating new refresh token
        RefreshToken newToken = new RefreshToken();
        Date expiry = jwtService.extractExpiration(newRefreshToken);
        LocalDateTime expiryDate = expiry.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        newToken.setExpiryDate(expiryDate);
        newToken.setToken(newRefreshToken);
        newToken.setUser(user);

        //save new refresh token
        refreshTokenRepository.save(newToken);

        //Prepraring the response
        RefreshTokenResponse response = new RefreshTokenResponse();
        response.setAccessToken(newAccessToken);
        response.setRefreshToken(newRefreshToken);


        return response;
    }
}
