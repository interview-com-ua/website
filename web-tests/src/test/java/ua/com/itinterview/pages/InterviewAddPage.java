package ua.com.itinterview.pages;

import com.thoughtworks.selenium.Selenium;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by kdv on 2/16/14.
 */
@DefaultUrl("/interview/add")
public class InterviewAddPage extends PageObject {

    @FindBy(id = "add_interview")
    public WebElement submitButton;

    @FindBy(name = "company")
    public WebElement comboBoxCompany;

    @FindBy(name = "city")
    public WebElement comboBoxCity;

    @FindBy(name = "position")
    public WebElement comboBoxPosition;

    @FindBy(name = "technology")
    public WebElement comboBoxTechnology;

    @FindBy(name = "feedback")
    public WebElement textBoxFeedback;



    public InterviewAddPage(WebDriver driver) {
        super(driver);
    }

    public void fillCompany(String company){

        Select select = new Select(comboBoxCompany);
        select.selectByVisibleText(company);
    }

    public void fillCity(String city){
        Select select = new Select(comboBoxCity);
        select.deselectAll();
        select.selectByVisibleText(city);
    }
    public void fillPosition(String position){
        Select select = new Select(comboBoxPosition);
        select.deselectAll();
        select.selectByVisibleText(position);
    }
    public void fillTechnology(String technology){
        Select select = new Select(comboBoxTechnology);
        select.deselectAll();
        select.selectByVisibleText(technology);
    }

    public void submitAddInterview(){
        submitButton.click();
    }

    public void fillFeedback(String feedback) {
        textBoxFeedback.sendKeys(feedback);
    }
}
