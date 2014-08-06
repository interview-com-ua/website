package ua.com.itinterview.webtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ProxyWebElement extends RemoteWebElement
{

    private final WebDriver driver;

    private By closure;

    private final static String INPUT_ELEMENT_TAG_NAME = "input";

    public ProxyWebElement(WebDriver driver, String id)
    {
        this.driver = driver;
        this.closure = By.id(id);
    }

    public ProxyWebElement(WebDriver driver, By closure)
    {
        this.driver = driver;
        this.closure = closure;
    }

    public String getText()
    {
        WebElement element = driver.findElement(closure);
        if (INPUT_ELEMENT_TAG_NAME.equals(element.getTagName()))
        {
            return element.getAttribute("value");
        }
        else
        {
            return element.getText();
        }
    }
    public String getAttribute(String attr){
        WebElement element = driver.findElement(closure);
        return element.getAttribute(attr);
    }
    public void click()
    {
        driver.findElement(closure).click();
    }

    public void clear()
    {
        driver.findElement(closure).clear();
    }

    public void sendKeys(CharSequence... arg0)
    {
        driver.findElement(closure).clear();
        appendText(arg0);
    }

    public void appendText(CharSequence... arg0)
    {
        driver.findElement(closure).sendKeys(arg0);
    }

    public void textIs(String expected)
    {
        assertThat(getText().trim(), is(expected.trim()));
    }

    public boolean visible()
    {
        boolean result = false;
        try
        {
            result = driver.findElement(closure).isDisplayed();
        }
        catch (WebDriverException e)
        {
        }
        return result;
    }

    public boolean hidden()
    {
        return !visible();
    }

    public boolean enable()
    {
        boolean result = false;
        try
        {
            result = driver.findElement(closure).isEnabled();
        }
        catch (WebDriverException wde)
        {
        }
        return result;
    }

    public boolean disable()
    {
        return !enable();
    }

    public void select(String name)
    {
        driver.findElement(
                By.xpath("//*[@id='"
                        + driver.findElement(closure).getAttribute("id")
                        + "']/*[@value='" + name + "']")).click();
    }

    public void selectByText(String text)
    {
        WebElement select = driver.findElement(closure);// .getAttribute("id");
        List<WebElement> options = select.findElements(By.tagName("option"));
        for (WebElement option : options)
        {
            if (option.getText().equals(text))
            {
                option.click();
                break;
            }
        }

    }

    public void waitingForVisible(int seconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(closure));
        assertTrue(webElement != null);
    }

    public void waitingForInvisible(int seconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(closure)));
    }

    public void waitingForText(String text, int seconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        assertTrue(wait.until(ExpectedConditions.textToBePresentInElement(closure, text)));
    }

    public boolean isElementPresent() {
        boolean result=false;
        try {
            driver.findElement(closure);
            return true;
        }catch (WebDriverException vde){

        }
        return false;
    }
}
