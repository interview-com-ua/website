package ua.com.itinterview.web.resource;

import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.ServletRequestDataBinder;

import org.springframework.web.bind.WebDataBinder;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.service.*;
import ua.com.itinterview.web.command.*;
import ua.com.itinterview.web.integration.BaseWebIntegrationTest;
import ua.com.itinterview.web.resource.propertyeditor.CityCommandPropertyEditor;
import ua.com.itinterview.web.resource.propertyeditor.CompanyCommandPropertyEditor;
import ua.com.itinterview.web.resource.propertyeditor.PositionCommandPropertyEditor;
import ua.com.itinterview.web.resource.propertyeditor.TechnologyCommandPropertyEditor;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * User: kuznetsov d.v.
 * Date: 14.11.13
 */
public class InterviewResourceIntegrationTest extends BaseWebIntegrationTest {

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private CityService cityService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private TechnologyService technologyService;

    @Test
    public void testShowListInterviewByUser_UserIsRegistration_ShouldAddListInterviewAndPagingFilterToModelAndRenderViewInterviewList() throws Exception {
        createUser();
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        mvc.perform(get("/interview/my").session(session))
                .andExpect(view().name("show_personal_interview_list"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("interviewList"))
                .andExpect(model().attributeExists("pagingFilter"))
                .andDo(print());
    }


    @Test
    public void testShowListInterviewByUser_UserNotRegistration_ShouldRedirectToRegistrationPage() throws Exception {
        mvc.perform(get("/interview/my"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/register"))
                .andDo(print());

    }

    @Test
    public void testShowCreateInterviewForm_UserNotRegistration_ShouldRedirectToRegistrationPage() throws Exception {
        mvc.perform(post("/interview/add"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/register"))
                .andDo(print());
    }

    @Test
    public void testShowCreateInterviewForm_UserIsRegistration_ShouldAddInterviewCommandAndListValuesPropertyInterviewCommandToModelAndViewAddInterviewView() throws Exception {
        createUser();
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        mvc.perform(get("/interview/add").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("add_interview"))
                .andExpect(forwardedUrl("/WEB-INF/views/add_interview.jsp"))
                .andExpect(model().attributeExists("listCompany"))
                .andExpect(model().attributeExists("listTechnology"))
                .andExpect(model().attributeExists("listCity"))
                .andExpect(model().attributeExists("listPosition"))
                .andExpect(model().attribute("interviewCommand", hasProperty("id", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("user", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("company", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("technology", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("position", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("city", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("feedback", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("created", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("questionCount", equalTo(0))))
                .andDo(print());
    }

    @Test
    public void testBindingFormFieldsToNestedObjectsForInterviewCommand() throws Exception {

        InterviewCommand expectedInterviewCommand = createInterviewCommand(createUser(), new Date());
        InterviewCommand actualInterviewCommand = new InterviewCommand();

        WebDataBinder binder = new ServletRequestDataBinder(actualInterviewCommand, "interviewCommand");

        binder.registerCustomEditor(TechnologyCommand.class, "technology", new TechnologyCommandPropertyEditor(technologyService));
        binder.registerCustomEditor(CityCommand.class, "city", new CityCommandPropertyEditor(cityService));
        binder.registerCustomEditor(PositionCommand.class, "position", new PositionCommandPropertyEditor(positionService));
        binder.registerCustomEditor(CompanyCommand.class, "company", new CompanyCommandPropertyEditor(companyService));

        MutablePropertyValues postRequest = new MutablePropertyValues();
        postRequest.addPropertyValue("city", String.valueOf(expectedInterviewCommand.getCity().getId()));
        postRequest.addPropertyValue("company", String.valueOf(expectedInterviewCommand.getCompany().getId()));
        postRequest.addPropertyValue("technology", String.valueOf(expectedInterviewCommand.getTechnology().getId()));
        postRequest.addPropertyValue("position", String.valueOf(expectedInterviewCommand.getPosition().getId()));
        postRequest.addPropertyValue("feedback", USER_FEEDBACK);

        binder.bind(postRequest);

        assertEquals(0, binder.getBindingResult().getErrorCount());
        assertEquals(expectedInterviewCommand.getCity(), actualInterviewCommand.getCity());
        assertEquals(expectedInterviewCommand.getCompany(), actualInterviewCommand.getCompany());
        assertEquals(expectedInterviewCommand.getPosition(), actualInterviewCommand.getPosition());
        assertEquals(expectedInterviewCommand.getTechnology(), actualInterviewCommand.getTechnology());
        assertEquals(expectedInterviewCommand.getFeedback(), actualInterviewCommand.getFeedback());

    }

    @Test
    public void addInvalidFieldsForm_ShouldRenderFormViewAndReturnValidationErrorsForFieldInterviewCommand() throws Exception {
        createUser();
        String invalidId = "1000";
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        ResultActions postRequest = mvc.perform(post("/interview/add").session(session)
                .param("city", invalidId)
                .param("company", invalidId)
                .param("technology", invalidId)
                .param("position", invalidId));
        //  .param("feedback", "testFeedback"));    validation null feedback
        postRequest.andExpect(status().isOk())
                .andExpect(view().name("add_interview"))
                .andExpect(forwardedUrl("/WEB-INF/views/add_interview.jsp"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "city"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "company"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "technology"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "position"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "feedback"))
                .andDo(print());

    }

    @Test
    public void createInterview_shouldAddInterviewAndRenderViewInterviewView() throws Exception {

        UserEntity userEntity = createUser();
        InterviewCommand expectedInterviewCommand = createInterviewCommand(userEntity, new Date());
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();

        ResultActions postRequest = mvc.perform(post("/interview/add").session(session)
                .param("city", String.valueOf(expectedInterviewCommand.getCity().getId()))
                .param("company", String.valueOf(expectedInterviewCommand.getCompany().getId()))
                .param("technology", String.valueOf(expectedInterviewCommand.getTechnology().getId()))
                .param("position", String.valueOf(expectedInterviewCommand.getPosition().getId()))
                .param("feedback", USER_FEEDBACK));

        postRequest.andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/{interviewId}/view"))
                .andExpect(flash().attribute("feedbackMessage", "OK added"))
                .andExpect(model().hasNoErrors());

    }

    @Test
    public void showViewInterviewForm_InterviewFound_shouldAddInterviewCommandToModelRenderViewInterviewView() throws Exception {
        createUser();
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        mvc.perform(get("/{interviewId}/view", 51).session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("view_interview"))
                .andExpect(forwardedUrl("/WEB-INF/views/view_interview.jsp"))
                .andExpect(model().attributeExists("interviewCommand"))
                /*.andExpect(model().attribute("interviewCommand", hasProperty("user", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("company", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("technology", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("position", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("city", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("feedback", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("created", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("questionCount", equalTo(0))))*/
                .andDo(print());
    }

    @Test
    public void showViewInterviewForm_InterviewNotFound_shouldRender404View() throws Exception {
        createUser();
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        mvc.perform(get("/{interviewId}/view", 51).session(session))
                .andExpect(status().isNotFound())
                /*
                .andExpect(view().name("view_interview"))
                .andExpect(forwardedUrl("/WEB-INF/views/view_interview.jsp"))
                .andExpect(model().attributeExists("interviewCommand"))
                .andExpect(model().attribute("interviewCommand", hasProperty("user", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("company", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("technology", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("position", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("city", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("feedback", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("created", nullValue())))
                .andExpect(model().attribute("interviewCommand", hasProperty("questionCount", equalTo(0))))*/
                .andDo(print());

    }

    private InterviewCommand createInterviewCommand(UserEntity userEntity, Date dateCreated) throws Exception {
        InterviewEntity interview = new InterviewEntity();
        interview.setUser(userEntity);
        interview.setCreated(dateCreated);
        interview.setCity(createCity());
        interview.setCompany(createCompany());
        interview.setFeedback(USER_FEEDBACK);
        interview.setPosition(createPosition());
        interview.setTechnology(createTechnology());
        return new InterviewCommand(interview);
    }


}
