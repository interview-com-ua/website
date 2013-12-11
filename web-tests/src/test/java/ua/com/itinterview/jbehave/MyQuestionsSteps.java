package ua.com.itinterview.jbehave;

import net.thucydides.core.annotations.Step;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import ua.com.itinterview.pages.MyQuestionsPage;

import static org.junit.Assert.assertTrue;

public class MyQuestionsSteps {

    MyQuestionsPage questionPage;

    @AfterScenario
    public void closePage(){
        questionPage.getDriver().close();
    }

    @Step
    @Given("the user is on MyQuestions page")
    public void openMyQuestionPage(){
        questionPage.open();
    }

    @Step
    @When("do nothing")
    public void doNothing(){
    }

    @Step
    @Then("they should empty question list message")
    public void checkEmptyQuestionsListMessage(){
        assertTrue(questionPage.isQuestionListEmpty());
    }

    @Step
    @Then("they should see question list")
    public void checkQuestionListIsNotEmpty(){
         assertTrue(!questionPage.isQuestionListEmpty());
    }
}
