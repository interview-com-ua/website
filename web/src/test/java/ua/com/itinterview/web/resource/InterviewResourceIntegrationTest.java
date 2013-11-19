package ua.com.itinterview.web.resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;
import ua.com.itinterview.entity.CityEntity;
import ua.com.itinterview.entity.CompanyEntity;
import ua.com.itinterview.entity.PositionEntity;
import ua.com.itinterview.entity.TechnologyEntity;
import ua.com.itinterview.service.InterviewService;
import ua.com.itinterview.web.integration.BaseWebIntegrationTest;
import ua.com.itinterview.web.resource.viewpages.ModeView;

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
                .andExpect(model().attributeExists("interviewCommand"))
                .andExpect(model().attributeExists("listCompany"))
                .andExpect(model().attributeExists("listTechnology"))
                .andExpect(model().attributeExists("listCity"))
                .andExpect(model().attributeExists("listPosition"))
                .andExpect(model().attribute("mode", ModeView.CREATE))
                .andDo(print());
    }

    @Test
    public void testBindingAndValidityNestedObjectsForInterviewCommand() throws Exception {
        createUser();
        CityEntity testCityEntity = createCity();
        PositionEntity testPositionEntity = createPosition();
        TechnologyEntity testTechnologyEntity = createTechnology();
        CompanyEntity testCompanyEntity = createCompany();

        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        ResultActions postRequest = mvc.perform(post("/interview/add").session(session)
                .param("city", String.valueOf(testCityEntity.getId()))
                .param("company", String.valueOf(testCompanyEntity.getId()))
                .param("technology", String.valueOf(testTechnologyEntity.getId()))
                .param("position", String.valueOf(testPositionEntity.getId()))
                .param("feedback", "testFeedback"));

        postRequest.andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andDo(print());

    }

    @Test
    public void testAddInterviewWithOKFields() throws Exception {
        createUser();
        ResultActions actions = mvc.perform(loginUser());
        MockHttpSession session = (MockHttpSession) actions.andReturn().getRequest().getSession();
        ResultActions postRequest = mvc.perform(post("/interview/add").session(session)
                .param("city", String.valueOf(createCity().getId()))
                .param("company", String.valueOf(createCompany().getId()))
                .param("technology", String.valueOf(createTechnology().getId()))
                .param("position", String.valueOf(createPosition().getId()))
                .param("feedback", "testFeedback"));

        postRequest.andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andDo(print());


    }

    @Test
    public void testViewInterviewById() {

    }

    @Test
    public void testEditInterviewWithOKFields() {

    }

    @Test
    public void testEditInterviewWithFailFields() {

    }

}
