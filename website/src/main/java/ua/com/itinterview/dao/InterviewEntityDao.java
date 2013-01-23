package ua.com.itinterview.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.UserEntity;

import static org.hibernate.criterion.Restrictions.eq;

public class InterviewEntityDao extends EntityWithIdDao<InterviewEntity> {

    @SuppressWarnings("unchecked")
    @Transactional
    public List<InterviewEntity> getInterviewsByUser(UserEntity user) {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(InterviewEntity.class);
	criteria.add(eq("user", user));
	return criteria.list();
    }

}
