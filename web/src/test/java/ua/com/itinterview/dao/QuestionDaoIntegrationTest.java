package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.QuestionEntity;

public class QuestionDaoIntegrationTest extends
        BaseEntityWithIdDaoIntegrationTest<QuestionEntity> {

    public static final String INTERVIEW_FEEDBACK = "Interwview feedback";
    public static final String QUESTION_TEXT = "Question text";
    public static final String QUESTION_TITLE = "Question title";

    @Autowired
    private InterviewDao interDao;

    @Autowired
    private QuestionDao questionDao;

    @Override
    protected QuestionEntity createEntity() {
        InterviewEntity interview = createInterview();
        return createQuestion(interview);
    }

    @Override
    protected EntityWithIdDao<QuestionEntity> getEntityWithIdDao() {
        return questionDao;
    }

    private InterviewEntity createInterview() {
        InterviewEntity interview = new InterviewEntity();
        interview.setCreated(new Date());
        interview.setFeedback(INTERVIEW_FEEDBACK);
        interview.setUser(testUser);
        return interDao.save(interview);
    }

    private QuestionEntity createQuestion() {
        return createQuestion(null);
    }

    private QuestionEntity createQuestion(InterviewEntity interviewEntity)
    {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setQuestion(QUESTION_TEXT);
        questionEntity.setTitle(QUESTION_TITLE);
        questionEntity.setInterview(interviewEntity);
        return questionDao.save(questionEntity);
    }

    @Test
    public void testGetQuestionForInterviewThatDoesNotExist()
    {
        InterviewEntity wrongInterview = new InterviewEntity();
        wrongInterview.setId(2143);
        List<QuestionEntity> questionsFound = questionDao.getQuestionsForInterview(wrongInterview);
        assertEquals(0, questionsFound.size());
    }

    @Test
    public void testGetQuestionsForInterview() {
        InterviewEntity interview = createInterview();
        QuestionEntity expectedQuestion = createQuestion(interview);
        InterviewEntity anotherInterview = createInterview();
        QuestionEntity unexpectedQuestion = createQuestion(anotherInterview);
        List<QuestionEntity> actualQuestions = questionDao.getQuestionsForInterview(interview);
        assertEquals(1, actualQuestions.size());
        assertEquals(expectedQuestion, actualQuestions.get(0));
        assertNotSame(unexpectedQuestion, actualQuestions.get(0));
    }

    @Test
    public void testGetQuestionsCountForInterview() {
        InterviewEntity interview = createInterview();
        createQuestion(interview);
        createQuestion(interview);
        createQuestion(interview);
        InterviewEntity anotherInterview = createInterview();
        createQuestion(anotherInterview);
        long questionsCountForInterview = questionDao.getQuestionsCountForInterview(interview);
        assertEquals(3L, questionsCountForInterview);
    }

    @Test
    public void getQuestionsForUser() {
        InterviewEntity interview = createInterview();
        QuestionEntity expectedQuestion = createQuestion(interview);
        List<QuestionEntity> actualQuestions = questionDao.getQuestionsForUser(testUser);
        assertEquals(1, actualQuestions.size());
        assertEquals(expectedQuestion, actualQuestions.get(0));
    }
}
