package ua.com.itinterview.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.dao.UserDao;

public class UniqueUserNameValidator implements
	ConstraintValidator<UniqueUserName, String> {

    @Autowired
    private UserDao userDao;

    @Override
    public void initialize(UniqueUserName constraintAnnotations) {

    }

    @Override
    public boolean isValid(String userName, ConstraintValidatorContext context) {
	return !userDao.doesUserExistsWithUserName(userName);
    }

}
