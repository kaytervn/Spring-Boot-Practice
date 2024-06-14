package com.example.demo.dto.request;

import com.example.demo.entity.Permission;
import com.example.demo.validator.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionRequest {
    @NotBlank(message = "NOT_BLANK_FIELD")
    @UniqueConstraint(entity = Permission.class, fieldName = "name")
    String name;
    @NotBlank(message = "NOT_BLANK_FIELD")
    String description;
}
