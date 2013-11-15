package ua.com.itinterview.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public ModelAndView showIndexPage() {
	ModelAndView view = new ModelAndView("index");
	return view;
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin() {
	return "login";
    }
}
