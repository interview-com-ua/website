package ua.com.itinterview.service;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import ua.com.itinterview.dao.CommentDao;
import ua.com.itinterview.entity.CommentEntity;
import ua.com.itinterview.web.command.CommentCommand;

public class CommentServiceUnitTest {
    private CommentDao commentDaoMock;
    private CommentService commentService;

    @Before
    public void createMockForComment() {
	commentDaoMock = EasyMock.createMock(CommentDao.class);
	commentService = new CommentService();
	commentService.commentDao = commentDaoMock;
    }

    @Test
    public void testConvertCommentCommandToEntityComment() {
	CommentCommand command = new CommentCommand();
	CommentEntity entity = new CommentEntity();

	command.setAuthorName("Василий");
	command.setEmail("vasya@gmail.com");
	command.setCommentText("Все круто, завтра идем в поход");
	command.setUserpicUrl("http://pictures/vasya.jpg");

	entity.setAuthorName("Василий");
	entity.setEmail("vasya@gmail.com");
	entity.setCommentText("Все круто, завтра идем в поход");
	entity.setUserpicUrl("http://pictures/vasya.jpg");

	CommentEntity actual = new CommentEntity(command);
	assertEquals(entity, actual);
    }

    @Test
    public void testConvertEntityToCommand() {
	CommentCommand command = new CommentCommand();
	CommentEntity entity = new CommentEntity();

	entity.setAuthorName("Василий");
	entity.setEmail("vasya@gmail.com");
	entity.setCommentText("Все круто, завтра идем в поход");
	entity.setUserpicUrl("http://pictures/vasya.jpg");

	command.setAuthorName("Василий");
	command.setEmail("vasya@gmail.com");
	command.setCommentText("Все круто, завтра идем в поход");
	command.setUserpicUrl("http://pictures/vasya.jpg");

	CommentCommand actual = new CommentCommand(entity);
	assertEquals(command, actual);
    }

    public void testAddCommentForQuestion() {

    }

}
