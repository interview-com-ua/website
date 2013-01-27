package ua.com.itinterview.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.com.itinterview.dao.InterviewEntityDao;
import ua.com.itinterview.dao.QuestionDao;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.web.command.QuestionCommand;

public class QuestionServiceUnitTest {

    private static final String QUESTION = "question";
    private InterviewEntityDao interviewDao;
    private QuestionDao questionDao;
    private QuestionService questionService;

    @Before
    public void setUpMocks() {
	questionService = new QuestionService();

	interviewDao = EasyMock.createMock(InterviewEntityDao.class);
	questionService.interviewDao = interviewDao;

	questionDao = EasyMock.createMock(QuestionDao.class);
	questionService.questionDao = questionDao;
    }

    private void replayAllMocks() {
	EasyMock.replay(interviewDao, questionDao);
    }

    private List<QuestionEntity> getQuestionListForInterviewMockResult() {
	List<QuestionEntity> result = new ArrayList<QuestionEntity>();
	QuestionEntity entity = new QuestionEntity();
	entity.setQuestion("entity");
	result.add(entity);
	QuestionEntity entity1 = new QuestionEntity();
	entity1.setQuestion("entity1");
	result.add(entity1);
	return result;
    }

    private List<QuestionCommand> convertEntityListToCommandList(
	    List<QuestionEntity> questionEntities) {
	List<QuestionCommand> result = new ArrayList<QuestionCommand>();
	for (QuestionEntity questionEntity : questionEntities) {
	    result.add(new QuestionCommand(questionEntity));
	}
	return result;
    }

    private QuestionCommand createTestQuestionCommand() {
	QuestionCommand result = new QuestionCommand();
	result.setQuestion(QUESTION);
	return result;
    }

    private QuestionEntity createTestQuestionEntity() {
	QuestionEntity result = new QuestionEntity();
	result.setQuestion(QUESTION);
	return result;
    }

    @Test
    public void testConvertFromEntityToCommand() {
	replayAllMocks();
	QuestionCommand expected = createTestQuestionCommand();
	assertEquals(expected, new QuestionCommand(createTestQuestionEntity()));
    }

    @Test
    public void testConvertFromCommandToEntity() {
	replayAllMocks();
	QuestionEntity expectedEntity = createTestQuestionEntity();
	assertEquals(expectedEntity, new QuestionEntity(
		createTestQuestionCommand()));
    }

    @Test
    public void testGetQuestionListForInterviewWhenInterviewExists() {
	InterviewEntity interview = new InterviewEntity();
	Integer interviewId = Integer.valueOf(21);
	List<QuestionEntity> questionListForInterviewResult = getQuestionListForInterviewMockResult();
	EasyMock.expect(interviewDao.getOneResultByParameter("id", interviewId))
		.andReturn(interview);
	EasyMock.expect(questionDao.getQuestionsForInterview(interview))
		.andReturn(questionListForInterviewResult);
	replayAllMocks();
	assertEquals(
		convertEntityListToCommandList(questionListForInterviewResult),
		questionService.getQuestionListForInterview(interviewId));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetQuestionListForInterviewWhenInterviewDoesNotExist() {
	EasyMock.expect(
		interviewDao.getOneResultByParameter("id", Integer.valueOf(10)))
		.andThrow(new EntityNotFoundException());
	replayAllMocks();
	questionService.getQuestionListForInterview(Integer.valueOf(10));
    }

    @Test
    public void testAddQuestionToInterviewWhenInterviewExists() {

	InterviewEntity interview = new InterviewEntity();
	interview.setId(42);

	QuestionEntity question = createTestQuestionEntity();
	question.setInterview(interview);

	EasyMock.expect(
		interviewDao.getOneResultByParameter("id", interview.getId()))
		.andReturn(interview);
	EasyMock.expect(questionDao.save(question)).andReturn(question);

	replayAllMocks();

	assertEquals(new QuestionCommand(question),
		questionService.addQuestionToInterview(interview.getId(),
			createTestQuestionCommand()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testAddQuestionToInterviewWhenInterviewDoesNotExist() {

	EasyMock.expect(interviewDao.getOneResultByParameter("id", 42))
		.andThrow(new EntityNotFoundException());

	replayAllMocks();

	questionService.addQuestionToInterview(42, null);
    }

    @Test
    public void testUpdateQuestionWhenQuestionExist() {
	QuestionEntity questionEntity = createTestQuestionEntity();
	questionEntity.setId(15);
	EasyMock.expect(
		questionDao.getOneResultByParameter("id",
			questionEntity.getId())).andReturn(questionEntity);
	EasyMock.expect(questionDao.save(questionEntity)).andReturn(
		questionEntity);
	replayAllMocks();
	assertEquals(new QuestionCommand(questionEntity),
		questionService.updateQuestion(questionEntity.getId(),
			createTestQuestionCommand()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateQuestionWhenuestionDoesNotExist() {

	EasyMock.expect(questionDao.getOneResultByParameter("id", 15))
		.andThrow(new EntityNotFoundException());

	replayAllMocks();

	questionService.updateQuestion(15, null);
    }

    @After
    public void verifyAllMocks() {
	EasyMock.verify(interviewDao, questionDao);

    }

}
