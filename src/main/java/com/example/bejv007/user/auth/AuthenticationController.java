package com.example.bejv007.user.auth;

import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.repositories.UserJpaRepository;
import com.example.bejv007.user.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log
public class AuthenticationController {

    private final UserJpaRepository userJpaRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request){
        var authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authenticationManager.authenticate(authentication);
        UserModel user = userJpaRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.createToken(user);
        return  new AuthenticationResponse(user.getId(), token);
    }

}
