package crud.sample.crud_demo.mapper;

import crud.sample.crud_demo.dto.UserDto;
import crud.sample.crud_demo.model.User;

public class UserMapper {
	public static UserDto mapToUserDto(User user) {
		return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPassword());
	}

	public static User mapToUser(UserDto userDto) {
		return new User(userDto.getId(), userDto.getName(), userDto.getEmail(), userDto.getPassword());
	}
}
