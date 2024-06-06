package com.user_spring.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorMessage {
    UNCATEGORIZED_EXCEPTION("Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_FORM("Invalid form", HttpStatus.BAD_REQUEST),
    ENTITY_NOT_FOUND("{entity} not found", HttpStatus.NOT_FOUND),
    ;
    String message;
    HttpStatus status;
}
