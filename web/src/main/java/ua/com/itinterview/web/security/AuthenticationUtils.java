package ua.com.itinterview.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationUtils
{

    @Autowired
    private AuthenticationManager authenticationManager;

    public Authentication loginUser(String email, String password, HttpServletRequest request)
    {
        Authentication authentication = null;
        authentication = tryToAuthenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private Authentication tryToAuthenticate(String email, String password)
    {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                email, password);
        Authentication auth = authenticationManager.authenticate(token);
        return auth;
    }

    public UserDetails getUserDetails()
    {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken))
        {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    public boolean hasRoles(String[] roles)
    {
        for (String role : roles)
        {
            if (!hasRole(role))
            {
                return false;
            }
        }
        return true;
    }

    public boolean hasRole(String role)
    {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication != null)
        {
            for (GrantedAuthority authority : authentication.getAuthorities())
            {
                if (authority.getAuthority().equals(role))
                {
                    return true;
                }
            }
        }
        return false;
    }

}
