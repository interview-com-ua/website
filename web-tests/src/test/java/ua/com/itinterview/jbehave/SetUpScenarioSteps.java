package ua.com.itinterview.jbehave;

import org.jbehave.core.annotations.*;
import ua.com.itinterview.pages.DbCleanerPage;

import static org.junit.Assert.assertTrue;

public class SetUpScenarioSteps {

    DbCleanerPage dbCleanerPage;

    @BeforeStory
    public void prepareDb() {
        dbCleanerPage.open();
        boolean successful = dbCleanerPage.isSuccessful();
        dbCleanerPage.close();
        assertTrue(successful);
    }

    @AfterScenario
    public void closePage() {
        dbCleanerPage.getDriver().quit();
    }

    @Given("given")
    public void dummyGiven(){}

    @When("when")
    public void dummyWhen(){}

    @Then("then")
    public void dummyThen(){}
}
