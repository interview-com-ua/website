package ua.com.itinterview.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import ua.com.itinterview.dao.CommentDao;
import ua.com.itinterview.dao.QuestionDao;
import ua.com.itinterview.entity.CommentEntity;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.web.command.CommentCommand;

public class CommentServiceUnitTest {
    private CommentDao commentDaoMock;
    private QuestionDao questionDaoMock;

    private CommentService commentService;

    private CommentCommand command;
    private CommentEntity entity;

    public void createDaoMockForCommentServiceandCommentService() {
	commentDaoMock = EasyMock.createMock(CommentDao.class);
	questionDaoMock = EasyMock.createMock(QuestionDao.class);
	commentService = new CommentService();
	commentService.commentDao = commentDaoMock;
	commentService.questionDao = questionDaoMock;

    }

    @Before
    public void initialCommandAndEntity() {
	command = addInformationToCommand(new CommentCommand());
	entity = addInformationToCommandEntity(new CommentEntity());

	createDaoMockForCommentServiceandCommentService();
    }

    @Test
    public void testConvertCommentCommandToEntityComment() {

	CommentEntity actual = new CommentEntity(command);
	assertEquals(entity, actual);
    }

    @Test
    public void testConvertEntityToCommand() {

	CommentCommand actual = new CommentCommand(entity);
	assertEquals(command, actual);
    }

    @Test
    public void testAddCommentForQuestion() {
	Integer questionId = new Integer(1);
	QuestionEntity questionEntity = new QuestionEntity();
	questionEntity.setId(questionId);

	CommentEntity expectCommentEntity = new CommentEntity(command);
	expectCommentEntity.setQuestionEntity(questionEntity);
	EasyMock.expect(questionDaoMock.getEntityById(questionId)).andReturn(
		questionEntity);

	EasyMock.expect(commentDaoMock.save(expectCommentEntity)).andReturn(
		expectCommentEntity);

	EasyMock.replay(commentDaoMock, questionDaoMock);

	CommentCommand actualCommentCommand = commentService
		.addCommentForQuestion(questionId, command);

	CommentCommand expectedCommentCommand = new CommentCommand(
		expectCommentEntity);

	assertEquals(expectedCommentCommand, actualCommentCommand);

	EasyMock.verify(commentDaoMock, questionDaoMock);
    }

    public CommentCommand addInformationToCommand(CommentCommand command) {
	command.setAuthorName("Василий");
	command.setEmail("vasya@gmail.com");
	command.setCommentText("Все круто, завтра идем в поход");
	command.setUserpicUrl("http://pictures/vasya.jpg");
	return command;
    }

    public CommentEntity addInformationToCommandEntity(CommentEntity entity) {
	entity.setAuthorName("Василий");
	entity.setEmail("vasya@gmail.com");
	entity.setCommentText("Все круто, завтра идем в поход");
	entity.setUserpicUrl("http://pictures/vasya.jpg");
	return entity;
    }

    public QuestionEntity CreateQuestionEntityForTest() {
	QuestionEntity questionEntity = new QuestionEntity();
	questionEntity.setCreate(new Date());
	questionEntity.setId(new Integer(1));

	return questionEntity;

    }

}
