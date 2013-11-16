package ua.com.itinterview.dao;

import org.springframework.transaction.annotation.Transactional;
import ua.com.itinterview.entity.UserEntity;

import javax.persistence.EntityNotFoundException;

public class UserDao extends EntityWithIdDao<UserEntity> {

    @Transactional
    public UserEntity getUserByEmail(String email) {
	return getOneResultByParameter("email", email);
    }

    @Transactional
    public boolean doesUserExistsWithEmail(String email) {
	try {
	    getOneResultByParameter("email", email);
	    return true;
	} catch (EntityNotFoundException e) {
	    return false;
	}
    }
}
