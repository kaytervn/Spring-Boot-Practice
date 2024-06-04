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
import vn.kayter.sample_code.model.Address;
import vn.kayter.sample_code.model.Gender;
import vn.kayter.sample_code.validator.PhoneNumber;
import vn.kayter.sample_code.model.UserStatus;
import vn.kayter.sample_code.model.UserType;
import vn.kayter.sample_code.validator.EnumPattern;
import vn.kayter.sample_code.validator.EnumValue;
import vn.kayter.sample_code.validator.GenderSubset;

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

	@NotEmpty(message = "addresses must not be empty")
	List<Address> addresses;

	@EnumPattern(name = "status", regexp = "ACTIVE|INACTIVE|NONE")
	UserStatus status;

	@GenderSubset(anyOf = { Gender.MALE, Gender.FEMALE, Gender.OTHER })
	private Gender gender;

	@NotNull(message = "type must not be null")
	@EnumValue(name = "type", enumClass = UserType.class)
	private String type;

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

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
