package com.example.demo.mapper;

import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    User toUser(UserCreationRequest request);

    @Named("userToUserResponse")
    @Mapping(target = "roles", qualifiedByName = "roleToRoleResponse")
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUserFromDto(@MappingTarget User user, UserUpdateRequest request);

    @IterableMapping(qualifiedByName = "userToUserResponse")
    List<UserResponse> toUserResponseList(List<User> users);
}