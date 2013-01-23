package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.entity.UserEntity;

public class UserDaoIntegrationTest extends
	BaseEntityWithIdDaoIntegrationTest<UserEntity> {

    @Autowired
    private UserDao userDao;

    @Test
    public void testGetUserByUserName() {
	UserEntity user = new UserEntity();
	user.setUserName("userName");
	userDao.save(user);
	UserEntity actual = userDao.getUserByUserName("userName");
	assertEquals("userName", actual.getUserName());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetUserByUserNameWhenNotExistsAndRecesiveException() {
	UserEntity user = new UserEntity();
	user.setUserName("userName");
	userDao.save(user);
	userDao.getUserByUserName("userFakeName");
    }

    @Override
    protected UserEntity createEntity() {
	UserEntity user = new UserEntity();
	user.setEmail("email@gmail.com");
	user.setUserName("userName");
	user.setPassword("password");
	return user;
    }

    @Override
    protected EntityWithIdDao<UserEntity> getEntityWithIdDao() {
	return userDao;
    }

}
