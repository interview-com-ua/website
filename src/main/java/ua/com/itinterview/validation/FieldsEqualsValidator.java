package ua.com.itinterview.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsEqualsValidator implements
	ConstraintValidator<FieldsEquals, Object> {

    private String errorKey;
    private String errorMessage;

    @Override
    public void initialize(FieldsEquals constraintAnnotations) {
	errorKey = constraintAnnotations.errorKey();
	errorMessage = constraintAnnotations.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
	Boolean valid = false;

	if (!valid) {
	    context.disableDefaultConstraintViolation();
	    context.buildConstraintViolationWithTemplate(errorMessage)
		    .addNode(errorKey).addConstraintViolation();
	}
	
	return false;
    }
}
