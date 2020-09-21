package com.track.tracker.validator.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.track.tracker.validator.imple.ValidSegmentPointsValidator;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidSegmentPointsValidator.class)
public @interface ValidSegmentPoints {
	String message();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
