package ua.com.itinterview.webtest;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.com.itinterview.webtest.pages.UserAddInterviewPage;
import ua.com.itinterview.webtest.pages.UserListInterviewPage;
import ua.com.itinterview.webtest.pages.UserViewInterviewPage;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Created by kuznetsov.danil on 7/29/14.
 */
public class InterviewTest extends BaseSeleniumWebTest {
    private static final String LINK_TO_CITY ="";
    private static final String LINK_TO_POSITION = "";
    private static final String LINK_TO_COMPANY = "";
    private UserAddInterviewPage userAddInterviewPage;
    private UserViewInterviewPage userViewInterviewPage;
    private UserListInterviewPage userListInterviewPage;

    private static final String COMPANY="COMPANY1";
    private static final String CITY="City1";
    private static final String POSITION="Junior Software Engineer";
    private static final String TECHNOLOGY="APL";
    private static final String TEXT_FEEDBACK="Test feedback";
    private static final String NO_TEXT_FEEDBACK="";
    private static final String ERROR_FEEDBACK="may not be empty";

    @Before
    public void setUpInterviewAddPage (){
        userAddInterviewPage = new UserAddInterviewPage(driver);
        userViewInterviewPage = new UserViewInterviewPage(driver);
        userListInterviewPage = new UserListInterviewPage(driver);
        registerRandomUser();
    }

    private void addInterview(String selectPosition, String selectTechnology, String selectCompany, String selectCity, String textFeedback){
        open("/interview/add");
        userAddInterviewPage.selectPosition.selectByText(selectPosition);
        userAddInterviewPage.selectTechnology.selectByText(selectTechnology);
        userAddInterviewPage.selectCompany.selectByText(selectCompany);
        userAddInterviewPage.selectCity.selectByText(selectCity);
        userAddInterviewPage.textFeedback.appendText(textFeedback);
        userAddInterviewPage.saveInterviewButton.click();
    }

    @Test
    public void testLinkOnAddingInterviewFromPageWithListInterview(){
        open("/interview/my");
        userListInterviewPage.btnAddInterview.click();
        assertThat(driver.getCurrentUrl(), containsString("/interview/add"));
    }

    @Test
    public void testAddInterviewByUser(){
      addInterview(POSITION, TECHNOLOGY, COMPANY, CITY, TEXT_FEEDBACK);
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
        addInterview(POSITION, TECHNOLOGY, COMPANY, CITY, NO_TEXT_FEEDBACK);
        Assert.assertTrue(userAddInterviewPage.errorTextFeedback.visible());
        userAddInterviewPage.errorTextFeedback.textIs(ERROR_FEEDBACK);
    }

    @Test
    public void testEmptyListInterviewByUser(){
        open("/interview/my");
        Assert.assertTrue(userListInterviewPage.emptyListLabel.isElementPresent());
        Assert.assertTrue(userListInterviewPage.emptyListLabel.visible());
        Assert.assertFalse(userListInterviewPage.listInterviewByUser.isElementPresent());
    }

    @Test
    public void testNotEmptyListInterviewByUser(){
        addInterview(POSITION, TECHNOLOGY, COMPANY, CITY, TEXT_FEEDBACK);
        open("/interview/my");
        Assert.assertFalse(userListInterviewPage.emptyListLabel.isElementPresent());
        Assert.assertTrue(userListInterviewPage.listInterviewByUser.visible());
    }

    @Test
    public void testLinkOnCompanyForInterviewFromList(){
        addInterview(POSITION, TECHNOLOGY, COMPANY, CITY, TEXT_FEEDBACK);
        open("/interview/my");
        Assert.assertTrue(userListInterviewPage.getLinkCompanyByIndexItem(0).isElementPresent());
        Assert.assertEquals(COMPANY,userListInterviewPage.getLinkCompanyByIndexItem(0).getText());
        Assert.assertEquals(LINK_TO_COMPANY,userListInterviewPage.getLinkCityByIndexItemList(0).getAttribute("href"));
    }

    @Test
    public void testLinkOnCityForInterviewFromList(){
      addInterview(POSITION,TECHNOLOGY,COMPANY,CITY,TEXT_FEEDBACK);
      open("/interview/my");
      Assert.assertTrue(userListInterviewPage.getLinkCityByIndexItemList(0).isElementPresent());
      Assert.assertEquals(CITY,userListInterviewPage.getLinkCityByIndexItemList(0).getText());
      Assert.assertEquals(LINK_TO_CITY,userListInterviewPage.getLinkCityByIndexItemList(0).getAttribute("href"));
    }

    @Test
    public void testLinkOnPositionForInterviewFromList(){
       addInterview(POSITION,TECHNOLOGY,COMPANY,CITY,TEXT_FEEDBACK);
       open("/interview/my");
       Assert.assertTrue(userListInterviewPage.getLinkPositionByIndexItemList(0).isElementPresent());
       Assert.assertEquals(POSITION,userListInterviewPage.getLinkPositionByIndexItemList(0).getText());
       Assert.assertEquals(LINK_TO_POSITION,userListInterviewPage.getLinkPositionByIndexItemList(0).getAttribute("href"));
    }
}
