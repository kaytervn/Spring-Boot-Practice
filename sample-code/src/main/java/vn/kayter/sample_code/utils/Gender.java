package vn.kayter.sample_code.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Gender {
	@JsonProperty("male")
	MALE, @JsonProperty("female")
	FEMALE, @JsonProperty("other")
	OTHER;
}
