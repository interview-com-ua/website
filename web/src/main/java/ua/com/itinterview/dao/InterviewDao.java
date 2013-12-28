package ua.com.itinterview.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import ua.com.itinterview.dao.paging.PagingFilter;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.UserEntity;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

@Transactional
public class InterviewDao extends EntityWithIdDao<InterviewEntity> {

    @SuppressWarnings("unchecked")
    public List<InterviewEntity> getInterviewsByUser(UserEntity user, PagingFilter filter) {
        Criteria criteria = createCriteria(user);
        if (filter == null) return criteria.list();
        return getResultWithPaginator(criteria, filter);
    }

    public Long getInterviewsCountByUser(UserEntity user) {
        return getCount(createCriteria(user));
    }

    public List<InterviewEntity> getInterviewsByUser(UserEntity user) {
        return getInterviewsByUser(user, null);
    }

    private Criteria createCriteria(UserEntity user) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(InterviewEntity.class);
        criteria.add(eq("user", user));
        return criteria;
    }
}
