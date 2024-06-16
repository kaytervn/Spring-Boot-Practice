package com.example.demo.controller;

import com.example.demo.configuration.MessageUtil;
import com.example.demo.dto.request.IntrospectRequest;
import com.example.demo.dto.request.SignInRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Authentication Controller")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/introspect")
    ApiResponse<?> introspect(@Valid @RequestBody IntrospectRequest request) throws JOSEException, ParseException {
        authenticationService.introspect(request);
        return ApiResponse.builder()
                .timestamp(new Date())
                .reasonPhrase(HttpStatus.ACCEPTED.getReasonPhrase())
                .status(HttpStatus.ACCEPTED.value())
                .message(MessageUtil.getMessage("token.error.invalid"))
                .build();
    }

    @PostMapping("/access")
    ApiResponse<?> login(@Valid @RequestBody SignInRequest request) throws JOSEException {
        var data = authenticationService.authenticate(request);
        return ApiResponse.builder()
                .timestamp(new Date())
                .reasonPhrase(HttpStatus.ACCEPTED.getReasonPhrase())
                .status(HttpStatus.ACCEPTED.value())
                .message(MessageUtil.getMessage("token.success.access"))
                .data(data)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<?> logout() {
        return ApiResponse.builder()
                .timestamp(new Date())
                .reasonPhrase(HttpStatus.NO_CONTENT.getReasonPhrase())
                .status(HttpStatus.NO_CONTENT.value())
                .message(MessageUtil.getMessage("token.success.destroy"))
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<?> refresh() {
        return ApiResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.ACCEPTED.value())
                .reasonPhrase(HttpStatus.ACCEPTED.getReasonPhrase())
                .message(MessageUtil.getMessage("token.success.refresh"))
                .build();
    }
}
