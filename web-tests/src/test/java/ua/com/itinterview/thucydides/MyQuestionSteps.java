package ua.com.itinterview.thucydides;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;
import ua.com.itinterview.pages.MyQuestionsPage;

import static org.junit.Assert.assertTrue;

public class MyQuestionSteps extends ScenarioSteps{

    MyQuestionsPage myQuestionsPage;

    public MyQuestionSteps(Pages pages) {
        super(pages);
        myQuestionsPage = getPages().get(MyQuestionsPage.class);
    }

    @Step
    public void opens_myQuestions_page(){
        myQuestionsPage.open();
    }

    @Step
    public void do_nothing(){
    }

    @Step
    public void sees_empty_questions_list(){
        assertTrue(myQuestionsPage.isQuestionListEmpty());
    }

    @Step
    public void sees_not_empty_questions_list(){
        assertTrue(!myQuestionsPage.isQuestionListEmpty());
    }

    @Step
    public void closes_myQuestions_page(){
        myQuestionsPage.getDriver().close();
    }
}
