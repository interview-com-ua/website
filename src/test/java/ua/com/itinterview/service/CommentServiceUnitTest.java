package ua.com.itinterview.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.com.itinterview.dao.CommentDao;
import ua.com.itinterview.dao.QuestionDao;
import ua.com.itinterview.dao.paging.PagingFilter;
import ua.com.itinterview.entity.CommentEntity;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.web.command.CommentCommand;
import ua.com.itinterview.web.configuration.ItemsPerPageConstantConfiguration;

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
	replayAllMocks();
	CommentEntity actual = new CommentEntity(command);
	assertEquals(entity, actual);
    }

    @Test
    public void testConvertEntityToCommand() {
	replayAllMocks();
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

	// EasyMock.replay(commentDaoMock, questionDaoMock);
	replayAllMocks();

	CommentCommand actualCommentCommand = commentService
		.addCommentForQuestion(questionId, command);

	CommentCommand expectedCommentCommand = new CommentCommand(
		expectCommentEntity);

	assertEquals(expectedCommentCommand, actualCommentCommand);

	EasyMock.verify(commentDaoMock, questionDaoMock);
    }

    @Test
    public void testGetCommentListForQuestion() {
	Integer questionId = 1;
	Integer currentPage = 0;
	PagingFilter pagingFilter = new PagingFilter(currentPage,
		ItemsPerPageConstantConfiguration.ITEMS_PER_COMMENTS_PAGE);
	QuestionEntity questionEntity = new QuestionEntity();
	questionEntity.setId(questionId);
	List<CommentEntity> expectedList = createCommentListForQuestion(questionEntity);
	EasyMock.expect(questionDaoMock.getEntityById(questionId)).andReturn(
		questionEntity);
	EasyMock.expect(
		commentDaoMock.getCommentsForQuestionsOrderedByRate(
			questionEntity, pagingFilter)).andReturn(expectedList);
	replayAllMocks();
	List<CommentCommand> actualList = commentService
		.getCommentListForQuestion(questionId, currentPage);
	assertEquals(expectedList.size(), actualList.size());
	for (int i = 0; i < expectedList.size(); i++) {
	    assertEquals(new CommentCommand(expectedList.get(i)),
		    actualList.get(i));
	}
    }

    private List<CommentEntity> createCommentListForQuestion(
	    QuestionEntity questionEntity) {
	List<CommentEntity> resultFromDb = new ArrayList<CommentEntity>();
	CommentEntity comment1 = new CommentEntity();
	comment1.setAuthorName("authorname1");
	comment1.setCommentText("commenttext1");
	comment1.setEmail("email1");
	comment1.setRate(4);
	comment1.setUserpicUrl("userpic1");
	comment1.setQuestionEntity(questionEntity);
	CommentEntity comment2 = new CommentEntity();
	comment2.setAuthorName("authorname2");
	comment2.setCommentText("commenttext2");
	comment2.setEmail("email2");
	comment2.setRate(3);
	comment2.setUserpicUrl("userpic2");
	comment2.setQuestionEntity(questionEntity);
	resultFromDb.add(comment1);
	resultFromDb.add(comment2);
	return resultFromDb;
    }

    private void replayAllMocks() {
	EasyMock.replay(commentDaoMock, questionDaoMock);
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

    @After
    public void VerifyAllMocks() {
	EasyMock.verify(commentDaoMock, questionDaoMock);
    }

}
