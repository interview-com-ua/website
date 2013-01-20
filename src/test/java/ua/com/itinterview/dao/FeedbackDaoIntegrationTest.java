package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.entity.FeedbackEntity;

public class FeedbackDaoIntegrationTest extends
	BaseEntityWithIdDaoIntegrationTest {

    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    protected FeedbackEntity createEntity() {
	FeedbackEntity entity = new FeedbackEntity();
	entity.setFeedbackText("");
	return entity;
    }

    @Override
    protected EntityWithIdDao getEntityWithIdDao() {
	// TODO Auto-generated method stub
	return feedbackDao;
    }

    @Test
    public void testGetAllUncheckedFeedbacks() {
	FeedbackEntity entity = createEntity();
	Calendar cal = Calendar.getInstance();

	cal.set(2009, 11, 9);
	Timestamp dt = (Timestamp) cal.getTime();
	entity.setCreateTime(dt);
	entity.setChecked(true);
	entity = feedbackDao.save(entity);

	FeedbackEntity entity1 = createEntity();
	cal.set(2012, 10, 9);
	dt = (Timestamp) cal.getTime();
	entity.setCreateTime(dt);
	entity.setChecked(false);
	entity = feedbackDao.save(entity1);

	FeedbackEntity entity2 = createEntity();
	cal.set(2013, 01, 9);
	dt = (Timestamp) cal.getTime();
	entity.setCreateTime(dt);
	entity.setChecked(true);
	entity = feedbackDao.save(entity2);

	FeedbackEntity entity3 = createEntity();
	cal.set(2013, 01, 15);
	dt = (Timestamp) cal.getTime();
	entity.setCreateTime(dt);
	entity.setChecked(false);
	entity = feedbackDao.save(entity3);

	List<FeedbackEntity> list = feedbackDao.getAllUncheckedFeedbacks();
	assertEquals(2, list.size());
	assertEquals(entity1, list.get(0));
	assertEquals(entity3, list.get(1));

    }

    @Test
    public void testGetFeedbacksForPeriod() {
	FeedbackEntity entity = createEntity();
	Date dateTo, dateFrom;

	Calendar cal = Calendar.getInstance();

	cal.set(2009, 11, 9);
	Timestamp dt = (Timestamp) cal.getTime();
	entity.setCreateTime(dt);
	entity.setChecked(true);
	entity = feedbackDao.save(entity);

	FeedbackEntity entity1 = createEntity();
	cal.set(2012, 10, 9);
	dt = (Timestamp) cal.getTime();
	dateTo = cal.getTime();
	entity.setCreateTime(dt);
	entity.setChecked(false);
	entity = feedbackDao.save(entity1);

	FeedbackEntity entity2 = createEntity();
	cal.set(2013, 01, 9);
	dt = (Timestamp) cal.getTime();
	dateFrom = cal.getTime();
	entity.setCreateTime(dt);
	entity.setChecked(true);
	entity = feedbackDao.save(entity2);

	FeedbackEntity entity3 = createEntity();
	cal.set(2013, 01, 15);
	dt = (Timestamp) cal.getTime();
	entity.setCreateTime(dt);
	entity.setChecked(false);
	entity = feedbackDao.save(entity3);

	List<FeedbackEntity> list = feedbackDao.getFeedbacksForPeriod(dateTo,
		dateFrom);
	assertEquals(2, list.size());
	assertEquals(entity1, list.get(0));
	assertEquals(entity2, list.get(1));

    }

}
