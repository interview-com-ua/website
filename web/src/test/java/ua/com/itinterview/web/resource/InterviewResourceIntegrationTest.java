package ua.com.itinterview.web.resource;

import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.command.InterviewCommand;
import ua.com.itinterview.web.integration.BaseWebIntegrationTest;
import ua.com.itinterview.web.resource.viewpages.ModeView;

import java.util.Date;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * User: kuznetsov d.v.
 * Date: 14.11.13
 */
public class InterviewResourceIntegrationTest extends BaseWebIntegrationTest {

    @Test
    public void testShowListInterviewByRegisterUser() throws Exception {
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
    public void testShowListByUnRegisterUserRedirectToRegistrationPage() throws Exception {
        mvc.perform(get("/interview/my"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/register"))
                .andDo(print());

    }

    @Test
    public void testAddInterviewWithoutRegistration() throws Exception {
        mvc.perform(post("/interview/add"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/register"))
                .andDo(print());
    }

    @Test
    public void testShowPageAddInterviewWithInitParam() throws Exception {
        createUser();
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        mvc.perform(get("/interview/add").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("interview"))
                .andExpect(model().attributeExists("listCompany"))
                .andExpect(model().attributeExists("listTechnology"))
                .andExpect(model().attributeExists("listCity"))
                .andExpect(model().attributeExists("listPosition"))
                .andExpect(model().attribute("mode", is(ModeView.CREATE)))
                .andDo(print());
    }

    @Test
    public void testBindingNestedObjectsForInterviewCommand() throws Exception {
        
        UserEntity userEntity = createUser();
        InterviewCommand expectedInterviewCommand = createInterviewCommand(userEntity, new Date());
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        mvc.perform(get("/interview/add").session(session)).andDo(print());
        ResultActions postRequest = mvc.perform(post("/interview/add").session(session)
                .param("city", String.valueOf(expectedInterviewCommand.getCity().getId()))
                .param("company", String.valueOf(expectedInterviewCommand.getCompany().getId()))
                .param("technology", String.valueOf(expectedInterviewCommand.getTechnology().getId()))
                .param("position", String.valueOf(expectedInterviewCommand.getPosition().getId()))
                .param("feedback", USER_FEEDBACK));

        postRequest.andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("interviewCommand", hasProperty("city", is(expectedInterviewCommand.getCity()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("company", is(expectedInterviewCommand.getCompany()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("position", is(expectedInterviewCommand.getPosition()))))
                .andExpect(model().attribute("interviewCommand", hasProperty("technology", is(expectedInterviewCommand.getTechnology()))));

    }

    @Test
    public void testInvalidValidityNestedObjectsForInterviewCommand() throws Exception {
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
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("interviewCommand"))
                .andDo(print());

    }

    @Test
    public void testAddInterviewWithOKFields() throws Exception {
        UserEntity userEntity = createUser();
        InterviewCommand expectedInterviewCommand = createInterviewCommand(userEntity, new Date());
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        mvc.perform(get("/interview/add").session(session)).andDo(print());
        ResultActions postRequest = mvc.perform(post("/interview/add").session(session)
                .param("city", String.valueOf(expectedInterviewCommand.getCity().getId()))
                .param("company", String.valueOf(expectedInterviewCommand.getCompany().getId()))
                .param("technology", String.valueOf(expectedInterviewCommand.getTechnology().getId()))
                .param("position", String.valueOf(expectedInterviewCommand.getPosition().getId()))
                .param("feedback", USER_FEEDBACK));

        postRequest.andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("interviewCommand"));

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
