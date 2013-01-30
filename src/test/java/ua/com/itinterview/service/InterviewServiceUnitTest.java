package ua.com.itinterview.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import ua.com.itinterview.dao.InterviewEntityDao;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.command.InterviewCommand;

public class InterviewServiceUnitTest {

    private UserEntity user;
    private InterviewService interviewService;
    private InterviewEntityDao interviewDaoMock;
    private UserDao userDao;

    @Before
    public void createMocks() {
	interviewDaoMock = EasyMock.createMock(InterviewEntityDao.class);
	interviewService = new InterviewService();
	interviewService.interviewEntityDao = interviewDaoMock;
	user = new UserEntity();
	userDao = EasyMock.createMock(UserDao.class);
    }

    @Test
    public void testAddInterview() {
	InterviewCommand interviewCommand = createInterviewCommand();
	InterviewEntity interviewEntity = new InterviewEntity(interviewCommand);
	// EasyMock.expect(
	// interviewDaoMock
	// .getInterviewsByUser(interviewCommand.getUser()).size())
	// .andReturn(1);
	EasyMock.expect(interviewDaoMock.save(interviewEntity)).andReturn(
		interviewEntity);
	EasyMock.replay(interviewDaoMock);
	interviewService.addInterview(interviewCommand);
	EasyMock.verify(interviewDaoMock);
    }

    @Test
    public void testConvertCommandToEntity() {
	InterviewCommand command = createInterviewCommand();
	InterviewEntity expected = new InterviewEntity();
	expected.setCreated(new Date(10000));
	expected.setFeedback("test");
	expected.setUser(user);
	InterviewEntity actual = new InterviewEntity(command);
	assertEquals(expected, actual);
    }

    @Test
    public void testConvertEntityToCommand() {
	InterviewEntity actual = new InterviewEntity();
	actual.setCreated(new Date(10000));
	actual.setFeedback("test");
	actual.setUser(user);
	InterviewCommand interviewCommand = new InterviewCommand(actual);
	InterviewCommand interviewCommand1 = createInterviewCommand();
	assertEquals(interviewCommand, interviewCommand1);
    }

    private InterviewCommand createInterviewCommand() {
	InterviewCommand interviewCommand = new InterviewCommand();
	interviewCommand.setFeedback("test");
	interviewCommand.setCreated(new Date(10000));
	user.setEmail("email");
	user.setPassword("password");
	user.setUserName("name");
	interviewCommand.setUser(user);
	return interviewCommand;
    }

    private InterviewEntity createInterviewEntity() {
	InterviewEntity interviewEntity = new InterviewEntity();
	interviewEntity.setFeedback("test");
	interviewEntity.setCreated(new Date(10000));
	user.setEmail("email");
	user.setPassword("password");
	user.setUserName("name");
	userDao.save(user);
	interviewEntity.setUser(user);
	return interviewEntity;
    }

    @Test
    public void testGetInterviewList() {
	List<InterviewEntity> list = new ArrayList<InterviewEntity>();
	InterviewEntity interviewEntity = createInterviewEntity();
	System.out.println(interviewEntity.getUser());
	list.add(interviewEntity);
	EasyMock.expect(interviewDaoMock.getAll()).andReturn(list);
	EasyMock.replay(interviewDaoMock);
	interviewService.getInterviewList();
	EasyMock.verify(interviewDaoMock);
    }
}
