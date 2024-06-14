package com.example.demo.dto.request;

import com.example.demo.enums.Gender;
import com.example.demo.validator.DobConstraint;
import com.example.demo.validator.EnumConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 3, message = "INVALID_FIELD_SIZE")
    String password;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dateOfBirth;

    @EnumConstraint(enumClass = Gender.class)
    String gender;

    List<String> roles;
}