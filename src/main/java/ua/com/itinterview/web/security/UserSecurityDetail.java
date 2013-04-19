package ua.com.itinterview.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserSecurityDetail extends User {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    public UserSecurityDetail(int id, String username, String password,
	    Collection<? extends GrantedAuthority> authorities) {
	super(username, password, authorities);
	this.id = id;
    }
    
    public UserSecurityDetail(String username, String password,
	    boolean enabled, boolean accountNonExpired,
	    boolean credentialsNonExpired, boolean accountNonLocked,
	    Collection<? extends GrantedAuthority> authorities) {
	super(username, password, enabled, accountNonExpired, credentialsNonExpired,
		accountNonLocked, authorities);
    }

    public UserSecurityDetail(String username, String password,
	    Collection<? extends GrantedAuthority> authorities) {
	super(username, password, authorities);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
