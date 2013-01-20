package ua.com.itinterview.dao;

import java.util.Date;
import java.util.List;

import ua.com.itinterview.entity.FeedbackEntity;

public class FeedbackDao extends EntityWithIdDao<FeedbackEntity> {

    public FeedbackEntity getAllUncheckedFeedbacks() {
	return null;
    }

    public List<FeedbackEntity> getFeedbacksForPeriod(Date from, Date to) {
	return null;
    }
}
