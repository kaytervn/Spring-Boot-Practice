package com.user_spring.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 8, message = "usename must be at least 8 characters")
    String username;

    @Size(min = 8, message = "password must be at least 8 characters")
    String password;

    String firstName;

    String lastName;

    LocalDate dateOfBirth;
}
