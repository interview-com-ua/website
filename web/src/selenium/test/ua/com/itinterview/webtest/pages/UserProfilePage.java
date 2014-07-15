package ua.com.itinterview.webtest.pages;

import org.openqa.selenium.WebDriver;

public class UserProfilePage
{

    public ProxyWebElement userName;

    public ProxyWebElement email;

    public ProxyWebElement name;

    public UserProfilePage(WebDriver driver)
    {
        userName = new ProxyWebElement(driver, "userName");
        email = new ProxyWebElement(driver, "email");
        name = new ProxyWebElement(driver, "name");

    }

}
