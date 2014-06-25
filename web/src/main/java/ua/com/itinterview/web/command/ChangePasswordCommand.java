package ua.com.itinterview.web.command;

import org.hibernate.validator.constraints.Length;
import ua.com.itinterview.validation.FieldsEquals;
import ua.com.itinterview.validation.FieldsNotEquals;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: viktor
 * Date: 6/18/14
 * Time: 6:42 PM
 */
@FieldsNotEquals(firstField = "oldPassword", secondField = "newPassword", errorKey = "newPassword", message = "{new.password}")

@FieldsEquals(firstField = "password", secondField = "confirmPassword", errorKey = "confirmPassword", message = "{confirm.password}")
public class ChangePasswordCommand {

    @Length(min = 6, message = "{password.short}")
    @NotNull
    private String oldPassword;
    @Length(min = 6, message = "{password.short}")
    @NotNull
    private String newPassword;
    @Length(min = 6, message = "{password.short}")
    @NotNull
    private String confirmPassword;

    public ChangePasswordCommand() {
    }

    public ChangePasswordCommand(String oldPassword, String newPassword, String confirmPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
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
        if (!(o instanceof ChangePasswordCommand)) {
            return false;
        }

        ChangePasswordCommand that = (ChangePasswordCommand) o;

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
        int result = oldPassword != null ? oldPassword.hashCode() : 0;
        result = 31 * result + (newPassword != null ? newPassword.hashCode() : 0);
        result = 31 * result + (confirmPassword != null ? confirmPassword.hashCode() : 0);
        return result;
    }

    @Override public String toString() {
        return "ChangePasswordCommand{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
