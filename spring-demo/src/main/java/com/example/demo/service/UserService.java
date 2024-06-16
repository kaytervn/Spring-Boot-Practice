package com.example.demo.service;

import com.example.demo.constant.PredefinedRole;
import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.PageResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("user.error.not-found", HttpStatus.NOT_FOUND));
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("user.error.not-found", HttpStatus.NOT_FOUND));
    }

    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findByName(PredefinedRole.USER).ifPresent(roles::add);
        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public PageResponse<?> getUsers(int pageNo, int pageSize, String... sorts) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String sortBy : sorts) {
            if (StringUtils.isNotEmpty(sortBy)) {
                Pattern pattern = Pattern.compile("(\\w+?):(asc|desc)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(sortBy);
                if (matcher.find()) {
                    String property = matcher.group(1);
                    Sort.Direction direction = matcher.group(2).equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
                    orders.add(new Sort.Order(direction, property));
                }
            }
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(orders));
        Page<User> usersPage = userRepository.findAll(pageable);
        List<User> users = usersPage.getContent();
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(usersPage.getTotalPages())
                .items(userMapper.toUserResponseList(users))
                .build();
    }

    public UserResponse getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("user.error.not-found", HttpStatus.NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo() {
        return userMapper.toUserResponse(getCurrentUser());
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("user.error.not-found", HttpStatus.NOT_FOUND));
        userMapper.updateUserFromDto(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = roleRepository.findByNameIn(request.getRoles());
        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new AppException("user.error.not-found", HttpStatus.NOT_FOUND));
        userRepository.deleteById(userId);
    }
}
