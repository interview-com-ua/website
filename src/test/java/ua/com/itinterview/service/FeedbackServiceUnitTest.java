package ua.com.itinterview.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import ua.com.itinterview.dao.FeedbackDao;
import ua.com.itinterview.entity.FeedbackEntity;
import ua.com.itinterview.web.command.FeedbackCommand;

public class FeedbackServiceUnitTest {

    private FeedbackDao feedbackDaoMock;

    private FeedbackService feedbackService;

    private FeedbackCommand command;
    private FeedbackEntity entity;

    public void createDaoMockForFeedbackService() {
	feedbackDaoMock = EasyMock.createMock(FeedbackDao.class);
	feedbackService = new FeedbackService();
	feedbackService.feedbackDao = feedbackDaoMock;
    }

    @Before
    public void initialFeedbackAndEntity() {
	command = addInformationToFeedBack(new FeedbackCommand());
	entity = addInformationToFeedbackEntity(new FeedbackEntity());

	createDaoMockForFeedbackService();

    }

    @Test
    public void testConvertFeedbackCommandToEntityFeedback() {

	FeedbackEntity actual = new FeedbackEntity(command);
	assertEquals(entity, actual);
    }

    @Test
    public void testConvertEntityToCommand() {

	FeedbackCommand actual = new FeedbackCommand(entity);
	assertEquals(command, actual);
    }

    @Test
    public void testAddFeedback() {

    }

    public FeedbackCommand addInformationToFeedBack(FeedbackCommand command) {
	command.setFeedbackText("some text");
	command.setCreateTime(new Date());
	command.setChecked(true);
	return command;
    }

    public FeedbackEntity addInformationToFeedbackEntity(FeedbackEntity entity) {
	entity.setFeedbackText("some text");
	entity.setCreateTime(new Date());
	entity.setChecked(true);
	return entity;
    }

}
