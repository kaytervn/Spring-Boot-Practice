package kaytervn.restful_web_services.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {
	@Autowired
	UserDaoService service;

	@GetMapping("/users")
	public List<User> retriveAllUsers() {
		return service.findAll();

	}

	@GetMapping("/users/{id}")
	public User retriveUser(@PathVariable int id) {
		return service.findOne(id);

	}

	@PostMapping("/users")
	public void createUser(@RequestBody User user) {
		User savedUser = service.save(user);
	}
}
