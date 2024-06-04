package crud.sample.crud_demo.service;

import java.util.List;

import crud.sample.crud_demo.dto.UserDto;

public interface UserService {
	UserDto createUser(UserDto userDto);

	UserDto getUserById(long userId);

	List<UserDto> getAllUsers();

	UserDto updateUser(long userId, UserDto updatedUser);

	void deleteUser(long userId);

}
