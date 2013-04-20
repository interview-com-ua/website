package ua.com.itinterview.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

public class AuthenticationUtils {

    @Autowired
    private AuthenticationManager authenticationManager;

    public Authentication loginUser(String userName, String password,
	    HttpServletRequest request) {
	Authentication authentication = null;
	authentication = tryToAuthenticate(userName, password);

	SecurityContextHolder.getContext().setAuthentication(authentication);

	HttpSession session = request.getSession();
	session.setAttribute(
		HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
		SecurityContextHolder.getContext());
	return authentication;
    }

    private Authentication tryToAuthenticate(String userName, String password) {
	UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
		userName, password);
	Authentication auth = authenticationManager.authenticate(token);
	return auth;
    }

}
