package vn.kayter.sample_code.dto.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import vn.kayter.sample_code.utils.EnumPattern;
import vn.kayter.sample_code.utils.PhoneNumber;
import vn.kayter.sample_code.utils.UserStatus;

public class UserRequestDTO implements Serializable {

	@NotBlank(message = "firstName must not be blank")
	String firstName;

	@NotNull(message = "lastName must not be null")
	String lastName;

	@PhoneNumber
	String phone;

	@Email(message = "email is invalid")
	String email;

	@NotNull(message = "dateOfBirth must not be null")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "MM/dd/yyyy")
	@Past(message = "dateOfBirth must be a past date")
	Date dateOfBirth;

	@NotEmpty(message = "permissions must not be empty")
	List<String> permissions;

	@EnumPattern(name = "status", regexp = "ACTIVE|INACTIVE|NONE")
	UserStatus status;

	public UserRequestDTO() {
	}

	public UserRequestDTO(@NotBlank(message = "firstName must not be blank") String firstName,
			@NotNull(message = "lastName must not be null") String lastName,
			@Pattern(regexp = "^\\d{10}$", message = "phone is invalid") String phone,
			@Email(message = "email is invalid") String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

}
