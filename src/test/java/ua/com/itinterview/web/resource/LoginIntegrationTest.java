package ua.com.itinterview.web.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.web.integration.BaseWebIntegrationTest;

public class LoginIntegrationTest extends BaseWebIntegrationTest {

    @Autowired
    private UserDao userDao;
    private String userId;

    @Before
    public void createTestUser() throws Exception {
	mvc.perform(
		registerUser("testUserName", "name", "email@email.com",
			"password", "password")).andExpect(
		status().isMovedTemporarily());
	userId = String.valueOf(userDao.getUserByUserName("testUserName")
		.getId());
    }

    @Test
    @Ignore
    public void testSuccessLogin() throws Exception {
	mvc.perform(loginUser("testUserName", "password"))
		.andExpect(status().isMovedTemporarily())
		.andExpect(redirectedUrl("/user/" + userId + "/view"));
    }
}
