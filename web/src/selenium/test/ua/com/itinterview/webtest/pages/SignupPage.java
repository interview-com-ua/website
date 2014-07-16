package ua.com.itinterview.webtest.pages;

import org.openqa.selenium.WebDriver;

public class SignupPage
{

    public ProxyWebElement email;
    public ProxyWebElement emailError;

    public ProxyWebElement name;
    public ProxyWebElement nameError;

    public ProxyWebElement password;

    public ProxyWebElement confirmPassword;
    public ProxyWebElement confirmPasswordError;

    public ProxyWebElement submitButton;

    public SignupPage(WebDriver driver)
    {
        email = new ProxyWebElement(driver, "email");
        emailError = new ProxyWebElement(driver, "email.errors");
        name = new ProxyWebElement(driver, "name");
        nameError = new ProxyWebElement(driver, "name.errors");
        password = new ProxyWebElement(driver, "password");
        confirmPassword = new ProxyWebElement(driver, "confirmPassword");
        confirmPasswordError = new ProxyWebElement(driver,
                "confirmPassword.errors");
        submitButton = new ProxyWebElement(driver, "register");
    }

}
