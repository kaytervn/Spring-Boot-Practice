package com.example.demo.controller;

import com.example.demo.configuration.Translator;
import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/roles")
@Tag(name = "Role Controller")
public class RoleController {
    RoleService roleService;

    @PostMapping
    public ApiResponse<?> createRole(@Valid @RequestBody RoleRequest request) {
        RoleResponse response = roleService.createRole(request);
        return ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .timestamp(new Date())
                .message(Translator.toLocale("role.success.create"))
                .data(response)
                .build();
    }

    @GetMapping
    public ApiResponse<?> getRoles() {
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .timestamp(new Date())
                .data(roleService.getRoles())
                .build();
    }

    @DeleteMapping("/{name}")
    public ApiResponse<?> deleteRole(@PathVariable("name") String id) {
        roleService.deleteRole(id);
        return ApiResponse.<String>builder()
                .timestamp(new Date())
                .message(Translator.toLocale("role.success.delete"))
                .status(HttpStatus.NO_CONTENT.value())
                .build();
    }
}
