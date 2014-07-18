package ua.com.itinterview.webtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: viktor
 * Date: 7/18/14
 * Time: 2:42 PM
 */
public class LoginPage
{

    public ProxyWebElement userName;
    public ProxyWebElement password;
    public ProxyWebElement submitButton;

    public LoginPage(WebDriver driver)
    {
        userName = new ProxyWebElement(driver, By.name("j_username"));
        password = new ProxyWebElement(driver, By.name("j_password"));
        submitButton = new ProxyWebElement(driver, "loginBtn");
    }
}
