package ua.com.itinterview.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.com.itinterview.dao.paging.PagingFilter;
import ua.com.itinterview.entity.EntityWithId;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class EntityWithIdDao<T extends EntityWithId> {

    @Autowired
    protected SessionFactory sessionFactory;

    private Class<?> clazz;

    public EntityWithIdDao() {
	Type t = getClass().getGenericSuperclass();
	if (t instanceof ParameterizedType) {
	    ParameterizedType paramType = (ParameterizedType) t;
	    clazz = (Class<?>) paramType.getActualTypeArguments()[0];
	}
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public T save(T entity) {
        Session session=sessionFactory.getCurrentSession();
        T obj = (T) session.merge(entity);
        return obj;
    }
    @Transactional
    @SuppressWarnings("unchecked")
    public T getOneResultByParameter(String fieldName, Object parameter) {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(clazz);
	criteria.add(Restrictions.eq(fieldName, parameter));
	T result = (T) criteria.uniqueResult();
	if (result == null) {
	    throw new EntityNotFoundException("Could not get entity "
		    + clazz.getSimpleName() + " for " + fieldName + "="
		    + parameter);
	}
	return result;
    }

    @Transactional
    public T getEntityById(Integer id) {
	return getOneResultByParameter("id", id);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    protected List<T> getResultWithPaginator(Criteria criteria,
	    PagingFilter pagingFilter) {
	int firstPosition = pagingFilter.getItemsPerPage()
		* pagingFilter.getCurrentPage();
	return criteria.setFirstResult(firstPosition)
		.setMaxResults(pagingFilter.getItemsPerPage()).list();
    }

    @Transactional
    public Long getCount() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(clazz);
        return getCount(criteria);
    }

    @Transactional
    public Long getCount(Criteria criteria) {
        return (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Transactional
    public List<T> getAll() {
	return getAll(0);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<T> getAll(int limit) {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(clazz).setMaxResults(limit);
	return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<T> getAllOrderedBy(String orderBy, int limit) {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(clazz)
		.addOrder(Order.asc(orderBy)).setMaxResults(limit);
	return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<T> getAllOrderedBy(String orderBy, String order, int limit) {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session
		.createCriteria(clazz)
		.addOrder(
			order.equals("asc") ? Order.asc(orderBy) : Order
				.desc(orderBy)).setMaxResults(limit);
	return criteria.list();
    }

    @Transactional
    public int deleteAll() {
        Session session = sessionFactory.getCurrentSession();
        String hql = String.format("delete from %s", clazz.getSimpleName());
        Query query = session.createQuery(hql);
        return query.executeUpdate();
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
