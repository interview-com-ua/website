package ua.com.itinterview.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;

@DefaultUrl("http://localhost:8080/it-interview/cleanDb")
public class DbCleanerPage extends PageObject {

    public DbCleanerPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSuccessful() {
        return getDriver().getPageSource().contains("OK");
    }
}
