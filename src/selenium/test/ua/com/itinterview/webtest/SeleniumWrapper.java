package ua.com.itinterview.webtest;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumWrapper {

    public static final String TRUE = "true";
    public static final String BROWSER = "browser";
    public static final String FIREFOX = "firefox";
    public static final String INTERNET_EXPLORER = "ie";
    public static final String CHROME = "chrome";

    private RemoteWebDriver driver;
    private String host = "localhost";

    @SuppressWarnings("deprecation")
    public void start() throws IOException {
	String browser = System.getProperty(BROWSER, FIREFOX);

	if (FIREFOX.equals(browser)) {
	    driver = new FirefoxDriver();
	} else if (INTERNET_EXPLORER.equals(browser)) {
	    DesiredCapabilities ieCapabilities = DesiredCapabilities
		    .internetExplorer();
	    driver = new InternetExplorerDriver(ieCapabilities);
	} else if (CHROME.equals(browser)) {
	    DesiredCapabilities chromeCapabilities = DesiredCapabilities
		    .chrome();
	    chromeCapabilities.setCapability("chrome.switches",
		    Arrays.asList("--ignore-certificate-errors"));
	    chromeCapabilities.setCapability("chrome.verbose", true);
	    driver = new ChromeDriver(chromeCapabilities);
	    System.setProperty(BROWSER, FIREFOX);
	} else {
	    throw new RuntimeException("You must specify browser property "
		    + browser);
	}
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void stop() {
	driver.quit();
    }

    public RemoteWebDriver getDriver() {
	return driver;
    }

    public String getHost() {
	return host;
    }
}