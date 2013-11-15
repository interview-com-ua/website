package ua.com.itinterview.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.entity.FeedbackEntity;

public class FeedbackDao extends EntityWithIdDao<FeedbackEntity> {

    @Autowired
    protected SessionFactory sessionFactory;

    @Transactional
    public List<FeedbackEntity> getAllUncheckedFeedbacks() {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(FeedbackEntity.class);
	criteria.add(Restrictions.eq("checked", false));
	@SuppressWarnings("unchecked")
	List<FeedbackEntity> unchecked = criteria.list();
	return unchecked;
    }

    @Transactional
    public List<FeedbackEntity> getFeedbacksForPeriod(Date from, Date to) {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(FeedbackEntity.class);
	criteria.add(Restrictions.between("createTime", from, to));
	@SuppressWarnings("unchecked")
	List<FeedbackEntity> createTime = criteria.list();
	return createTime;

    }
}
