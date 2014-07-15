package ua.com.itinterview.webtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FeedbackOnSignupPage
{

    public ProxyWebElement feedbackLink;

    public ProxyWebElement feedbackTextError;
    public ProxyWebElement feedbackText;

    public ProxyWebElement feedbackEmailError;
    public ProxyWebElement feedbackEmail;

    public ProxyWebElement feedbackSubmit;

    public FeedbackOnSignupPage(WebDriver driver)
    {
        feedbackLink = new ProxyWebElement(driver, By.className("feedback_link"));

        feedbackTextError = new ProxyWebElement(driver, "ftext_error");
        feedbackText = new ProxyWebElement(driver, "feedback_text");

        feedbackEmailError = new ProxyWebElement(driver, "femail_error");
        feedbackEmail = new ProxyWebElement(driver, "feedback_email");

        feedbackSubmit = new ProxyWebElement(driver, "feedback_submit");
    }
}
