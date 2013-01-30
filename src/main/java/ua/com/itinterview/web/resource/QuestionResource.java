package ua.com.itinterview.web.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    private final static Logger LOGGER = Logger
	    .getLogger(InterviewResource.class);

    @Autowired
    private CommentService commentService;
    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/{questionId}/view", method = RequestMethod.GET)
    public ModelAndView viewQuestion(@PathVariable("questionId") int questionId) {

	// QuestionCommand oneQuestionCommand = questionService
	// .getQuestionById(questionId);
	// List<CommentCommand> commentsForQuestion = commentService
	// .getCommentListForQuestion(questionId);

	List<CommentCommand> commentsForQuestion = new ArrayList<CommentCommand>();
	CommentCommand commandTest1 = new CommentCommand();
	commandTest1.setAuthorName("Вася");
	commandTest1.setCommentText("Некоторорый коментарий");

	CommentCommand commandTest2 = new CommentCommand();
	commandTest2.setAuthorName("Никодим");
	commandTest2.setCommentText("Плохой коментарий");

	commentsForQuestion.add(commandTest1);
	commentsForQuestion.add(commandTest2);
	commentsForQuestion.add(commandTest1);
	commentsForQuestion.add(commandTest2);
	commentsForQuestion.add(commandTest1);
	commentsForQuestion.add(commandTest2);

	QuestionCommand oneQuestionCommand = new QuestionCommand();
	oneQuestionCommand.setQuestion("Вопрос номер один");

	ModelAndView viewQuestion = new ModelAndView("view_question");

	viewQuestion.addObject("oneQuestionCommand", oneQuestionCommand);
	viewQuestion.addObject("commentsForQuestion", commentsForQuestion);
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

    @RequestMapping(value = "/{questionId}/edit", method = RequestMethod.GET)
    public ModelAndView getEditQuestionPage(@PathVariable Integer questionId) {
	ModelAndView view = new ModelAndView("edit_question");
	view.addObject(new QuestionCommand());

	return view;
    }

    @RequestMapping(value = "/{questionId}/edit", method = RequestMethod.POST)
    public ModelAndView editQuestion(@PathVariable Integer questionId,
	    @ModelAttribute QuestionCommand questionCommand) {
	LOGGER.info(questionCommand);
	return new ModelAndView("/index");
    }
}
