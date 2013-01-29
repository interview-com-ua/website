package ua.com.itinterview.web.resource;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ua.com.itinterview.dao.InterviewEntityDao;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.service.InterviewService;
import ua.com.itinterview.service.QuestionService;
import ua.com.itinterview.web.command.InterviewCommand;
import ua.com.itinterview.web.command.QuestionCommand;

@Controller
@RequestMapping(value = "/interview")
public class InterviewResource {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private InterviewEntityDao interviewEntityDao;

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
	ModelAndView view = new ModelAndView("add_question");
	view.addObject(new QuestionCommand());
	return view;
    }

    @RequestMapping(value = "/{interviewId}/add_question", method = RequestMethod.POST)
    public ModelAndView addQuestionForInterview(
	    @PathVariable Integer interviewId,
	    @ModelAttribute QuestionCommand questionCommand) {
	QuestionCommand questionCmnd = new QuestionCommand();
	System.out.println(questionCommand);
	return new ModelAndView("redirect:/question/" + questionCmnd.getId()
		+ "/view");
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addInterview(@ModelAttribute InterviewCommand interviewCommand) {
	Date date = new Date();
	UserEntity user = new UserEntity();
	user.setEmail("email@com");
	user.setPassword("password");
	user.setUserName("name");
	user = userDao.save(user);
	interviewCommand.setCreated(date);
	interviewCommand.setUser(user);
	interviewService.addInterview(interviewCommand);
	System.out.println(interviewCommand.getFeedback());
	System.out.println(interviewCommand.getCreated());
	System.out.println(interviewCommand.getUser());
	System.out.println(interviewEntityDao.getInterviewsByUser(user).get(0)
		.getId());
	// ModelAndView view = new ModelAndView("add_interview");
	// view.addObject(interviewCommand);

    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addInterview() {
	ModelAndView view = new ModelAndView("add_interview");
	view.addObject(new InterviewCommand());
	return view;
    }

    @RequestMapping(value = "/{interviewId}/edit", method = RequestMethod.GET)
    public ModelAndView getEditInterviewPage(
	    @PathVariable("interviewId") Integer interviewId) {
	InterviewEntity interviewEntity = interviewEntityDao
		.getInterviewById(interviewId);
	UserEntity userEntity = interviewEntity.getUser();
	String userName = userEntity.getUserName();
	InterviewCommand interviewCommand = new InterviewCommand(
		interviewEntity);
	ModelAndView view = new ModelAndView("edit_interview");
	// System.out.println(interviewCommand.getFeedback());
	// System.out.println(interviewCommand.getCreated());
	// System.out.println(interviewCommand.getUser());
	view.addObject(interviewCommand);
	view.addObject("userName", userName);
	return view;

    }

    @RequestMapping(value = "/{interviewId}/edit", method = RequestMethod.POST)
    public void editInterview(
	    @ModelAttribute InterviewCommand interviewCommand,
	    @RequestParam("name") String name) {
	UserEntity user = userDao.getUserByUserName(name);
	interviewCommand.setCreated(new Date());
	interviewCommand.setUser(user);
	interviewService.addInterview(interviewCommand);
	System.out.println(interviewCommand.getFeedback());
	System.out.println(interviewCommand.getCreated());
	System.out.println(interviewCommand.getUser());
    }

}
