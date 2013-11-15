package ua.com.itinterview.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class UserAuthenticationSuccessHandler extends
	SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
	    HttpServletResponse response, Authentication authentication)
	    throws IOException, ServletException {
	UserSecurityDetail userSecurityDetail = (UserSecurityDetail) authentication
		.getPrincipal();
	setDefaultTargetUrl("/user/" + userSecurityDetail.getInfo().getId()
		+ "/view");
	super.onAuthenticationSuccess(request, response, authentication);
    }
}
