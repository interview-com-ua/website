package ua.com.itinterview.webtest.pages;

import org.openqa.selenium.WebDriver;

/**
 * Created by noskills on 7/29/14.
 */
public class UserAddInterviewPage {

    public ProxyWebElement saveInterviewButton;
    public ProxyWebElement selectCity;
    public ProxyWebElement selectPosition;
    public ProxyWebElement selectTechnology;
    public ProxyWebElement selectCompany;
    public ProxyWebElement textFeedback;
    public ProxyWebElement errorTextFeedback;

    public UserAddInterviewPage(WebDriver driver)
    {
        saveInterviewButton = new ProxyWebElement(driver, "btnSaveInterview");
        selectCity= new ProxyWebElement(driver,"city");
        selectCompany = new ProxyWebElement(driver,"company");
        selectTechnology= new ProxyWebElement(driver,"technology");
        selectPosition = new ProxyWebElement(driver,"position");
        textFeedback=new ProxyWebElement(driver,"feedback");
        errorTextFeedback=new ProxyWebElement(driver,"feedback.errors");

    }

}
