package ua.com.itinterview.web.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import ua.com.itinterview.entity.UserEntity;

public class UserSecurityDetail extends User {

    private static final long serialVersionUID = 1L;

    private UserEntity info;

    public UserSecurityDetail(UserEntity info, String username,
	    String password, Collection<? extends GrantedAuthority> authorities) {
	super(username, password, authorities);
	this.info = info;
    }

    public UserSecurityDetail(String username, String password,
	    boolean enabled, boolean accountNonExpired,
	    boolean credentialsNonExpired, boolean accountNonLocked,
	    Collection<? extends GrantedAuthority> authorities) {
	super(username, password, enabled, accountNonExpired,
		credentialsNonExpired, accountNonLocked, authorities);
    }

    public UserSecurityDetail(String username, String password,
	    Collection<? extends GrantedAuthority> authorities) {
	super(username, password, authorities);
    }

    public UserEntity getInfo() {
	return info;
    }

    public void setInfo(UserEntity info) {
	this.info = info;
    }
}
