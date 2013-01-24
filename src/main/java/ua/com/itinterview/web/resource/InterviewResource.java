package ua.com.itinterview.web.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	List<QuestionCommand> questionList = questionService
		.getQuestionListForInterview(interviewId);
	ModelAndView view = new ModelAndView("show_question_list");
	view.addObject("questionList", questionList);
	return view;
    }
}
