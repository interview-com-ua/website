package ua.com.itinterview.webtest;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.com.itinterview.webtest.pages.UserAddInterviewPage;
import ua.com.itinterview.webtest.pages.UserViewInterviewPage;

import javax.validation.constraints.AssertTrue;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Created by noskills on 7/29/14.
 */
public class InterviewAddTest extends BaseSeleniumWebTest {
    private UserAddInterviewPage userAddInterviewPage;
    private UserViewInterviewPage userViewInterviewPage;
    private static final String COMPANY="COMPANY1";
    private static final String CITY="CITY1";
    private static final String POSITION="Junior Software Engineer";
    private static final String TECHNOLOGY="APL";
    private static final String TEXT_FEEDBACK="Test feedback";
    private static final String ERROR_FEEDBACK="may not be empty";

    @Before
    public void setUpInterviewAddPage (){
        userAddInterviewPage = new UserAddInterviewPage(driver);
        userViewInterviewPage = new UserViewInterviewPage(driver);
        registerDefaultUser();
    }

    @Test
    public void testAddInterviewByUser(){
      login(DEFAULT_EMAIL,DEFAULT_PASSWORD);
      open("/interview/add");
      userAddInterviewPage.selectPosition.selectByText(POSITION);
      userAddInterviewPage.selectTechnology.selectByText(TECHNOLOGY);
      userAddInterviewPage.selectCompany.selectByText(COMPANY);
      userAddInterviewPage.selectCity.selectByText(CITY);
      userAddInterviewPage.textFeedback.appendText(TEXT_FEEDBACK);
      userAddInterviewPage.saveInterviewButton.click();

      Assert.assertFalse(userAddInterviewPage.errorTextFeedback.visible());
      assertThat(driver.getCurrentUrl(), containsString("/view"));
      userViewInterviewPage.fieldCity.textIs(CITY);
      userViewInterviewPage.fieldCompany.textIs(COMPANY);
      userViewInterviewPage.fieldPosition.textIs(POSITION);
      userViewInterviewPage.fieldFeedback.textIs(TEXT_FEEDBACK);
      userViewInterviewPage.fieldTechnology.textIs(TECHNOLOGY);
    }

    @Test
    public void testAddInterviewWithNoFeedback(){
        login(DEFAULT_EMAIL,DEFAULT_PASSWORD);
        open("/interview/add");
        userAddInterviewPage.selectPosition.selectByText(POSITION);
        userAddInterviewPage.selectTechnology.selectByText(TECHNOLOGY);
        userAddInterviewPage.selectCompany.selectByText(COMPANY);
        userAddInterviewPage.selectCity.selectByText(CITY);
        userAddInterviewPage.saveInterviewButton.click();

        Assert.assertTrue(userAddInterviewPage.errorTextFeedback.visible());
        userAddInterviewPage.errorTextFeedback.textIs(ERROR_FEEDBACK);

    }

}
