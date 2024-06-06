package com.user_spring.exception;

import com.user_spring.dto.response.ApiResponse;
import com.user_spring.dto.response.ValidResponse;
import com.user_spring.entity.Gender;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";
    private static final String FIELD_NAME = "field";
    private static final String ENUM_CLASS_ATTRIBUTE = "enumClass";

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        ErrorMessage errorMessage = ErrorMessage.UNCATEGORIZED_EXCEPTION;
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(errorMessage.getStatus().value());
        apiResponse.setMessage(errorMessage.getMessage());
        System.out.println(exception);
        return ResponseEntity.status(errorMessage.getStatus()).body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorMessage errorMessage = exception.getErrorMessage();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(errorMessage.getStatus().value());
        apiResponse.setMessage(errorMessage.getMessage());
        System.out.println(exception);
        return ResponseEntity.status(errorMessage.getStatus()).body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
        ErrorMessage errorMessage = ErrorMessage.INVALID_FORM;
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(errorMessage.getMessage());
        apiResponse.setStatus(errorMessage.getStatus().value());
        List<ValidResponse> errors = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String enumKey = error.getDefaultMessage();
            ValidMessage validMessage = ValidMessage.INVALID_KEY;
            try {
                validMessage = ValidMessage.valueOf(enumKey);
                Map<String, Object> attributes = error
                        .unwrap(ConstraintViolation.class)
                        .getConstraintDescriptor()
                        .getAttributes();
                String message = mapAttribute(validMessage.getMessage(), attributes, fieldName);
                errors.add(new ValidResponse(fieldName, message));
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        });
        apiResponse.setData(errors);
        return ResponseEntity.status(errorMessage.getStatus()).body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes, String field) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        String enumClass = String.valueOf(attributes.get(ENUM_CLASS_ATTRIBUTE));
        String acceptedValuesString = "[" + Stream.of(Gender.values()).map(Enum::name).collect(Collectors.joining(", ")) + "]";

        return message
                .replace("{" + MIN_ATTRIBUTE + "}", minValue)
                .replace("{" + FIELD_NAME + "}", field)
                .replace("{" + ENUM_CLASS_ATTRIBUTE + "}", acceptedValuesString);
    }
}
