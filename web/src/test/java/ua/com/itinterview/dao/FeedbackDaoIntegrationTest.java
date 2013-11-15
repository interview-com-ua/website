package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.entity.FeedbackEntity;

public class FeedbackDaoIntegrationTest extends
	BaseEntityWithIdDaoIntegrationTest<FeedbackEntity> {

    private static final String DEFAULT_DATE_FORMATTER = "yyyy-MM-dd";

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    InterviewDao interviewEntityDao;

    @Override
    protected FeedbackEntity createEntity() {
	FeedbackEntity feedback = new FeedbackEntity();
	return feedback;
    }

    @Override
    protected EntityWithIdDao<FeedbackEntity> getEntityWithIdDao() {
	return feedbackDao;
    }

    private Date createDateFromString(String dateString) throws ParseException {
	DateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMATTER);
	return formatter.parse(dateString);
    }

    private FeedbackEntity createFeedbackWithCheckedAndFeedbackText(
	    boolean checked, String feedbackText) throws ParseException {
	return createFeedbackWithCheckedAndFeedbackTextAndCreateTimee(checked,
		feedbackText, null);
    }

    private FeedbackEntity createFeedbackWithCheckedAndFeedbackTextAndCreateTimee(
	    boolean checked, String feedbackText, String createTimeString)
	    throws ParseException {
	FeedbackEntity entity = new FeedbackEntity();
	entity.setChecked(checked);
	entity.setFeedbackText(feedbackText);
	Date createTime = null;
	if (createTimeString != null) {
	    createTime = createDateFromString(createTimeString);
	}
	entity.setCreateTime(createTime);
	return feedbackDao.save(entity);
    }

    @Test
    public void testGetAllUncheckedFeedbacks() throws ParseException {
	createFeedbackWithCheckedAndFeedbackText(true, "entity");
	FeedbackEntity entity1 = createFeedbackWithCheckedAndFeedbackText(
		false, "entity1");
	createFeedbackWithCheckedAndFeedbackText(true, "entity2");
	FeedbackEntity entity3 = createFeedbackWithCheckedAndFeedbackText(
		false, "entity4");

	List<FeedbackEntity> list = feedbackDao.getAllUncheckedFeedbacks();
	assertEquals(2, list.size());

	assertEquals(entity1, list.get(0));
	assertEquals(entity3, list.get(1));
    }

    @Test
    public void testGetFeedbacksForPeriod() throws ParseException {
	createFeedbackWithCheckedAndFeedbackTextAndCreateTimee(true, "entity",
		"2012-12-07");
	createFeedbackWithCheckedAndFeedbackTextAndCreateTimee(false, "entity",
		"2012-12-20");
	FeedbackEntity entity2 = createFeedbackWithCheckedAndFeedbackTextAndCreateTimee(
		true, "entity", "2012-12-25");
	createFeedbackWithCheckedAndFeedbackTextAndCreateTimee(false, "entity",
		"2013-01-20");

	Date dateFrom = createDateFromString("2012-12-24");
	Date dateTo = createDateFromString("2012-12-26");
	List<FeedbackEntity> actualResult = feedbackDao.getFeedbacksForPeriod(
		dateFrom, dateTo);
	assertEquals(1, actualResult.size());
	assertEquals(entity2, actualResult.get(0));

    }
}
