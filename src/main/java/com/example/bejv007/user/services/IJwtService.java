package com.example.bejv007.user.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;
import java.util.function.Function;

public interface IJwtService {

    public String extractUsername(String token);

    public <T> T extractClaim(String token, Function<Claims, T> resolver);
    String createToken(UserDetails user);
    String createToken(Map<String, Object> claims, UserDetails userDetails);

    public boolean isTokenValid(String token, UserDetails user);



}
