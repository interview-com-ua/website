package ua.com.itinterview.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.UserEntity;

import java.util.List;


public class InterviewEntityDao extends EntityWithIdDao<InterviewEntity> {

    @Transactional
    public List<InterviewEntity> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(InterviewEntity.class);
        return criteria.list();
    }

}
