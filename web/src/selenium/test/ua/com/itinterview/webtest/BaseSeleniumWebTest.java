package ua.com.itinterview.webtest;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import ua.com.itinterview.webtest.conf.SeleniumWrapper;

import java.util.concurrent.TimeUnit;

@ContextConfiguration(value = {"classpath:selenium-context.xml"})
public class BaseSeleniumWebTest extends AbstractJUnit4SpringContextTests
{

    private static final long MILIS_IN_SECCOND = 1000;

    protected RemoteWebDriver driver;

    private String host = "localhost";

    @Value("${httpPort}")
    private String httpPort;

    @Value("${contextPath}")
    private String contextPath;

    @Before
    public void setUp()
    {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.manage().deleteAllCookies();
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

}
