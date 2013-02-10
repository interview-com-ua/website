package ua.com.itinterview.web.resource;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.com.itinterview.service.UserService;
import ua.com.itinterview.web.command.UserCommand;
import ua.com.itinterview.web.resource.viewpages.ModeView;

@Controller
@RequestMapping(value = "/user")
public class UserResource extends ValidatedResource {

    ModeView modeView;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView getSignupUserPage() {
	return goToSignupPageWithCommand(new UserCommand(), ModeView.CREATE);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createUser(
	    @Valid @ModelAttribute UserCommand userCommand,
	    BindingResult bindResult, HttpServletRequest request) {
	if (bindResult.hasErrors()) {
	    System.out.println("Errors have been detected");
	    Map<String, String> validationErrors = new HashMap<String, String>();
	    validationErrors.put("name", "Manual error");

	    for (ObjectError error : bindResult.getAllErrors()) {
		System.out.println(error.getCode() + " "
			+ error.getDefaultMessage() + " "
			+ error.getObjectName());
	    }
	    return goToSignupPageWithCommand(userCommand, ModeView.CREATE,
		    validationErrors);
	}
	UserCommand newUserCommand = userService.createUser(userCommand);
	return new ModelAndView("redirect:/user/" + newUserCommand.getId()
		+ "/view");

    }

    @RequestMapping(value = "/{id}/view", method = RequestMethod.GET)
    public ModelAndView getViewUser(@PathVariable("id") int userId) {
	UserCommand userCommand = userService.getUserById(userId);
	return goToSignupPageWithCommand(userCommand, ModeView.VIEW);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView getEditUser(@PathVariable("id") int userId) {
	UserCommand userCommand = userService.getUserById(userId);
	return goToSignupPageWithCommand(userCommand, ModeView.EDIT);
    }

    @RequestMapping(value = "/{id}/save", method = RequestMethod.POST)
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
