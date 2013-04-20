package ua.com.itinterview.web.resource;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import ua.com.itinterview.service.UserService;
import ua.com.itinterview.web.command.UserCommand;
import ua.com.itinterview.web.resource.viewpages.ModeView;

public class UserResourceStandaloneTest {

    private MockMvc mockMvc;

    private UserService userService;

    @Before
    public void setUpMvc() {
	UserResource userResource = new UserResource();
	mockMvc = standaloneSetup(userResource).build();
	userService = createMock(UserService.class);
	userResource.userService = userService;
    }

    @Test
    public void testRegisterPage() throws Exception {
	mockMvc.perform(get("/register"))
		.andExpect(status().isOk())
		.andExpect(view().name("signup"))
		.andExpect(model().attribute("mode", ModeView.CREATE))
		.andExpect(
			model().attribute("userCommand", is(UserCommand.class)));
    }

    @Test
    public void testRegisterUser() throws Exception {
	UserCommand expectedCommand = new UserCommand();
	expectedCommand.setId(23);
	expect(userService.createUser(anyObject(UserCommand.class))).andReturn(
		expectedCommand);
	replay(userService);
	mockMvc.perform(
		registerUser("UserName", "Viktor", "", "", "", expectedCommand))
		.andExpect(redirectedUrl("/user/23/view"));
	verify(userService);
    }

    private MockHttpServletRequestBuilder registerUser(String userName,
	    String name, String email, String password, String confirmPassword,
	    UserCommand userCommand) {
	userCommand.setConfirmPassword(confirmPassword);
	userCommand.setEmail(email);
	userCommand.setName(name);
	userCommand.setPassword(password);
	userCommand.setUserName(userName);
	return post("/register").param("userName", userName).param(name, name)
		.param("email", email).param("password", password)
		.param("confirmPassword", confirmPassword);
    }
}
