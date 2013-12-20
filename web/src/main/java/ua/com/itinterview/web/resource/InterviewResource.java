package ua.com.itinterview.web.resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.itinterview.dao.paging.PagingFilter;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.service.*;
import ua.com.itinterview.web.command.*;
import ua.com.itinterview.web.resource.propertyeditor.CityCommandPropertyEditor;
import ua.com.itinterview.web.resource.propertyeditor.CompanyCommandPropertyEditor;
import ua.com.itinterview.web.resource.propertyeditor.PositionCommandPropertyEditor;
import ua.com.itinterview.web.resource.propertyeditor.TechnologyCommandPropertyEditor;
import ua.com.itinterview.web.resource.viewpages.ModeView;
import ua.com.itinterview.web.security.AuthenticationUtils;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/interview")
public class InterviewResource {
    protected static final String FLASH_MESSAGE_KEY_ERROR = "errorMessage";
    protected static final String FLASH_MESSAGE_KEY_FEEDBACK = "feedbackMessage";
    protected static final String PARAMETER_INTERVIEW_ID = "interviewId";
    protected static final String MODEL_ATTRIBUTE_INTERVIEW = "interviewCommand";
    protected static final String MODEL_ATTRIBUTE_LIST_COMPANY = "listCompany";
    protected static final String MODEL_ATTRIBUTE_LIST_TECHNOLOGY = "listTechnology";
    protected static final String MODEL_ATTRIBUTE_LIST_CITY = "listCity";
    protected static final String MODEL_ATTRIBUTE_LIST_POSITION = "listPosition";

    protected static final String FLASH_MESSAGE_TEXT_INTERVIEW_ADDED = "OK added";
    protected static final String FLASH_MESSAGE_TEXT_INTERVIEW_View = "Interview not found";

    protected static final String FEEDBACK_MESSAGE_TEXT_INTERVIEW_UPDATED = "OK update";

    protected static final String REQUEST_MAPPING_INTERVIEW_LIST    = "/my";
    protected static final String REQUEST_MAPPING_INTERVIEW_VIEW    =  "/{interviewId}/view";
    protected static final String REQUEST_MAPPING_INTERVIEW_UPDATE  = "/{interviewId}/edit";

    protected static final String VIEW_INTERVIEW_ADD = "add_interview";
    protected static final String VIEW_INTERVIEW_LIST = "show_personal_interview_list";
    protected static final String VIEW_INTERVIEW_UPDATE = "update_interview";
    protected static final String VIEW_INTERVIEW_VIEW = "view_interview";
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
        binder.registerCustomEditor(TechnologyCommand.class, "technology", new TechnologyCommandPropertyEditor(technologyService));
        binder.registerCustomEditor(CityCommand.class, "city", new CityCommandPropertyEditor(cityService));
        binder.registerCustomEditor(PositionCommand.class, "position", new PositionCommandPropertyEditor(positionService));
        binder.registerCustomEditor(CompanyCommand.class, "company", new CompanyCommandPropertyEditor(companyService));
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
    public String showFormCreateInterview(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_INTERVIEW, new InterviewCommand());
        model.addAttribute(MODEL_ATTRIBUTE_LIST_CITY, cityService.getCities());
        model.addAttribute(MODEL_ATTRIBUTE_LIST_COMPANY, companyService.getCompanyList());
        model.addAttribute(MODEL_ATTRIBUTE_LIST_POSITION, positionService.getPositionList());
        model.addAttribute(MODEL_ATTRIBUTE_LIST_TECHNOLOGY, technologyService.getTechnologyList());
        return VIEW_INTERVIEW_ADD;

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String createInterview(@Valid @ModelAttribute("interviewCommand") InterviewCommand interviewCommand, BindingResult result, RedirectAttributes attributes, Model model) {
        if (result.hasErrors()) {
            LOGGER.debug("Add interview form was submitted with binding errors. Rendering form view.");
            model.addAttribute(MODEL_ATTRIBUTE_LIST_CITY, cityService.getCities());
            model.addAttribute(MODEL_ATTRIBUTE_LIST_COMPANY, companyService.getCompanyList());
            model.addAttribute(MODEL_ATTRIBUTE_LIST_POSITION, positionService.getPositionList());
            model.addAttribute(MODEL_ATTRIBUTE_LIST_TECHNOLOGY, technologyService.getTechnologyList());
            return VIEW_INTERVIEW_ADD;
        }
        interviewCommand.setUser(userService.getUserByEmail(authenticationUtils.getUserDetails().getUsername()));
        InterviewEntity savedInterviewEntity = interviewService.addInterview(interviewCommand);
        attributes.addFlashAttribute(FLASH_MESSAGE_KEY_FEEDBACK, FLASH_MESSAGE_TEXT_INTERVIEW_ADDED);
        attributes.addAttribute(PARAMETER_INTERVIEW_ID, savedInterviewEntity.getId());
        return createRedirectViewPath(REQUEST_MAPPING_INTERVIEW_VIEW);
    }

