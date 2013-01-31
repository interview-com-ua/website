package ua.com.itinterview.service;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import javax.persistence.EntityNotFoundException;

import org.easymock.Capture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.command.UserCommand;

public class UserServiceUnitTest {

    private static final int USER_ID = 1;
    private static final String EMAIL = "user@email.com";
    private static final String NAME = "name";
    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "password";
    private static final String SEX = "Male";

    private static final String FAKE_USER_NAME = "Fake User Name";
    private static final String NEW_NAME = "new name";
    private static final String NEW_EMAIL = "new email";
    private static final int NEW_USER_ID = USER_ID + 1;
    private static final String NEW_SEX = "Male";

    private UserDao userDaoMock;
    private UserService userService;

    @Before
    public void createMocks() {
	userDaoMock = createMock(UserDao.class);
	userService = new UserService();
	userService.userDao = userDaoMock;
    }

    private void replayAllMocks() {
	replay(userDaoMock);
    }

    @Test
    public void testConvertUserCommandToUserEntity() {
	replayAllMocks();
	UserCommand userCommand = createUserCommand();
	UserEntity expectedUserEntity = createUserEntity();
	UserEntity actualUserEntity = new UserEntity(userCommand);
	assertEquals(expectedUserEntity, actualUserEntity);
    }

    @Test
    public void testConvertUserEntityToUserCommand() {
	replayAllMocks();
	UserEntity userEntity = createUserEntity();
	UserCommand expectedUserCommand = createUserCommand();
	UserCommand actualUserCommand = new UserCommand(userEntity);
	assertEquals(expectedUserCommand, actualUserCommand);
    }

    @Test
    public void testCreateUserWhenThereAreNoUsernameInDatabase() {
	UserCommand userCommand = createUserCommand();
	UserEntity userEntity = new UserEntity(userCommand);

	expect(
		userDaoMock.doesUserExistsWithUserName(userCommand
			.getUserName())).andReturn(false);
	expect(userDaoMock.save(userEntity)).andReturn(userEntity);
	replayAllMocks();

	UserCommand actualUserCommand = userService.createUser(userCommand);
	UserCommand expectedUserCommand = createUserCommand();
	expectedUserCommand.setId(actualUserCommand.getId());
	assertEquals(expectedUserCommand, actualUserCommand);
    }

    @Test(expected = RuntimeException.class)
    public void testCreateUserWhenUsernameInDatabase() {
	UserCommand userCommand = createUserCommand();
	expect(
		userDaoMock.doesUserExistsWithUserName(userCommand
			.getUserName())).andReturn(true);
	replayAllMocks();
	userService.createUser(userCommand);
    }

    @Test
    public void testUpdateUser() {
	UserEntity oldUserInDb = createUserEntity();
	UserCommand userToUpdate = createCustomUserCommand(NEW_USER_ID,
		PASSWORD, NEW_EMAIL, NEW_NAME, FAKE_USER_NAME, NEW_SEX);

	expect(userDaoMock.getEntityById(USER_ID)).andReturn(oldUserInDb);
	Capture<UserEntity> userToSaveCapture = new Capture<UserEntity>();
	expect(userDaoMock.save(capture(userToSaveCapture))).andReturn(
		oldUserInDb);
	replayAllMocks();

	UserCommand expectedCommand = createCustomUserCommand(USER_ID,
		PASSWORD, NEW_EMAIL, NEW_NAME, USER_NAME, NEW_SEX);
	assertEquals(expectedCommand,
		userService.updateUser(USER_ID, userToUpdate));

	UserEntity actualSavedEntity = userToSaveCapture.getValue();
	assertEquals(NEW_EMAIL, actualSavedEntity.getEmail());
	assertEquals(NEW_NAME, actualSavedEntity.getName());
	assertEquals(USER_NAME, actualSavedEntity.getUserName());
	assertEquals(USER_ID, actualSavedEntity.getId());
	assertEquals(PASSWORD, actualSavedEntity.getPassword());
	assertEquals(SEX, actualSavedEntity.getSex());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUserUpdateWhenUserDoesNotExist() {
	expect(userDaoMock.getEntityById(NEW_USER_ID)).andThrow(
		new EntityNotFoundException());
	replayAllMocks();
	userService.updateUser(NEW_USER_ID, createUserCommand());
    }

    @Test
    public void testGetUserById() {
	UserEntity userEntity = createUserEntity();
	expect(userDaoMock.getEntityById(USER_ID)).andReturn(userEntity);
	replayAllMocks();

	UserCommand actualUserCommand = userService.getUserById(USER_ID);
	UserCommand expectedUserCommand = createUserCommand();
	assertEquals(expectedUserCommand, actualUserCommand);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetUserByIdWhenUserDoesNotExist() {
	expect(userDaoMock.getEntityById(NEW_USER_ID)).andThrow(
		new EntityNotFoundException());
	replayAllMocks();

	userService.getUserById(NEW_USER_ID);
    }

    @After
    public void verifyAllMocks() {
	verify(userDaoMock);
    }

    private UserCommand createCustomUserCommand(int id, String password,
	    String email, String name, String userName, String sex) {
	UserCommand command = new UserCommand();
	command.setId(id);
	command.setConfirmPassword(password);
	command.setPassword(password);
	command.setEmail(email);
	command.setName(name);
	command.setUserName(userName);
	command.setSex(sex);
	return command;
    }

    private UserCommand createUserCommand() {
	UserCommand command = new UserCommand();
	command.setId(USER_ID);
	command.setConfirmPassword(PASSWORD);
	command.setPassword(PASSWORD);
	command.setEmail(EMAIL);
	command.setName(NAME);
	command.setUserName(USER_NAME);
	command.setSex(SEX);
	return command;
    }

    private UserEntity createUserEntity() {
	UserEntity userEntity = new UserEntity();
	userEntity.setId(USER_ID);
	userEntity.setPassword(PASSWORD);
	userEntity.setEmail(EMAIL);
	userEntity.setName(NAME);
	userEntity.setUserName(USER_NAME);
	userEntity.setSex(SEX);
	return userEntity;
    }
}
