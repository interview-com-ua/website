package ua.com.itinterview.web.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.com.itinterview.web.command.QuestionCommand;

@Controller
@RequestMapping(value = "/interview")
public class InterviewResource {

    @RequestMapping(value = "/{interviewId}/question_list", method = RequestMethod.GET)
    public ModelAndView showQuestionListFoeInterview(
	    @PathVariable Integer interviewId) {
	List<QuestionCommand> questionList = new ArrayList<QuestionCommand>();
	ModelAndView view = new ModelAndView("show_question_list");
	view.addObject(questionList);
	return view;
    }
}