    @RequestMapping(value = "/{interviewId}/edit", method = RequestMethod.GET)
    public String showFormUpdateInterview(@PathVariable("interviewId") Integer interviewId, Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_LIST_CITY, cityService.getCities());
        model.addAttribute(MODEL_ATTRIBUTE_LIST_COMPANY, companyService.getCompanyList());
        model.addAttribute(MODEL_ATTRIBUTE_LIST_POSITION, positionService.getPositionList());
        model.addAttribute(MODEL_ATTRIBUTE_LIST_TECHNOLOGY, technologyService.getTechnologyList());
        model.addAttribute(MODEL_ATTRIBUTE_INTERVIEW, interviewService.getInterviewById(interviewId));
        return VIEW_INTERVIEW_UPDATE;
    }

    @RequestMapping(value = "/{interviewId}/edit", method = RequestMethod.POST)
    public String updateInterview(@Valid @ModelAttribute("interviewCommand") InterviewCommand formInterviewCommand,
                                  BindingResult result,
                                  @PathVariable("interviewId") Integer interviewId,
                                  RedirectAttributes attributes,
                                  Model model) {

        if (result.hasErrors()) {
            LOGGER.debug("Update interview form was submitted with binding errors. Rendering form view.");
            model.addAttribute(MODEL_ATTRIBUTE_LIST_CITY, cityService.getCities());
            model.addAttribute(MODEL_ATTRIBUTE_LIST_COMPANY, companyService.getCompanyList());
            model.addAttribute(MODEL_ATTRIBUTE_LIST_POSITION, positionService.getPositionList());
            model.addAttribute(MODEL_ATTRIBUTE_LIST_TECHNOLOGY, technologyService.getTechnologyList());
            return VIEW_INTERVIEW_UPDATE;
        }
        formInterviewCommand.setId(interviewId);
        formInterviewCommand.setUser(userService.getUserByEmail(authenticationUtils.getUserDetails().getUsername()));
        InterviewEntity updatedInterviewEntity = interviewService.update(formInterviewCommand);
        attributes.addAttribute(PARAMETER_INTERVIEW_ID, updatedInterviewEntity.getId());
        attributes.addFlashAttribute(FLASH_MESSAGE_KEY_FEEDBACK, FEEDBACK_MESSAGE_TEXT_INTERVIEW_UPDATED);

        return createRedirectViewPath(REQUEST_MAPPING_INTERVIEW_VIEW);
    }

    @RequestMapping(value = "/{interviewId}/view", method = RequestMethod.GET)
    public String showFormInterviewForm(@PathVariable int interviewId, Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_INTERVIEW, interviewService.getInterviewById(interviewId));
        return VIEW_INTERVIEW_VIEW;
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public ModelAndView showFormInterviewListByUser(@Valid @ModelAttribute PaginateCommand paginateCommand, BindingResult bindResult) {
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

    private String createRedirectViewPath(String requestMapping) {
        StringBuilder redirectViewPath = new StringBuilder();
        redirectViewPath.append("redirect:/interview");
        redirectViewPath.append(requestMapping);
        return redirectViewPath.toString();
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Interview entity not found")  // 409
    @ExceptionHandler(EntityNotFoundException.class)
    public void entityNotFound(Exception exception) {
        //TODO make a page error
    }

}
