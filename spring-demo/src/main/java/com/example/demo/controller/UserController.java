package com.example.demo.controller;

import com.example.demo.configuration.Translator;
import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "User Controller")
public class UserController {
    UserService userService;

    @PostMapping
    public ApiResponse<?> createUser(@RequestBody @Valid UserCreationRequest request) {
        UserResponse userResponse = userService.createUser(request);
        return ApiResponse.<UserResponse>builder()
                .timestamp(new Date())
                .status(HttpStatus.CREATED.value())
                .message(Translator.toLocale("user.success.create"))
                .data(userResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<?> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .data(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<?> getUser(@PathVariable("userId") String id) {
        return ApiResponse.<UserResponse>builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .data(userService.getUser(id))
                .build();
    }

    @GetMapping("/my-infor")
    public ApiResponse<?> getMyInfo() {
        return ApiResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.OK.value())
                .data(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<?> updateUser(@PathVariable("userId") String id,
                                     @Valid @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .timestamp(new Date())
                .data(userService.updateUser(id, request))
                .message(Translator.toLocale("user.success.update"))
                .status(HttpStatus.ACCEPTED.value())
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<?> deleteUser(@PathVariable("userId") String id) {
        userService.deleteUser(id);
        return ApiResponse.<String>builder()
                .timestamp(new Date())
                .message(Translator.toLocale("user.success.delete"))
                .status(HttpStatus.NO_CONTENT.value())
                .build();
    }

}
