package com.user_spring.dto.request;

import com.user_spring.enums.Platform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignInRequest {
    @NotBlank(message = "NOT_BLANK_FIELD")
    String username;
    @NotBlank(message = "NOT_BLANK_FIELD")
    String password;
    @NotNull(message = "NOT_NULL_FIELD")
    Platform platform;
}
