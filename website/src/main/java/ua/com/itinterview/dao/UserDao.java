package ua.com.itinterview.dao;

import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.entity.UserEntity;

public class UserDao extends EntityWithIdDao<UserEntity> {

	@Transactional
	public UserEntity getUserByUserName(String userName) {
		return getOneResultByParameter("userName", userName);
	}

}
