package ua.com.itinterview.web.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-web-context.xml")
public class UserResourceIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
		.build();
    }

    @Test
    public void testRegisterUserWithInvalidParameters() throws Exception {
	mockMvc.perform(
		registerUser("$%#^", "name", "invalid_email", "123", "321",
			null)).andExpect(model().hasErrors());
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
	return post("/register").param("userName", userName).param(name, name)
		.param("email", email).param("password", password)
		.param("confirmPassword", confirmPassword);
    }
}
