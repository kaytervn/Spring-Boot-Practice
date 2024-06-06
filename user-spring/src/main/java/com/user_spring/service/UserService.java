package com.user_spring.service;

import com.user_spring.dto.request.UserCreationRequest;
import com.user_spring.dto.request.UserUpdateRequest;
import com.user_spring.dto.response.UserResponse;
import com.user_spring.entity.User;
import com.user_spring.exception.AppException;
import com.user_spring.exception.ErrorMessage;
import com.user_spring.mapper.UserMapper;
import com.user_spring.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorMessage.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    public UserResponse getUserWithoutPassword(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorMessage.USER_NOT_FOUND));
        return userMapper.toUserResponseWithoutPassword(user);
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorMessage.USER_NOT_FOUND));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorMessage.USER_NOT_FOUND));
        userRepository.deleteById(userId);
    }
}
