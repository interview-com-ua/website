package ua.com.itinterview.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.security.UserSecurityDetail;

public class UserSecurityService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userName)
	    throws UsernameNotFoundException {

	UserEntity user = userDao.getUserByUserName(userName);
	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

	UserSecurityDetail userSecurityDetail = new UserSecurityDetail(user.getId(), user.getUserName(),
		user.getPassword(), authorities);
	
	System.out.println("Id = " + userSecurityDetail.getId());
	return userSecurityDetail;
    }
}
