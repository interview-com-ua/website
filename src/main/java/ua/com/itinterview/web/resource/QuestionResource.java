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

import ua.com.itinterview.service.CommentService;
import ua.com.itinterview.service.CompanyService;
import ua.com.itinterview.service.PositionService;
import ua.com.itinterview.service.QuestionService;
import ua.com.itinterview.service.TechnologyService;
import ua.com.itinterview.web.command.CommentCommand;
import ua.com.itinterview.web.command.CompanyCommand;
import ua.com.itinterview.web.command.PositionCommand;
import ua.com.itinterview.web.command.QuestionCommand;
import ua.com.itinterview.web.command.TechnologyCommand;

@Controller
@RequestMapping(value = "/question")
public class QuestionResource {

    @Autowired
    private CommentService commentService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private TechnologyService technologyService;

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
		.getCommentListForQuestion(questionId, 0);
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
	System.out.println(questionCommand);
	return new ModelAndView("/index");
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView showQuestionSearchPage() {

	List<CompanyCommand> companies = companyService.getCompanyList();
	List<PositionCommand> positions = positionService.getPositionList();
	List<TechnologyCommand> technologies = technologyService
		.getTechnologyList();
	List<QuestionCommand> questions = questionService
		.getRecentQuestionList();

	ModelAndView view = new ModelAndView("search_questions");
	view.addObject("companies", companies);
	view.addObject("positions", positions);
	view.addObject("technologies", technologies);
	view.addObject("questions", questions);

	return view;
    }

}
