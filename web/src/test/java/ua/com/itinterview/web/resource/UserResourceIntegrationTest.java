package ua.com.itinterview.web.resource;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.integration.BaseWebIntegrationTest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserResourceIntegrationTest extends BaseWebIntegrationTest
{

    @Autowired
    private UserDao userDao;

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-empty.xml")
    @ExpectedDatabase(
            value = "file:src/test/resources/dataset/UserResource/users-empty.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT
    )
    public void testRegisterUserWithInvalidConfirmPasswordEmail()
            throws Exception
    {
        mvc.perform(
                registerUser(NAME, INVALID_EMAIL, PASSWORD, PASSWORD_ANOTHER))
                .andExpect(model().hasErrors())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeErrorCount("userCommand", 2))
                .andExpect(model().attributeHasFieldErrors("userCommand", "email", "confirmPassword"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-empty.xml")
    public void testMoveToRegisterIfAuthenticatedRequired() throws Exception
    {
        String[] authenticatedRequiredUrls = {"/user/0/view", "/user/0/edit"};
        for (String url : authenticatedRequiredUrls)
        {
            mvc.perform(get(url))
                    .andExpect(redirectedUrl("http://localhost/register"))
                    .andExpect(status().isMovedTemporarily());
        }
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-empty.xml")
    @ExpectedDatabase(
            value = "file:src/test/resources/dataset/UserResource/users-testRegisterUser.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT
    )
    public void testRegisterUser() throws Exception
    {
        ResultActions requestActions = mvc.perform(registerUser(NAME, EMAIL, PASSWORD, PASSWORD));
        String userId = String.valueOf(userDao.getUserByEmail(EMAIL).getId());
        requestActions.andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/user/" + userId + "/view"))
                .andExpect(status().isMovedTemporarily());
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    public void testRegisterUserWithEmailAlreadyExists() throws Exception
    {
        mvc.perform(
                registerUser(NAME, EMAIL, PASSWORD, PASSWORD))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("userCommand", "email"));
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    public void testRedirectAfterLogin() throws Exception
    {
        UserEntity user = userDao.getUserByEmail(EMAIL);
        String userProfileUrl = "/user/" + user.getId() + "/view";
        mvc.perform(loginUser(EMAIL, PASSWORD))
                .andExpect(redirectedUrl(userProfileUrl))
                .andExpect(status().isMovedTemporarily());
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    public void testRedirectAfterLogout() throws Exception
    {
        ResultActions actions = mvc.perform(loginUser(EMAIL, PASSWORD));
        mvc.perform(logout(getHttpSession(actions)))
                .andExpect(redirectedUrl("/register"))
                .andExpect(status().isMovedTemporarily());
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    public void testReturnToTargetAfterSuccessLogin() throws Exception
    {
        UserEntity user = userDao.getUserByEmail(EMAIL);
        String userTargetUrl = "http://localhost/user/" + user.getId() + "/edit";

        ResultActions actions = mvc.perform(get(userTargetUrl))
                .andExpect(redirectedUrl("http://localhost/register"))
                .andExpect(status().isMovedTemporarily());

        mvc.perform(loginUser(EMAIL, PASSWORD)
                .session(getHttpSession(actions))).andExpect(redirectedUrl(userTargetUrl));
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    public void testEditProfileChangeOnlyName() throws Exception
    {
        UserEntity user = userDao.getUserByEmail(EMAIL);
        String userSaveProfileUrl = "/user/" + user.getId() + "/save";
        String userProfileUrl = "/user/" + user.getId() + "/view";
        ResultActions actions = mvc.perform(loginUser(EMAIL, PASSWORD));

        mvc.perform(post(userSaveProfileUrl).session(getHttpSession(actions))
                .param("name", NAME_ANOTHER)
                .param("email", EMAIL))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl(userProfileUrl));

        UserEntity modifiedUser = userDao.getUserByEmail(EMAIL);
        assertEquals(NAME_ANOTHER, modifiedUser.getName());
        assertEquals(EMAIL, modifiedUser.getEmail());
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    public void testEditProfileChangeOnlyEmail() throws Exception
    {
        UserEntity user = userDao.getUserByEmail(EMAIL);
        String userSaveProfileUrl = "/user/" + user.getId() + "/save";
        String userProfileUrl = "/user/" + user.getId() + "/view";
        ResultActions actions = mvc.perform(loginUser(EMAIL, PASSWORD));

        mvc.perform(post(userSaveProfileUrl).session(getHttpSession(actions))
                .param("name", NAME)
                .param("email", EMAIL_ANOTHER))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl(userProfileUrl));

        UserEntity modifiedUser = userDao.getUserByEmail(EMAIL_ANOTHER);
        assertEquals(NAME, modifiedUser.getName());
        assertEquals(EMAIL_ANOTHER, modifiedUser.getEmail());
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    public void testEditProfileChangeOnlySex() throws Exception
    {
        UserEntity user = userDao.getUserByEmail(EMAIL);
        String userSaveProfileUrl = "/user/" + user.getId() + "/save";
        String userProfileUrl = "/user/" + user.getId() + "/view";
        ResultActions actions = mvc.perform(loginUser(EMAIL, PASSWORD));

        mvc.perform(post(userSaveProfileUrl).session(getHttpSession(actions))
                .param("name", NAME)
                .param("email", EMAIL))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl(userProfileUrl));

        UserEntity modifiedUser = userDao.getUserByEmail(EMAIL);
        assertEquals(NAME, modifiedUser.getName());
        assertEquals(EMAIL, modifiedUser.getEmail());
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    public void testEditProfileChangeAll() throws Exception
    {
        UserEntity user = userDao.getUserByEmail(EMAIL);
        String userSaveProfileUrl = "/user/" + user.getId() + "/save";
        String userProfileUrl = "/user/" + user.getId() + "/view";
        ResultActions actions = mvc.perform(loginUser(EMAIL, PASSWORD));

        mvc.perform(post(userSaveProfileUrl).session(getHttpSession(actions))
                .param("name", NAME_ANOTHER)
                .param("email", EMAIL_ANOTHER))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl(userProfileUrl));

        UserEntity modifiedUser = userDao.getUserByEmail(EMAIL_ANOTHER);
        assertEquals(NAME_ANOTHER, modifiedUser.getName());
        assertEquals(EMAIL_ANOTHER, modifiedUser.getEmail());
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    public void testEditProfileInvalidEmail() throws Exception
    {
        UserEntity user = userDao.getUserByEmail(EMAIL);
        String userSaveProfileUrl = "/user/" + user.getId() + "/save";
        ResultActions actions = mvc.perform(loginUser(EMAIL, PASSWORD));

        mvc.perform(post(userSaveProfileUrl).session(getHttpSession(actions))
                .param("name", NAME)
                .param("email", INVALID_EMAIL))
                .andExpect(view().name("profile_page"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeErrorCount("userEditProfileCommand", 1))
                .andExpect(model().attributeHasFieldErrors("userEditProfileCommand", "email"))
                .andExpect(status().isOk()).andDo(print());

        UserEntity modifiedUser = userDao.getUserByEmail(EMAIL);
        assertEquals(NAME, modifiedUser.getName());
        assertEquals(EMAIL, modifiedUser.getEmail());
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    public void testEditProfileAllWrong() throws Exception
    {
        UserEntity user = userDao.getUserByEmail(EMAIL);
        String userSaveProfileUrl = "/user/" + user.getId() + "/save";
        ResultActions actions = mvc.perform(loginUser(EMAIL, PASSWORD));

        mvc.perform(post(userSaveProfileUrl).session(getHttpSession(actions))
                .param("name", "")
                .param("email", ""))
                .andExpect(view().name("profile_page"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeErrorCount("userEditProfileCommand", 2))
                .andExpect(model().attributeHasFieldErrors("userEditProfileCommand", "name", "email"))
                .andExpect(status().isOk())
                .andDo(print());

        UserEntity modifiedUser = userDao.getUserByEmail(EMAIL);
        assertEquals(NAME, modifiedUser.getName());
        assertEquals(EMAIL, modifiedUser.getEmail());
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    public void shouldReturnErrorKeysWhenOldPasswordIsNotCorrect() throws Exception
    {
        ResultActions actions = mvc.perform(loginUser(EMAIL, PASSWORD));
        String changePasswordUrl = "/user/" + USER_ID + "/change_password";
        String oldPassword = "invalidPassword";
        String newPassword = "newPassword";
        mvc.perform(post(changePasswordUrl).session(getHttpSession(actions)).
                param("userId", USER_ID).
                param("oldPassword", oldPassword).
                param("newPassword", newPassword).
                param("confirmPassword", newPassword)).
                andDo(print()).
                andExpect(view().name("profile_page")).
                andExpect(status().isOk()).
                andExpect(model().hasErrors()).
                andExpect(model().attributeErrorCount("changePasswordCommand", 1)).
                andExpect(model().attributeHasFieldErrors("changePasswordCommand", "oldPassword"));
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    @ExpectedDatabase(value = "file:src/test/resources/dataset/UserResource/user-after-password-changed.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void shouldChangePassword() throws Exception
    {
        ResultActions actions = mvc.perform(loginUser(EMAIL, PASSWORD));
        UserEntity user = userDao.getUserByEmail(EMAIL);
        String changePasswordUrl = "/user/" + user.getId() + "/change_password";

        String newPassword = "newPassword";
        String userProfileUrl = "/user/" + user.getId() + "/view";
        mvc.perform(post(changePasswordUrl).session(getHttpSession(actions)).
                param("userId", user.getId() + "").
                param("oldPassword", PASSWORD).
                param("newPassword", newPassword).
                param("confirmPassword", newPassword)).
                andExpect(status().isMovedTemporarily()).
                andExpect(redirectedUrl(userProfileUrl)).
                andExpect(model().hasNoErrors()).
                andDo(print());

        mvc.perform(loginUser(EMAIL, newPassword))
                .andExpect(redirectedUrl(userProfileUrl))
                .andExpect(status().isMovedTemporarily());

        mvc.perform(loginUser(EMAIL, PASSWORD))
                .andExpect(redirectedUrl("/register?login_error=1"))
                .andExpect(status().isMovedTemporarily());
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    public void shouldReturnErrorKeysWhenConfirmPasswordNotEqualsNewPassword() throws Exception
    {
        String changePasswordUrl = "/user/" + USER_ID + "/change_password";

        ResultActions actions = mvc.perform(loginUser(EMAIL, PASSWORD));
        String newPassword = "newPassword";
        String confirmPassword = "confirmPassword";
        mvc.perform(post(changePasswordUrl).session(getHttpSession(actions)).
                param("userId", USER_ID).
                param("oldPassword", PASSWORD).
                param("newPassword", newPassword).
                param("confirmPassword", confirmPassword)).
                andExpect(view().name("profile_page")).
                andExpect(status().isOk()).
                andExpect(model().hasErrors()).
                andExpect(model().attributeErrorCount("changePasswordCommand", 1)).
                andExpect(model().attributeHasFieldErrors("changePasswordCommand", "confirmPassword"));
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    public void shouldReturnErrorKeysWhenNewPasswordEqualsOldPassword() throws Exception
    {
        String changePasswordUrl = "/user/" + USER_ID + "/change_password";

        ResultActions actions = mvc.perform(loginUser(EMAIL, PASSWORD));
        String newPassword = PASSWORD;
        mvc.perform(post(changePasswordUrl).session(getHttpSession(actions)).
                param("userId", USER_ID).
                param("oldPassword", PASSWORD).
                param("newPassword", newPassword).
                param("confirmPassword", newPassword)).
                andExpect(view().name("profile_page")).
                andExpect(status().isOk()).
                andExpect(model().hasErrors()).
                andExpect(model().attributeErrorCount("changePasswordCommand", 1)).
                andExpect(model().attributeHasFieldErrors("changePasswordCommand", "newPassword"));
    }

    @Test
    @DatabaseSetup("file:src/test/resources/dataset/UserResource/users-initial.xml")
    public void shouldReturnForbiddenStatusWhenUserTryChangeNotHisPassword() throws Exception
    {
        ResultActions actions = mvc.perform(loginUser(EMAIL, PASSWORD));
        String fakeUserId = "11";
        String changePasswordUrl = "/user/" + fakeUserId + "/change_password";
        String newPassword = "newPassword2";
        mvc.perform(post(changePasswordUrl).session(getHttpSession(actions)).
                param("userId", fakeUserId).
                param("oldPassword", PASSWORD).
                param("newPassword", newPassword).
                param("confirmPassword", newPassword)).
                andExpect(status().isFound()).
                andExpect(redirectedUrl("/user/" + USER_ID + "/view"));
    }

    @Test
    public void shouldResetPassword() throws Exception {

        mvc.perform(post("/reset_password").
                param("email", EMAIL)).
                andDo(print()).
                andExpect(view().name("reset_password_info")).
                andExpect(status().isOk());

        String hash = "111111";
        mvc.perform(get("/confirm_reset_password/" + hash)).
                andDo(print()).
                andExpect(view().name("confirm_reset_password")).
                andExpect(status().isOk()).
                andExpect(model().attribute("hash", is(hash)));

        String resetPassword = "reset_password";
        mvc.perform(post("/confirm_reset_password").
                param("password", resetPassword).
                param("confirmPassword", resetPassword)).
                andDo(print()).
                andExpect(view().name("confirm_reset_password_success")).
                andExpect(status().isOk());

        UserEntity user = userDao.getUserByEmail(EMAIL);
        String userProfileUrl = "/user/" + user.getId() + "/view";
        mvc.perform(loginUser(EMAIL, resetPassword))
                .andExpect(redirectedUrl(userProfileUrl))
                .andExpect(status().isMovedTemporarily());
    }

    @Test
    public void expectNotFoundWhenInvalidHash() throws Exception {
        String hash = "invalidHash";
        mvc.perform(get("/confirm_reset_password/" + hash)).
                andDo(print()).
                andExpect(view().name("confirm_reset_password")).
                andExpect(status().isNotFound());
    }

    @Test
    @ExpectedDatabase(value = "file:src/test/resources/dataset/UserResource/reset-password-hash.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void shouldSaveHashToDatabase() throws Exception {

        mvc.perform(post("/reset_password").
                param("email", EMAIL)).
                andDo(print());
    }

}