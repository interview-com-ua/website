package ua.com.itinterview.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.com.itinterview.entity.ResetPasswordEntity;

/**
 * Created by Bohdan on 04/08/2014.
 */
public class ResetPasswordDao {

    @Autowired
    protected SessionFactory sessionFactory;

    @Transactional
    public ResetPasswordEntity save(ResetPasswordEntity entity) {
        Session session = sessionFactory.getCurrentSession();
        ResetPasswordEntity obj = (ResetPasswordEntity) session.merge(entity);
        return obj;
    }
}
