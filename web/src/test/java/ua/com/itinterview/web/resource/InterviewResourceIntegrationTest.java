package ua.com.itinterview.web.resource;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import ua.com.itinterview.service.*;
import ua.com.itinterview.web.command.*;
import ua.com.itinterview.web.integration.BaseWebIntegrationTest;
import ua.com.itinterview.web.resource.propertyeditor.CityCommandPropertyEditor;
import ua.com.itinterview.web.resource.propertyeditor.CompanyCommandPropertyEditor;
import ua.com.itinterview.web.resource.propertyeditor.PositionCommandPropertyEditor;
import ua.com.itinterview.web.resource.propertyeditor.TechnologyCommandPropertyEditor;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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
    private UserService userService;
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

    private final String INVALID_DB_ID = "1000";
    private final int  INTERVIEW_DB_ID = 1;
    private final String UPDATE_USER_FEEDBACK = "New feedback to interview";
    private final int CITY_DB_ID =1 ;
    private final int COMPANY_DB_ID =1 ;
    private final int POSITION_DB_ID =1 ;
    private final int TECHNOLOGY_DB_ID =1 ;


    @Test
    @DatabaseSetup(value = "file:src/test/resources/dataset/InterviewResource/interview-list.xml")
    public void testShowListInterviewForRegisteredUser() throws Exception {
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
    public void testShowListInterviewForNotRegisteredUser() throws Exception {
        mvc.perform(get("/interview/my"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/register"))
                .andDo(print());

    }

    @Test
    public void testShowFormViewInterviewForNotRegisteredUser() throws Exception {
        mvc.perform(post("/interview/{interviewId}/view",INTERVIEW_DB_ID))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/register"))
                .andDo(print());
    }


    @Test
    public void testShowFormEditInterviewForNotRegisteredUser() throws Exception {
        mvc.perform(post("/interview/{interviewId}/edit",INTERVIEW_DB_ID))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/register"))
                .andDo(print());
    }


    @Test
    public void testShowFormCreateInterviewForNotRegisteredUser() throws Exception {
        mvc.perform(post("/interview/add"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/register"))
                .andDo(print());
    }

    @Test
    @DatabaseSetup(value = "file:src/test/resources/dataset/InterviewResource/interview-list.xml")
    public void testShowFormCreateInterview() throws Exception {
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        mvc.perform(get("/interview/add").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("add_interview"))
                .andExpect(forwardedUrl("/WEB-INF/views/add_interview.jsp"))
                .andExpect(model().attributeExists("listCompany"))
                .andExpect(model().attribute("listCompany",hasSize(equalTo(3))))
                .andExpect(model().attributeExists("listTechnology"))
                .andExpect(model().attribute("listTechnology",hasSize(equalTo(3))))
                .andExpect(model().attributeExists("listCity"))
                .andExpect(model().attribute("listCity",hasSize(equalTo(3))))
                .andExpect(model().attributeExists("listPosition"))
                .andExpect(model().attribute("listPosition",hasSize(equalTo(3))))
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
    @DatabaseSetup(value = "file:src/test/resources/dataset/InterviewResource/interview-list.xml")
    public void testShowFormViewInterviewWhenInterviewFound() throws Exception {
        InterviewCommand expectedInterviewCommand  = interviewService.getInterviewById(INTERVIEW_DB_ID);
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        mvc.perform(get("/interview/{interviewId}/view", expectedInterviewCommand.getId()).session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("view_interview"))
                .andExpect(forwardedUrl("/WEB-INF/views/view_interview.jsp"))
                .andExpect(model().attributeExists("interviewCommand"))
                .andExpect(model().attribute("interviewCommand", hasProperty("user", is(expectedInterviewCommand.getUser()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("company",is(expectedInterviewCommand.getCompany()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("technology", is(expectedInterviewCommand.getTechnology()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("position", is(expectedInterviewCommand.getPosition()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("city", is(expectedInterviewCommand.getCity()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("feedback", is(expectedInterviewCommand.getFeedback()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("created", is(expectedInterviewCommand.getCreated()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("questionCount", is(expectedInterviewCommand.getQuestionCount()))))
                .andDo(print());
    }

    @Test
    @DatabaseSetup(value = "file:src/test/resources/dataset/InterviewResource/interview-list.xml")
    public void testShowFormEditInterviewWhenInterviewFound() throws Exception {
        InterviewCommand expectedInterviewCommand  = interviewService.getInterviewById(INTERVIEW_DB_ID);
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        mvc.perform(get("/interview/{interviewId}/edit", expectedInterviewCommand.getId()).session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("update_interview"))
                .andExpect(forwardedUrl("/WEB-INF/views/update_interview.jsp"))
                .andExpect(model().attributeExists("interviewCommand"))
                .andExpect(model().attribute("interviewCommand", hasProperty("user", is(expectedInterviewCommand.getUser()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("company", is(expectedInterviewCommand.getCompany()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("technology", is(expectedInterviewCommand.getTechnology()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("position", is(expectedInterviewCommand.getPosition()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("city", is(expectedInterviewCommand.getCity()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("feedback", is(expectedInterviewCommand.getFeedback()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("created", is(expectedInterviewCommand.getCreated()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("questionCount", is(expectedInterviewCommand.getQuestionCount()))))
                .andExpect(model().attributeExists("listCompany"))
                .andExpect(model().attributeExists("listTechnology"))
                .andExpect(model().attributeExists("listCity"))
                .andExpect(model().attributeExists("listPosition"))
                .andDo(print());
    }

    @Test
    @DatabaseSetup(value = "file:src/test/resources/dataset/InterviewResource/interview-list.xml")
    public void testShowFormEditInterviewWhenInterviewNotFound() throws Exception {

        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        mvc.perform(get("/interview/{interviewId}/edit", INVALID_DB_ID).session(session))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    @DatabaseSetup(value = "file:src/test/resources/dataset/InterviewResource/interview-list.xml")
    public void testShowFormViewInterviewWhenInterviewNotFound() throws Exception {

        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        mvc.perform(get("/interview/{interviewId}/view", INVALID_DB_ID).session(session))
                .andExpect(status().isNotFound())
                .andDo(print());

    }


    @Test
    public void testBindingNestedObjectsForInterviewCommand() throws Exception {
        InterviewCommand expectedInterviewCommand = createNewInterviewCommand();
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

        assertThat(binder.getBindingResult().getErrorCount(),is(0));
        assertThat(actualInterviewCommand.getCity(), is(expectedInterviewCommand.getCity()));
        assertThat(actualInterviewCommand.getCompany(), is(expectedInterviewCommand.getCompany()));
        assertThat(actualInterviewCommand.getPosition(), is(expectedInterviewCommand.getPosition()));
        assertThat(actualInterviewCommand.getTechnology(), is(expectedInterviewCommand.getTechnology()));
        assertThat(actualInterviewCommand.getFeedback(), is(expectedInterviewCommand.getFeedback()));

    }

    @Test
    public void testValidationIncorrectFieldsIntoFormCreateInterview() throws Exception {
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        ResultActions postRequest = mvc.perform(post("/interview/add").session(session)
                .param("city", INVALID_DB_ID)
                .param("company", INVALID_DB_ID)
                .param("technology", INVALID_DB_ID)
                .param("position", INVALID_DB_ID));
        //  .param("feedback", "testFeedback"));    validation null feedback
        postRequest.andExpect(status().isOk())
                .andExpect(view().name("add_interview"))
                .andExpect(forwardedUrl("/WEB-INF/views/add_interview.jsp"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "city"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "company"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "technology"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "position"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "feedback"))
                .andExpect(model().attributeExists("listCompany"))
                .andExpect(model().attributeExists("listTechnology"))
                .andExpect(model().attributeExists("listCity"))
                .andExpect(model().attributeExists("listPosition"))
                .andDo(print());
    }

    @Test
    @DatabaseSetup(value = "file:src/test/resources/dataset/InterviewResource/after-updating-interview.xml")
    public void testValidationIncorrectFieldsIntoFormEditInterview() throws Exception {
        InterviewCommand expectedInterviewCommand  = interviewService.getInterviewById(INTERVIEW_DB_ID);
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        ResultActions postRequest = mvc.perform(post("/interview/" + expectedInterviewCommand.getId() + "/edit").session(session)
                .param("city", INVALID_DB_ID)
                .param("company", INVALID_DB_ID)
                .param("technology", INVALID_DB_ID)
                .param("position", INVALID_DB_ID));
            //    .param("feedback", "testFeedback"));  validation null feedback
      postRequest
               .andExpect(status().isOk())
                .andExpect(view().name("update_interview"))
                .andExpect(forwardedUrl("/WEB-INF/views/update_interview.jsp"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "city"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "company"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "technology"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "position"))
                .andExpect(model().attributeHasFieldErrors("interviewCommand", "feedback"))
                .andExpect(model().attributeExists("listCompany"))
                .andExpect(model().attributeExists("listTechnology"))
                .andExpect(model().attributeExists("listCity"))
                .andExpect(model().attributeExists("listPosition"))
                .andDo(print());
    }



    @Test
    @DatabaseSetup(value = "file:src/test/resources/dataset/InterviewResource/after-adding-interview.xml")
    @ExpectedDatabase(
            value = "file:src/test/resources/dataset/InterviewResource/before-adding-interview.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT
    )
    public void testCreateInterview() throws Exception {
        InterviewCommand expectedInterviewCommand = createNewInterviewCommand();
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
                .andExpect(view().name("redirect:/interview/{interviewId}/view"))
                .andExpect(model().attributeExists("interviewId"))
                .andExpect(flash().attribute("feedbackMessage", "OK added"))
                .andExpect(model().hasNoErrors());
    }


    @Test
    @DatabaseSetup(value = "file:src/test/resources/dataset/InterviewResource/after-updating-interview.xml")
    @ExpectedDatabase(
            value = "file:src/test/resources/dataset/InterviewResource/before-updating-interview.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT
    )
    public void testUpdateInterview() throws Exception {
        InterviewCommand expectedInterviewCommand  = interviewService.getInterviewById(INTERVIEW_DB_ID);
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        ResultActions postRequest = mvc.perform(post("/interview/"+expectedInterviewCommand.getId() +"/edit").session(session)
                .param("city", String.valueOf(expectedInterviewCommand.getCity().getId()))
                .param("company", String.valueOf(expectedInterviewCommand.getCompany().getId()))
                .param("technology", String.valueOf(expectedInterviewCommand.getTechnology().getId()))
                .param("position", String.valueOf(expectedInterviewCommand.getPosition().getId()))
                .param("feedback", UPDATE_USER_FEEDBACK));

        postRequest.andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/interview/{interviewId}/view"))
                .andExpect(model().attribute("interviewId", is(String.valueOf(expectedInterviewCommand.getId()))))
                .andExpect(flash().attribute("feedbackMessage", "OK update"))
                .andExpect(model().hasNoErrors());

    }


    private InterviewCommand createNewInterviewCommand() throws Exception {
        InterviewCommand interview = new InterviewCommand();
        interview.setUser(userService.getUserByEmail(EMAIL));
        interview.setCreated(new Date());
        interview.setCity(cityService.getCityById(CITY_DB_ID));
        interview.setCompany(companyService.getCompanyById(COMPANY_DB_ID));
        interview.setFeedback(USER_FEEDBACK);
        interview.setPosition(positionService.getPositionById(POSITION_DB_ID));
        interview.setTechnology(technologyService.getTechnologyById(TECHNOLOGY_DB_ID));
        return interview;
    }



}
