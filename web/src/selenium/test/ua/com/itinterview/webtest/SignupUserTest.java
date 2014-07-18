package ua.com.itinterview.webtest;

import org.junit.Before;
import org.junit.Test;
import ua.com.itinterview.webtest.pages.SignupPage;
import ua.com.itinterview.webtest.pages.UserProfilePage;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class SignupUserTest extends BaseSeleniumWebTest
{

    private UserProfilePage userProfilePage;

    @Before
    public void setUpLoginPage()
    {
        userProfilePage = new UserProfilePage(driver);
    }

    @Test
    public void testSignUpUserSuccessfullyAndLogin()
    {
        registerUser("testUser@domain.com", "Name", "password1");

        assertThat(driver.getCurrentUrl(), containsString("/user/"));
        assertThat(driver.getCurrentUrl(), containsString("/view"));
        userProfilePage.name.textIs("Name");
        userProfilePage.email.textIs("testUser@domain.com");
    }

    @Test
    public void testSignUpUserWithBadDataAndGetErrorMEssages()
    {
        registerUser("invalid_email", "", "password1", "password2");

        SignupPage signupPage = getSignupPage();

        signupPage.emailError.textIs("Неправильный формат email");
        signupPage.nameError.textIs("Укажите непустое имя");
        signupPage.confirmPasswordError
                .textIs("Подтверждение пароля не совпадает с паролем");
    }

}
