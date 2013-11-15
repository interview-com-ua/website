package ua.com.itinterview.web.resource;

import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;
import ua.com.itinterview.web.integration.BaseWebIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class QuestionResourceIntegrationTest extends BaseWebIntegrationTest {

    @Test
    public void testGetQuestions() throws Exception {
        createUser();
        ResultActions actions = mvc.perform(loginUser()).andDo(print());
        MockHttpSession session = (MockHttpSession)actions.andReturn().getRequest().getSession();
        mvc.perform(get("/question/my").session(session))
                .andDo(print())
                .andExpect(view().name("show_question_list"))
                .andExpect(model().attributeExists("questionList"));
    }
}
