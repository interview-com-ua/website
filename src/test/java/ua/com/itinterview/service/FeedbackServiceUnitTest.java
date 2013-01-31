package ua.com.itinterview.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	createDaoMockForFeedbackService();
	command = addInformationToFeedBack(new FeedbackCommand());
	entity = addInformationToFeedbackEntity(new FeedbackEntity());

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

	FeedbackCommand feedbackCommand = new FeedbackCommand();
	FeedbackEntity feedbackEntity = new FeedbackEntity(feedbackCommand);
	EasyMock.expect(feedbackDaoMock.save(feedbackEntity)).andReturn(
		feedbackEntity);
	EasyMock.replay(feedbackDaoMock);
	feedbackService.addFeedback(feedbackCommand);
	EasyMock.verify(feedbackDaoMock);

    }

    @Test
    public void testGetFeedbackList() {
	FeedbackEntity fb1 = new FeedbackEntity();
	fb1.setChecked(true);
	fb1.setCreateTime(new Date());
	fb1.setFeedbackText("fb1 text");
	FeedbackEntity fb2 = new FeedbackEntity();
	fb2.setCreateTime(new Date());
	fb2.setChecked(true);
	fb2.setFeedbackText("fb2 text");

	List<FeedbackEntity> listFb = new ArrayList<FeedbackEntity>();
	listFb.add(fb1);
	listFb.add(fb2);

	EasyMock.expect(feedbackDaoMock.getAll()).andReturn(listFb);
	EasyMock.replay(feedbackDaoMock);

	List<FeedbackCommand> fbCommandList = feedbackService.getFeedbackList();
	FeedbackCommand fbCommand1 = new FeedbackCommand(fb1);
	FeedbackCommand fbCommand2 = new FeedbackCommand(fb2);

	assertEquals(fbCommandList.get(0), fbCommand1);
	assertEquals(fbCommandList.get(1), fbCommand2);
	EasyMock.verify();
    }

    public FeedbackCommand addInformationToFeedBack(FeedbackCommand command) {
	command.setFeedbackText("some text");
	command.setCreateTime(new Date(10000));
	command.setChecked(true);
	return command;
    }

    public FeedbackEntity addInformationToFeedbackEntity(FeedbackEntity entity) {
	entity.setFeedbackText("some text");
	entity.setCreateTime(new Date(10000));
	entity.setChecked(true);
	return entity;
    }

    public FeedbackEntity CreateFeedbackEntityForTest() {
	FeedbackEntity feedbackEntity = new FeedbackEntity();
	feedbackEntity.setFeedbackText("test");
	feedbackEntity.setCreateTime(new Date());
	feedbackEntity.setChecked(true);
	return feedbackEntity;

    }

}
