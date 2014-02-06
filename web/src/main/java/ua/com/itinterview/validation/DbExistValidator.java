package ua.com.itinterview.validation;

import org.hibernate.validator.util.ReflectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.dao.EntityWithIdDao;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DbExistValidator implements ConstraintValidator<DbExist, Object> {
    @Autowired
    EntityWithIdDao entityWithIdDao;

    Class<?> clazz;
    String idField;

    @Override
    public void initialize(DbExist constraintAnnotation) {
        clazz = constraintAnnotation.dbEntity();
        idField = constraintAnnotation.idField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return false;
        entityWithIdDao.setClazz(clazz);
        Integer objectId = null;
        try {
            objectId = (Integer) getPropertyFromBean(value, idField);
        } catch (IllegalAccessException e) {
            return false;
        }
        if (objectId == null) return false;

        try {
            entityWithIdDao.getEntityById(objectId);
        } catch (EntityNotFoundException e) {
            return false;
        }
        return true;
    }

    private Object getPropertyFromBean(Object bean, String propertyName)
            throws IllegalArgumentException, IllegalAccessException {
        return ReflectionHelper.getDeclaredField(bean.getClass(), propertyName)
                .get(bean);
    }
}
