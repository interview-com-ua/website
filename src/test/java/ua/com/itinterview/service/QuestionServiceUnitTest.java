package ua.com.itinterview.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.easymock.Capture;
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
    public void testGetQuestionById() {
	Integer questionId = 10;
	QuestionEntity questionEntity = new QuestionEntity();
	EasyMock.expect(questionDao.getEntityById(questionId)).andReturn(
		questionEntity);
	replayAllMocks();
	assertEquals(questionService.getQuestionById(questionId),
		new QuestionCommand(questionEntity));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetQuestionByIdWhenQuestionDoesNotExist() {
	EasyMock.expect(questionDao.getEntityById(10)).andThrow(
		new EntityNotFoundException());
	replayAllMocks();
	questionService.getQuestionById(10);
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
	EasyMock.expect(interviewDao.getEntityById(interviewId)).andReturn(
		interview);
	EasyMock.expect(questionDao.getQuestionsForInterview(interview))
		.andReturn(questionListForInterviewResult);
	replayAllMocks();
	assertEquals(
		convertEntityListToCommandList(questionListForInterviewResult),
		questionService.getQuestionListForInterview(interviewId));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetQuestionListForInterviewWhenInterviewDoesNotExist() {
	EasyMock.expect(interviewDao.getEntityById(Integer.valueOf(10)))
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

	EasyMock.expect(interviewDao.getEntityById(interview.getId()))
		.andReturn(interview);
	EasyMock.expect(questionDao.save(question)).andReturn(question);

	replayAllMocks();

	assertEquals(new QuestionCommand(question),
		questionService.addQuestionToInterview(interview.getId(),
			createTestQuestionCommand()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testAddQuestionToInterviewWhenInterviewDoesNotExist() {

	EasyMock.expect(interviewDao.getEntityById(42)).andThrow(
		new EntityNotFoundException());

	replayAllMocks();

	questionService.addQuestionToInterview(42, null);
    }

    @Test
    public void testUpdateQuestionWhenQuestionExist() {
	Integer questionId = 15;
	QuestionEntity questionEntity = createTestQuestionEntity();
	questionEntity.setId(questionId);
	QuestionCommand questionCommand = new QuestionCommand();
	questionCommand.setQuestion("New Question");

	EasyMock.expect(questionDao.getEntityById(questionEntity.getId()))
		.andReturn(questionEntity);
	Capture<QuestionEntity> questionCapture = new Capture<QuestionEntity>();
	EasyMock.expect(questionDao.save(EasyMock.capture(questionCapture)))
		.andReturn(questionEntity);
	replayAllMocks();

	assertEquals(questionCommand,
		questionService.updateQuestion(questionId, questionCommand));
	assertEquals("New Question", questionCapture.getValue().getQuestion());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateQuestionWhenuestionDoesNotExist() {

	EasyMock.expect(questionDao.getEntityById(15)).andThrow(
		new EntityNotFoundException());

	replayAllMocks();

	questionService.updateQuestion(15, null);
    }

    @Test
    public void testGetRecentQuestionList() {

	List<QuestionEntity> questionList = getQuestionListForInterviewMockResult();
	EasyMock.expect(questionDao.getRecentQuestionList()).andReturn(
		questionList);
	replayAllMocks();
	assertEquals(convertEntityListToCommandList(questionList),
		questionService.getRecentQuestionList());
    }

    @After
    public void verifyAllMocks() {
	EasyMock.verify(interviewDao, questionDao);

    }

}
