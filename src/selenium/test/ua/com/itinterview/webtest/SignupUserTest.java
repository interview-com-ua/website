package ua.com.itinterview.webtest;

import org.junit.Before;
import org.junit.Test;

import ua.com.itinterview.webtest.pages.SignupPage;

public class SignupUserTest extends BaseSeleniumWebTest {

    private SignupPage signupPage;

    @Before
    public void setUpLoginPage() {
	signupPage = new SignupPage(driver);
    }

    @Test
    public void testSignUpUserWithBadDataAndGetErrorMEssages() {
	driver.get(constructUrl("/user/signup"));
	signupPage.userName.sendKeys("");
	signupPage.email.sendKeys("invalid_email");
	signupPage.name.sendKeys("");
	signupPage.password.sendKeys("password1");
	signupPage.confirmPassword.sendKeys("password2");
	signupPage.confirmButton.click();
	signupPage.emailError.textIs("Неправильный формат email");
	signupPage.userNameError.textIs("Укажите непустое имя пользователя");
	signupPage.nameError.textIs("Укажите непустое имя");
	signupPage.confirmPasswordError
		.textIs("Подтверждение пароля не совпадает с паролем");
    }

}
