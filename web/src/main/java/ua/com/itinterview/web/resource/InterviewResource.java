package ua.com.itinterview.web.resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.itinterview.dao.paging.PagingFilter;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.service.*;
import ua.com.itinterview.web.command.*;
import ua.com.itinterview.web.resource.viewpages.ModeView;
import ua.com.itinterview.web.security.AuthenticationUtils;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

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
    private AuthenticationUtils authenticationUtils;
    @Autowired
    private CityService cityService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private TechnologyService technologyService;
    @Autowired
    private CompanyService companyService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(TechnologyCommand.class, "technology", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(technologyService.getTechnologyById(Integer.valueOf(text)));
            }
        });
        binder.registerCustomEditor(CityCommand.class, "city", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(cityService.getCityById(Integer.valueOf(text)));
            }
        });
        binder.registerCustomEditor(PositionCommand.class, "position", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(positionService.getPositionById(Integer.valueOf(text)));
            }
        });
        binder.registerCustomEditor(CompanyCommand.class, "company", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(companyService.getCompanyById(Integer.valueOf(text)));
            }
        });
    }
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

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView getAddInterviewPage() {
        return goToInterviewPageWithInterviewCommand(new InterviewCommand(), ModeView.CREATE);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView createInterview(
            @Valid @ModelAttribute("interviewCommand") InterviewCommand interviewCommand,
            BindingResult bindResult) {

        if (bindResult.hasErrors()) {
            return goToInterviewPageWithInterviewCommand(new InterviewCommand(), ModeView.CREATE);
        }
        interviewCommand.setUser(userService.getUserByEmail(authenticationUtils.getUserDetails().getUsername()));
        interviewCommand.setCreated(new Date());
        InterviewEntity save = interviewService.addInterview(interviewCommand);

        ModelAndView resultModelAndView = new ModelAndView("redirect:/interview/" + save.getId() + "/view");
        resultModelAndView.addObject("interviewCommand",new InterviewCommand(save));

        return resultModelAndView;
    }

    @RequestMapping(value = "/{interviewId}/edit", method = RequestMethod.GET)
    public ModelAndView getEditInterviewPage(@PathVariable("interviewId") Integer interviewId, InterviewCommand interviewCommand) {
        ModelAndView resultModelAndView;
        if (interviewCommand==null)
             resultModelAndView = goToInterviewPageWithInterviewCommand(interviewService.getInterviewById(interviewId), ModeView.EDIT);
        else
           resultModelAndView=goToInterviewPageWithInterviewCommand(interviewCommand,ModeView.VIEW);
        return resultModelAndView ;
    }

    @RequestMapping(value = "/{interviewId}/edit", method = RequestMethod.POST)
    public ModelAndView updateInterview(@PathVariable("interviewId") Integer interviewId,
                                        @ModelAttribute InterviewCommand interviewCommand,

                                        BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            goToInterviewPageWithInterviewCommand(interviewService.getInterviewById(interviewId), ModeView.EDIT);
        }
        return new ModelAndView("redirect:/interview/" + interviewId + "/view");
    }

    @RequestMapping(value = "/{interviewId}/view", method = RequestMethod.GET)
    public ModelAndView getViewInterview(@PathVariable int interviewId) {
        return goToInterviewPageWithInterviewCommand(interviewService.getInterviewById(interviewId), ModeView.VIEW);
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public ModelAndView showInterviewList(@Valid @ModelAttribute PaginateCommand paginateCommand, BindingResult bindResult) {
        UserCommand userCommand = userService.getUserByEmail(authenticationUtils.getUserDetails().getUsername());
        Long totalResults = interviewService.getInterviewsCountForUser(userCommand.getId());
        PagingFilter pagingFilter = new PagingFilter(0, RESULTS_ON_PAGE, totalResults.intValue());
        if (paginateCommand != null && !bindResult.hasErrors()) {
            pagingFilter.setCurrentPage(paginateCommand.getPage());
        }
        List<InterviewCommand> interviewList = interviewService.getUserInterviewList(userCommand.getId(), pagingFilter);
        ModelAndView modelAndView = new ModelAndView("show_personal_interview_list");
        modelAndView.addObject("interviewList", interviewList);
        modelAndView.addObject("pagingFilter", pagingFilter);
        return modelAndView;
    }

    private ModelAndView goToInterviewPageWithInterviewCommand(InterviewCommand interviewCommand, ModeView modeView) {
        ModelAndView interview = new ModelAndView("interview");

        if ((modeView==ModeView.EDIT)||(modeView==ModeView.CREATE)){

            interview.addObject("listCompany", companyService.getCompanyList());
            interview.addObject("listTechnology", technologyService.getTechnologyList());
            interview.addObject("listCity", cityService.getCities());
            interview.addObject("listPosition", positionService.getPositionList());
        }
        interview.addObject("interviewCommand", interviewCommand);
        interview.addObject("mode", modeView);
        return interview;

    }
}
