package ua.com.itinterview.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.findby.By;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@DefaultUrl("http://localhost:8080/it-interview/register")
public class SignUpPage extends PageObject {

    @FindBy(css = ".reg_data>input[type=\"submit\"]")
    public WebElement submitButton;

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public void submit() {
        submitButton.click();
    }

    public void fill(String fieldName, String value) {
        WebElement element = getDriver().findElement(By.cssSelector("#" + fieldName));
        element(element).type(value);
    }

    public boolean isErrorDisplayed(String fieldName, String message) {
        WebElement element = getDriver().findElement(By.cssSelector("[id=\"" + fieldName + ".errors\"]"));
        return element.isDisplayed() && element.getText().equals(message);
    }
}
