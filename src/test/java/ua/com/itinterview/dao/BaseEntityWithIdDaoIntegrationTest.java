package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ua.com.itinterview.entity.EntityWithId;

public abstract class BaseEntityWithIdDaoIntegrationTest<T extends EntityWithId> extends
		BaseDaoIntegrationTest {

	@Test
	public void testSaveEntity() {
		T entity = createEntity();
		T savedEntity = getEntityWithIdDao().save(entity);
		assertEquals(entity, savedEntity);
		assertTrue(savedEntity.getId() != 0);
	}

	protected abstract T createEntity();

	protected abstract EntityWithIdDao<T> getEntityWithIdDao();

	@Test
	 public void plus() {
	  int a = 10;
	  assertEquals(a, plus1(2,8));
	 }
	 
	 public int plus1(int a, int b) {
	  return a+b;
	 }
}

