package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.entity.UserEntity;

public class UserDaoIntegrationTest extends
	BaseEntityWithIdDaoIntegrationTest<UserEntity> {

    private static final String SOME_OTHER_EMAIL = "someemail@test.com";

    @Autowired
    private UserDao userDao;

    @Test
    public void testGetUserByUserName() {
	UserEntity actual = userDao.getUserByUserName(DEFAULT_TEST_USER_NAME);
	assertEquals(DEFAULT_TEST_USER_NAME, actual.getUserName());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetUserByUserNameWhenNotExistsAndRecesiveException() {
	userDao.getUserByUserName("userFakeName");
    }

    @Test
    public void testDoesUserWithUserNameExists() {
	assertFalse(userDao.doesUserExistsWithUserName("userName"));
	assertTrue(userDao.doesUserExistsWithUserName(DEFAULT_TEST_USER_NAME));
    }

    @Test
    public void testDoesUserWithEmailExists() {
	assertTrue(userDao.doesUserExistsWithEmail(TEST_USER_EMAIL));
	assertFalse(userDao.doesUserExistsWithEmail(SOME_OTHER_EMAIL));
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

    @Override
    protected List<UserEntity> createEntityList() {
	UserEntity user1 = createEntity();
	UserEntity user2 = createEntity();
	user2.setUserName(user2.getUserName() + "2");
	user2.setEmail(user2.getEmail() + "2");

	return Arrays.asList(user1, user2);
    }
}
