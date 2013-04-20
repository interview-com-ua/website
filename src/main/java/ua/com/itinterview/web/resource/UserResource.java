package ua.com.itinterview.web.resource;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.com.itinterview.service.UserService;
import ua.com.itinterview.web.command.UserCommand;
import ua.com.itinterview.web.resource.viewpages.ModeView;
import ua.com.itinterview.web.security.AuthenticationUtils;

@Controller
public class UserResource extends ValidatedResource {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationUtils authenticationUtils;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView getSignupUserPage() {
	return goToSignupPageWithCommand(new UserCommand(), ModeView.CREATE);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createUser(
	    @Valid @ModelAttribute UserCommand userCommand,
	    BindingResult bindResult, HttpServletRequest request) {
	if (bindResult.hasErrors()) {
	    return goToSignupPageWithCommand(userCommand, ModeView.CREATE);
	}
	UserCommand newUserCommand = userService.createUser(userCommand);
	authenticationUtils.loginUser(userCommand.getUserName(),
		userCommand.getPassword(), request);
	return new ModelAndView("redirect:/user/" + newUserCommand.getId()
		+ "/view");

    }

    @PreAuthorize("#userId == principal.info.id")
    @RequestMapping(value = "/user/{id}/view", method = RequestMethod.GET)
    public ModelAndView getViewUser(@PathVariable("id") Integer userId) {
	UserCommand userCommand = userService.getUserById(userId);
	return goToSignupPageWithCommand(userCommand, ModeView.VIEW);
    }

    @PreAuthorize("#userId == principal.info.id")
    @RequestMapping(value = "/user/{id}/edit", method = RequestMethod.GET)
    public ModelAndView getEditUser(@PathVariable("id") Integer userId) {
	UserCommand userCommand = userService.getUserById(userId);
	return goToSignupPageWithCommand(userCommand, ModeView.EDIT);
    }

    @RequestMapping(value = "/user/{id}/save", method = RequestMethod.POST)
    public ModelAndView saveUser(@PathVariable("id") int userId,
	    @ModelAttribute UserCommand userCommand, BindingResult bindResult) {
	if (bindResult.hasErrors()) {
	    return goToSignupPageWithCommand(userCommand, ModeView.EDIT);
	}
	return new ModelAndView("redirect:/user/" + userId + "/view");
    }

    private ModelAndView goToSignupPageWithCommand(UserCommand userCommand,
	    ModeView modeView) {
	ModelAndView view = new ModelAndView("signup");
	view.addObject(userCommand);
	view.addObject("mode", modeView);
	return view;
    }

}
