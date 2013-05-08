package ua.com.itinterview.web.command;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.validation.UniqueEmail;

public class UserEditProfileCommand {

    private int id;
    @NotEmpty(message = "{empty.name}")
    @Length(max = 255)
    private String name;
    @Email
    @NotEmpty(message = "{empty.email}")
    @Length(max = 255)
    @UniqueEmail
    private String email;
    private UserEntity.Sex sex = UserEntity.Sex.MALE;

    public UserEditProfileCommand() {
    }

    public UserEditProfileCommand(UserEntity userEntity) {
	this.id = userEntity.getId();
	this.name = userEntity.getName();
	this.email = userEntity.getEmail();
	this.sex = userEntity.getSex();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public UserEntity.Sex getSex() {
	return sex;
    }

    public void setSex(UserEntity.Sex sex) {
	this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;

	UserEditProfileCommand that = (UserEditProfileCommand) o;

	if (id != that.id)
	    return false;
	if (email != null ? !email.equals(that.email) : that.email != null)
	    return false;
	if (name != null ? !name.equals(that.name) : that.name != null)
	    return false;
	if (sex != that.sex)
	    return false;

	return true;
    }

    @Override
    public int hashCode() {
	int result = id;
	result = 31 * result + (name != null ? name.hashCode() : 0);
	result = 31 * result + (email != null ? email.hashCode() : 0);
	result = 31 * result + (sex != null ? sex.hashCode() : 0);
	return result;
    }
}
