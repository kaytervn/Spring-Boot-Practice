package vn.kayter.sample_code.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import vn.kayter.sample_code.dto.request.UserRequestDTO;
import vn.kayter.sample_code.dto.response.ResponseData;

@RestController
@RequestMapping("/users")
public class UserController {

	@PostMapping("/")
	public ResponseData<?> addUser(@Valid @RequestBody UserRequestDTO userDTO) {
		System.out.println("Request add user: " + userDTO.getFirstName());
		return new ResponseData<>(HttpStatus.CREATED.value(), "User added successfully", 1);
	}

	@PutMapping("/{userId}")
	public ResponseData<?> updateUser(@PathVariable("userId") int id, @RequestBody UserRequestDTO userDTO) {
		System.out.println("Request update userId = " + id);
		return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User added successfully");
	}

	@PatchMapping("/{userId}")
	public ResponseData<?> changeStatus(@Min(1) @PathVariable int userId,
			@RequestParam(required = false) boolean status) {
		System.out.println("Request change user status, userId = " + userId);
		return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User status changed successfully");
	}

	@DeleteMapping("/{userId}")
	public ResponseData<?> deleteUser(@Min(1) @PathVariable int userId) {
		System.out.println("Request delete userId = " + userId);
		return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "User deleted");
	}

	@GetMapping("/{userId}")
	public ResponseData<?> getUser(@Min(1) @PathVariable("userId") int userId) {
		System.out.println("Request get userId = " + userId);
		return new ResponseData<>(HttpStatus.OK.value(), "user",
				new UserRequestDTO("Trong", "Kien", "0123", "trong@123"));
	}

	@GetMapping("/list")
	public ResponseData<?> getAllUsers(@RequestParam(required = false) String email,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
		return new ResponseData<>(HttpStatus.OK.value(), "user",
				List.of(new UserRequestDTO("Trong", "Kien", "0123", "trong@123"),
						new UserRequestDTO("Trung", "Nguyen", "0134", "trung@123")));
	}

}
