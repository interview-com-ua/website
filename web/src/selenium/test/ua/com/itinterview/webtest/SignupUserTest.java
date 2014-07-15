package ua.com.itinterview.webtest;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ua.com.itinterview.webtest.pages.SignupPage;
import ua.com.itinterview.webtest.pages.UserProfilePage;

@Ignore
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
        driver.get(constructUrl("/register"));
        signupPage.userName.sendKeys("testUser");
        signupPage.email.sendKeys("testUser@domain.com");
        signupPage.name.sendKeys("Name");
        signupPage.password.sendKeys("password1");
        signupPage.confirmPassword.sendKeys("password1");

        userProfilePage.userName.textIs("testUser");
        userProfilePage.name.textIs("Name");
        userProfilePage.email.textIs("testUser@domain.com");
        Assert.fail("Test need to be finished. Test redirect to profile and login");
    }

    @Test
    public void testSignUpUserWithBadDataAndGetErrorMEssages()
    {
        driver.get(constructUrl("/register"));
        signupPage.userName.sendKeys("");
        signupPage.email.sendKeys("invalid_email");
        signupPage.name.sendKeys("");
        signupPage.password.sendKeys("password1");
        signupPage.confirmPassword.sendKeys("password2");
        signupPage.submitButton.click();
        signupPage.emailError.textIs("Неправильный формат email");
        signupPage.userNameError.textIs("Укажите непустое имя пользователя");
        signupPage.nameError.textIs("Укажите непустое имя");
        signupPage.confirmPasswordError
                .textIs("Подтверждение пароля не совпадает с паролем");
    }

}
