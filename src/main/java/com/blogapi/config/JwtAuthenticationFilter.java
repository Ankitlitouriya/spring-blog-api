package com.blogapi.config;

import com.blogapi.entity.User;
import com.blogapi.repository.UserRepository;
import com.blogapi.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("Header = " + authHeader);


        System.out.println("JWT Filter Executed");
        if (authHeader==null || !authHeader.startsWith("Bearer ")){

            filterChain.doFilter(request,response);

             return;
        }
         String jwtToken = authHeader.substring(7);
        System.out.println(jwtToken);
         String email = jwtService.extractEmail(jwtToken);
        System.out.println(email);
         User user = userRepository.findByEmail(email)
                 .orElseThrow(()->new RuntimeException("User not found"));

         boolean validToken = jwtService.isTokenValid(jwtToken,user);

        System.out.println(validToken);

         if (validToken) {

             Collection<? extends GrantedAuthority> authorities =
                     user.getRoles()
                             .stream()
                             .map(role ->
                                     new SimpleGrantedAuthority(
                                             role.getName()
                                     ))
                             .toList();


             Authentication authentication =
                     new UsernamePasswordAuthenticationToken(user, null, authorities);

             SecurityContextHolder
                     .getContext()
                     .setAuthentication(authentication);
         }
         filterChain.doFilter(request,response);

    }

}
