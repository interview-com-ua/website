package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

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
    public void testGetCount() {
        cleanUpBeforeTest();
        createEntities();
        assertEquals(2L, getEntityWithIdDao().getCount().longValue());
    }

    @Test
    public void testGetAllWithLimit() {
	cleanUpBeforeTest();

        List<T> entityList = createEntities();

	List<T> entityGetWithoutLimit = getEntityWithIdDao().getAll();
	assertEquals(entityList.size(), entityGetWithoutLimit.size());

	List<T> entityGetWithLimit = getEntityWithIdDao().getAll(
		entityList.size() - 1);
	assertEquals(entityList.size() - 1, entityGetWithLimit.size());
    }

    @Test
    public void testGetEntityByIdDao() {

	T entityExpected = createEntity();
	entityExpected = getEntityWithIdDao().save(entityExpected);
	assertEquals(entityExpected,
		getEntityWithIdDao().getEntityById(entityExpected.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetEntityByIdWenDoesNotExist() {
	getEntityWithIdDao().getEntityById(-1);
    }

    @Test
    public void testGetAllEmptyListWithLimit() {
	cleanUpBeforeTest();

	List<T> entityGetWithoutLimit = getEntityWithIdDao().getAll();
	assertEquals(0, entityGetWithoutLimit.size());

	List<T> entityGetWithLimit = getEntityWithIdDao().getAll(1);
	assertEquals(0, entityGetWithLimit.size());
    }

    private void cleanUpBeforeTest() {
	Class<?> clazz;
	Type t = getClass().getGenericSuperclass();
	if (t instanceof ParameterizedType) {
	    ParameterizedType paramType = (ParameterizedType) t;
	    clazz = (Class<?>) paramType.getActualTypeArguments()[0];
	} else {
	    fail("Can't get generic type.");
	    return;
	}

	cleanUpEntity(clazz);
    }

    private List<T> createEntities() {
        List<T> entityList = createEntityList();
        if (entityList.size() < 2) {
            fail("Entity list should contain two or more elements");
        }
        for (T entity : entityList) {
            getEntityWithIdDao().save(entity);
        }
        return entityList;
    }

    protected List<T> createEntityList() {
	List<T> entityList = new ArrayList<T>();
	entityList.add(createEntity());
	entityList.add(createEntity());
	return entityList;
    }

    protected abstract T createEntity();

    protected abstract EntityWithIdDao<T> getEntityWithIdDao();

}
