package ua.com.itinterview.web.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.com.itinterview.web.resource.QuestionResourceIntegrationTest;
import ua.com.itinterview.web.resource.UserResourceIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserResourceIntegrationTest.class,
        QuestionResourceIntegrationTest.class
})
/**
 * Suite to run all integration tests
 */
public class WebIntegrationTestSuite {

}
