package ua.com.itinterview.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.findby.By;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@DefaultUrl("http://localhost:8080/it-interview/register")
public class SignUpPage extends PageObject {

    @FindBy(css = "#userName")
    public WebElement userName;
    @FindBy(css = "[id=\"userName.errors\"]")
    public WebElement userNameError;

    @FindBy(css = "#email")
    public WebElement email;
    @FindBy(css = "[id=\"email.errors\"]")
    public WebElement emailError;

    @FindBy(css = "#name")
    public WebElement name;
    @FindBy(css = "[id=\"name.errors\"]")
    public WebElement nameError;

    @FindBy(css = "#password")
    public WebElement password;

    @FindBy(css = "#confirmPassword")
    public WebElement confirmPassword;
    @FindBy(css = "[id=\"confirmPassword.errors\"]")
    public WebElement confirmPasswordError;

    @FindBy(css = ".reg_data>input[type=\"submit\"]")
    public WebElement submitButton;

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public void fillLogin(String loginText) {
        element(userName).type(loginText);
    }

    public void fill_name(String nameText) {
        element(name).type(nameText);
    }

    public void fill_email(String emailText) {
        element(email).type(emailText);
    }

    public void fill_password(String passwordText) {
        element(password).type(passwordText);
    }

    public void fill_confirm_password(String confirmPasswordText) {
        element(confirmPassword).type(confirmPasswordText);
    }

    public void fill_sex() {
        List<WebElement> radioButtons = getDriver().findElements(By.cssSelector("#userCommand input[name=\"sex\"]"));
        element(radioButtons.get(0)).click();
    }

    public void submit() {
        submitButton.click();
//        try {
//            Thread.sleep(1 * 1000);
//        } catch (InterruptedException e) {
//        }
    }
}
