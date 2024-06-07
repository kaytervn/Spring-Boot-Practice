package com.user_spring.mapper;

import com.user_spring.dto.response.PostResponse;
import com.user_spring.dto.response.UserResponse;
import com.user_spring.dto.request.UserCreationRequest;
import com.user_spring.dto.request.UserUpdateRequest;
import com.user_spring.entity.Post;
import com.user_spring.mapper.utils.MapperUtils;
import com.user_spring.entity.User;
import com.user_spring.repository.UserRepository;
import com.user_spring.service.UserService;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {MapperUtils.class})
public interface UserMapper {
    @Mapping(target = "gender", source = "gender", qualifiedByName = "toUpperCase")
    User toUser(UserCreationRequest request);

    @Named("userToUserResponse")
    @Mapping(target = "password", ignore = true)
    UserResponse toUserResponse(User user);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "toUpperCase")
    void updateUserFromDto(@MappingTarget User user, UserUpdateRequest request);

    @IterableMapping(qualifiedByName = "userToUserResponse")
    List<UserResponse> toUserResponseList(List<User> users);
}