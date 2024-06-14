package com.example.demo.dto.request;

import com.example.demo.entity.User;
import com.example.demo.enums.Gender;
import com.example.demo.validator.DobConstraint;
import com.example.demo.validator.EnumConstraint;
import com.example.demo.validator.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @UniqueConstraint(entity = User.class, fieldName = "username")
    @Size(min = 5, message = "INVALID_FIELD_SIZE")
    String username;

    @Size(min = 3, message = "INVALID_FIELD_SIZE")
    String password;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dateOfBirth;

    @EnumConstraint(enumClass = Gender.class)
    String gender;
}
