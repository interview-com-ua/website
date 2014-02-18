package ua.com.itinterview.jbehave;

import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import ua.com.itinterview.thucydides.InterviewAddSteps;

/**
 * Created by kdv on 2/16/14.
 */
public class InterviewAddScenarioSteps {

    @Steps
    InterviewAddSteps user;

    @Given("the user login site with a '$username' and '$password' and go to the page add the interview")
    public void openAddInterviewPage(String username, String password) {
        user.loginUser(username, password);
        user.openAddInterviewPage();
    }

    @When("the user submits form with '$city', '$company', '$position', '$technology','$feedback'")
    public void fillInterviewFields(String city, String company, String position, String technology, String feedback) {
        user.choosesCity(city);
        user.choosesCompany(company);
        user.choosesPosition(position);
        user.choosesTechnology(technology);
        user.fillFeedback(feedback);
        user.submitAddInterview();
    }

    @Then("they should view field interview added")
    public void checkEmptyQuestionsListMessage() {
        user.seesAddedInterview();
    }


}