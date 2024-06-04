package vn.kayter.sample_code.validator;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.kayter.sample_code.model.Gender;

public class GenderSubSetValidator implements ConstraintValidator<GenderSubset, Gender> {
	private Gender[] genders;

	@Override
	public void initialize(GenderSubset constraint) {
		this.genders = constraint.anyOf();
	}

	@Override
	public boolean isValid(Gender value, ConstraintValidatorContext context) {
		return value == null || Arrays.asList(genders).contains(value);
	}
}
