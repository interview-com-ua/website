package ua.com.itinterview.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class QuestionDao extends EntityWithIdDao<QuestionEntity> {

    @Autowired
    protected SessionFactory sessionFactory;

    @Transactional
    public List<QuestionEntity> getQuestionsForUser(UserEntity user) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(QuestionEntity.class);
        criteria.add(Restrictions.eq("userId", new Integer(user.getId())));
        List<QuestionEntity> questions = criteria.list();
        return questions;
    }

}
