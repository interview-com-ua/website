package ua.com.itinterview.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.UserEntity;

public class InterviewEntityDao extends EntityWithIdDao<InterviewEntity> {

    @Transactional
    public List<InterviewEntity> getInterviewsByUser(UserEntity user) {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(InterviewEntity.class);
	criteria.add(Restrictions.eq("userId", user.getId()));
	return criteria.list();
    }

}
