package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	UserEntity actual = userDao.getUserByUserName(TEST_USER_NAME);
	assertEquals(TEST_USER_NAME, actual.getUserName());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetUserByUserNameWhenNotExistsAndRecesiveException() {
	userDao.getUserByUserName("userFakeName");
    }

    @Test
    public void testDoesUserWithUserNameExists() {
	assertFalse(userDao.doesUserExistsWithUserName("userName"));
	assertTrue(userDao.doesUserExistsWithUserName(TEST_USER_NAME));
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
