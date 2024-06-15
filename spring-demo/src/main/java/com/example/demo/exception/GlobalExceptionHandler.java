package com.example.demo.exception;

import com.example.demo.configuration.MessageUtil;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.InvalidResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<?> handlingAppException(AppException exception) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .timestamp(new Date())
                .status(exception.getHttpStatus().value())
                .message(MessageUtil.getMessage(exception.getMessage()))
                .build();
        return ResponseEntity.status(exception.getHttpStatus()).body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<?> handlingValidation(MethodArgumentNotValidException exception) {
        List<InvalidResponse> errors = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.add(InvalidResponse.builder()
                    .field(field)
                    .message(MessageUtil.getMessage(message))
                    .build());
        });
        var apiResponse = ApiResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(MessageUtil.getMessage("error.validation"))
                .data(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<?> handlingAccessDeniedException(AccessDeniedException exception) {
        var apiResponse = ApiResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(MessageUtil.getMessage("error.access-denied"))
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }

    @ExceptionHandler({RuntimeException.class, ExpiredJwtException.class})
    ResponseEntity<?> handlingGeneralException(Exception exception) {
        var apiResponse = ApiResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(MessageUtil.getMessage(exception.getLocalizedMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
}
