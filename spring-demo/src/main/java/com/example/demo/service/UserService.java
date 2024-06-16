package com.example.demo.service;

import com.example.demo.constant.AppConst;
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
import com.example.demo.repository.specification.SearchCriteria;
import com.example.demo.repository.specification.SearchOperation;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.demo.constant.AppConst.SEARCH_OPERATOR;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    SearchService searchService;

    @NonFinal
    @PersistenceContext
    private EntityManager entityManager;

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
        roleRepository.findByName(AppConst.ROLE_USER).ifPresent(roles::add);
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

    public PageResponse<?> getUsersAdvance(Pageable pageable, String[] user, String[] role) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        // Creating the query to fetch results
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);
        Join<User, Role> roleRoot = userRoot.join("roles");

        List<Predicate> userPreList = new ArrayList<>();
        Pattern pattern = Pattern.compile(SEARCH_OPERATOR);
        for (String u : user) {
            Matcher matcher = pattern.matcher(u);
            if (matcher.find()) {
                SearchCriteria searchCriteria = new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3));
                userPreList.add(searchService.toPredicate(userRoot, builder, searchCriteria));
            }
        }

        List<Predicate> rolePreList = new ArrayList<>();
        for (String r : role) {
            Matcher matcher = pattern.matcher(r);
            if (matcher.find()) {
                SearchCriteria searchCriteria = new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3));
                rolePreList.add(searchService.toPredicate(roleRoot, builder, searchCriteria));
            }
        }

        Predicate userPre = builder.and(userPreList.toArray(new Predicate[0]));
        Predicate rolePre = builder.and(rolePreList.toArray(new Predicate[0]));
        Predicate finalPre = builder.and(userPre, rolePre);

        query.where(finalPre);

        // Apply sorting
        if (pageable.getSort().isSorted()) {
            List<Order> orders = new ArrayList<>();
            for (Sort.Order sortOrder : pageable.getSort()) {
                Path<Object> path = userRoot.get(sortOrder.getProperty());
                orders.add(sortOrder.isAscending() ? builder.asc(path) : builder.desc(path));
            }
            query.orderBy(orders);
        }

        // Creating the query to count results
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        Join<User, Role> countRoleRoot = countRoot.join("roles");

        List<Predicate> countUserPreList = new ArrayList<>();
        for (String u : user) {
            Matcher matcher = pattern.matcher(u);
            if (matcher.find()) {
                SearchCriteria searchCriteria = new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3));
                countUserPreList.add(searchService.toPredicate(countRoot, builder, searchCriteria));
            }
        }

        List<Predicate> countRolePreList = new ArrayList<>();
        for (String r : role) {
            Matcher matcher = pattern.matcher(r);
            if (matcher.find()) {
                SearchCriteria searchCriteria = new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3));
                countRolePreList.add(searchService.toPredicate(countRoleRoot, builder, searchCriteria));
            }
        }

        Predicate countUserPre = builder.and(countUserPreList.toArray(new Predicate[0]));
        Predicate countRolePre = builder.and(countRolePreList.toArray(new Predicate[0]));
        Predicate finalCountPre = builder.and(countUserPre, countRolePre);

        countQuery.select(builder.count(countRoot)).where(finalCountPre);
        long totalElements = entityManager.createQuery(countQuery).getSingleResult();
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

        // Get paginated results
        List<User> users = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return PageResponse.builder()
                .pageNo(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalPages(totalPages)
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
