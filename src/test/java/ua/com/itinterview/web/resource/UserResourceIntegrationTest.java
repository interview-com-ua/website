package ua.com.itinterview.web.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;

import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.integration.BaseWebIntegrationTest;

public class UserResourceIntegrationTest extends BaseWebIntegrationTest {

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

	ResultActions requestActions = mvc.perform(registerUser("UserName",
		"Viktor", "email@mail.com", "123456", "123456"));
	String userId = String.valueOf(userDao.getUserByUserName("UserName")
		.getId());
	requestActions.andExpect(model().hasNoErrors())
		.andExpect(redirectedUrl("/user/" + userId + "/view"))
		.andExpect(status().isMovedTemporarily());

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
