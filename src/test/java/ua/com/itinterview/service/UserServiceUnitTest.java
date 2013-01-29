package ua.com.itinterview.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.command.UserCommand;

public class UserServiceUnitTest {

    private static final int USER_ID = 1;
    private static final String USER_EMAIL = "user@email.com";
    private static final String NAME = "name";
    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "password";

    private UserDao userDaoMock;
    private UserService userService;

    @Before
    public void createMocks() {
	userDaoMock = EasyMock.createMock(UserDao.class);
	userService = new UserService();
	userService.userDao = userDaoMock;
    }

    @Test
    public void testConvertUserCommandToUserEntity() {
	UserCommand userCommand = createUserCommand();
	UserEntity expectedUserEntity = createUserEntity();
	UserEntity actualUserEntity = new UserEntity(userCommand);
	assertEquals(expectedUserEntity, actualUserEntity);
    }

    @Test
    public void testConvertUserEntityToUserCommand() {
	UserEntity userEntity = createUserEntity();
	UserCommand expectedUserCommand = createUserCommand();
	UserCommand actualUserCommand = new UserCommand(userEntity);
	assertEquals(expectedUserCommand, actualUserCommand);
    }

    @Test
    public void testCreateUserWhenThereAreNoUsernameInDatabase() {
	UserCommand userCommand = createUserCommand();
	UserEntity userEntity = new UserEntity(userCommand);

	EasyMock.expect(
		userDaoMock.doesUserExistsWithUserName(userCommand
			.getUserName())).andReturn(false);
	EasyMock.expect(userDaoMock.save(userEntity)).andReturn(userEntity);
	EasyMock.replay(userDaoMock);
	userService.createUser(userCommand);
	EasyMock.verify(userDaoMock);
    }

    @Test()
    public void testCreateUserWhenUsernameInDatabase() {
	try {
	    UserCommand userCommand = createUserCommand();
	    EasyMock.expect(
		    userDaoMock.doesUserExistsWithUserName(userCommand
			    .getUserName())).andReturn(true);
	    EasyMock.replay(userDaoMock);
	    userService.createUser(userCommand);
	    fail("Expected RuntimeException");
	} catch (RuntimeException e) {
	} finally {
	    EasyMock.verify(userDaoMock);
	}
    }

    private UserCommand createUserCommand() {
	UserCommand command = new UserCommand();
	command.setId(USER_ID);
	command.setConfirmPassword(PASSWORD);
	command.setPassword(PASSWORD);
	command.setEmail(USER_EMAIL);
	command.setName(NAME);
	command.setUserName(USER_NAME);
	return command;
    }

    private UserEntity createUserEntity() {
	UserEntity userEntity = new UserEntity();
	userEntity.setId(USER_ID);
	userEntity.setPassword(PASSWORD);
	userEntity.setEmail(USER_EMAIL);
	userEntity.setName(NAME);
	userEntity.setUserName(USER_NAME);
	return userEntity;
    }
}
