package ua.com.itinterview.web.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.com.itinterview.service.UserService;
import ua.com.itinterview.web.command.UserCommand;

@Controller
@RequestMapping(value = "/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView getSignupUserPage() {
	return goToSignupPageWithCommand(new UserCommand());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createUser(@ModelAttribute UserCommand userCommand) {
	userService.createUser(userCommand);
	return goToSignupPageWithCommand(userCommand);
    }

    private ModelAndView goToSignupPageWithCommand(UserCommand userCommand) {
	ModelAndView view = new ModelAndView("signup");
	view.addObject(userCommand);
	view.addObject("view", false);
	return view;
    }
}
