package ua.com.itinterview.webtest.conf;


import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SeleniumWrapper
{

    public static final String TRUE = "true";
    public static final String BROWSER = "browser";
    public static final String FIREFOX = "firefox";
    public static final String INTERNET_EXPLORER = "ie";
    public static final String CHROME = "chrome";

    private RemoteWebDriver driver;
    private String host = "localhost";

    public void start() throws IOException
    {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void stop()
    {
        driver.quit();
    }

    public RemoteWebDriver getDriver()
    {
        return driver;
    }

    public String getHost()
    {
        return host;
    }
}