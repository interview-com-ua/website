package ua.com.itinterview.webtest;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import ua.com.itinterview.webtest.pages.LoginPage;

@ContextConfiguration(value = { "classpath:selenium-context.xml" })
public class BaseSeleniumWebTest extends AbstractJUnit4SpringContextTests {

    private RemoteWebDriver driver;

    private LoginPage loginPage;
    private String host;

    @Before
    public void setUp() {
	System.getProperties().setProperty("webdriver.chrome.driver",
		"D:/dev/Selenium/chromedriver/chromedriver.exe");
	driver = new ChromeDriver();
	driver.manage().deleteAllCookies();
	loginPage = new LoginPage(driver);
    }

    @After
    public void quitBrowser() {
	driver.quit();
    }

    @Test
    public void fakeTest() throws InterruptedException {
	driver.get("http://google.com");
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	Thread.sleep(5000);
	fail("Default behaviour for mock test");
    }

    // @Autowired
    public void setSeleniumWrapper(SeleniumWrapper wrapper) {
	this.driver = wrapper.getDriver();
	this.host = wrapper.getHost();
    }

}
