package ua.com.itinterview.validation;

import org.apache.log4j.Logger;
import org.hibernate.validator.util.ReflectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.UserEntity;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Bohdan on 02/07/2014.
 */
public class CompareOldPasswordValidator implements
        ConstraintValidator<CompareOldPassword, Object> {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String errorKey;
    private String errorMessage;

    private String userIdPropertyName;
    private String oldPasswordPropertyName;

    private static final Logger LOGGER = Logger
            .getLogger(CompareOldPasswordValidator.class);

    @Override
    public void initialize(CompareOldPassword constraintAnnotations) {
        errorKey = constraintAnnotations.errorKey();
        errorMessage = constraintAnnotations.message();

        userIdPropertyName = constraintAnnotations.userId();
        oldPasswordPropertyName = constraintAnnotations.oldPassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = false;
        try {
            int userId = (Integer) getPropertyFromBean(value, userIdPropertyName);
            String enteredOldPassword = (String) getPropertyFromBean(value, oldPasswordPropertyName);
            if (enteredOldPassword == null) {
                enteredOldPassword = "";
            }
            UserEntity userEntity = userDao.getEntityById(userId);

            String oldPassword = passwordEncoder.encodePassword(enteredOldPassword, "");
            valid = oldPassword.equals(userEntity.getPassword());

        } catch (EntityNotFoundException e) {
            valid = false;
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
