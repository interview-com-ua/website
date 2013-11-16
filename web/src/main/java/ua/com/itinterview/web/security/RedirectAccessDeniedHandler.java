package ua.com.itinterview.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectAccessDeniedHandler extends AccessDeniedHandlerImpl {
    @Autowired
    AuthenticationUtils authenticationUtils;
    @Autowired
    UserDao userDao;

    @Override
    public void handle(HttpServletRequest request,
	    HttpServletResponse response,
	    AccessDeniedException accessDeniedException) throws IOException,
	    ServletException {
	if (authenticationUtils.hasRole("ROLE_USER")) {
	    UserDetails userDetails = authenticationUtils.getUserDetails();
	    if (userDetails != null
		    && redirectToUserProfile(userDetails, request, response)) {
		return;
	    }
	}

	super.handle(request, response, accessDeniedException);
    }

    private boolean redirectToUserProfile(UserDetails userDetails,
	    HttpServletRequest request, HttpServletResponse response)
	    throws IOException {
	UserEntity userEntity = userDao.getUserByEmail(userDetails
            .getUsername());
	if (userEntity != null) {
	    String userProfilePath = "/user/" + userEntity.getId() + "/view";
	    response.sendRedirect(request.getContextPath() + userProfilePath);
	    return true;
	}
	return false;
    }
}
