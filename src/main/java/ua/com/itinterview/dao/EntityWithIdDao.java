package ua.com.itinterview.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityNotFoundException;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
		T result = (T) sessionFactory.getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq(fieldName, parameter)).uniqueResult();
		if (result == null) {
			throw new EntityNotFoundException("Could not get entity "
					+ clazz.getSimpleName() + " for " + fieldName + "="
					+ parameter);
		}
		return result;
	}
}
