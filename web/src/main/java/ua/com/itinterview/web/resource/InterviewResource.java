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
import ua.com.itinterview.dao.InterviewDao;
import ua.com.itinterview.dao.paging.PagingFilter;
import ua.com.itinterview.service.InterviewService;
import ua.com.itinterview.service.QuestionService;
import ua.com.itinterview.service.UserService;
import ua.com.itinterview.web.command.InterviewCommand;
import ua.com.itinterview.web.command.PaginateCommand;
import ua.com.itinterview.web.command.QuestionCommand;
import ua.com.itinterview.web.command.UserCommand;
import ua.com.itinterview.web.resource.viewpages.ModeView;
import ua.com.itinterview.web.security.AuthenticationUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/interview")
public class InterviewResource {
    private final static Logger LOGGER = Logger.getLogger(InterviewResource.class);
    private final static Integer RESULTS_ON_PAGE = 10;

    @Autowired
    private QuestionService questionService;
    @Autowired
    private InterviewService interviewService;
    @Autowired
    private UserService userService;
    @Autowired
    private InterviewDao interviewEntityDao;
    @Autowired
    private AuthenticationUtils authenticationUtils;

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
        ModeView modeView = ModeView.CREATE;
        view.addObject("mode", modeView);
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
    public ModelAndView addInterview(
            @Valid @ModelAttribute InterviewCommand interviewCommand,
            BindingResult bindResult) {
        /*if (bindResult.hasErrors()) {
            ModelAndView view = new ModelAndView("add_interview");
            view.addObject(interviewCommand);
            view.addObject("SUCCESS_MESSAGE", "You have errors");
            return view;
        }
        UserEntity user = new UserEntity();
        user.setEmail("email@com");
        user.setPassword("password");
        user = userDao.save(user);
        interviewCommand.setUser(user);
        interviewCommand.setCreated(new Date());
        InterviewEntity interview = interviewService
                .addInterview(interviewCommand);
        Integer id = interview.getId();
        ModelAndView view = new ModelAndView("add_interview");
        view.addObject(interviewCommand);
        view.addObject("SUCCESS_MESSAGE",
                "Interview saved successfully with id=" + id);
        return view;*/
        return new ModelAndView();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddInterview(Map<String, Object> map,
                                  HttpServletRequest request) {
        map.put("interviewCommand", new InterviewCommand());
        map.put("mode", ModeView.CREATE);
        return "interview";
    }

    @RequestMapping(value = "/{interviewId}/edit", method = RequestMethod.GET)
    public String getEditInterview(
            @PathVariable("interviewId") Integer interviewId,
            Map<String, Object> map, HttpServletRequest request) {
        map.put("interviewCommand", createInterviewCommand());
        map.put("mode", ModeView.EDIT);
        return "interview";
    }

    @RequestMapping(value = "/{interviewId}/edit", method = RequestMethod.POST)
    public String saveInterview(
            @PathVariable("interviewId") Integer interviewId,
            @ModelAttribute InterviewCommand interviewCommand,
            Map<String, Object> map, HttpServletRequest request,
            BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            map.put("interviewCommand", interviewCommand);
            map.put("mode", ModeView.EDIT);
            return "interview";
        }
        return "redirect:/interview/" + interviewId + "/view";
    }

    @RequestMapping(value = "/{interviewId}/view", method = RequestMethod.GET)
    public String getViewInterview(@PathVariable int interviewId,
                                   Map<String, Object> map, HttpServletRequest request) {
        map.put("interviewCommand", createInterviewCommand());
        map.put("mode", ModeView.VIEW);
        return "interview";
    }

    private InterviewCommand createInterviewCommand() {
        InterviewCommand interviewCommand = new InterviewCommand();
        interviewCommand.setId(1);
        interviewCommand.setFeedback("some feedback");
        return interviewCommand;
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public ModelAndView showInterviewList(@Valid @ModelAttribute PaginateCommand paginateCommand, BindingResult bindResult) {
        UserCommand userCommand = userService.getUserByEmail(authenticationUtils.getUserDetails().getUsername());
        Long totalResults = interviewService.getInterviewsCountForUser(userCommand.getId());
        PagingFilter pagingFilter = new PagingFilter(0, RESULTS_ON_PAGE, totalResults.intValue());
        if (paginateCommand != null && !bindResult.hasErrors()){
            pagingFilter.setCurrentPage(paginateCommand.getPage());
        }
        List<InterviewCommand> interviewList = interviewService.getUserInterviewList(userCommand.getId(), pagingFilter);
        ModelAndView modelAndView = new ModelAndView("show_personal_interview_list");
        modelAndView.addObject("interviewList", interviewList);
        modelAndView.addObject("pagingFilter", pagingFilter);
        return modelAndView;
    }
}
