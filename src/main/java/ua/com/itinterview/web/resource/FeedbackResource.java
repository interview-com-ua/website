package ua.com.itinterview.web.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/feedback")
public class FeedbackResource {

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView getAddFeedbackPage() {
	ModelAndView view = new ModelAndView("add_feedback");
	return view;
    }
}
