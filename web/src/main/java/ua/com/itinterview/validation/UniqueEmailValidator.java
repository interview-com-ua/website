package ua.com.itinterview.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.security.AuthenticationUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements
	ConstraintValidator<UniqueEmail, String> {
    @Autowired
    AuthenticationUtils authenticationUtils;
    @Autowired
    private UserDao userDao;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
	UserDetails userDetails = authenticationUtils.getUserDetails();
	if (userDetails != null) {
	    System.out.println(userDetails.getUsername());
	    UserEntity userEntity = userDao.getUserByEmail(userDetails
                .getUsername());
	    String userEmail = userEntity.getEmail();
	    if (userEmail != null && userEmail.equals(email)) {
		return true;
	    }
	}
	return !userDao.doesUserExistsWithEmail(email);
    }
}
