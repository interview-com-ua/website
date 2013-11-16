package ua.com.itinterview.jbehave;

import net.thucydides.core.annotations.Step;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import ua.com.itinterview.pages.SignUpPage;

import static org.fest.assertions.Assertions.assertThat;

public class SignUpSteps {

    SignUpPage registrationPage;

    @Step
    @Given("the user is on the registration page")
    public void openSignUpPage() {
        registrationPage.open();
    }

    @Step
    @When("the user submits empty form")
    public void submitEmptyForm() {
        registrationPage.submit();
    }

    @Step
    @When("the user submits form with '$login', '$name', '$email', '$password', '$confirmPassword'")
    public void submitsForm(String login, String name, String email, String password, String confirmPassword) {
        registrationPage.fillLogin(login);
        registrationPage.fill_name(name);
        registrationPage.fill_email(email);
        registrationPage.fill_password(password);
        registrationPage.fill_confirm_password(confirmPassword);
        registrationPage.submit();
    }

    @Step
    @Then("they should see the error message '$message' beside login field")
    public void checkErrorMessageBesideLoginField(String message) {
        assertThat(registrationPage.userNameError.isDisplayed());
        assertThat(registrationPage.userNameError.getText()).isEqualTo(message);
    }

    @Step
    @Then("they should see the error message '$message' beside email field")
    public void checkErrorMessageBesideEmailField(String message) {
        assertThat(registrationPage.emailError.isDisplayed());
        assertThat(registrationPage.emailError.getText()).isEqualTo(message);
    }

    @Step
    @Then("they should see the error message '$message' beside password confirmation field")
    public void checkErrorMessageBesidePasswordConfirmationField(String message) {
        assertThat(registrationPage.confirmPasswordError.isDisplayed());
        assertThat(registrationPage.confirmPasswordError.getText()).isEqualTo(message);
    }

    @Step
    @Then("they should see the destination page")
    public void checkDestinationPageOpening() {
        assertThat(registrationPage.getDriver().getCurrentUrl()).doesNotContain("/it-interview/register");
    }
}
