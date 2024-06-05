package com.user_spring.mapper;

import com.user_spring.dto.response.UserResponse;
import com.user_spring.dto.request.UserCreationRequest;
import com.user_spring.dto.request.UserUpdateRequest;
import com.user_spring.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}