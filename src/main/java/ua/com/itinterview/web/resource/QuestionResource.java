package ua.com.itinterview.web.resource;

import java.util.ArrayList;
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
    public ModelAndView showCommentList(
	    @PathVariable("questionId") int questionId) {

	List<CommentCommand> commentsToPrint = commentService
		.getCommentListForQuestion(questionId);

	commentsToPrint = new ArrayList<CommentCommand>();

	CommentCommand comment1 = new CommentCommand();
	comment1.setAuthorName("name1");
	comment1.setCommentText("some text 1");
	comment1.setRate(10);
	comment1.setUserpicUrl("http://media.habrahabr.ru/images/thumbs/avatars/7f/f9/92/45346/small_45346.jpg");
	commentsToPrint.add(comment1);

	CommentCommand comment2 = new CommentCommand();
	comment2.setAuthorName("name2");
	comment2.setCommentText("some text 2");
	comment2.setRate(1);
	comment2.setUserpicUrl("http://media.habrahabr.ru/images/thumbs/avatars/18/86/66/24076/small_24076.jpg");
	commentsToPrint.add(comment2);

	ModelAndView view = new ModelAndView("show_comment_list");
	view.addObject("commentsToPrint", commentsToPrint);

	return view;
    }
}
