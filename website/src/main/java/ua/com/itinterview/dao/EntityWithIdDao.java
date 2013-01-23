package ua.com.itinterview.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.dao.paging.PagingFilter;
import ua.com.itinterview.entity.EntityWithId;

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
	return (T) sessionFactory.getCurrentSession().merge(entity);
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

    @SuppressWarnings("unchecked")
    protected List<T> getResultWithPaginator(Criteria criteria,
	    PagingFilter pagingFilter) {
	int firstPosition = pagingFilter.getItemsPerPage()
		* pagingFilter.getCurrentPage();
	return criteria.setFirstResult(firstPosition)
		.setMaxResults(pagingFilter.getItemsPerPage()).list();
    }
}
