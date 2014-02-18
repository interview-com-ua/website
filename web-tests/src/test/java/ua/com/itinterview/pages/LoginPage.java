package ua.com.itinterview.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by kdv on 2/16/14.
 */
@DefaultUrl("/register")
public class LoginPage extends PageObject {

    private final WebDriver driver;
    private By userNameText = By.name("j_username");
    private By userPasswordText = By.name("j_password");
    private By loginButton = By.className("enter_but");

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void typeUserName(String username) {
        driver.findElement(userNameText).sendKeys(username);
    }

    public void typeUserPassword(String password){
        driver.findElement(userPasswordText).sendKeys(password);
    }

    public void sumbitLogin(){
        driver.findElement(loginButton).click();
    }

    public void loginAs(String userName, String userPassword){
        typeUserName(userName);
        typeUserPassword(userPassword);
        sumbitLogin();
    }
}
