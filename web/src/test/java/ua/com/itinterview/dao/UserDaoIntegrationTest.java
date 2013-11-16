package ua.com.itinterview.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.entity.UserEntity;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserDaoIntegrationTest extends
	BaseEntityWithIdDaoIntegrationTest<UserEntity> {

    private static final String SOME_OTHER_EMAIL = "someemail@test.com";

    @Autowired
    private UserDao userDao;

    @Test
    public void testGetUserByEmail() {
	UserEntity actual = userDao.getUserByEmail(TEST_USER_EMAIL);
	assertEquals(TEST_USER_EMAIL, actual.getEmail());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetUserByEmailWhenNotExistsAndReceiveException() {
	userDao.getUserByEmail("userFakeEmail");
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
	user2.setEmail(user2.getEmail() + "2");

	return Arrays.asList(user1, user2);
    }
}
