package ua.com.itinterview.web.resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.integration.BaseWebIntegrationTest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserResourceIntegrationTest extends BaseWebIntegrationTest {

    public static final String USER_NAME = "UserName";
    public static final String USER_NAME_ANOTHER = "UserName2";
    public static final String NAME = "Name";
    public static final String NAME_ANOTHER = "Name2";
    public static final String EMAIL = "email@mail.com";
    public static final String EMAIL_ANOTHER = "email2@mail.com";
    public static final String PASSWORD = "password";
    public static final String PASSWORD_ANOTHER = "password2";
    public static final UserEntity.Sex SEX = UserEntity.Sex.FEMALE;
    public static final UserEntity.Sex SEX_ANOTHER = UserEntity.Sex.MALE;

    public static final String INVALID_USER_NAME = "$%#^";
    public static final String INVALID_EMAIL = "invalid_email";


    @Autowired
    private UserDao userDao;

    @Test
    public void testRegisterUserWithInvalidConfirmPasswordEmail()
            throws Exception {
        mvc.perform(
                registerUser(INVALID_USER_NAME, NAME, INVALID_EMAIL, PASSWORD,
                        PASSWORD_ANOTHER))
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
        mvc.perform(registerUser(null, null, INVALID_EMAIL, "", ""))
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
        ResultActions requestActions = mvc.perform(registerUser(USER_NAME,
                NAME, EMAIL, PASSWORD, PASSWORD));
        String userId = String.valueOf(userDao.getUserByUserName(USER_NAME)
                .getId());
        requestActions.andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/user/" + userId + "/view"))
                .andExpect(status().isMovedTemporarily());

    }

    @Test
    public void testRegisterUserWithUserNameAlreadyExists() throws Exception {
        createUser(USER_NAME, NAME, EMAIL, PASSWORD);
        mvc.perform(
                registerUser(USER_NAME, NAME, EMAIL_ANOTHER, PASSWORD, PASSWORD))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(
                        model().attributeHasFieldErrors("userCommand",
                                "userName"));
    }

    @Test
    public void testRegisterUserWithEmailAlreadyExists() throws Exception {
        createUser(USER_NAME, NAME, EMAIL, PASSWORD);
        mvc.perform(
                registerUser(USER_NAME_ANOTHER, NAME, EMAIL, PASSWORD, PASSWORD))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(
                        model().attributeHasFieldErrors("userCommand", "email"));
    }

    @Test
    public void testMoveToRegisterIfAuthenticatedRequired() throws Exception {
        String[] authenticatedRequiredUrls = {"/user/0/view", "/user/0/edit"};
        for (String url : authenticatedRequiredUrls) {
            mvc.perform(get(url))
                    .andExpect(redirectedUrl("http://localhost/register"))
                    .andExpect(status().isMovedTemporarily());
        }
    }

    @Test
    public void testRedirectAfterLogin() throws Exception {
        UserEntity user = createUser(USER_NAME, NAME, EMAIL, PASSWORD);
        String userProfileUrl = "/user/" + user.getId() + "/view";
        mvc.perform(loginUser(USER_NAME, PASSWORD))
                .andExpect(redirectedUrl(userProfileUrl))
                .andExpect(status().isMovedTemporarily());
    }

    @Test
    public void testRedirectAfterLogout() throws Exception {
        createUser(USER_NAME, NAME, EMAIL, PASSWORD);
        ResultActions actions = mvc.perform(loginUser(USER_NAME, PASSWORD));
        mvc.perform(logout(getHttpSession(actions)))
                .andExpect(redirectedUrl("/register"))
                .andExpect(status().isMovedTemporarily());
    }

    @Test
    public void testReturnToTargetAfterSuccessLogin() throws Exception {
        UserEntity user = createUser(USER_NAME, NAME, EMAIL, PASSWORD);
        String userTargetUrl = "http://localhost/user/" + user.getId() + "/edit";

        ResultActions actions = mvc.perform(get(userTargetUrl))
                .andExpect(redirectedUrl("http://localhost/register"))
                .andExpect(status().isMovedTemporarily());

        mvc.perform(loginUser(USER_NAME, PASSWORD)
                .session(getHttpSession(actions))).andExpect(redirectedUrl(userTargetUrl));
    }

    @Test
    public void testEditProfileChangeOnlyName() throws Exception {
        UserEntity user = createUser(USER_NAME, NAME, EMAIL, PASSWORD, SEX);
        String userSaveProfileUrl = "/user/" + user.getId() + "/save";
        String userProfileUrl = "/user/" + user.getId() + "/view";
        ResultActions actions = mvc.perform(loginUser(USER_NAME, PASSWORD));

        mvc.perform(post(userSaveProfileUrl).session(getHttpSession(actions))
                .param("name", NAME_ANOTHER)
                .param("email", EMAIL)
                .param("sex", SEX.toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl(userProfileUrl));

        UserEntity modifiedUser = userDao.getUserByUserName(USER_NAME);
        assertEquals(NAME_ANOTHER, modifiedUser.getName());
        assertEquals(EMAIL, modifiedUser.getEmail());
        assertEquals(SEX, modifiedUser.getSex());
    }

    @Test
    public void testEditProfileChangeOnlyEmail() throws Exception {
        UserEntity user = createUser(USER_NAME, NAME, EMAIL, PASSWORD, SEX);
        String userSaveProfileUrl = "/user/" + user.getId() + "/save";
        String userProfileUrl = "/user/" + user.getId() + "/view";
        ResultActions actions = mvc.perform(loginUser(USER_NAME, PASSWORD));

        mvc.perform(post(userSaveProfileUrl).session(getHttpSession(actions))
                .param("name", NAME)
                .param("email", EMAIL_ANOTHER)
                .param("sex", SEX.toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl(userProfileUrl));

        UserEntity modifiedUser = userDao.getUserByUserName(USER_NAME);
        assertEquals(NAME, modifiedUser.getName());
        assertEquals(EMAIL_ANOTHER, modifiedUser.getEmail());
        assertEquals(SEX, modifiedUser.getSex());
    }

    @Test
    public void testEditProfileChangeOnlySex() throws Exception {
        UserEntity user = createUser(USER_NAME, NAME, EMAIL, PASSWORD, SEX);
        String userSaveProfileUrl = "/user/" + user.getId() + "/save";
        String userProfileUrl = "/user/" + user.getId() + "/view";
        ResultActions actions = mvc.perform(loginUser(USER_NAME, PASSWORD));

        mvc.perform(post(userSaveProfileUrl).session(getHttpSession(actions))
                .param("name", NAME)
                .param("email", EMAIL)
                .param("sex", SEX_ANOTHER.toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl(userProfileUrl));

        UserEntity modifiedUser = userDao.getUserByUserName(USER_NAME);
        assertEquals(NAME, modifiedUser.getName());
        assertEquals(EMAIL, modifiedUser.getEmail());
        assertEquals(SEX_ANOTHER, modifiedUser.getSex());
    }

    @Test
    public void testEditProfileChangeAll() throws Exception {
        UserEntity user = createUser(USER_NAME, NAME, EMAIL, PASSWORD, SEX);
        String userSaveProfileUrl = "/user/" + user.getId() + "/save";
        String userProfileUrl = "/user/" + user.getId() + "/view";
        ResultActions actions = mvc.perform(loginUser(USER_NAME, PASSWORD));

        mvc.perform(post(userSaveProfileUrl).session(getHttpSession(actions))
                .param("name", NAME_ANOTHER)
                .param("email", EMAIL_ANOTHER)
                .param("sex", SEX_ANOTHER.toString()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl(userProfileUrl));

        UserEntity modifiedUser = userDao.getUserByUserName(USER_NAME);
        assertEquals(NAME_ANOTHER, modifiedUser.getName());
        assertEquals(EMAIL_ANOTHER, modifiedUser.getEmail());
        assertEquals(SEX_ANOTHER, modifiedUser.getSex());
    }

    @Test
    public void testEditProfileInvalidEmail() throws Exception {
        UserEntity user = createUser(USER_NAME, NAME, EMAIL, PASSWORD, SEX);
        String userSaveProfileUrl = "/user/" + user.getId() + "/save";
        ResultActions actions = mvc.perform(loginUser(USER_NAME, PASSWORD));

        mvc.perform(post(userSaveProfileUrl).session(getHttpSession(actions))
                .param("name", NAME)
                .param("email", INVALID_EMAIL)
                .param("sex", SEX.toString()))
                .andExpect(view().name("profile_page"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeErrorCount("userEditProfileCommand", 1))
                .andExpect(model().attributeHasFieldErrors("userEditProfileCommand", "email"))
                .andExpect(status().isOk()).andDo(print());

        UserEntity modifiedUser = userDao.getUserByUserName(USER_NAME);
        assertEquals(NAME, modifiedUser.getName());
        assertEquals(EMAIL, modifiedUser.getEmail());
        assertEquals(SEX, modifiedUser.getSex());
    }

    @Test
    public void testEditProfileAllWrong() throws Exception {
        UserEntity user = createUser(USER_NAME, NAME, EMAIL, PASSWORD, SEX);
        String userSaveProfileUrl = "/user/" + user.getId() + "/save";
        ResultActions actions = mvc.perform(loginUser(USER_NAME, PASSWORD));

        mvc.perform(post(userSaveProfileUrl).session(getHttpSession(actions))
                .param("name", "")
                .param("email", "")
                .param("sex", ""))
                .andExpect(view().name("profile_page"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeErrorCount("userEditProfileCommand", 3))
                .andExpect(model().attributeHasFieldErrors("userEditProfileCommand", "name", "email", "sex"))
                .andExpect(status().isOk()).andDo(print());

        UserEntity modifiedUser = userDao.getUserByUserName(USER_NAME);
        assertEquals(NAME, modifiedUser.getName());
        assertEquals(EMAIL, modifiedUser.getEmail());
        assertEquals(SEX, modifiedUser.getSex());
    }

    private MockHttpSession getHttpSession(ResultActions actions) {
        return (MockHttpSession) actions.andReturn().getRequest().getSession();
    }
}
