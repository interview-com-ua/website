package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ua.com.itinterview.entity.EntityWithId;

public abstract class BaseEntityWithIdDaoIntegrationTest<T extends EntityWithId>
	extends BaseDaoIntegrationTest {
    
    @Test
    public void testSaveEntity() {
	T entity = createEntity();
	T savedEntity = getEntityWithIdDao().save(entity);
	assertEquals(entity, savedEntity);
	assertTrue(savedEntity.getId() != 0);
    }
    
    @Test
    public void testGetAllWithLimit() {
	cleanUpBeforeTest();
	T entity1 = createEntity();
	T entity2 = createEntity();
	getEntityWithIdDao().save(entity1);
	getEntityWithIdDao().save(entity2);
	
	List<T> entityGetWithoutLimit = getEntityWithIdDao().getAll();
	assertEquals(2, entityGetWithoutLimit.size());
	
	List<T> entityGetWithLimit = getEntityWithIdDao().getAll(1);
	assertEquals(1, entityGetWithLimit.size());
    }
    
    @Test
    public void testGetAllEmptyListWithLimit() {
	cleanUpBeforeTest();
	
	List<T> entityGetWithoutLimit = getEntityWithIdDao().getAll();
	assertEquals(0, entityGetWithoutLimit.size());
	
	List<T> entityGetWithLimit = getEntityWithIdDao().getAll(1);
	assertEquals(0, entityGetWithLimit.size());
    }
    
    private void cleanUpBeforeTest() 
    {
	Class<?> clazz;
	Type t = getClass().getGenericSuperclass();
	if (t instanceof ParameterizedType) {
	    ParameterizedType paramType = (ParameterizedType) t;
	    clazz = (Class<?>) paramType.getActualTypeArguments()[0];
	} else {
	    Assert.fail("Can't get generic type.");
	    return;
	}
	
	cleanUpEntity(clazz);
    }

    protected abstract T createEntity();

    protected abstract EntityWithIdDao<T> getEntityWithIdDao();

}
