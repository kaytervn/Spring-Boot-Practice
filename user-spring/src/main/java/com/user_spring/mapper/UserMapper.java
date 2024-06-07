package com.user_spring.mapper;

import com.user_spring.dto.response.UserResponse;
import com.user_spring.dto.request.UserCreationRequest;
import com.user_spring.dto.request.UserUpdateRequest;
import com.user_spring.mapper.utils.MapperUtils;
import com.user_spring.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public interface UserMapper {
    @Mapping(target = "gender", source = "gender", qualifiedByName = "toUpperCase")
    User toUser(UserCreationRequest request);

    @Named("userToUserResponse")
    @Mapping(target = "password", ignore = true)
    UserResponse toUserResponse(User user);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "toUpperCase")
    void updateUserFromDto(@MappingTarget User user, UserUpdateRequest request);

    default List<UserResponse> toUserResponseList(List<User> users) {
        return users.stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }
}