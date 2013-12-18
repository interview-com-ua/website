package ua.com.itinterview.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@DefaultUrl("/question/my")
public class MyQuestionsPage extends PageObject{

    @CacheLookup
    @FindBy(className = "question")
    private List<WebElement> questions;

    public MyQuestionsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isQuestionListEmpty(){
        return questions.isEmpty();
    }
}
