package ua.com.itinterview.jbehave;

import net.thucydides.core.annotations.Step;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import ua.com.itinterview.pages.SignUpPage;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class SignUpSteps {

    SignUpPage registrationPage;

    @Step
    @Given("the user is on the registration page")
    public void openSignUpPage() {
        registrationPage.open();
    }

    @Step
    @When("the user submits form with '$name', '$email', '$password', '$confirmPassword'")
    public void submitForm(String name, String email, String password, String confirmPassword) {
        registrationPage.fill("name", name);
        registrationPage.fill("email", email);
        registrationPage.fill("password", password);
        registrationPage.fill("confirmPassword", confirmPassword);
        registrationPage.submit();
    }

    @Step
    @Then("they should see the error messages '$nameError', '$emailError', '$passwordError', '$confirmPasswordError'")
    public void checkErrorMessages(String nameError, String emailError, String passwordError, String confirmPasswordError) {
        assertTrue(registrationPage.isErrorDisplayed("name", nameError));
        assertTrue(registrationPage.isErrorDisplayed("email", emailError));
        assertTrue(registrationPage.isErrorDisplayed("password", passwordError));
        assertTrue(registrationPage.isErrorDisplayed("confirmPassword", confirmPasswordError));
    }

    @Step
    @Then("they should see the destination page")
    public void checkDestinationPageOpening() {
        assertThat(registrationPage.getDriver().getCurrentUrl()).doesNotContain("/it-interview/register");
    }
}
