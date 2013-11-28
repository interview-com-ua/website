package ua.com.itinterview.web.resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.com.itinterview.service.*;
import ua.com.itinterview.web.command.*;
import ua.com.itinterview.web.resource.viewpages.ModeView;
import ua.com.itinterview.web.security.AuthenticationUtils;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/question")
public class QuestionResource {

    private final static Logger LOGGER = Logger
	    .getLogger(InterviewResource.class);

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

    ModeView modeView;

    @RequestMapping(value = "/{questionId}/view", method = RequestMethod.GET)
    public ModelAndView viewQuestion(@PathVariable("questionId") int questionId) {

	QuestionCommand oneQuestionCommand = questionService
		.getQuestionById(questionId);
	List<CommentCommand> commentsForQuestion = commentService
		.getCommentListForQuestion(questionId, 0);

	ModelAndView viewQuestion = new ModelAndView("add_question");

	viewQuestion.addObject("oneQuestionCommand", oneQuestionCommand);
	viewQuestion.addObject("commentsForQuestion", commentsForQuestion);
	modeView = ModeView.VIEW;
	viewQuestion.addObject("mode", modeView);
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
	ModelAndView view = new ModelAndView("add_question");
	view.addObject(new QuestionCommand());
	modeView = ModeView.EDIT;
	view.addObject("mode", modeView);
	return view;
    }

    @RequestMapping(value = "/{questionId}/edit", method = RequestMethod.POST)
    public ModelAndView editQuestion(@PathVariable Integer questionId,
	    @ModelAttribute QuestionCommand questionCommand) {
	LOGGER.info(questionCommand);
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
	view.addObject("questionSearchCommand", new QuestionSearchCommand());

	return view;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView getQuestionSearchResult() {

	return showQuestionSearchPage();
    }

    @RequestMapping(value = "/{questionId}/add_comment", method = RequestMethod.GET)
    public ModelAndView getAddCommentPage(
	    @PathVariable("questionId") Integer questionId) {
	ModelAndView view = new ModelAndView("add_comment");
	CommentCommand commentToAdd = new CommentCommand();
	commentToAdd.setRate(0);
	view.addObject(commentToAdd);
	return view;
    }

    @RequestMapping(value = "/{questionId}/add_comment", method = RequestMethod.POST)
    public ModelAndView addCommentToQuestion(
	    @PathVariable("questionId") Integer questionId,
	    @ModelAttribute @Valid CommentCommand commentCommand,
	    BindingResult bindingResult) {
	System.out.println(commentCommand);
	if (bindingResult.hasErrors()) {
	    return new ModelAndView("add_comment");
	}
	return new ModelAndView("redirect:/question/" + questionId
		+ "/comment_list");
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public ModelAndView getQuestions(){
        String email = new AuthenticationUtils().getUserDetails().getUsername();
        List<QuestionCommand> questionCommandList = questionService.getQuestionListForUser(email);
        ModelAndView model = new ModelAndView("show_question_list");
        model.addObject("questionList", questionCommandList);
        return model;
    }
}
