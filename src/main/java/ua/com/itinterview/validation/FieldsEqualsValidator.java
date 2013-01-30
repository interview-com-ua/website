package ua.com.itinterview.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsEqualsValidator implements
	ConstraintValidator<FieldsEquals, Object> {

    @Override
    public void initialize(FieldsEquals constraintAnnotations) {
	// TODO Auto-generated method stub

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
	return false;
    }

}
