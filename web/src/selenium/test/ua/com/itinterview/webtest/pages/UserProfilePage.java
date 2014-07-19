package ua.com.itinterview.webtest.pages;

import org.openqa.selenium.WebDriver;

public class UserProfilePage
{

    public ProxyWebElement email;

    public ProxyWebElement name;

    public ProxyWebElement oldPassword;
    public ProxyWebElement newPassword;
    public ProxyWebElement confirmPassword;
    public ProxyWebElement changePasswordButton;

    public UserProfilePage(WebDriver driver)
    {
        email = new ProxyWebElement(driver, "email");
        name = new ProxyWebElement(driver, "name");
        oldPassword = new ProxyWebElement(driver, "oldPassword");
        newPassword = new ProxyWebElement(driver, "newPassword");
        confirmPassword = new ProxyWebElement(driver, "confirmPassword");
        changePasswordButton = new ProxyWebElement(driver, "submitPasswordBtn");
    }

}
