package ua.com.itinterview.web.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectAccessDeniedHandler extends AccessDeniedHandlerImpl {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (request.getServletPath().equals("/register") && authentication != null && authentication.isAuthenticated()) {
            if (hasUserRole(authentication)) {
                try {
                    UserSecurityDetail userSecurityDetail = (UserSecurityDetail) authentication.getPrincipal();
                    String userProfilePath = "/user/" + userSecurityDetail.getInfo().getId() + "/view";
                    response.sendRedirect(request.getContextPath() + userProfilePath);
                    return;
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        }

        super.handle(request, response, accessDeniedException);
    }

    public boolean hasUserRole(Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_USER")) {
                return true;
            }
        }
        return false;
    }
}
