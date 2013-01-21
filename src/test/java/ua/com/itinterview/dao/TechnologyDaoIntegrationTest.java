package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.entity.TechnologyEntity;

public class TechnologyDaoIntegrationTest extends
	BaseEntityWithIdDaoIntegrationTest<TechnologyEntity> {

    @Autowired
    private TechnologyDao technologyDao;

    @Override
    protected TechnologyEntity createEntity() {
	TechnologyEntity entity = new TechnologyEntity();
	entity.setTechnologyName("Java");
	return entity;
    }

    @Override
    protected TechnologyDao getEntityWithIdDao() {
	return technologyDao;
    }

    public void testGetAllTechnologies() {
	TechnologyEntity technology = new TechnologyEntity();
	technology.setTechnologyName("Java");
	technologyDao.save(technology);
	List<TechnologyEntity> technologies = technologyDao
		.getAllTechnologies();
	assertEquals(1, technologies.size());
	assertEquals("Java", technologies.get(0).getTechnologyName());
    }

}
