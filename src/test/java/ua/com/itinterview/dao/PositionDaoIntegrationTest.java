package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.entity.PositionEntity;

public class PositionDaoIntegrationTest extends
	BaseEntityWithIdDaoIntegrationTest<PositionEntity> {

    @Autowired
    private PositionDao positionDao;

    @Override
    protected PositionEntity createEntity() {
	PositionEntity entity = new PositionEntity();
	return entity;
    }

    @Override
    protected PositionDao getEntityWithIdDao() {
	return positionDao;
    }

    @Test
    public void testgetAllPositions() {
	PositionEntity position = new PositionEntity();
	position.setPositionGroup("java");
	position.setPositionName("junior");
	positionDao.save(position);
	List<PositionEntity> list = positionDao.getAllPositions();
	assertEquals(1, list.size());
	assertEquals("java", list.get(0).getPositionGroup());
	assertEquals("junior", list.get(0).getPositionName());
    }

    @Test
    public void testgetAllPositionsWithoutPositions() {
	List<PositionEntity> list = positionDao.getAllPositions();
	assertEquals(0, list.size());
    }
}
