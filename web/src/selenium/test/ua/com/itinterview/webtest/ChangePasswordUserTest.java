package ua.com.itinterview.webtest;

import org.junit.Test;
import ua.com.itinterview.webtest.pages.UserProfilePage;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: viktor
 * Date: 7/18/14
 * Time: 12:52 PM
 */
public class ChangePasswordUserTest extends BaseSeleniumWebTest
{
    private UserProfilePage userProfilePage = new UserProfilePage(driver);

    @Test
    public void shouldChangePassword() throws Exception
    {
        registerUser("change.password.user1@email.com", "change.password.user.name", "password");
        changePassword("password", "newPassword");
        UserProfilePage profilePage = login("change.password.user@email.com", "newPassword");
        assertThat(profilePage.name.getText(), is("change.password.user.name"));
    }

    private void changePassword(String oldPassword, String newPassword)
    {
        userProfilePage.oldPassword.sendKeys(oldPassword);
        userProfilePage.newPassword.sendKeys(newPassword);
        userProfilePage.confirmPassword.sendKeys(newPassword);
        userProfilePage.changePasswordButton.click();
    }
}
