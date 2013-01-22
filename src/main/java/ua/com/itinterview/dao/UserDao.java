package ua.com.itinterview.dao;

import javax.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.entity.UserEntity;

public class UserDao extends EntityWithIdDao<UserEntity> {

    @Transactional
    public UserEntity getUserByUserName(String userName) {
	return getOneResultByParameter("userName", userName);
    }

    @Transactional
    public boolean doesUserExistsWithUserName(String userName) {
	try {
	    getUserByUserName(userName);
	    return true;
	} catch (EntityNotFoundException e) {
	    return false;
	}
    }

}
