package com.track.tracker.validator.imple;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.track.tracker.model.TrackingPoint;
import com.track.tracker.validator.annotation.ValidSegmentPoints;

public class ValidSegmentPointsValidator implements ConstraintValidator<ValidSegmentPoints,Set<TrackingPoint>> {

	@Override
	public boolean isValid(Set<TrackingPoint> value, ConstraintValidatorContext context) {
		return value.size() > 1;
	}

}
