package com.user_spring.dto.request;

import com.user_spring.enums.Gender;
import com.user_spring.entity.User;
import com.user_spring.validator.DobConstraint;
import com.user_spring.validator.EnumConstraint;
import com.user_spring.validator.UniqueValueConstraint;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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

    @UniqueValueConstraint(entity = User.class, fieldName = "phone")
    @Pattern(regexp = "^\\d{10}$", message = "INVALID_PHONE")
    String phone;

    @EnumConstraint(enumClass = Gender.class)
    String gender;
}