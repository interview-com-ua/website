package ua.com.itinterview.webtest.pages;

import org.openqa.selenium.WebDriver;

/**
 * Created by noskills on 7/29/14.
 */
public class UserViewInterviewPage {
    public ProxyWebElement backInterviewListButton;
    public ProxyWebElement fieldCity;
    public ProxyWebElement fieldPosition;
    public ProxyWebElement fieldTechnology;
    public ProxyWebElement fieldCompany;
    public ProxyWebElement fieldFeedback;

    public UserViewInterviewPage(WebDriver driver)
    {
        backInterviewListButton = new ProxyWebElement(driver, "back_interview_list");
        fieldCity = new ProxyWebElement(driver,"label_city");
        fieldCompany = new ProxyWebElement(driver,"label_company");
        fieldTechnology = new ProxyWebElement(driver,"label_technology");
        fieldPosition = new ProxyWebElement(driver,"label_position");
        fieldFeedback =new ProxyWebElement(driver,"label_feedback");
    }
}
