package ua.com.itinterview.dao;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.entity.CityEntity;

public class CityDaoIntegrationTest extends
	BaseEntityWithIdDaoIntegrationTest<CityEntity> {

    @Autowired
    private CityDao cityDao;

    @Override
    protected CityEntity createEntity() {
	CityEntity city = new CityEntity();
	city.setCityName("Kyiv");
	return city;
    }

    @Override
    protected EntityWithIdDao<CityEntity> getEntityWithIdDao() {
	return cityDao;
    }

}
