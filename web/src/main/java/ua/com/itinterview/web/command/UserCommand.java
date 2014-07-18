package ua.com.itinterview.web.command;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.validation.FieldsEquals;
import ua.com.itinterview.validation.UniqueEmail;

import javax.validation.constraints.NotNull;

@FieldsEquals(firstField = "password", secondField = "confirmPassword", errorKey = "confirmPassword", message = "{confirm.password}")
public class UserCommand {

    private int id;
    @Email(message = "{invalid.email}")
    @NotEmpty(message = "{empty.email}")
    @Length(max = 255)
    @UniqueEmail
    private String email;
    @NotEmpty(message = "{empty.name}")
    @Length(max = 255)
    private String name;
    @Length(min = 6, message = "{password.short}")
    @NotNull
    private String password;
    @Length(min = 6, message = "{password.short}")
    @NotNull
    private String confirmPassword;

    public UserCommand() {
    }

    public UserCommand(UserEntity userEntity) {
	id = userEntity.getId();
	email = userEntity.getEmail();
	name = userEntity.getName();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((confirmPassword == null) ? 0 : confirmPassword.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + id;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result
		+ ((password == null) ? 0 : password.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	UserCommand other = (UserCommand) obj;
	if (confirmPassword == null) {
	    if (other.confirmPassword != null)
		return false;
	} else if (!confirmPassword.equals(other.confirmPassword))
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (id != other.id)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (password == null) {
	    if (other.password != null)
		return false;
	} else if (!password.equals(other.password))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "UserCommand [id=" + id + ", email=" + email + ", name=" + name + ", password=" + password + ", confirmPassword=" + confirmPassword + "]";
    }

}
