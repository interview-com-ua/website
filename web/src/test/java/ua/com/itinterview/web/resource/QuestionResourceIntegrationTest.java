package ua.com.itinterview.web.resource;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;
import ua.com.itinterview.web.integration.BaseWebIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DatabaseSetup("file:src/test/resources/dataset/QuestionResource/users-initial.xml")
public class QuestionResourceIntegrationTest extends BaseWebIntegrationTest {

    @Test
    @ExpectedDatabase(
            value = "file:src/test/resources/dataset/QuestionResource/users-initial.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT
    )
    public void testGetQuestions() throws Exception {
        ResultActions actions = null;
        actions = mvc.perform(loginUser()).andDo(print());
        MockHttpSession session = getHttpSession(actions);
        actions = mvc.perform(get("/question/my").session(session));
        actions.andDo(print())
            .andExpect(status().isOk())
            .andExpect(handler().handlerType(QuestionResource.class))
            .andExpect(handler().methodName("getQuestions"));
        actions.andExpect(view().name("show_question_list"));
        actions.andExpect(model().hasNoErrors())
            .andExpect(model().size(1))
            .andExpect(model().attributeExists("questionList"));
    }

}
