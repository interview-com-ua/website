package ua.com.itinterview.web.command;

/**
 * Created by Bohdan on 30/07/2014.
 */
public class ResetPasswordCommand {

    private String hash;
    private String password;
    private String confirmPassword;

    public ResetPasswordCommand() {
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
