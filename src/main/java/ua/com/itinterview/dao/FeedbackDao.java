package ua.com.itinterview.dao;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.entity.FeedbackEntity;

public class FeedbackDao extends EntityWithIdDao<FeedbackEntity> {

    @Transactional
    public List<FeedbackEntity> getAllUncheckedFeedbacks() {
	return null;
    }

    @Transactional
    public List<FeedbackEntity> getFeedbacksForPeriod(Date from, Date to) {
	return null;
    }
}
