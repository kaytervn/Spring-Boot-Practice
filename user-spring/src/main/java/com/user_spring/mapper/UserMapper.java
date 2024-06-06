package com.user_spring.mapper;

import com.user_spring.dto.response.UserResponse;
import com.user_spring.dto.request.UserCreationRequest;
import com.user_spring.dto.request.UserUpdateRequest;
import com.user_spring.entity.Gender;
import com.user_spring.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "gender", qualifiedByName = "stringToGender")
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "password", ignore = true)
    UserResponse toUserResponseWithoutPassword(User user);

    @Mapping(target = "gender", qualifiedByName = "stringToGender")
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    @Named("stringToGender")
    default Gender stringToGender(String gender) {
        return Gender.valueOf(gender.toUpperCase());
    }
}