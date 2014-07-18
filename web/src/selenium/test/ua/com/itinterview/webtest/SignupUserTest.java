package ua.com.itinterview.webtest;

import org.junit.Before;
import org.junit.Test;
import ua.com.itinterview.webtest.pages.SignupPage;
import ua.com.itinterview.webtest.pages.UserProfilePage;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class SignupUserTest extends BaseSeleniumWebTest
{

    private SignupPage signupPage;
    private UserProfilePage userProfilePage;

    @Before
    public void setUpLoginPage()
    {
        signupPage = new SignupPage(driver);
        userProfilePage = new UserProfilePage(driver);
    }

    @Test
    public void testSignUpUserSuccessfullyAndLogin()
    {
        open("/register");
        signupPage.email.sendKeys("testUser@domain.com");
        signupPage.name.sendKeys("Name");
        signupPage.password.sendKeys("password1");
        signupPage.confirmPassword.sendKeys("password1");
        signupPage.submitButton.click();

        assertThat(driver.getCurrentUrl(), containsString("/user/"));
        assertThat(driver.getCurrentUrl(), containsString("/view"));
        userProfilePage.name.textIs("Name");
        userProfilePage.email.textIs("testUser@domain.com");
    }

    @Test
    public void testSignUpUserWithBadDataAndGetErrorMEssages()
    {
        open("/register");
        signupPage.email.sendKeys("invalid_email");
        signupPage.name.sendKeys("");
        signupPage.password.sendKeys("password1");
        signupPage.confirmPassword.sendKeys("password2");
        signupPage.submitButton.click();
        signupPage.emailError.textIs("Неправильный формат email");
        signupPage.nameError.textIs("Укажите непустое имя");
        signupPage.confirmPasswordError
                .textIs("Подтверждение пароля не совпадает с паролем");
    }

}
