package ua.com.itinterview.dao;

import static org.hibernate.criterion.Restrictions.eq;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class QuestionDao extends EntityWithIdDao<QuestionEntity> {

//    @Autowired
//    protected SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Transactional
    public List<QuestionEntity> getQuestionsForUser(UserEntity user) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(QuestionEntity.class);
        criteria.add(eq("user", user));
        return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    @Transactional
    public List<QuestionEntity> getQuestionsForInterview(InterviewEntity interview) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(QuestionEntity.class);
        criteria.add(eq("interview", interview));
        return criteria.list();
    }
    
    
//    @SuppressWarnings("unchecked")
//    @Transactional
//    public List<InterviewEntity> getInterviewsByUser(UserEntity user) {
//	Session session = sessionFactory.getCurrentSession();
//	Criteria criteria = session.createCriteria(InterviewEntity.class);
//	criteria.add(eq("user", user));
//	return criteria.list();
//    }
    
//    getQuestionsForInterview(InterviewEntity interview) 
//    getQuestionsForUser(UserEntity user)

}
