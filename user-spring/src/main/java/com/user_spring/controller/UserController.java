package com.user_spring.controller;

import com.user_spring.dto.request.UserCreationRequest;
import com.user_spring.dto.request.UserUpdateRequest;
import com.user_spring.dto.response.ApiResponse;
import com.user_spring.dto.response.UserResponse;
import com.user_spring.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        UserResponse userResponse = userService.createUser(request);
        return ApiResponse.<UserResponse>builder()
                .result(userResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<?> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<?> getUser(@PathVariable("userId") String id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(id)).build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<?> updateUser(@PathVariable("userId") String id,
                                     @Valid @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<?> deleteUser(@PathVariable("userId") String id) {
        userService.deleteUser(id);
        return ApiResponse.<String>builder()
                .result("User has been deleted")
                .build();
    }

}
