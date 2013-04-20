package ua.com.itinterview.web.resource;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.http.HttpServletRequest;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.com.itinterview.service.UserService;
import ua.com.itinterview.web.command.UserCommand;
import ua.com.itinterview.web.security.AuthenticationUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-web-context.xml")
public class UserResourceIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationUtils authenticationUtils;

    @Autowired
    private UserResource userResource;

    @Before
    public void setUp() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
		.build();
    }

    @Test
    public void testRegisterUserWithInvalidConfirmPasswordEmail()
	    throws Exception {
	mockMvc.perform(
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
	mockMvc.perform(registerUser(null, null, "invalid_email", "", ""))
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

	mockMvc.perform(
		registerUser("UserName", "Viktor", "email@mail.com", "123456",
			"123456", expectedCommand))
		.andExpect(model().hasNoErrors())
		.andExpect(redirectedUrl("/user/23/view"))
		.andExpect(status().isMovedTemporarily());

	verify(userService, authenticationUtils);
	assertEquals("UserName", captureUserName.getValue());
	assertEquals("123456", capturePassword.getValue());

    }

    private MockHttpServletRequestBuilder registerUser(String userName,
	    String name, String email, String password, String confirmPassword) {
	return registerUser(userName, name, email, password, confirmPassword,
		null);
    }

    private MockHttpServletRequestBuilder registerUser(String userName,
	    String name, String email, String password, String confirmPassword,
	    UserCommand userCommand) {
	if (userCommand != null) {
	    userCommand.setConfirmPassword(confirmPassword);
	    userCommand.setEmail(email);
	    userCommand.setName(name);
	    userCommand.setPassword(password);
	    userCommand.setUserName(userName);
	}
	return post("/register").param("userName", userName)
		.param("name", name).param("email", email)
		.param("password", password)
		.param("confirmPassword", confirmPassword);
    }
}
