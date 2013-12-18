package ua.com.itinterview.jbehave;

import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import ua.com.itinterview.thucydides.MyQuestionSteps;

public class MyQuestionsScenarioSteps{

    @Steps
    MyQuestionSteps user;

    @Given("the user is on MyQuestions page")
    public void openMyQuestionPage(){
        user.opens_myQuestions_page();
    }

    @When("do nothing")
    public void doNothing(){
        user.do_nothing();
    }

    @Then("they should empty question list message")
    public void checkEmptyQuestionsListMessage(){
        user.sees_empty_questions_list();
    }

    @Then("they should see question list")
    public void checkQuestionListIsNotEmpty(){
         user.sees_not_empty_questions_list();
    }
}
