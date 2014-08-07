package ua.com.itinterview.webtest;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import ua.com.itinterview.webtest.conf.SeleniumWrapper;
import ua.com.itinterview.webtest.pages.LoginPage;
import ua.com.itinterview.webtest.pages.SignupPage;
import ua.com.itinterview.webtest.pages.UserProfilePage;

import java.util.concurrent.TimeUnit;

@ContextConfiguration(value = {"classpath:selenium-context.xml"})
public abstract class BaseSeleniumWebTest extends AbstractJUnit4SpringContextTests
{

    private static final long MILIS_IN_SECCOND = 1000;
    public static final String DEFAULT_EMAIL = "default.user@email.com";
    public static final String DEFAULT_NAME = "Username";
    public static final String DEFAULT_PASSWORD = "password";

    protected RemoteWebDriver driver;

    private String host = "localhost";

    @Value("${httpPort}")
    private String httpPort;

    @Value("${contextPath}")
    private String contextPath;
    private SignupPage signupPage;

    @Before
    public void setUp()
    {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.manage().deleteAllCookies();

        signupPage = new SignupPage(driver);
//        registerDefaultUser();
    }

    protected void registerDefaultUser()
    {
        registerUser(DEFAULT_EMAIL, DEFAULT_NAME, DEFAULT_PASSWORD);
    }

    @After
    public void quitBrowser()
    {
//        driver.quit();
    }

    protected void pause(int secconds)
    {
        try
        {
            Thread.sleep(secconds * MILIS_IN_SECCOND);
        }
        catch (InterruptedException e)
        {
        }
    }

    protected String constructUrl(String relativePath)
    {
        return new StringBuilder(host).append(":").append(httpPort)
                .append(contextPath).append(relativePath).toString();
    }

    //    @Autowired
    public void setSeleniumWrapper(SeleniumWrapper wrapper)
    {
        this.driver = wrapper.getDriver();
        this.host = wrapper.getHost();
    }

    protected void open(String url)
    {
        driver.get(constructUrl(url));
    }

    protected void registerUser(String email, String name, String password)
    {
        registerUser(email, name, password, password);
    }

    protected void registerRandomUser(){
        String nameUser= RandomStringUtils.randomAlphabetic(5);
        String passwordUser=RandomStringUtils.random(6);
        registerUser(nameUser+"@email.com",nameUser,passwordUser);
    }
    protected UserProfilePage login(String email, String password)
    {
        open("/register");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.password.sendKeys(password);
        loginPage.userName.sendKeys(email);
        loginPage.submitButton.click();

        return new UserProfilePage(driver);
    }


    protected void registerUser(String email, String name, String password, String confirmPassword)
    {
        open("/register");
        signupPage.email.sendKeys(email);
        signupPage.name.sendKeys(name);
        signupPage.password.sendKeys(password);
        signupPage.confirmPassword.sendKeys(confirmPassword);
        signupPage.submitButton.click();
    }

    public SignupPage getSignupPage()
    {
        return signupPage;
    }
}
