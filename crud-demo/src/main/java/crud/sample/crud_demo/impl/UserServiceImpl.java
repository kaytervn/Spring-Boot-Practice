package crud.sample.crud_demo.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crud.sample.crud_demo.dto.UserDto;
import crud.sample.crud_demo.exception.ResourceNotFoundException;
import crud.sample.crud_demo.mapper.UserMapper;
import crud.sample.crud_demo.model.User;
import crud.sample.crud_demo.repository.UserRepository;
import crud.sample.crud_demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = UserMapper.mapToUser(userDto);
		User savedUser = userRepository.save(user);
		return UserMapper.mapToUserDto(savedUser);
	}

	@Override
	public UserDto getUserById(long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist with Id = " + userId));
		return UserMapper.mapToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map((user) -> UserMapper.mapToUserDto(user)).collect(Collectors.toList());
	}

	@Override
	public UserDto updateUser(long userId, UserDto updatedUser) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist with Id = " + userId));
		user.setName(updatedUser.getName());
		user.setEmail(updatedUser.getEmail());
		user.setPassword(updatedUser.getPassword());
		User userObj = userRepository.save(user);
		return UserMapper.mapToUserDto(userObj);
	}

	@Override
	public void deleteUser(long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist with Id = " + userId));
		userRepository.deleteById(user.getId());
	}

}
