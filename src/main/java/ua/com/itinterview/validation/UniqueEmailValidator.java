package ua.com.itinterview.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.dao.UserDao;

public class UniqueEmailValidator implements
	ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserDao userDao;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
	return !userDao.doesUserExistsWithEmail(email);
    }
}
