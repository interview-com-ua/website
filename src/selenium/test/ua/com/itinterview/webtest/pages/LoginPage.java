package ua.com.itinterview.webtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    public ProxyWebElement loginButton;

    public ProxyWebElement errorMessage;

    public ProxyWebElement phonenumber;

    public ProxyWebElement password;

    public ProxyWebElement lostPassword;

    public ProxyWebElement phoneNumberError;

    public ProxyWebElement passwordError;

    public LoginPage(WebDriver driver) {
	loginButton = new ProxyWebElement(driver, "login-submit");
	errorMessage = new ProxyWebElement(driver, "errorMessage");
	phonenumber = new ProxyWebElement(driver, "loginPhoneNumber");
	password = new ProxyWebElement(driver, "loginPassword");
	lostPassword = new ProxyWebElement(driver,
		By.xpath("//a[contains(@href,'/retrieve-password')]"));
	phoneNumberError = new ProxyWebElement(driver, "loginPhoneNumberError");
	passwordError = new ProxyWebElement(driver, "loginPasswordError");
    }

    boolean visible() {
	boolean isVisible = loginButton.visible() && phonenumber.visible()
		&& password.visible();
	if (!isVisible) {
	    return false;
	}

	return lostPassword.visible();
    }

}
