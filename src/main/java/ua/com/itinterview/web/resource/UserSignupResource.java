package ua.com.itinterview.web.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.com.itinterview.web.command.UserCommand;

@Controller
@RequestMapping(value = "/user")
public class UserSignupResource {

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView getUserSignupPage() {
	ModelAndView view = new ModelAndView("signup");
	view.addObject(new UserCommand());
	return view;
    }
}
