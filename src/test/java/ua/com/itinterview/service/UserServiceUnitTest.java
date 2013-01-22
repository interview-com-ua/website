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

    private UserDao userDaoMock;
    private UserService userService;

    @Before
    public void createMocks() {
	userDaoMock = EasyMock.createMock(UserDao.class);
	userService = new UserService();
	userService.userDao = userDaoMock;
    }

    @Test
    public void testConvertUserEntityFromUserCommand() {
	UserCommand command = createUserCommand();
	UserEntity expectedUser = new UserEntity();
	expectedUser.setEmail("user@email.com");
	expectedUser.setUserName("userName");
	expectedUser.setPassword("password");
	UserEntity actualUser = new UserEntity(command);
	assertEquals(expectedUser, actualUser);
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
	    // expected exception
	} finally {
	    EasyMock.verify(userDaoMock);
	}
    }

    private UserCommand createUserCommand() {
	UserCommand command = new UserCommand();
	command.setConfirmPassword("confirmPassword");
	command.setPassword("password");
	command.setEmail("user@email.com");
	command.setName("Name");
	command.setUserName("userName");
	return command;
    }

}
