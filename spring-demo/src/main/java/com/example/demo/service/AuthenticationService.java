package com.example.demo.service;

import com.example.demo.dto.request.SignInRequest;
import com.example.demo.dto.response.TokenResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    JwtService jwtService;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public TokenResponse authenticate(SignInRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new AppException("token.error.bad-cred", HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException("user.error.not-found", HttpStatus.NOT_FOUND));
        String token = jwtService.generateToken(user);
        return TokenResponse.builder()
                .token(token)
                .userId(user.getId())
                .build();
    }
}
