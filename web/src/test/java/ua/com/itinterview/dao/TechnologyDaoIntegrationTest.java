package ua.com.itinterview.dao;

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

}
