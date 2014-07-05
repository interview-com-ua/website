package ua.com.itinterview.web.command;

import org.hibernate.validator.constraints.Length;
import ua.com.itinterview.validation.CompareOldPassword;
import ua.com.itinterview.validation.FieldsEquals;
import ua.com.itinterview.validation.FieldsNotEquals;

import javax.validation.constraints.NotNull;

/**
 * Created by Bohdan on 02/07/2014.
 */
@FieldsNotEquals(firstField = "oldPassword", secondField = "newPassword", errorKey = "newPassword", message = "{new.password}")
@FieldsEquals(firstField = "newPassword", secondField = "confirmPassword", errorKey = "confirmPassword", message = "{confirm.password}")
@CompareOldPassword(userId = "userId", oldPassword = "oldPassword", errorKey = "oldPassword")
public class UserEditPasswordCommand {

    private int userId;
    @Length(min = 6, message = "{password.short}")
    @NotNull
    private String oldPassword;
    @Length(min = 6, message = "{password.short}")
    @NotNull
    private String newPassword;
    @Length(min = 6, message = "{password.short}")
    @NotNull
    private String confirmPassword;

    public UserEditPasswordCommand() {
    }

    public UserEditPasswordCommand(int userId, ChangePasswordCommand changePasswordCommand) {
        this.userId = userId;
        this.oldPassword = changePasswordCommand.getOldPassword();
        this.newPassword = changePasswordCommand.getNewPassword();
        this.confirmPassword = changePasswordCommand.getConfirmPassword();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserEditPasswordCommand)) {
            return false;
        }

        UserEditPasswordCommand that = (UserEditPasswordCommand) o;

        if (userId != that.userId) {
            return false;
        }
        if (confirmPassword != null ? !confirmPassword.equals(that.confirmPassword) : that.confirmPassword != null) {
            return false;
        }
        if (newPassword != null ? !newPassword.equals(that.newPassword) : that.newPassword != null) {
            return false;
        }
        if (oldPassword != null ? !oldPassword.equals(that.oldPassword) : that.oldPassword != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (oldPassword != null ? oldPassword.hashCode() : 0);
        result = 31 * result + (newPassword != null ? newPassword.hashCode() : 0);
        result = 31 * result + (confirmPassword != null ? confirmPassword.hashCode() : 0);
        return result;
    }

    @Override public String toString() {
        return "UserEditPasswordCommand{" +
                "userId=" + userId +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
