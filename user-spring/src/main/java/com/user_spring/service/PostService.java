package com.user_spring.service;

import com.user_spring.dto.request.PostCreationRequest;
import com.user_spring.dto.request.PostUpdateRequest;
import com.user_spring.dto.request.UserCreationRequest;
import com.user_spring.dto.request.UserUpdateRequest;
import com.user_spring.dto.response.PostResponse;
import com.user_spring.dto.response.UserResponse;
import com.user_spring.entity.Post;
import com.user_spring.entity.User;
import com.user_spring.exception.AppException;
import com.user_spring.exception.ErrorMessage;
import com.user_spring.mapper.PostMapper;
import com.user_spring.mapper.UserMapper;
import com.user_spring.repository.PostRepository;
import com.user_spring.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {
    PostRepository postRepository;
    UserRepository userRepository;
    PostMapper postMapper;

    public PostResponse createPost(PostCreationRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new AppException(User.class, ErrorMessage.ENTITY_NOT_FOUND));
        Post post = postMapper.toPost(request);
        post.setUser(user);
        return postMapper.toPostResponse(postRepository.save(post));
    }

    public List<PostResponse> getPosts() {
        return postRepository.findAll().stream().map(postMapper::toPostResponse).toList();
    }

    public PostResponse getPost(String id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new AppException(Post.class, ErrorMessage.ENTITY_NOT_FOUND));
        return postMapper.toPostResponse(post);
    }

    public PostResponse updatePost(String id, PostUpdateRequest request) {
        Post post = postRepository.findById(id).orElseThrow(() -> new AppException(Post.class, ErrorMessage.ENTITY_NOT_FOUND));
        postMapper.updatePost(post, request);
        return postMapper.toPostResponse(postRepository.save(post));
    }

    public void deletePost(String id) {
        postRepository.findById(id).orElseThrow(() -> new AppException(Post.class, ErrorMessage.ENTITY_NOT_FOUND));
        postRepository.deleteById(id);
    }
}
