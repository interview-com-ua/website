package ua.com.itinterview.dao;

import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.entity.UserEntity;

public class QuestionDao extends EntityWithIdDao<QuestionEntity> {

    @SuppressWarnings("unchecked")
    @Transactional
    public List<QuestionEntity> getQuestionsForUser(UserEntity user) {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(QuestionEntity.class);
	criteria.createAlias("interview", "i");
	criteria.add(eq("i.user", user));
	return criteria.list();

    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<QuestionEntity> getQuestionsForInterview(
	    InterviewEntity interview) {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(QuestionEntity.class);
	criteria.add(eq("interview", interview));
	return criteria.list();
    }

}
