package ua.com.itinterview.dao;

import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.dao.paging.PagingFilter;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.UserEntity;

public class InterviewDao extends EntityWithIdDao<InterviewEntity> {

    @SuppressWarnings("unchecked")
    @Transactional
    public List<InterviewEntity> getInterviewsByUser(UserEntity user, PagingFilter filter) {
        Criteria criteria = createCriteria(user);
        if (filter == null) return criteria.list();
        return getResultWithPaginator(criteria, filter);
    }

    @Transactional
    public Long getInterviewsCountByUser(UserEntity user) {
        return getCount(createCriteria(user));
    }

    @Transactional
    public List<InterviewEntity> getInterviewsByUser(UserEntity user) {
        return getInterviewsByUser(user, null);
    }

    @Transactional
    public InterviewEntity getInterviewById(Integer interviewId) {
        return getEntityById(interviewId);
    }

    @Transactional
    private Criteria createCriteria(UserEntity user) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(InterviewEntity.class);
        criteria.add(eq("user", user));
        return criteria;
    }
}
