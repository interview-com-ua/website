package ua.com.itinterview.thucydides;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;
import ua.com.itinterview.pages.InterviewAddPage;
import ua.com.itinterview.pages.InterviewViewPage;
import ua.com.itinterview.pages.LoginPage;

import static org.junit.Assert.assertTrue;

/**
 * Created by kdv on 2/16/14.
 */
public class InterviewAddSteps extends ScenarioSteps {

    private final LoginPage loginPage;
    private final InterviewAddPage addInterviewPage;
    private final InterviewViewPage viewInterviewPage;


    public InterviewAddSteps(Pages pages) {
        super(pages);
        loginPage = getPages().get(LoginPage.class);
        addInterviewPage = getPages().get(InterviewAddPage.class);
        viewInterviewPage = getPages().get(InterviewViewPage.class);
    }

    @Step
    public void loginUser(String user, String password) {
        loginPage.loginAs(user, password);
    }
    @Step
    public void openAddInterviewPage() {
        addInterviewPage.open();
    }
    @Step
    public void choosesCity(String city){
        addInterviewPage.fillCity(city);
    }

    @Step
    public void choosesPosition(String position){
        addInterviewPage.fillCity(position);
    }
    @Step
    public void choosesTechnology(String technology){
        addInterviewPage.fillCity(technology);
    }
    @Step
    public void choosesCompany(String company){
        addInterviewPage.fillCity(company);
    }

    @Step
    public void submitAddInterview(){
        addInterviewPage.submitAddInterview();
    }
    @Step
    public void seesAddedInterview() {
       //TO-DO assert
    }
    @Step
    public void fillFeedback(String feedback) {
        addInterviewPage.fillFeedback(feedback);
    }
}

