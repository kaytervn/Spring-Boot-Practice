package vn.kayter.sample_code.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserStatus {
	@JsonProperty("active")
	ACTIVE, @JsonProperty("inactive")
	INACTIVE, @JsonProperty("none")
	NONE
}
