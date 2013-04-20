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
import org.springframework.security.authentication.encoding.PasswordEncoder;

import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.entity.UserEntity.Sex;
import ua.com.itinterview.web.command.UserCommand;

public class UserServiceUnitTest {

    private static final int USER_ID = 1;
    private static final String EMAIL = "user@email.com";
    private static final String NAME = "name";
    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "password";
    private static final Sex MALE = Sex.MALE;

    private static final String ENCODED_PASSWORD = "encodedPassword";

    private static final String FAKE_USER_NAME = "Fake User Name";
    private static final String NEW_NAME = "new name";
    private static final String NEW_EMAIL = "new email";
    private static final int NEW_USER_ID = USER_ID + 1;
    private static final Sex NEW_SEX = Sex.FEMALE;

    private UserDao userDaoMock;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Before
    public void createMocks() {
	userDaoMock = createMock(UserDao.class);
	userService = new UserService();
	userService.userDao = userDaoMock;
	passwordEncoder = createMock(PasswordEncoder.class);
	userService.passwordEncoder = passwordEncoder;
    }

    private void replayAllMocks() {
	replay(userDaoMock, passwordEncoder);
    }

    @Test
    public void testConvertUserCommandToUserEntity() {
	replayAllMocks();
	UserCommand userCommand = createUserCommandWithPassword();
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
    public void testCreateUser() {
	UserCommand userCommand = createUserCommandWithPassword();
	UserEntity userEntity = new UserEntity(userCommand);
	userEntity.setPassword(ENCODED_PASSWORD);

	expect(userDaoMock.save(userEntity)).andReturn(userEntity);
	expect(passwordEncoder.encodePassword(PASSWORD, "")).andReturn(
		ENCODED_PASSWORD);
	replayAllMocks();

	UserCommand actualUserCommand = userService.createUser(userCommand);
	UserCommand expectedUserCommand = createUserCommand();
	expectedUserCommand.setId(actualUserCommand.getId());
	assertEquals(expectedUserCommand, actualUserCommand);
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

	UserCommand expectedCommand = createCustomUserCommand(USER_ID, null,
		NEW_EMAIL, NEW_NAME, USER_NAME, NEW_SEX);
	assertEquals(expectedCommand,
		userService.updateUser(USER_ID, userToUpdate));

	UserEntity actualSavedEntity = userToSaveCapture.getValue();
	assertEquals(NEW_EMAIL, actualSavedEntity.getEmail());
	assertEquals(NEW_NAME, actualSavedEntity.getName());
	assertEquals(USER_NAME, actualSavedEntity.getUserName());
	assertEquals(USER_ID, actualSavedEntity.getId());
	assertEquals(PASSWORD, actualSavedEntity.getPassword());
	assertEquals(NEW_SEX, actualSavedEntity.getSex());
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
	verify(userDaoMock, passwordEncoder);
    }

    private UserCommand createCustomUserCommand(int id, String password,
	    String email, String name, String userName, Sex sex) {
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
	UserCommand command = createCustomUserCommand(USER_ID, null, EMAIL,
		NAME, USER_NAME, MALE);
	return command;
    }

    private UserCommand createUserCommandWithPassword() {
	UserCommand res = createUserCommand();
	res.setPassword(PASSWORD);
	res.setConfirmPassword(PASSWORD);
	return res;
    }

    private UserEntity createUserEntity() {
	UserEntity userEntity = new UserEntity();
	userEntity.setId(USER_ID);
	userEntity.setPassword(PASSWORD);
	userEntity.setEmail(EMAIL);
	userEntity.setName(NAME);
	userEntity.setUserName(USER_NAME);
	userEntity.setSex(MALE);
	return userEntity;
    }
}
