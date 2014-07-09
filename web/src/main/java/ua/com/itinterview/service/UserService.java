package ua.com.itinterview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.command.ChangePasswordCommand;
import ua.com.itinterview.web.command.UserCommand;
import ua.com.itinterview.web.command.UserEditProfileCommand;

import javax.validation.ValidationException;

public class UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserCommand createUser(UserCommand userCommand) {
	UserEntity user = new UserEntity(userCommand);
	user.setPassword(passwordEncoder.encodePassword(user.getPassword(), ""));
	return new UserCommand(userDao.save(user));
    }

    public UserCommand updateUser(Integer userId, UserCommand userCommand) {
	UserEntity userEntity = userDao.getEntityById(userId);
	userEntity.setEmail(userCommand.getEmail());
	userEntity.setName(userCommand.getName());
	UserEntity savedEntity = userDao.save(userEntity);
	return new UserCommand(savedEntity);
    }

    public UserCommand getUserById(Integer userId) {
	UserEntity userEntity = userDao.getEntityById(userId);
	return new UserCommand(userEntity);
    }

    public UserCommand getUserByEmail(String email) {
        UserEntity userEntity = userDao.getUserByEmail(email);
        return new UserCommand(userEntity);
    }

    public UserEditProfileCommand updateUser(int userId,
	    UserEditProfileCommand changedUserProfile) {
	UserEntity userEntityToUpdate = userDao.getEntityById(userId);
	userEntityToUpdate.setName(changedUserProfile.getName());
	userEntityToUpdate.setEmail(changedUserProfile.getEmail());
	UserEntity changedUser = userDao.save(userEntityToUpdate);
	return new UserEditProfileCommand(changedUser);
    }

    public void deleteAll() {
        userDao.deleteAll();
    }

    public UserCommand updatePassword(int userId, ChangePasswordCommand changePasswordCommand)
    {
        UserEntity userEntity = userDao.getEntityById(userId);
        userEntity.setPassword(passwordEncoder.encodePassword(changePasswordCommand.getNewPassword(), ""));
        return new UserCommand(userDao.save(userEntity));
    }
}
