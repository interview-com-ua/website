package ua.com.itinterview.web.resource;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.com.itinterview.web.command.CommentCommand;

@Controller
@RequestMapping(value = "/feedback")
public class FeedbackResource {

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView getAddFeedbackPage() {
	ModelAndView view = new ModelAndView("add_feedback");
	return view;
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView showFeedbackList() {
	ModelAndView view = new ModelAndView("show_feedback_list");
	return view;
    }
}