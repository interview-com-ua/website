package ua.com.itinterview.web.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.com.itinterview.service.CommentService;
import ua.com.itinterview.service.QuestionService;
import ua.com.itinterview.web.command.CommentCommand;
import ua.com.itinterview.web.command.QuestionCommand;

@Controller
@RequestMapping(value = "/question")
public class QuestionResource {

    @Autowired
    private CommentService commentService;
    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/{questionId}/view", method = RequestMethod.GET)
    public ModelAndView viewQuestion(@PathVariable("questionId") int questionId) {

	// QuestionCommand oneQuestionCommand = questionService
	// .getQuestionById(questionId);
	QuestionCommand oneQuestionCommand = new QuestionCommand();
	oneQuestionCommand.setQuestion("Вопрос номер один");

	ModelAndView viewQuestion = new ModelAndView("view_question");
	viewQuestion.addObject("oneQuestionCommand", oneQuestionCommand);
	return viewQuestion;
    }

    @RequestMapping(value = "/{questionId}/comment_list", method = RequestMethod.GET)
    public ModelAndView getShowCommentList(
	    @PathVariable("questionId") int questionId) {
	List<CommentCommand> commentsToPrint = commentService
		.getCommentListForQuestion(questionId);
	ModelAndView view = new ModelAndView("show_comment_list");
	view.addObject("commentsToPrint", commentsToPrint);
	return view;
    }
}
