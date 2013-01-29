package ua.com.itinterview.web.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.com.itinterview.service.QuestionService;
import ua.com.itinterview.web.command.QuestionCommand;

@Controller
@RequestMapping(value = "/interview")
public class InterviewResource {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/{interviewId}/question_list", method = RequestMethod.GET)
    public ModelAndView showQuestionListFoeInterview(
	    @PathVariable Integer interviewId) {
	ModelAndView view = new ModelAndView("add_question");
	view.addObject(new QuestionCommand());
	return view;
    }

    @RequestMapping(value = "/{interviewId}/add_question", method = RequestMethod.GET)
    public ModelAndView getAddQuestionToInterviewPage(
	    @PathVariable Integer interviewId) {
	List<QuestionCommand> addQuestionList = new ArrayList<QuestionCommand>();
	ModelAndView view = new ModelAndView("add_question");
	view.addObject(new QuestionCommand());
	view.addObject(addQuestionList);
	return view;
    }

    @RequestMapping(value = "/{interviewId}/add_question", method = RequestMethod.POST)
    public ModelAndView addQuestionForInterview(
	    @PathVariable Integer interviewId,
	    @ModelAttribute QuestionCommand questionCommand) {
	System.out.println(questionCommand);
	return new ModelAndView("redirect:/{questionId}/comment_list");
    }

}
