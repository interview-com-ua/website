package ua.com.itinterview.validation;

import org.apache.log4j.Logger;
import org.hibernate.validator.util.ReflectionHelper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Bohdan on 25/06/2014.
 */
public class FieldsNotEqualsValidator implements
        ConstraintValidator<FieldsNotEquals, Object> {

    private String errorKey;
    private String errorMessage;

    private String firstFieldPropertyName;
    private String secondFieldPropertyName;

    private static final Logger LOGGER = Logger
            .getLogger(FieldsNotEqualsValidator.class);

    @Override
    public void initialize(FieldsNotEquals constraintAnnotations) {
        errorKey = constraintAnnotations.errorKey();
        errorMessage = constraintAnnotations.message();

        firstFieldPropertyName = constraintAnnotations.firstField();
        secondFieldPropertyName = constraintAnnotations.secondField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        boolean valid = false;
        try {
            Object firstField = getPropertyFromBean(value,
                    firstFieldPropertyName);
            Object secondField = getPropertyFromBean(value,
                    secondFieldPropertyName);
            if (firstField != null) {
                valid = !firstField.equals(secondField);
            } else {
                valid = !(secondField == null);
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid argument or property name", e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Invalid argument or property name", e);
            throw new RuntimeException(e);
        }

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage)
                    .addNode(errorKey).addConstraintViolation();
        }

        return valid;
    }

    private Object getPropertyFromBean(Object bean, String propertyName)
            throws IllegalArgumentException, IllegalAccessException {
        return ReflectionHelper.getDeclaredField(bean.getClass(), propertyName)
                .get(bean);
    }
}
