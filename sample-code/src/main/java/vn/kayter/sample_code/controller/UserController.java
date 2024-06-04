package vn.kayter.sample_code.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.kayter.sample_code.dto.request.UserRequestDTO;

@RestController
@RequestMapping("/users")
public class UserController {

	@PostMapping("/")
	public String addUser(@RequestBody UserRequestDTO userDTO) {
		return "User Added";
	}

	@PutMapping("/{userId}")
	public String updateUser(@PathVariable("userId") int id, @RequestBody UserRequestDTO userDTO) {
		System.out.println("Request update userId = " + id);
		return "User updated";
	}

	@PatchMapping("/{userId}")
	public String changeStatus(@PathVariable int userId, @RequestParam(required = false) boolean status) {
		System.out.println("Request change user status, userId = " + userId);
		return "User status changed";
	}

	@DeleteMapping("/{userId}")
	public String deleteUser(@PathVariable int userId) {
		System.out.println("Request delete userId = " + userId);
		return "User deleted";
	}

	@GetMapping("/{userId}")
	public UserRequestDTO getUser(@PathVariable int userId) {
		System.out.println("Request get userId = " + userId);
		return new UserRequestDTO("Trong", "Kien", "0123", "trong@123");
	}

	@GetMapping("/list")
	public List<UserRequestDTO> getAllUsers(@RequestParam(required = false) String email,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
		return List.of(new UserRequestDTO("Trong", "Kien", "0123", "trong@123"),
				new UserRequestDTO("Trung", "Nguyen", "0134", "trung@123"));
	}

}
