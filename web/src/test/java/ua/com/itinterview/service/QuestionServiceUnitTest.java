package ua.com.itinterview.service;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ua.com.itinterview.dao.InterviewDao;
import ua.com.itinterview.dao.QuestionDao;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.command.QuestionCommand;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuestionServiceUnitTest {

    private static final int QUESTION_ID = 15;
    private static final String TITLE = "title";
    private static final String QUESTION = "question";
    private static final String ANSWER = "answer";

    private static final int INVALID_QUESTON_ID = QUESTION_ID+1;
    private static final String UPDATED_TITLE = "updated title";
    private static final String UPDATED_QUESTION = "updated question";
    private static final String UPDATED_ANSWER = "updated answer";

    private InterviewDao interviewDao;
    private QuestionDao questionDao;
    private QuestionService questionService;
    private UserDao userDao;

    @Before
    public void setUpMocks() {
	questionService = new QuestionService();

	interviewDao = EasyMock.createMock(InterviewDao.class);
	questionService.interviewDao = interviewDao;

	questionDao = EasyMock.createMock(QuestionDao.class);
	questionService.questionDao = questionDao;

    userDao = EasyMock.createMock(UserDao.class);
    questionService.userDao = userDao;
    }

    private void replayAllMocks() {
	EasyMock.replay(interviewDao, questionDao, userDao);
    }

    private List<QuestionEntity> createTestQuestionList() {
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
	QuestionCommand questionCommand = new QuestionCommand();
	questionCommand.setId(QUESTION_ID);
	questionCommand.setQuestion(QUESTION);
	questionCommand.setTitle(TITLE);
	questionCommand.setAnswer(ANSWER);
	return questionCommand;
    }

    private QuestionEntity createTestQuestionEntity() {
	QuestionEntity questionEntity = new QuestionEntity();
	questionEntity.setId(QUESTION_ID);
	questionEntity.setQuestion(QUESTION);
	questionEntity.setTitle(TITLE);
	questionEntity.setAnswer(ANSWER);
	return questionEntity;
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

    @Test
    public void testGetQuestionListForUser(){
        String email = "test@email.com";
        UserEntity user = new UserEntity();
        user.setEmail(email);
        List<QuestionEntity> expectedQuestionEntityList = createTestQuestionList();
        List<QuestionCommand> expectedQuestionCommandList = convertEntityListToCommandList(expectedQuestionEntityList);
        EasyMock.expect(userDao.getUserByEmail(email)).andReturn(user);
        EasyMock.expect(questionDao.getQuestionsForUser(user)).andReturn(expectedQuestionEntityList);
        replayAllMocks();
        assertArrayEquals(questionService.getQuestionListForUser(email).toArray(), expectedQuestionCommandList.toArray());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetQuestionListForUserWhenUserDoesNotExist(){
    String email = "test@email.com";
    EasyMock.expect(userDao.getUserByEmail(email)).andThrow(new EntityNotFoundException());
    replayAllMocks();
    questionService.getQuestionListForUser(email);
    }

    @Test
    public void testGetQuestionListForUserWhenQuestionListIsEmpty(){
    String email = "test@email.com";
    UserEntity user = new UserEntity();
    user.setEmail(email);
    List<QuestionEntity> empty = new ArrayList<QuestionEntity>();
    EasyMock.expect(userDao.getUserByEmail(email)).andReturn(user);
    EasyMock.expect(questionDao.getQuestionsForUser(user)).andReturn(empty);
    replayAllMocks();
    assertTrue(questionService.getQuestionListForUser(email).isEmpty());
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
	List<QuestionEntity> questionListForInterviewResult = createTestQuestionList();
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
	QuestionEntity questionInDb = createTestQuestionEntity();
	QuestionCommand questionToUpdate = createTestQuestionCommand();
	questionToUpdate.setId(INVALID_QUESTON_ID);
	questionToUpdate.setQuestion(UPDATED_QUESTION);
	questionToUpdate.setAnswer(UPDATED_ANSWER);
	questionToUpdate.setTitle(UPDATED_TITLE);

	EasyMock.expect(questionDao.getEntityById(QUESTION_ID)).andReturn(
		questionInDb);
	Capture<QuestionEntity> questionCapture = new Capture<QuestionEntity>();
	EasyMock.expect(questionDao.save(EasyMock.capture(questionCapture)))
		.andReturn(questionInDb);
	replayAllMocks();

	QuestionCommand expectedQuestionCommand = createTestQuestionCommand();
	expectedQuestionCommand.setQuestion(UPDATED_QUESTION);
	expectedQuestionCommand.setAnswer(UPDATED_ANSWER);
	expectedQuestionCommand.setTitle(UPDATED_TITLE);
	assertEquals(expectedQuestionCommand,
		questionService.updateQuestion(QUESTION_ID, questionToUpdate));
	
	assertEquals(QUESTION_ID, questionCapture.getValue().getId());
	assertEquals(UPDATED_QUESTION, questionCapture.getValue().getQuestion());
	assertEquals(UPDATED_ANSWER, questionCapture.getValue().getAnswer());
	assertEquals(UPDATED_TITLE, questionCapture.getValue().getTitle());
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

	List<QuestionEntity> questionList = createTestQuestionList();
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
