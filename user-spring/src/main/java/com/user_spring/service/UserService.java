package com.user_spring.service;

import com.user_spring.dto.request.UserCreationRequest;
import com.user_spring.dto.response.UserResponse;
import com.user_spring.entity.User;
import com.user_spring.mapper.UserMapper;
import com.user_spring.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    @PostMapping
    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

}
