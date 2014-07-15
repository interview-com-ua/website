package ua.com.itinterview.webtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.com.itinterview.webtest.pages.FeedbackOnSignupPage;

public class FeedbackTest extends BaseSeleniumWebTest
{

    public static final int WAITING_SEC = 10;
    public static final String ERROR_EMPTY_EMAIL = "may not be empty";
    public static final String ERROR_INCORRECT_EMAIL = "not a well-formed email address";
    public static final String ERROR_EMPTY_TEXT = ERROR_EMPTY_EMAIL;

    private FeedbackOnSignupPage feedbackOnSignupPage;

    @Before
    public void setUpFeedbackPage()
    {
        feedbackOnSignupPage = new FeedbackOnSignupPage(driver);
        driver.get(constructUrl("/register"));
        feedbackOnSignupPage.feedbackLink.waitingForVisible(WAITING_SEC);
    }

    @Test
    public void checkWindowFeedbackShowHideWhenClickWithHiddenError()
    {
        showWindowFeedback();

        Assert.assertTrue(feedbackOnSignupPage.feedbackEmailError.hidden());
        Assert.assertTrue(feedbackOnSignupPage.feedbackTextError.hidden());

        hideWindowFeedback();
    }

    @Test
    public void errorWhenDoNotFillEmailAndFeedbackText()
    {
        showWindowFeedback();

        feedbackOnSignupPage.feedbackEmail.clear();
        feedbackOnSignupPage.feedbackText.clear();
        feedbackOnSignupPage.feedbackSubmit.click();

        feedbackOnSignupPage.feedbackEmailError.waitingForVisible(WAITING_SEC);
        feedbackOnSignupPage.feedbackTextError.waitingForVisible(WAITING_SEC);

        feedbackOnSignupPage.feedbackEmailError.waitingForText(ERROR_EMPTY_EMAIL, WAITING_SEC);
        feedbackOnSignupPage.feedbackTextError.waitingForText(ERROR_EMPTY_TEXT, WAITING_SEC);
    }

    @Test
    public void errorWhenEnterIncorrectEmail()
    {
        //Given
        showWindowFeedback();

        feedbackOnSignupPage.feedbackEmail.sendKeys("incorrect email");
        feedbackOnSignupPage.feedbackText.sendKeys("text");
        //When
        feedbackOnSignupPage.feedbackSubmit.click();

        feedbackOnSignupPage.feedbackEmailError.waitingForVisible(WAITING_SEC);
        feedbackOnSignupPage.feedbackTextError.waitingForInvisible(WAITING_SEC);

        //Then
        feedbackOnSignupPage.feedbackEmailError.waitingForText(ERROR_INCORRECT_EMAIL, WAITING_SEC);
    }

    private void showWindowFeedback()
    {
        feedbackOnSignupPage.feedbackLink.click();
        feedbackOnSignupPage.feedbackEmail.waitingForVisible(WAITING_SEC);
        feedbackOnSignupPage.feedbackText.waitingForVisible(WAITING_SEC);
    }

    private void hideWindowFeedback()
    {
        feedbackOnSignupPage.feedbackLink.click();
        feedbackOnSignupPage.feedbackEmail.waitingForInvisible(WAITING_SEC);
        feedbackOnSignupPage.feedbackText.waitingForInvisible(WAITING_SEC);
    }
}
