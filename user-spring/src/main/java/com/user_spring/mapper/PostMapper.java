package com.user_spring.mapper;

import com.user_spring.dto.request.PostCreationRequest;
import com.user_spring.dto.request.PostUpdateRequest;
import com.user_spring.dto.response.PostResponse;
import com.user_spring.dto.response.UserPostsResponse;
import com.user_spring.dto.response.UserResponse;
import com.user_spring.entity.Post;
import com.user_spring.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "user", source = "userId", qualifiedByName = "userFromId")
    Post toPost(PostCreationRequest request);

    @Named("userFromId")
    default User userFromId(String userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

    @Mapping(target = "user", ignore = true)
    void updatePost(@MappingTarget Post post, PostUpdateRequest request);

    @Mapping(target = "user.password", ignore = true)
    PostResponse toPostResponse(Post post);

    @Mapping(target = "user", ignore = true)
    PostResponse toPostResponseWithoutUser(Post post);

    UserPostsResponse toUserPostsResponse(UserResponse user, List<PostResponse> posts);
}
