package vn.kayter.sample_code.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { EnumValueValidator.class })
public @interface EnumValue {
	String name();

	String message() default "{name} must be any of enum {enumClass}";

	Class<? extends Enum<?>> enumClass();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
