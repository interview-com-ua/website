package ua.com.itinterview.web.resource;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.http.HttpServletRequest;

import org.easymock.Capture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.service.UserService;
import ua.com.itinterview.web.command.UserCommand;
import ua.com.itinterview.web.integration.BaseWebIntegrationTest;
import ua.com.itinterview.web.security.AuthenticationUtils;

@ContextConfiguration("classpath:mocked-services-context.xml")
public class UserResourceIntegrationTest extends BaseWebIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationUtils authenticationUtils;

    @Autowired
    private UserResource userResource;

    @Autowired
    private UserDao userDao;

    @Test
    public void testRegisterUserWithInvalidConfirmPasswordEmail()
	    throws Exception {
	mvc.perform(
		registerUser("$%#^", "name", "invalid_email", "123321",
			"321123"))
		.andExpect(model().hasErrors())
		.andExpect(view().name("signup"))
		.andExpect(model().attributeErrorCount("userCommand", 3))
		.andExpect(
			model().attributeHasFieldErrors("userCommand",
				"userName", "email", "confirmPassword"))
		.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testRegisterUserWithUserNameNullName() throws Exception {
	mvc.perform(registerUser(null, null, "invalid_email", "", ""))
		.andExpect(model().hasErrors())
		.andExpect(view().name("signup"))
		.andExpect(model().attributeErrorCount("userCommand", 5))
		.andExpect(
			model().attributeHasFieldErrors("userCommand",
				"userName", "email", "name", "password",
				"confirmPassword")).andExpect(status().isOk())
		.andDo(print());
    }

    @Test
    public void testRegisterUser() throws Exception {
	UserCommand expectedCommand = new UserCommand();
	expectedCommand.setId(23);
	expect(userService.createUser(anyObject(UserCommand.class))).andReturn(
		expectedCommand);
	Capture<String> captureUserName = new Capture<String>();
	Capture<String> capturePassword = new Capture<String>();
	expect(
		authenticationUtils.loginUser(capture(captureUserName),
			capture(capturePassword),
			anyObject(HttpServletRequest.class))).andReturn(null);
	replay(userService, authenticationUtils);

	mvc.perform(
		registerUser("UserName", "Viktor", "email@mail.com", "123456",
			"123456", expectedCommand))
		.andExpect(model().hasNoErrors())
		.andExpect(redirectedUrl("/user/23/view"))
		.andExpect(status().isMovedTemporarily());

	verify(userService, authenticationUtils);
	assertEquals("UserName", captureUserName.getValue());
	assertEquals("123456", capturePassword.getValue());

    }

    @Test
    public void testRegisterUserWithUserNameAlreadyExists() throws Exception {
	insertUserToDataBase("someTest@email.com", "testUserName");
	mvc.perform(
		registerUser("testUserName", "name", "valid@email.com",
			"123456", "123456"))
		.andExpect(model().hasErrors())
		.andExpect(model().errorCount(1))
		.andExpect(
			model().attributeHasFieldErrors("userCommand",
				"userName"));
    }

    @Test
    public void testRegisterUserWithEmailAlreadyExists() throws Exception {
	insertUserToDataBase("exist@email.com", "testUserName");
	mvc.perform(
		registerUser("validUserName", "name", "exist@email.com",
			"123456", "123456"))
		.andExpect(model().hasErrors())
		.andExpect(model().errorCount(1))
		.andExpect(
			model().attributeHasFieldErrors("userCommand", "email"));
    }

    @Rollback
    private void insertUserToDataBase(String email, String userName) {
	UserEntity user = new UserEntity();
	user.setEmail(email);
	user.setUserName(userName);
	userDao.save(user);
    }
}
