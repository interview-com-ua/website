package ua.com.itinterview.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.entity.PositionEntity;

public class PositionDao extends EntityWithIdDao<PositionEntity> {

    @Transactional
    @SuppressWarnings("unchecked")
    public List<PositionEntity> getAllPositions() {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(PositionEntity.class);
	return criteria.list();
    }
}
