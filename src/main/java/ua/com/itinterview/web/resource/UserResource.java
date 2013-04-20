package ua.com.itinterview.web.resource;

import java.util.HashMap;
import java.util.Map;

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

@Controller
public class UserResource extends ValidatedResource {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView getSignupUserPage() {
	return goToSignupPageWithCommand(new UserCommand(), ModeView.CREATE);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createUser(
	    @Valid @ModelAttribute UserCommand userCommand,
	    BindingResult bindResult) {
	if (bindResult.hasErrors()) {
	    Map<String, String> validationErrors = new HashMap<String, String>();
	    return goToSignupPageWithCommand(userCommand, ModeView.CREATE,
		    validationErrors);
	}
	UserCommand newUserCommand = userService.createUser(userCommand);
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
	return goToSignupPageWithCommand(userCommand, modeView, null);
    }

    private ModelAndView goToSignupPageWithCommand(UserCommand userCommand,
	    ModeView modeView, Map<String, String> validationErrors) {
	ModelAndView view = new ModelAndView("signup");
	view.addObject(userCommand);
	view.addObject("mode", modeView);
	if (validationErrors != null) {
	    view.addObject("validationErrors", validationErrors);
	}
	return view;
    }
}
