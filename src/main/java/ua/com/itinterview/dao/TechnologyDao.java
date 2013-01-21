package ua.com.itinterview.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.entity.TechnologyEntity;

public class TechnologyDao extends EntityWithIdDao<TechnologyEntity> {
    @Transactional
    public List<TechnologyEntity> getAllTechnologies() {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(TechnologyEntity.class);
	return criteria.list();
    }

}
