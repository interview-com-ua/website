package ua.com.itinterview.web.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.com.itinterview.service.FeedbackService;
import ua.com.itinterview.web.command.FeedbackCommand;

@Controller
@RequestMapping(value = "/feedback")
public class FeedbackResource {
    
    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView getAddFeedbackPage() {
	ModelAndView view = new ModelAndView("add_feedback");
	return view;
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView showFeedbackList() {
	List<FeedbackCommand> feedbackList = feedbackService.getFeedbackList();
	ModelAndView view = new ModelAndView("show_feedback_list");
	view.addObject("feedbackList", feedbackList);
	return view;
    }
}