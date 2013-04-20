package ua.com.itinterview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.command.UserCommand;

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
	userEntity.setSex(userCommand.getSex());
	UserEntity savedEntity = userDao.save(userEntity);
	return new UserCommand(savedEntity);
    }

    public UserCommand getUserById(Integer userId) {
	UserEntity userEntity = userDao.getEntityById(userId);
	return new UserCommand(userEntity);
    }
}
